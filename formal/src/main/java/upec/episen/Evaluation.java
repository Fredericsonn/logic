package upec.episen;

import java.util.ArrayList;
import java.util.List;

public class Evaluation {

    private static final List<String> visitedNodes = new ArrayList<>();
    public static int depth = 0;
    
    public static Integer[] evaluate(Integer[] p, Integer[] q, Operand o) {
        switch (o) {
            case EQUIVALENCE -> {
                return EvaluationHelper.equivalence(p, q);
            }
            case IMPLICATION -> {
                return EvaluationHelper.implication(p, q);
            }
            case NEGATION -> {
                return EvaluationHelper.negation(p);
            }
            case AND -> {
                return EvaluationHelper.and(p, q);
            }
            case OR -> {
                return EvaluationHelper.or(p, q);
            }
            default -> throw new RuntimeException("Unknown operand");
        }
    }

        public static EvaluationResult deduct(AstNode ast) {
        if (EvaluationHelper.isTautology(ast.getValue())) {
            return EvaluationResult.TAUTOLOGY;
        } else if (EvaluationHelper.isContradiction(ast.getValue())) {
            return EvaluationResult.CONTRADICTION;
        } else {
            return EvaluationResult.INDETERMINED;
        }
    }

    public static Integer[] evaluateAtomicPreposition(String p) {
        if (visitedNodes.contains(p)) {
            return EvaluationHelper.atomicProposition(visitedNodes.indexOf(p));
        }
        throw new RuntimeException("Preposition doesn't exist in the current tree : " + p);
    }
    public static void addNode(String node) {
        if (!visitedNodes.contains(node)) {
            visitedNodes.add(node);
            incrementDepth();
        }
    }

    public static void incrementDepth() {
        depth++;
    }

    public void evaluateAst(AstNode ast) {
        ast.computeTreeValue();
    }
}
