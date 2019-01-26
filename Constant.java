package dendron.tree;

import dendron.machine.Machine;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Liang,Yu(yxl5521)
 */
public class Constant implements ExpressionNode {

    private int value;
    private List<Machine.Instruction> list;
    /**
     * Store the integer value in this new Constant
     * @param value the integer it will hold
     */
    public Constant(int value){
        this.value=value;
        this.list=new LinkedList<>();

    }

    /**
     * Print this Constant's value on standard output
     */
    public void infixDisplay(){
        System.out.print(value);
    }

    /**
     * Evaluate the constant
     * @param symTab symbol table, if needed, to fetch variable values
     * @return this Constant's value
     */
    public int evaluate(java.util.Map<String,Integer> symTab){

        return value;
    }

    /**
     * Emit an instruction to push the value onto the stack
     * @return a list containing that one instruction
     */
    public java.util.List<Machine.Instruction> emit(){
        Machine.PushConst push=new Machine.PushConst(value);
        list.add(push);
        return list;
    }

}
