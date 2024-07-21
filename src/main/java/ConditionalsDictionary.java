import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConditionalsDictionary {
    private static final List<String> CONDITIONAL_KEYWORDS = Arrays.asList(
            "if", "else if", "while", "for", "switch", "case", "default"
    );

    public static boolean isConditional(String line) {
        return CONDITIONAL_KEYWORDS.stream().anyMatch(line::contains);
    }
}
