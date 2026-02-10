package upec.episen;

import java.util.ArrayList;
import java.util.List;

public class Evaluation {

    private List<String> visitedNodes = new ArrayList<>();
    
    public Integer[] evaluate(Integer[] p, Integer[] q, Operand o) {
        switch (o) {
            case EQUIVALENCE:
                return EvaluationHelper.equivalence(p, q);
            case IMPLICATION:
                return EvaluationHelper.implication(p, q);
            case NEGATION:
                return EvaluationHelper.negation(p);
            case AND:
                return EvaluationHelper.and(p, q);
            case OR:
                return EvaluationHelper.or(p, q);        
            default:
                throw new RuntimeException("Unknown operand");
        }
    }

    public void evaluateAst(AstNode ast) {
        if (!ast.hasChildren()) {

        }
    }
}
