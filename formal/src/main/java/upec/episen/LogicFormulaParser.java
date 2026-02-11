package upec.episen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogicFormulaParser {

    public String spaceAdjuster(String formula) {
        String flat = formula.replace(" ", "");
        List<Character> out = new ArrayList<>();
        int depth = 0;

        for (int i = 0; i < flat.length(); i++) {
            char c = flat.charAt(i);

            if (c == '(') {
                depth++;
                out.add(c);
                continue;
            }
            if (c == ')') {
                depth--;
                out.add(c);
                continue;
            }

            // Only space around operators at top-level (depth == 0),
            // but operators can be multi-char (->, <->)
            if (depth == 0) {
                if (c == '!') {
                    out.add(' ');
                    out.add('!');
                    out.add(' ');
                    continue;
                }

                String op = LogicFormulaParserHelper.matchOperatorAt(flat, i);
                if (op != null) {
                    out.add(' ');
                    for (int k = 0; k < op.length(); k++)
                        out.add(op.charAt(k));
                    out.add(' ');
                    i += op.length() - 1; // jump over operator length
                    continue;
                }
            }

            out.add(c);
        }

        return out.stream().map(String::valueOf).collect(Collectors.joining());
    }

    public String unwrapParenthesis(String formula) {
        String unwrapped = formula;

        while (unwrapped.length() >= 2
                && unwrapped.charAt(0) == '('
                && unwrapped.charAt(unwrapped.length() - 1) == ')') {

            int depth = 0;
            boolean balanced = true;

            for (int i = 0; i < unwrapped.length(); i++) {
                char ch = unwrapped.charAt(i);
                if (ch == '(')
                    depth++;
                if (ch == ')')
                    depth--;

                // If we close the outer parens before the end -> not a full wrap
                if (depth == 0 && i != unwrapped.length() - 1) {
                    balanced = false;
                    break;
                }
            }

            if (balanced)
                unwrapped = unwrapped.substring(1, unwrapped.length() - 1);
            else
                break;
        }

        return unwrapped;
    }

    public String parenthesesFormatter(String input) {
        String unwrapped = unwrapParenthesis(input);
        String adjusted = spaceAdjuster(unwrapped);
        List<String> tokens = new ArrayList<>(Arrays.asList(adjusted.trim().split("\\s+")));

        // Precedence (high -> low): !, &, |, ->, <->
        // Unary NOT: wrap (!X)
        // Unary NOT: wrap from right to left to support !!!p, !!p, etc.
        int i = tokens.size() - 1;
        while (i >= 0) {
            String t = tokens.get(i);
            if (t.equals("!") && i < tokens.size() - 1) {
                String right = tokens.get(i + 1);
                String wrapped = "(!" + right + ")";
                tokens.subList(i, i + 2).clear(); // remove '!' and right
                tokens.add(i, wrapped); // insert wrapped
            }
            i--;
        }

        // AND (&) left-associative
        i = 0;
        while (i < tokens.size()) {
            String t = tokens.get(i);
            if (t.equals("&") && i > 0 && i < tokens.size() - 1) {
                String left = tokens.get(i - 1);
                String right = tokens.get(i + 1);
                String wrapped = "(" + left + "&" + right + ")";
                tokens.subList(i - 1, i + 2).clear();
                tokens.add(i - 1, wrapped);
                i = Math.max(i - 1, 0);
                continue;
            }
            i++;
        }

        // OR (|) left-associative
        i = 0;
        while (i < tokens.size()) {
            String t = tokens.get(i);
            if (t.equals("|") && i > 0 && i < tokens.size() - 1) {
                String left = tokens.get(i - 1);
                String right = tokens.get(i + 1);
                String wrapped = "(" + left + "|" + right + ")";
                tokens.subList(i - 1, i + 2).clear();
                tokens.add(i - 1, wrapped);
                i = Math.max(i - 1, 0);
                continue;
            }
            i++;
        }

        // IMPLIES (->) right-associative => scan from right to left
        i = tokens.size() - 1;
        while (i >= 0) {
            String t = tokens.get(i);
            if (t.equals("->") && i > 0 && i < tokens.size() - 1) {
                String left = tokens.get(i - 1);
                String right = tokens.get(i + 1);
                String wrapped = "(" + left + "->" + right + ")";
                tokens.subList(i - 1, i + 2).clear();
                tokens.add(i - 1, wrapped);
                i = Math.min(i - 2, tokens.size() - 1);
                continue;
            }
            i--;
        }

        // EQUIV (<->) usually left-associative
        i = 0;
        while (i < tokens.size()) {
            String t = tokens.get(i);
            if (t.equals("<->") && i > 0 && i < tokens.size() - 1) {
                String left = tokens.get(i - 1);
                String right = tokens.get(i + 1);
                String wrapped = "(" + left + "<->" + right + ")";
                tokens.subList(i - 1, i + 2).clear();
                tokens.add(i - 1, wrapped);
                i = Math.max(i - 1, 0);
                continue;
            }
            i++;
        }

        return tokens.get(0);
    }

    public String spaceFormatter(String input) {
        String formatted = parenthesesFormatter(input);
        formatted = unwrapParenthesis(formatted);

        StringBuilder result = new StringBuilder();
        int depth = 0;

        for (int i = 0; i < formatted.length(); i++) {
            char c = formatted.charAt(i);

            if (c == '(') {
                depth++;
                result.append(c);
                continue;
            }
            if (c == ')') {
                depth--;
                result.append(c);
                continue;
            }

            // Only add spaces for operators at depth 0 (operators may be multi-char)
            if (depth == 0) {
                if (c == '!') {
                    result.append(' ');
                    result.append('!');
                    result.append(' ');
                    continue;
                }

                String op = LogicFormulaParserHelper.matchOperatorAt(formatted, i);
                if (op != null) {
                    result.append(' ').append(op).append(' ');
                    i += op.length() - 1;
                    continue;
                }
            }

            result.append(c);
        }

        return result.toString();
    }

    public ExtractedNode extractNode(String formula) {
        String formatted = parenthesesFormatter(formula);
        formatted = spaceFormatter(formatted);
        String[] tokens = formatted.trim().split("\\s+");

        // Unary: ! X
        if (tokens.length == 2 && tokens[0].equals("!")) {
            return new ExtractedNode(List.of(tokens[1]), "!");
        }

        // Binary: A op B
        if (tokens.length == 3) {
            return new ExtractedNode(List.of(tokens[0], tokens[2]), tokens[1]);
        }

        // Atom / constant
        if (tokens.length == 1) {
            return new ExtractedNode(List.of(tokens[0]), "");
        }

        throw new IllegalArgumentException("Invalid logical formula format: " + formula);
    }

    public AstNode createAst(String formula) {
        ExtractedNode extracted = extractNode(formula);

        List<String> children = extracted.getChildren();
        String operator = extracted.getOperator();

        // Atom / literal
        if (children.size() == 1 && operator.isEmpty()) {
            return LogicFormulaParserHelper.createNode(children.get(0));
        }

        // Operator node (unary or binary)
        AstNode node = LogicFormulaParserHelper.createNode(operator);

        if (operator.equals("!")) {
            node.setChildren(List.of(createAst(children.get(0))));
        } else {
            node.setChildren(List.of(
                    createAst(children.get(0)),
                    createAst(children.get(1))));
        }

        return node;
    }
}
