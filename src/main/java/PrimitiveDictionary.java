
import java.util.Set;
/***
 @author Noa

 Class Description: Dictionary that hold the primitive types
 and determines if a string is one.
 */
public class PrimitiveDictionary {
    private static final Set<String> PRIMITIVES = Set.of(
            "byte", "short", "int", "long", "float", "double", "boolean", "char", "void"
    );
    public static boolean isPrimitve(String type) {
        return PRIMITIVES.contains(type);
    }
}
