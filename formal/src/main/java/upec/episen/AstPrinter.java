package upec.episen;

public class AstPrinter {

    public static void print(AstNode node) {
        print(node, "", true);
    }

    private static void print(AstNode node, String prefix, boolean isLast) {
        if (node == null)
            return;

        System.out.println(prefix
                + (isLast ? "└── " : "├── ")
                + nodeLabel(node)
                + nodeValue(node));

        for (int i = 0; i < node.getChildren().size(); i++) {
            boolean last = (i == node.getChildren().size() - 1);
            print(node.getChildren().get(i),
                    prefix + (isLast ? "    " : "│   "),
                    last);
        }
    }

    private static String nodeLabel(AstNode node) {
        if (node instanceof AstOperandNode opNode) {
            return opNode.getOperand() + " [" + node.getLabel() + "]";
        }
        return node.getLabel();
    }

    private static String nodeValue(AstNode node) {
        Integer[] value = node.getValue();
        if (value == null)
            return "";

        StringBuilder sb = new StringBuilder(" [");
        for (int i = 0; i < value.length; i++) {
            sb.append(value[i]);
            if (i < value.length - 1)
                sb.append(" ");
        }
        sb.append("]");

        return sb.toString();
    }

    public static void printValue(Integer[] value) {
        for (int i = 0; i < value.length; i++) {
            System.err.println(value[i] + " ");
        }
    }
}
