package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;
import dendron.tree.ActionNode;
import dendron.tree.ExpressionNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Operations that are done on a Dendron code parse tree.
 *
 * THIS CLASS IS UNIMPLEMENTED. All methods are stubbed out.
 *
 * @author Liang,Yu(yxl5521)
 */
public class ParseTree {
    private Program pro;
    private HashMap<String,Integer> map;

    /**
     * Parse the entire list of program tokens. The program is a
     * sequence of actions (statements), each of which modifies something
     * in the program's set of variables. The resulting parse tree is
     * stored internally.
     * @param program the token list (Strings)
     */
    public ParseTree( List< String > program ) {
        this.pro = new Program();
        this.map=new HashMap<>();
        while (program.size()!=0) {
            pro.addAction(parseAction(program));
        }
    }

    /**
     * Parse the next action (statement) in the list.
     * (This method is not required, just suggested.)
     * @param program the list of tokens
     * @return a parse tree for the action
     */
    private ActionNode parseAction( List< String > program ) {
        String s = program.remove(0);
        if (s.equals(":=")){
            if (program.size()==0){
                Errors.report(Errors.Type.PREMATURE_END, ":=");
            }

            return new Assignment(program.remove(0),parseExpr(program));
        }
        else if (s.equals("@")){
            if (program.size()==0){
                Errors.report(Errors.Type.PREMATURE_END, "@");
            }
            return new Print(parseExpr(program));
        }
        else {
            Errors.report(Errors.Type.ILLEGAL_VALUE,s);
            return null;
        }
    }

    /**
     * Parse the next expression in the list.
     * (This method is not required, just suggested.)
     * @param program the list of tokens
     * @return a parse tree for this expression
     */
    private ExpressionNode parseExpr( List< String > program ) {
        if (program.get(0).equals("_") || program.get(0).equals("#")){
            if (program.size()==1){
                Errors.report(Errors.Type.ILLEGAL_VALUE, program.get(0));
            }
            return new UnaryOperation(program.remove(0),parseExpr(program));
        }
        else if(program.get(0).equals("+") || program.get(0).equals("-") || program.get(0).equals("*") || program.get(0).equals("/")) {
            if (program.size()==2){
                Errors.report(Errors.Type.ILLEGAL_VALUE, program.get(0));
            }
            return new BinaryOperation(program.remove(0),parseExpr(program),parseExpr(program));
        }
        else if (program.get(0).matches("^[a-zA-Z].*")){
            return new Variable(program.remove(0));
        }
        else if (program.get(0).matches("[-+]?\\d+")){
            return new Constant(Integer.parseInt(program.remove(0)));
        }
        else {
            Errors.report(Errors.Type.ILLEGAL_VALUE,program.get(0));
            return null;
        }
    }

    /**
     * Print the program the tree represents in a more typical
     * infix style, and with one statement per line.
     * @see dendron.tree.ActionNode#infixDisplay()
     */
    public void displayProgram() {
        System.out.println("The Program, with expressions in infix notation:");
        System.out.println();
        pro.infixDisplay();
    }

    /**
     * Run the program represented by the tree directly
     * @see dendron.tree.ActionNode#execute(Map)
     */
    public void interpret() {
        System.out.println("Interpreting the parse tree...");
        pro.execute(map);
        System.out.println("Interpretation complete.");
        System.out.println();
        Errors.dump(map);

    }

    /**
     * Build the list of machine instructions for
     * the program represented by the tree.
     * @return the Machine.Instruction list
     * @see Machine.Instruction#execute()
     */
    public List< Machine.Instruction > compile() {
        return pro.emit();
    }

}
