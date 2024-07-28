/***
 @author Anthony
 @author Blake

 Class Description: stores metrics for
 methods parsed in the GithubRepo parser
 */
public class MethodMetrics {
    private String methodName;
    private int conditionalCount;

    public String getMetricStatus() {
        return metricStatus;
    }

    private String metricStatus;

    public void setMetricStatus(String metricStatus) {
        this.metricStatus = metricStatus;
    }

    private int lines;
    private int loc;
    private int eloc;
    private int iloc;
    private String methodBody;

    public MethodMetrics(String name){
        methodName = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
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

    public String getMethodBody() {  // Getter for method body
        return methodBody;
    }

    public void setMethodBody(String methodBody) {  // Setter for method body
        this.methodBody = methodBody;
    }
}
