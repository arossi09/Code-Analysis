public class MethodMetrics {
    private String methodName;
    private int lineCount;
    private int conditionalCount;

    private int lines;
    private int loc;
    private int eloc;
    private int iloc;

    public MethodMetrics(String name){
        methodName = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getConditionalCount() {
        return conditionalCount;
    }

    public void setConditionalCount(int conditionalCount) {
        this.conditionalCount = conditionalCount;
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
