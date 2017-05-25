import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * A circle represented by {@link Figure}'s parameters.
 * When draw is invoked, a circle (or oval) will be drawn matching the given parameters.
 *
 * @author Joseph T. Parsons
 * @version 23/05/17
 */
public class Circle extends Figure {
    /**
     * Creates a circle (or oval) with the given parameters.
     *
     * @param startX {@link Figure#startX}
     * @param startY {@link Figure#startY}
     * @param width {@link Figure#width}
     * @param height {@link Figure#height}
     * @param color {@link Figure#color}
     */
    public Circle(int startX, int startY, int width, int height, Color color) {
        super(startX, startY, width, height, color);
    }

    /**
     * Draws a circle of the given start position, dimensions, and color to the graphics buffer.
     * @param graphics the graphics object to draw to
     */
    public void draw(Graphics graphics) {
        super.draw(graphics);

        Graphics2D graphics2d = (Graphics2D)graphics;
        // Assume x, y, and diameter are instance variables.
        Ellipse2D.Double circle = new Ellipse2D.Double(startX, startY, width, height);
        graphics2d.fill(circle);
    }

    /**
     * @return A string representation, including starting position, width, height, and color.
     */
    public String toString() {
        return "[Circle: starting at (" + startX + ", " + startY + "), width " + width + ", height " + height + ", fill color: " + this.color + "]";
    }
}