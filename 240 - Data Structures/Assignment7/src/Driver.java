import java.util.Collections;

/**
 * @author Joseph T. Parsons
 *
 * A basic demonstration of recursion.
 * (...All of these methods should be done using normal loops, but I suppose most cases where recursion is actually useful would be too complicated for your average student -- stuff like parsing JSON. Though I think merge sort would have been a really good test?)
 */
public class Driver {
    public static void main(String[] args) {
        System.out.println("Sumover for 3: " + Driver.sumover(3));
        System.out.println("Sumover for 10: " + Driver.sumover(10));
        System.out.println("Cannonballs in 2-layer Pyramid: " + Driver.countBallsInPyramid(2));
        System.out.println("Cannonballs in 10-layer Pyramid: " + Driver.countBallsInPyramid(10));
        System.out.println("Asterisks, starting 3 up to 6: ");
        Driver.printAsterisks(3, 6);
        System.out.println("Asterisks, starting 1 up to 5: ");
        Driver.printAsterisks(1, 5);
    }


    /**
     * Calculates the sum of a reciprocol series. (Yes, this should be written as a loop.)
     * @param n size of series
     */
    public static double sumover(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n at least 0.");

        else if (n == 0)
            return 0;

        else if (n == 1)
            return 1;

        else
            return Driver.sumover(n-1) + ((double) 1 / n);
    }


    /**
     * Calculates the sum of a square series. (Yes, this should be written as a loop.)
     * @param layers size of series
     */
    public static int countBallsInPyramid(int layers) {
        if (layers < 0)
            throw new IllegalArgumentException("layers must at least 0.");

        else if (layers == 0)
            return 0;

        else if (layers == 1)
            return 1;

        else
            return Driver.countBallsInPyramid(layers-1) + layers * layers;
    }


    /**
     * Prints asterisks in rows, starting at size m, going to size n, and then going back to size m. (Yes, this should be written as a loop.)
     * @param m initial size of asterisk lines
     * @param n final size of asterisk lines
     */
    public static void printAsterisks(int m, int n) {
        if (m < 0)
            throw new IllegalArgumentException("m must be at least 0.");

        else if (n < m)
            throw new IllegalArgumentException("n must be at lesat m.");

        // http://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string-in-java
        System.out.println(String.join("", Collections.nCopies(m, "*")));

        if (m < n) {
            Driver.printAsterisks(m+1, n);
        }

        System.out.println(String.join("", Collections.nCopies(m, "*")));
    }
}
