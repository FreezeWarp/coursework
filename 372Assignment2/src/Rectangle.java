import java.awt.*;

/**
 * Created by joseph on 23/05/17.
 */
public class Rectangle extends Figure {
    int startX;
    int startY;
    int width;
    int height;

    public Rectangle(int startX, int startY, int width, int height, Color color) {
        this.color = color;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
        graphics.fillRect(startX, startY, width, height);
    }

    public String toString() {
        return "[Rectangle: starting at (" + startX + ", " + startY + "), width " + width + ", height " + height + "]";
    }
}