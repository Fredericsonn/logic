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

    @Override
    public void computeTreeValue() {
        for (AstNode child : this.getChildren())
            child.computeTreeValue();

        if (this.operand == Operand.NEGATION) {
            AstNode arg = this.getChildren().get(0);
            this.setValue(Evaluation.evaluate(arg.getValue(), null, this.getOperand()));
        } else {
            AstNode a = this.getChildren().get(0);
            AstNode b = this.getChildren().get(1);
            this.setValue(Evaluation.evaluate(a.getValue(), b.getValue(), this.getOperand()));
        }
    }

}
