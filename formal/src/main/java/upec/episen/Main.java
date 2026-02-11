package upec.episen;

public class Main {
    public static void main(String[] args) {
        AstPopulator populator = new AstPopulator();
        String tautologyExample = "((p -> q) & (q -> r)) -> (p -> r)";
        String contradictionExample = "(p <-> q) & (p <-> !q)";
        AstNode ast = populator.createAndPopulateAst("!!p -> a");
        AstPrinter.print(ast);

    }
}