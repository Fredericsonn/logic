package upec.episen;

public class EvaluationHelper {

    public static Integer[] equivalence(Integer[] p, Integer[] q) {
        Integer[] evaluation = new Integer[] { 0, 0, 0, 0 };
        for (int i=0; i < 4; i++) {
            if (p[i] == q[i]) {
                evaluation[i] = 1;
            }
        }
        return evaluation;
    }

    public static Integer[] implication(Integer[] p, Integer[] q) {
        Integer[] evaluation = new Integer[] { 0, 0, 0, 0 };
        for (int i=0; i < 4; i++) {
            if (p[i] == 0 && (p[i] == 1 && q[i] == 1)) {
                evaluation[i] = 1;
            }
        }
        return evaluation;
    }

    public static Integer[] negation(Integer[] p) {
        Integer[] evaluation = new Integer[] { 0, 0, 0, 0 };
        for (int i=0; i < 4; i++) {
            if (p[i] == 0) {
                evaluation[i] = 1;
            }
        }
        return evaluation;
    }

    public static Integer[] and(Integer[] p, Integer[] q) {
        Integer[] evaluation = new Integer[] { 0, 0, 0, 0 };
        for (int i=0; i < 4; i++) {
            if (p[i] == 1 && q[i] == 1) {
                evaluation[i] = 1;
            }
        }
        return evaluation;
    }

    public static Integer[] or(Integer[] p, Integer[] q) {
        Integer[] evaluation = new Integer[] { 0, 0, 0, 0 };
        for (int i=0; i < 4; i++) {
            if (p[i] == 1 || q[i] == 1) {
                evaluation[i] = 1;
            }
        }
        return evaluation;
    }

    public static boolean isTautology(Integer[] p) {
        for (int i=0; i < 4; i++) {
            if (p[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isContradiction(Integer[] p) {
        for (int i=0; i < 4; i++) {
            if (p[i] == 1) {
                return false;
            }
        }
        return true;
    }

    public static EvaluationResult deduct(Integer[] p) {
        if (isTautology(p)) {
            return EvaluationResult.TAUTOLOGY;
        } else if (isContradiction(p)) {
            return EvaluationResult.CONTRADICTION;
        } else {
            return EvaluationResult.INDETERMINE;
        }
    }
}
