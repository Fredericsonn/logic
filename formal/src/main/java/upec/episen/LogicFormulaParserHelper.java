package upec.episen;

import java.util.List;

public class LogicFormulaParserHelper {

    // Order matters: match longest first (<-> before ->)
    private static final List<String> operators = List.of("<->", "->", "&", "|", "!");
    private static final List<String> binaryOperators = List.of("<->", "->", "&", "|");

    public static String normalizeNegation(String formula) {
        return formula.replace("!", " ! ");
    }

    public static String matchOperatorAt(String s, int index) {
        for (String op : operators) {
            if (index + op.length() <= s.length() && s.startsWith(op, index)) {
                return op;
            }
        }
        return null;
    }

    public static boolean isOperatorToken(String token) {
        return operators.contains(token);
    }

    public static boolean isBinaryOperator(String token) {
        return binaryOperators.contains(token);
    }

    public static boolean isBooleanLiteral(String token) {
        return "true".equalsIgnoreCase(token) || "false".equalsIgnoreCase(token);
    }

    public static Operand transalteOperatorToEnum(String operator) {
        switch (operator) {
            case "<->" -> {
                return Operand.EQUIVALENCE;
            }
            case "->" -> {
                return Operand.IMPLICATION;
            }
            case "&" -> {
                return Operand.AND;
            }
            case "|" -> {
                return Operand.OR;
            }
            case "!" -> {
                return Operand.NEGATION;
            }
            default -> throw new RuntimeException("Invalid Operator");
        }
    }

    public static AstNode createNode(String nodeName) {
        if (isOperatorToken(nodeName)) {
            return new AstOperandNode(nodeName, transalteOperatorToEnum(nodeName));
        }
        Evaluation.addNode(nodeName);
        return new AstNode(nodeName);
    }
}
