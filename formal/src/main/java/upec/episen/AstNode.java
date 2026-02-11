package upec.episen;

import java.util.ArrayList;
import java.util.List;

public class AstNode {

    private String label;
    private List<AstNode> children = new ArrayList<>();
    private Integer[] value;

    public AstNode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public List<AstNode> getChildren() {
        return children;
    }
    public void setChildren(List<AstNode> children) {
        this.children = children;
    }
    public void setValue(Integer[] value) {
        this.value = value;
    }
    public Integer[] getValue() {
        return this.value;
    }
    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public static void increaseDepth() {
        Evaluation.depth = Evaluation.depth + 1;
    }

    public void computeTreeValue() {
        if (this.value == null) {
            this.setValue(Evaluation.evaluateAtomicPreposition(label));
        }
    }
    
}
