/**
 * A longitude object represented by a degree (restricted to [0, 180]), minute, and direction (either "E" or "W").
 *
 * @author Joseph T. Parsons <josephtparsons@gmail.com>
 */
public class Longitude extends Position {
    /**
     * The minimum number of degrees valid for a longitude, 0. Use {@link Longitude#eastOrWest} to denote negatives.
     */
    public static final int MIN_DEGREE = 0;

    /**
     * The maximum number of degrees valid for a longitude, 180.
     */
    public static final int MAX_DEGREE = 180;


    /**
     * A string constant (either "E" or "W") indicating whether the Longitude applies to the eastern or western hemisphere.
     * (Why, yes, this should be an enum, or even combined into the sign of the degree. But I'm following directions.)
     */
    String eastOrWest;



    /**
     * Creates a longitude object from the passed parameters.
     *
     * @param degree Sets {@link Position#degree} using {@link Position#Position(int, int)}
     * @param minute {@link Position#minute} using {@link Position#Position(int, int)}
     * @param eastOrWest {@link Longitude#eastOrWest} using {@link Longitude#setEastOrWest(String)}
     */
    public Longitude(int degree, int minute, String eastOrWest) {
        super(degree, minute);
        setEastOrWest(eastOrWest);
    }



    /**
     * @param eastOrWest {@link Longitude#eastOrWest}
     * @throws IllegalAccessException If eastOrWest is invalid.
     */
    public void setEastOrWest(String eastOrWest) {
        if (!(eastOrWest == "E" || eastOrWest == "W")) {
            throw new IllegalArgumentException("Value must be either \"E\" or \"W\". Yes, this should have been an enum. Or even combined with degree.");
        }

        this.eastOrWest = eastOrWest;
    }

    /**
     * @return {@link Longitude#eastOrWest}
     */
    public String getEastOrWest() {
        return eastOrWest;
    }


    /**
     * @param degree {@link Position#degree}.
     * @throws IllegalAccessException if minute is outside of range between {@link Position#MIN_DEGREE} and {@link Position#MAX_DEGREE}.
     */
    public void setDegree(int degree) {
        if (degree < MIN_DEGREE || degree > MAX_DEGREE) {
            throw new IllegalArgumentException("Degree must be between " + MIN_DEGREE + " and " + MAX_DEGREE + ".");
        }

        super.setDegree(degree);
    }



    /**
     * @return A string representation of the object, typically in the format 0°0'D
     */
    public String toString() {
        return super.toString() + this.eastOrWest;
    }
}