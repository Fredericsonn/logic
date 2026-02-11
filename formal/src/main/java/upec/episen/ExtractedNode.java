package upec.episen;

import java.util.List;

public class ExtractedNode {
    private final List<String> children;
    private final String operator;

    public ExtractedNode(List<String> children, String operator) {
        this.children = children;
        this.operator = operator;
    }

    public List<String> getChildren() { return children; }
    public String getOperator() { return operator; }
}
