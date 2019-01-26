package dendron.tree;

import dendron.machine.Machine;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Liang,Yu(yxl5521)
 */
public class Variable implements ExpressionNode{
    private String name;
    private List<Machine.Instruction> list;

    /**
     * Set the name of this new Variable.
     * @param name the name of this variable
     */
    public Variable(String name){
        this.name=name;
        this.list=new LinkedList<>();

    }

    /**
     * Print on standard output the Variable's name
     */
    public void infixDisplay(){
        System.out.print(name);
    }


    /**
     * Evaluate a variable by fetching its values.
     * @param symTab symbol table, if needed, to fetch variable values
     * @return this variable's current value in the symbol table
     */
    public int evaluate(java.util.Map<String,Integer> symTab){

        return symTab.get(name);

    }

    /**
     * Emit a LOAD instruction that pushes the Variable's value onto the stack.
     * @return a list containing a single LOAD instruction
     */
    public java.util.List<Machine.Instruction> emit(){
        Machine.Load load = new Machine.Load(name);
        list.add(load);
        return list;
    }
}
