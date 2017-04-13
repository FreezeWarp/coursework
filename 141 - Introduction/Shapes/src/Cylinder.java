/**
 * Created by Joseph on 04/11/2016.
 * Represents a Cylinder, an extension of Shape.
 */
public class Cylinder extends Shape {
    /* Properties */
    private double radius;
    private double height;


    /* Constructors */
    public Cylinder(double radius, double height, String color) {
        super("cylinder", color);
        this.setRadius(radius);
        this.setHeight(height);
    }

    public Cylinder(double radius, double height) {
        this(radius, height, Shape.defaultColor);
    }


    /* Getters */
    public double getRadius() {
        return this.radius;
    }

    public double getHeight() {
        return this.height;
    }


    /* Setters */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setHeight(double height) {
        this.height = height;
    }


    /* Magic Methods */
    public String toString() {
        return super.toString() + " (radius: " + this.getRadius() + ", height: " + this.getHeight() + ", area: " + this.area() + ")";
    }


    /* Methods */
    public double area() {
        return Math.PI * this.getHeight() * Math.pow(this.getRadius(), 2);
    }
}