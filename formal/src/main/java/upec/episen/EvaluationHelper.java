package upec.episen;

public class EvaluationHelper {

    public static Integer[] initializeValue(int depth) {
        int length = (int) Math.pow(2, depth);
        Integer[] value = new Integer[length];
        for (int i = 0; i < length; i++) {
            value[i] = 0;
        }
        return value;
    }

    public static Integer[] atomicProposition(int index) {
        Integer[] value = initializeValue(Evaluation.depth);
        boolean switchValue = false;
        int switchPoint = (int) Math.pow(2, Evaluation.depth - index - 1);
        for (int i = 0; i < value.length; i++) {
            if (i != 0 && i % switchPoint == 0) {
                switchValue = !switchValue;
            }
            if (switchValue) {
                value[i] = 1;
            }
        }
        return value;
    }

    public static Integer[] equivalence(Integer[] p, Integer[] q) {
        Integer[] evaluation = initializeValue(Evaluation.depth);
        for (int i = 0; i < evaluation.length; i++) {
            if (p[i] == q[i]) {
                evaluation[i] = 1;
            }
        }
        return evaluation;
    }

    public static Integer[] implication(Integer[] p, Integer[] q) {
        Integer[] evaluation = initializeValue(Evaluation.depth);
        for (int i = 0; i < evaluation.length; i++) {
            evaluation[i] = (p[i] == 1 && q[i] == 0) ? 0 : 1;
        }
        return evaluation;
    }

    public static Integer[] negation(Integer[] p) {
        Integer[] evaluation = initializeValue(Evaluation.depth);
        for (int i = 0; i < evaluation.length; i++) {
            if (p[i] == 0) {
                evaluation[i] = 1;
            }
        }
        return evaluation;
    }

    public static Integer[] and(Integer[] p, Integer[] q) {
        Integer[] evaluation = initializeValue(Evaluation.depth);
        for (int i = 0; i < evaluation.length; i++) {
            if (p[i] == 1 && q[i] == 1) {
                evaluation[i] = 1;
            }
        }
        return evaluation;
    }

    public static Integer[] or(Integer[] p, Integer[] q) {
        Integer[] evaluation = initializeValue(Evaluation.depth);
        for (int i = 0; i < evaluation.length; i++) {
            if (p[i] == 1 || q[i] == 1) {
                evaluation[i] = 1;
            }
        }
        return evaluation;
    }

    public static boolean isTautology(Integer[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isContradiction(Integer[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == 1) {
                return false;
            }
        }
        return true;
    }
}
