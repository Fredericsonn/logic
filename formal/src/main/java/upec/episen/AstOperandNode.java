package upec.episen;

public class AstOperandNode extends AstNode {

    Operand operand;

    public AstOperandNode(String label, Operand operand) {
        super(label);
        this.operand = operand;
    }

    public Operand getOperand() {
        return operand;
    }
    
}
