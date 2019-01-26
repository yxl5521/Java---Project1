package dendron.tree;

import dendron.machine.Machine;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Liang,Yu(yxl5521)
 */
public class Print implements ActionNode{


    private ExpressionNode printee;
    private List<Machine.Instruction> list;

    /**
     * Set up a Print node.
     * @param printee the expression to be evaluated and printed
     */
    public Print(ExpressionNode printee){
        this.printee=printee;
        this.list=new LinkedList<>();

    }

    /**
     * Evaluate the expression and display the result on the console.
     * Precede it with three equal sign so it stands  out a little.
     * @param symTab the table where variable values are stored
     */
    public void execute(java.util.Map<String, Integer> symTab){
        System.out.println("=== "+printee.evaluate(symTab));
    }

    /**
     * Show this statement on standard output as the word "Print" followed by the infix form of the expression
     */
    public void infixDisplay(){
        System.out.print("Print ");
        printee.infixDisplay();
    }

    /**
     * This method returns the code emitted by the printee node that pushes the value of the printee expression onto the stack, followed by a PRINT instruction
     * @return a list of instructions ending in the ones that compute the value to be printes, and print it.
     */
    public java.util.List<Machine.Instruction> emit(){
        list.addAll(printee.emit());
        Machine.Print pr = new Machine.Print();
        list.add(pr);
        return list;
    }
}
