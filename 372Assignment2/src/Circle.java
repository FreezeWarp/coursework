import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by joseph on 23/05/17.
 */
public class Circle extends Figure {
    int startX;
    int startY;
    int width;
    int height;

    public Circle(int startX, int startY, int width, int height, Color color) {
        this.color = color;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);

        Graphics2D graphics2d = (Graphics2D)graphics;
        // Assume x, y, and diameter are instance variables.
        Ellipse2D.Double circle = new Ellipse2D.Double(startX, startY, width, height);
        graphics2d.fill(circle);
    }

    public String toString() {
        return "[Circle: starting at (" + startX + ", " + startY + "), width " + width + ", height " + height + "]";
    }
}