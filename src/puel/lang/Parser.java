package puel.lang;

import puel.lang.env.DataType;
import puel.lang.env.Environment;
import puel.lang.env.Namespace;
import puel.misc.InstantiationException;


/**
 * Created by Michael on 09/10/2015.
 *
 */
public final class Parser {

    public static final char SEPARATOR = ':';
    public static final char TERMINATOR = ';';

    public static final int FIND_FAIL = -1;

    private Parser() {
        throw new InstantiationException(this.getClass());
    }

    public static Environment parseScript(String script) {
        Environment env = new Environment();
        namespace_parse(Namespace.EMPTY_NAMESPACE, script.trim(), env);
        return (env);
    }

    private static void namespace_parse(String namespace, String script, Environment env) {
        int sep, term;
        String new_name;
        while (!script.equals("")) {
            switch (script.charAt(0)) {
                case '#':
                    if ((sep = findAtLevel(script, 0, SEPARATOR)) == FIND_FAIL) {
                        throw new Namespace.InvalidNamespaceDeclarationException("separator \'" + SEPARATOR + "\' not found");
                    }

                    if ((term = findAtLevel(script, sep, TERMINATOR)) == FIND_FAIL) {
                        throw new Namespace.InvalidNamespaceDeclarationException("terminator \'" + TERMINATOR + "\' not found");
                    }

                    new_name = script.substring(1, sep).trim();

                    env.namespace(namespace, new_name);

                    namespace_parse(namespace.equals("") ? new_name : namespace + "." + new_name, script.substring(sep + 1, term), env);
                    script = script.substring(term + 1);
                    break;
                case '~':
                    if ((sep = findAtLevel(script, 0, SEPARATOR)) == FIND_FAIL) {
                        throw new DataType.InvalidTypeDeclarationException("separator \'" + SEPARATOR + "\' not found");
                    }

                    if ((term = findAtLevel(script, sep, TERMINATOR)) == FIND_FAIL) {
                        throw new DataType.InvalidTypeDeclarationException("terminator \'" + TERMINATOR + "\' not found");
                    }

                    {
                        new_name = script.substring(1, sep).trim();
                        String[] raw_attr = script.substring(sep + 1, term).split(",");
                        String[] attr_names = new String[raw_attr.length];
                        String[] attr_types = new String[raw_attr.length];

                        String[] temp;
                        for (int i = 0; i < raw_attr.length; ++i) {
                            temp = raw_attr[i].split("[|]");

                            if (temp.length != 2 || temp[0] == "" || temp[1] == "") {
                                //TODO add error
                            }

                            attr_names[i] = temp[0];
                            attr_types[i] = temp[1];
                        }

                        env.defineType(new_name, namespace, attr_names, attr_types);
                    }

                    script = script.substring(term + 1);
                    break;
            }
        }
    }

    private static void parse(String namespace, String script, Environment env) {
        System.out.println(script);

        while (!script.equals("")) {
            switch (script.charAt(0)) {
                case '#':
                    int sep = findAtLevel(script, 0, SEPARATOR);
                    int term = findAtLevel(script, sep, TERMINATOR);

                    String newspace = script.substring(1, sep);
                    env.namespace(namespace, newspace);

                    namespace_parse(namespace.equals("") ? newspace : namespace + "#" + newspace, script.substring(sep + 1, term), env);
                    script = script.substring(term + 1);
                    break;
                case '!':
                    //TODO break;
                case ';':
                    script = script.substring(1);
                    break;
                default:
                    /*
                    Expression top = null;

                    //TODO keywords
                    if (script.equals("true")) {
                        top = new Boolean(true);
                        script = "";
                    } else if (script.equals("false")) {
                        top = new Boolean(false);
                        script = "";
                    } else {
                        //TODO find binary operators
                        ArrayList<String> subexp = new ArrayList<String>();
                        ArrayList<Operation.Operator> ops = new ArrayList<Operation.Operator>();
                        int brace = 0;
                        int prev_pos = 0;
                        int pos = 0;

                        while (pos < script.length() && script.charAt(pos) != ';' && script.charAt(pos) != '@') { //TODO more
                            switch (script.charAt(pos)) {
                                case '(':
                                case '{':
                                case '[':
                                    ++brace;
                                    break;
                                case ')':
                                case '}':
                                case ']':
                                    --brace;
                                    break;
                                case '=':
                                    if (brace == 0) {
                                        if (script.charAt(pos + 1) == '=') {
                                            subexp.add(script.substring(prev_pos, pos));
                                            ops.add(Operation.Operator.EQUIV);
                                            prev_pos = pos + 2;
                                        }
                                    }
                                    break;
                                case '!':
                                    if (brace == 0) {
                                        if (script.charAt(pos + 1) == '=') {
                                            subexp.add(script.substring(prev_pos, pos));
                                            ops.add(Operation.Operator.NONEQ);
                                            prev_pos = pos + 2;
                                        }
                                    }
                                    break;
                                case '&':
                                    if (brace == 0) {
                                        if (script.charAt(pos + 1) == '&') {
                                            subexp.add(script.substring(prev_pos, pos));
                                            ops.add(Operation.Operator.AND);
                                            prev_pos = pos + 2;
                                        }
                                    }
                                    break;
                                case '|':
                                    if (brace == 0) {
                                        if (script.charAt(pos + 1) == '|') {
                                            subexp.add(script.substring(prev_pos, pos));
                                            ops.add(Operation.Operator.OR);
                                            prev_pos = pos + 2;
                                        }
                                    }
                            }
                            ++pos;
                        }
                        subexp.add(script.substring(prev_pos, pos));
                        script = script.substring(pos);

                        //TODO deal with precedence
                        int size = ops.size();
                        int filled = 0;
                        BinaryOperation[] braced = new BinaryOperation[size];

                        while (size != filled) {
                            for (int i = 0; i < size; ++i) {
                                for (int j=0; j<braced.length; ++j) {
                                    System.out.print(braced[j] == null ? "null" : braced[j].parsedForm() + " , ");
                                }
                                System.out.println();

                                if (ops.get(i) != Operation.Operator.NONE) {
                                    if (i < size - 1 && ops.get(i).precedence() <= ops.get(i + 1).precedence()) {
                                        if (i == 0) {
                                            top = braced[i + 1] = braced[i] = BinaryOperation.newBinOp(ops.get(i), parse(namespace, subexp.get(i)),
                                                    (braced[i + 1] != null) ? braced[i + 1] : parse(namespace, subexp.get(i + 1)));
                                            ops.set(i, Operation.Operator.NONE);
                                            ++filled;
                                        } else if (ops.get(i).precedence() <= ops.get(i - 1).precedence()) {
                                            top = braced[i + 1] = braced[i] = BinaryOperation.newBinOp(ops.get(i),
                                                    (braced[i] != null) ? braced[i] : parse(namespace, subexp.get(i)),
                                                    (braced[i + 1] != null) ? braced[i + 1] : parse(namespace, subexp.get(i + 1)));
                                            ops.set(i, Operation.Operator.NONE);
                                            ++filled;
                                        }
                                    } else if (i == size - 1){
                                        if (i == 0) {
                                            top = braced[i] = BinaryOperation.newBinOp(ops.get(i), parse(namespace, subexp.get(i)), parse(namespace, subexp.get(i + 1)));
                                            ops.set(i, Operation.Operator.NONE);
                                            ++filled;
                                        } else {
                                            top = braced[i] = BinaryOperation.newBinOp(ops.get(i),
                                                    (braced[i] != null) ? braced[i] : parse(namespace, subexp.get(i)),
                                                    parse(namespace, subexp.get(i + 1)));
                                            ops.set(i, Operation.Operator.NONE);
                                            ++filled;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (parsed == null) {
                        parsed = top;
                    } else {
                        parsed = top.add(parsed);
                    }*/
            }
        }
    }

    protected static int findAtLevel(String str, int pos, char c) {
        int level = 0;
        boolean found = false;

        while (!(level == 1 || found) && pos < str.length() - 1) {
            ++pos;

            char cp = str.charAt(pos);

            if (cp == c) {
                if (level == 0) {
                    found = true;
                }
            }
            if (cp == TERMINATOR) {
                ++level;
            } else if (compoundStatementId(cp)) {
                --level;
            }
        }

        return (found ? pos : FIND_FAIL);
    }

    protected static boolean compoundStatementId(char c) {
        for (StatementID id : StatementID.values())
            if (id.CHAR == c)
                return (true);
        return (false);
    }

    protected int indexOfRightBrace(String s, int start, Brace type) {
        int brace = 0;
        int pos = start;

        while (brace != 1) {
            ++pos;

            char c = s.charAt(pos);

            if (c == type.left)
                --brace;
            else if (c == type.right)
                ++brace;
        }

        return (pos);
    }

    protected enum Brace {
        ROUND('(', ')'),
        SQUARE('[', ']'),
        CURLY('{','}');

        private final char left;
        private final char right;

        Brace(char left, char right) {
            this.left = left;
            this.right = right;
        }
    }

    protected enum Keyword {
        TRUE("true"),
        FALSE("false"),
        NONE("");

        private final String name;

        Keyword(String name) {
            this.name = name;
        }

        public Keyword getKeyword(String word) {
            for (Keyword k : Keyword.values()) {
                if (k.name.equals(word))
                    return (k);
            }
            return (NONE);
        }
    }

    public enum StatementID {
        TYPE('~'),
        NAMESPACE('#'),
        FUNC_DEF('$'),
        FUNC_CALL('@'),
        CONDITIONAL('?');

        public final char CHAR;

        StatementID(char id) {
            CHAR = id;
        }
    }

    public static void main(String args[]) {
        Environment env = Parser.parseScript("# examplenamespace :" +
                "#subspace:" +
                "~ Point :x|int,y|int;" +
                "#secondsubspace:" +
                "~Square:corner|examplenamespace.subspace.Point,side|int;" +
                ";;;");
        System.out.println(env.toString());
    }
}
