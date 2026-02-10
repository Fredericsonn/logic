package upec.episen;

import java.util.ArrayList;
import java.util.List;

public class AstNode {

    String label;
    List<AstNode> children = new ArrayList<>();

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

    public boolean hasChildren() {
        return this.children.size() > 0;
    }
    
}
