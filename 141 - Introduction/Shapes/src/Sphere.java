/**
 * Created by Joseph on 04/11/2016.
 */
public class Sphere extends Shape {
    /* Properties */
    private double radius;


    /* Constructors */
    public Sphere(double radius, String color) {
        super("sphere", color);
        this.setRadius(radius);
    }

    public Sphere(double radius) {
        this(radius, Shape.defaultColor);
    }


    /* Getters */
    public double getRadius() {
        return this.radius;
    }


    /* Setters */
    public void setRadius(double radius) {
        this.radius = radius;
    }


    /* Magic Methods */
    public String toString() {
        return super.toString() + " (radius: " + this.getRadius() + ", area: " + this.area() + ")";
    }


    /* Methods */
    public double area() {
        return 4 * Math.PI * Math.pow(this.getRadius(), 2);
    }
}
