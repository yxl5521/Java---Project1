package dendron.tree;

import dendron.machine.Machine;
import jdk.jfr.Experimental;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Liang,Yu(yxl5521)
 */

public class Assignment implements ActionNode{

    private String ident;
    private ExpressionNode rhs;
    private List<Machine.Instruction> list;

    /**
     * Set up an Assignment node. Note that the identifier is not turned into a Variable node.
     * The reason is that the variable's value is not needed; instead it is a destination for a computation.
     * This use is not compatible with Variable's mission.
     * @param ident the name of the variable that is getting a new value
     * @param rhs the expression on the "right-hand side" of the assignment statement
     */
    public Assignment(String ident, ExpressionNode rhs){
        this.ident=ident;
        this.rhs=rhs;
        this.list=new LinkedList<>();

    }

    /**
     * Evaluate the RHS expression and assign the result value to the variable
     * @param symTab the table where variable values are stored
     */
    public void execute(java.util.Map<String,Integer> symTab){
        int i=rhs.evaluate(symTab);
        symTab.put(ident,i);
    }

    /**
     * Show this assignment on standard output as a variable followed by an assignment arrow (":=") followed by the infix form of the RHS expression.
     */
    public void infixDisplay(){
        System.out.print(ident);
        System.out.print(":=");
        this.rhs.infixDisplay();
        System.out.print(" ");
    }

    /**
     * This method returns a STORE instruction for the variable in question preceded by the code emitted by the RHS node that eventually pushes the value of the expression onto the stack.
     * @return a list of instructions ending in one that stores the top value on the stack to this node's variable
     */
    public java.util.List<Machine.Instruction> emit(){
        list.addAll(rhs.emit());
        Machine.Store st = new Machine.Store(ident);
        list.add(st);

        return list;
    }
}
