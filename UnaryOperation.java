package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Liang,Yu(yxl5521)
 */
public class UnaryOperation implements ExpressionNode{
    public static final String NEG="_";
    public static final String SQRT="#";
    public static final java.util.Collection<String> OPERATORS = new ArrayList<>();
    private String operator;
    private ExpressionNode expr;
    private List<Machine.Instruction> list;


    /**
     * Create a new UnaryOperation node.
     * @param operator the string rep. of the operation
     * @param expr the operand
     */
    public UnaryOperation(String operator, ExpressionNode expr){
        OPERATORS.add(NEG);
        OPERATORS.add(SQRT);
        this.list=new LinkedList<>();
        if (OPERATORS.contains(operator) &&  expr!=null){
            this.operator=operator;
            this.expr=expr;
        }
        else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, expr);

        }
    }

    /**
     * Compute the result of evaluating the expression and applying the operator to it.
     * @param symTab symbol table, if needed, to fetch variable values
     * @return the result of the computation
     */
    public int evaluate(java.util.Map<String,Integer> symTab){
        int num= expr.evaluate(symTab);

        if (operator.equals(NEG)){
            return -num;
        }
        else if (operator.equals(SQRT)){
            return (int)Math.sqrt(num);
        }
        return num;
    }

    /**
     * Print, on standard output, the infixDisplay of the child nodes preceded by the operator and without an intervening blank.
     */
    public void infixDisplay(){
        if (operator.equals(NEG)){
            System.out.print("_");
            expr.infixDisplay();
        }
        if (operator.equals(SQRT)){
            System.out.print("#");
            expr.infixDisplay();
        }
    }

    /**
     * Emit the Machine instructions necessary to perform the computation of this UnaryOperation.
     * The operator itself is realized by an instruction that pops a value off the stack, applies the operator, and pushes the answer.
     * @return a list containing instructions for the expression and the instruction to perform the operation.
     */
    public java.util.List<Machine.Instruction> emit(){
        list.addAll(expr.emit());
        if (operator.equals(NEG)) {
            Machine.Negate neg = new Machine.Negate();
            list.add(neg);
        }
        else if (operator.equals(SQRT)){
            Machine.SquareRoot sqrt = new Machine.SquareRoot();
            list.add(sqrt);
        }
        return list;
    }
}
