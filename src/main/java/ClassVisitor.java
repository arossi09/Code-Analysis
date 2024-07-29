import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.HashSet;
import java.util.Set;

/***
 @author Noa

 Class Description:
 Processes the data of a java class using the visitor
 behavoiral pattern.
 */

public class ClassVisitor extends VoidVisitorAdapter<Void>{

    public void visit(ClassOrInterfaceDeclaration n, Void arg) {

            super.visit(n, arg);
            String className = n.getNameAsString();
            Set<String> references = DependencyData.getInstance().classReferences.getOrDefault(className, new HashSet<>());

            // Process field declarations
            for (FieldDeclaration field : n.findAll(FieldDeclaration.class)) {
                for (var var : field.getVariables()) {
                    String type = var.getType().toString();
                    addReferenceIfValid(references, type, className);
                }
            }

            // Process method return types
            for (MethodDeclaration method : n.findAll(MethodDeclaration.class)) {
                String type = method.getType().toString();
                addReferenceIfValid(references, type, className);
            }

            // Process extends clause
            for (ClassOrInterfaceType extendedType : n.getExtendedTypes()) {
                String type = extendedType.getNameAsString();
                addReferenceIfValid(references, type, className);
            }

            // Process implements clause
            for (ClassOrInterfaceType implementedType : n.getImplementedTypes()) {
                String type = implementedType.getNameAsString();
                addReferenceIfValid(references, type, className);
            }

            DependencyData.getInstance().classReferences.put(className, references);
        }

    private void addReferenceIfValid(Set<String> references, String type, String name) {
            if (!PrimitiveDictionary.isPrimitve(type)& !type.equals(name)) {
                if (type.contains("<")) {
                    String genericType = extractGenericType(type);
                    if (!PrimitiveDictionary.isPrimitve(type)) {
                        references.add(genericType);
                    }
                } else {
                    references.add(type);
                }
            }
    }

    private String extractGenericType(String type) {
        int startIndex = type.indexOf('<') + 1;
        int endIndex = type.lastIndexOf('>');
        return type.substring(startIndex, endIndex).trim();
    }
}
