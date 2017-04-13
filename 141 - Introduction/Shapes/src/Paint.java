/**
 * Created by Joseph on 18/11/2016.
 */
public class Paint {
    private int coverage;

    public Paint(int coverage) {
        this.setCoverage(coverage);
    }

    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }

    public int getCoverage() {
        return this.coverage;
    }

    public double amount(Shape s) {
        return s.area() / coverage;
    }
}
