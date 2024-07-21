import java.util.ArrayList;
import java.util.List;

public class FileMetrics {
    private String fileName;
    private List<MethodMetrics> methods = new ArrayList<>();

    private int lines;
    private int loc;
    private int eloc;
    private int iloc;
    private int conditionals;

    public int getConditionals() {
        return conditionals;
    }

    public void setConditionals(int conditionals) {
        this.conditionals = conditionals;
    }

    public FileMetrics(String filename){
        this.fileName =filename;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<MethodMetrics> getMethods() {
        return methods;
    }

    public void addMethod(MethodMetrics method) {
        this.methods.add(method);
    }
    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getEloc() {
        return eloc;
    }

    public void setEloc(int eloc) {
        this.eloc = eloc;
    }

    public int getIloc() {
        return iloc;
    }

    public void setIloc(int iloc) {
        this.iloc = iloc;
    }
}
