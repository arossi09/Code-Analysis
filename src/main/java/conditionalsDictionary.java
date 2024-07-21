import java.util.ArrayList;
import java.util.List;

public class conditionalsDictionary {
    private static final List<String> CONDITIONAL_KEYWORDS = new ArrayList<>();

    static {
        CONDITIONAL_KEYWORDS.add("if");
        CONDITIONAL_KEYWORDS.add("else if");
        CONDITIONAL_KEYWORDS.add("while");
        CONDITIONAL_KEYWORDS.add("for");
        CONDITIONAL_KEYWORDS.add("switch");
        CONDITIONAL_KEYWORDS.add("case");
        CONDITIONAL_KEYWORDS.add("default");
    }

    public static boolean isConditional(String line) {
        for (String keyword : CONDITIONAL_KEYWORDS) {
            if (line.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
