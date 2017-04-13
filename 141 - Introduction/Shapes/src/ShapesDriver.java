/**
 * Created by Joseph on 04/11/2016.
 * Tests different Shape(s).
 */
public class ShapesDriver {
    public static void main(String[] args) {
        Rectangle deck = new Rectangle(20, 35, "brown");
        Sphere bigBall = new WeirdSphere(15, "polkadot");
        Cylinder tank = new Cylinder(10, 30);

        Paint bluePaint = new Paint(5);

        Shape[] shapes = new Shape[5];
        shapes[0] = deck;
        shapes[1] = bigBall;
        shapes[2] = tank;
        shapes[3] = new Sphere(5);
        shapes[4] = new Rectangle(10, 8);

        for (int shapeNum = 0; shapeNum < shapes.length; shapeNum++) {
            System.out.println(shapes[shapeNum]);
            System.out.println("Required blue paint: " + bluePaint.amount(shapes[shapeNum]));
            System.out.println();
        }
    }
}
