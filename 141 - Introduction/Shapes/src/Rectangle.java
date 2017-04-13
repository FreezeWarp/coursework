/**
 * Created by Joseph on 04/11/2016.
 * Represents a Rectangle, an extension of Shape.
 */
public class Rectangle extends Shape {
    /* Properties */
    private double width;
    private double height;


    /* Constructors */
    public Rectangle(double width, double height, String color) {
        super("rectangle", color);
        this.setWidth(width);
        this.setHeight(height);
    }

    public Rectangle(double width, double height) {
        this(width, height, Shape.defaultColor);
    }


    /* Getters */
    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }


    /* Setters */
    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }


    /* Magic Methods */
    public String toString() {
        return super.toString() + " (width: " + this.getWidth() + ", height: " + this.getHeight() + ", area: " + this.area() + ")";
    }


    /* Methods */
    public double area() {
        return this.getWidth() * this.getHeight();
    }
}