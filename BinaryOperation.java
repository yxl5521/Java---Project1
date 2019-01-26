package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Liang,Yu(yxl5521)
 */
public class BinaryOperation implements ExpressionNode {

    public static final String ADD="+";
    public static final String SUB="-";
    public static final String MUL="*";
    public static final String DIV="/";
    public static final java.util.Collection<String> OPERATORS= new ArrayList<>();
    private String operator;
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;
    private List<Machine.Instruction> list;


    /**
     * Create a new BinaryOperation node
     * @param operator the string rep. of the operation
     * @param leftChild the left operand
     * @param rightChild the right operand
     */
    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild){
        OPERATORS.add(ADD);
        OPERATORS.add(SUB);
        OPERATORS.add(MUL);
        OPERATORS.add(DIV);
        this.list = new LinkedList<>();
        if (OPERATORS.contains(operator) && leftChild!=null && rightChild!=null){
            this.operator=operator;
            this.leftChild=leftChild;
            this.rightChild=rightChild;
        }
        else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, operator);
        }
    }

    /**
     * Computer the result of evaluation bothe operands and applying the operator to them
     * @param symTab symbol table, if needed, to fetch variable values
     * @return the result of the computation
     */
    public int evaluate(java.util.Map<String,Integer> symTab){
        int num1=leftChild.evaluate(symTab);
        int num2=rightChild.evaluate(symTab);
        if (operator.equals(ADD)){
            return num1+num2;
        }
        if (operator.equals(SUB)){
            return num1-num2;
        }
        if (operator.equals(MUL)){
            return num1*num2;
        }
        if (operator.equals(DIV)){
            if (num2==0){
                Errors.report(Errors.Type.DIVIDE_BY_ZERO, "0");
            }
            return num1/num2;
        }
        else {
            return 0;
        }
    }

    /**
     * Print, on standard output, the infixDisplay of the two child nodes separated by the operator and surrounded by parentheses.
     * Blanks are inserted throughout.
     */
    public void infixDisplay(){
        System.out.print("(");
        leftChild.infixDisplay();
        if (operator.equals(ADD)){
            System.out.print("+");
        }
        if (operator.equals(SUB)){
            System.out.print("-");
        }
        if (operator.equals(MUL)){
            System.out.print("*");
        }
        if (operator.equals(DIV)){
            System.out.print("/");
        }
        rightChild.infixDisplay();
        System.out.print(")");

    }

    /**
     * Emit the Machine instructions necessary to perform the computation of this UnaryOperation.
     * The operator itself is realized by an instruction that pops a value off the stack, applies the operator, and pushes the answer.
     * @return a list containing instructions for the left operand, instructions for the right operand, and the instruction to perform the operation
     */
    public java.util.List<Machine.Instruction> emit(){
        list.addAll(leftChild.emit());
        list.addAll(rightChild.emit());
        if (operator.equals(ADD)){
            Machine.Add add= new Machine.Add();
            list.add(add);
        }
        else if (operator.equals(SUB)){
            Machine.Subtract sub = new Machine.Subtract();
            list.add(sub);
        }
        else if (operator.equals(MUL)){
            Machine.Multiply mul= new Machine.Multiply();
            list.add(mul);
        }
        else if (operator.equals(DIV)){
            Machine.Divide div = new Machine.Divide();
            list.add(div);
        }
        return list;
    }
}
