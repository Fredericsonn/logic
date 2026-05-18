package upec.episen;

public class AstPopulator {

    LogicFormulaParser parser = new LogicFormulaParser();

    public AstNode createAndPopulateAst(String formula) {
        AstNode initialTree = parser.createAst(formula); // initial empty tree

        initialTree.computeTreeValue(); // recursively computing each node's value by going through its descendance

        return initialTree;

    }

    public void initializeValues(AstNode ast) {
        ast.setValue(EvaluationHelper.initializeValue(Evaluation.depth));

        if (ast.hasChildren()) {
            for (AstNode child : ast.getChildren()) {
                initializeValues(child);
            }
        }
    }
}
