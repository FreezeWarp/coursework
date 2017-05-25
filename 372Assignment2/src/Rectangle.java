import java.awt.*;

/**
 * A rectangle represented by {@link Figure}'s parameters.
 * When draw is invoked, a rectangle will be drawn matching the given parameters.
 *
 * @author Joseph T. Parsons
 * @version 23/05/17
 */
public class Rectangle extends Figure {
    /**
     * Creates a rectangle with the given parameters.
     */
    public Rectangle(int startX, int startY, int width, int height, Color color) {
        super(startX, startY, width, height, color);
    }

    /**
     * Draws a circle of the given start position, dimensions, and color to the graphics buffer.
     * @param graphics the graphics object to draw to
     */
    public void draw(Graphics graphics) {
        super.draw(graphics);
        graphics.fillRect(startX, startY, width, height);
    }

    /**
     * @return A string representation, including starting position, width, height, and color.
     */
    public String toString() {
        return "[Rectangle: starting at (" + startX + ", " + startY + "), width " + width + ", height " + height + ", fill color: " + this.color + "]";
    }
}