import com.github.javaparser.StaticJavaParser;

import com.github.javaparser.ast.CompilationUnit;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/***
@author Noa

Class Description: singleton containing dependency data
and methods to parse dependency data from github repo
*/


public class DependencyData {


    public static final Map<String, Set<String>> classReferences = new HashMap<>();
    public static final Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    private static DependencyData dd;


    private static String classesPath = "cloned_repos";
    public static DependencyData getInstance() {
        if (dd == null) {
            dd = new DependencyData();
        }
        return dd;
    }

    public DependencyData(){
    }

    public static void dependencyParser() throws IOException {

        File projectDir = new File(classesPath);
        parseProject(projectDir);
        for (String className : classReferences.keySet()) {
            graph.addVertex(className);
            for (String reference : classReferences.get(className)) {
                graph.addVertex(reference);
                graph.addEdge(className, reference);
            }
        }
    }
    private static void parseProject(File dir) throws IOException {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                parseProject(file);
            } else if (file.getName().endsWith(".java")) {
                parseJavaFile(file);
            }
        }
    }
    private static void parseJavaFile(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        CompilationUnit cu;
        cu = StaticJavaParser.parse(in);
        in.close();

        cu.accept(new ClassVisitor(), null);
    }
    public static void printDependencies(){
        for (Map.Entry<String, Set<String>> entry : classReferences.entrySet()) {
            System.out.println("Class " + entry.getKey() + " references: " + entry.getValue());
        }
    }
}

