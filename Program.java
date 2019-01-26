package dendron.tree;

import dendron.machine.Machine;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Liang,Yu(yxl5521)
 */
public class Program implements ActionNode {

    private List<ActionNode> list;
    private List<Machine.Instruction> link;


    /**
     * Initialize this as an empty squence of ActionNode children
     */
    public Program(){
        this.list=new LinkedList<>();
        this.link=new LinkedList<>();

    }

    /**
     * Add a child of this Program node.
     * @param newNode the node representing the action that will execute last
     */
    public void addAction(ActionNode newNode){
        this.list.add(newNode);
    }

    /**
     * Execute each ActionNode in this object, from first-added to last-added.
     * @param symTab the table where variable values are stored
     */
    public void execute(java.util.Map<String, Integer> symTab){
        for (ActionNode a: list){
            a.execute(symTab);
        }
    }

    /**
     * Show the infix displays of all children on standard output.
     * The order is first-added to last-added.
     */
    public void infixDisplay(){
        for (ActionNode a:list){
            a.infixDisplay();
            System.out.println();
        }
    }

    /**
     * Create a list of instructions emitted by each child, from the first-added child to the last-added.
     * @return a list of Machine Instructors
     */
    public java.util.List<Machine.Instruction> emit(){
        for (ActionNode l:list){
            link.addAll(l.emit());
        }
        return link;
    }
}
