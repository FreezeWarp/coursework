import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Serves as a type for all figures in the simple drawing program.
 * Derived from instructor-provided code.
 *
 * @author Joseph T. Parsons
 * @version 23/05/17
 */
public abstract class Figure implements Serializable {
    /* Normally public variables are frowned upon, but this is the rare case where I think they make perfect sense: the contents of the variables don't effect anything else, and are merely reflected in the draw() call. For this purpose, I think this is the best approach */

    /* These are the required parameters for every 2D figure. Some figures may have additional parameters, like triangles or rounded rectangles, but all will have a starting position, width, height, and color. (Likewise, an outline would be added to this class, not a subclass.) */

    /**
     * The starting X coordinate for the figure. This will be relative to the start of the graphics object used in the draw call.
     */
    protected int startX;
    /**
     * The starting Y coordinate for the figure. This will be relative to the start of the graphics object used in the draw call.
     */
    protected int startY;
    /**
     * The object's width.
     */
    protected int width;
    /**
     * The object's height.
     */
    protected int height;
    /**
     * The object's color.
     */
    protected Color color = Color.red;


    /**
     * Creates a generic figure with the given parameters.
     *
     * @param startX {@link Figure#startX}
     * @param startY {@link Figure#startY}
     * @param width {@link Figure#width}
     * @param height {@link Figure#height}
     * @param color {@link Figure#color}
     */
    public Figure(int startX, int startY, int width, int height, Color color) {
        this.color = color;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }


    /**
     * @return {@link Figure#startX}
     */
    public int getStartX() {
        return startX;
    }
    /**
     * Sets {@link Figure#startX}
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * @return {@link Figure#startY}
     */
    public int getStartY() {
        return startY;
    }
    /**
     * Sets {@link Figure#startY}
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * @return {@link Figure#width}
     */
    public int getWidth() {
        return width;
    }
    /**
     * Sets {@link Figure#width}
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return {@link Figure#height}
     */
    public int getHeight() {
        return height;
    }
    /**
     * Sets {@link Figure#height}
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return {@link Figure#color}
     */
    public Color getColor() {
        return color;
    }
    /**
     * Sets {@link Figure#color}
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Prepares a drawing of the figure using the given Graphics parameter
     * It does not perform any actual drawing; this is left to subclasses.
     *
     * @param graphics the Graphics object for drawing the figure
     */
    public void draw(Graphics graphics) {
        graphics.setColor(color);
    }


    /**
     * @param translateBy Translates (moves) the figure by this amount on the X axis.
     */
    public void translateX(int translateBy) {
        startX += translateBy;
    }
    /**
     * @param translateBy Translates (moves) the figure by this amount on the Y axis.
     */
    public void translateY(int translateBy) {
        startY += translateBy;
    }
    /**
     * @param translateByX Passed to {@link Figure#translateX(int)}
     * @param translateByY Passed to {@link Figure#translateY(int)}
     */
    public void translate(int translateByX, int translateByY) {
        translateX(translateByX);
        translateY(translateByY);
    }
}
