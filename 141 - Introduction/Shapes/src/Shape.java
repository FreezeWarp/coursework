/**
 * Created by Joseph on 04/11/2016.
 * Represents a generic shape.
 */
public class Shape {
    public static String defaultColor = "transparent";

    private String shapeName;
    private String color = this.defaultColor;

    /* Constructors */
    public Shape(String shapeName, String color) {
        this(shapeName);
        this.setColor(color);
    }

    public Shape(String shapeName) {
        this.setShapeName(shapeName);
    }

    /* Settesr */
    public String getColor() {
        return this.color;
    }

    public String getShapeName() {
        return this.shapeName;
    }

    /* Getters */
    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /* Magic Methods */
    public String toString() {
        return this.getColor() + " " + this.getShapeName();
    }

    /* Methods */
    public double area() {
        return 0;
    }
}
