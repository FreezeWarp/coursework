/**
 * A latitude object represented by a degree (restricted to [0, 90]), minute, and direction (either "N" or "S").
 *
 * @author Joseph T. Parsons <josephtparsons@gmail.com>
 */
public class Latitude extends Position {
    /**
     * The minimum number of degrees valid for a latitude, 0. Use {@link Latitude#northOrSouth} to denote negatives.
     */
    public static final int MIN_DEGREE = 0;

    /**
     * The maximum number of degrees valid for a latitude, 90.
     */
    public static final int MAX_DEGREE = 90;


    /**
     * A string constant (either "N" or "S") indicating whether the Longitude applies to the eastern or western hemisphere.
     * (Why, yes, this should be an enum, or even combined into the sign of the degree. But I'm following directions.)
     */
    String northOrSouth;



    /**
     * Creates a latitude object from the passed parameters.
     *
     * @param degree Sets {@link Position#degree} using {@link Position#Position(int, int)}
     * @param minute {@link Position#minute} using {@link Position#Position(int, int)}
     * @param northOrSouth {@link Latitude#northOrSouth} using {@link Latitude#setNorthOrSouth(String)}
     */
    public Latitude(int degree, int minute, String northOrSouth) {
        super(degree, minute);
        setNorthOrSouth(northOrSouth);
    }



    /**
     * @param northOrSouth {@link Latitude#northOrSouth}
     * @throws IllegalAccessException If northOrSouth is invalid.
     */
    public void setNorthOrSouth(String northOrSouth) {
        if (!(northOrSouth == "N" || northOrSouth == "S")) {
            throw new IllegalArgumentException("Value must be either \"N\" or \"S\". Yes, this should have been an enum. Or even combined with degree.");
        }

        this.northOrSouth = northOrSouth;
    }

    /**
     * {@link Latitude#northOrSouth}
     */
    public String getNorthOrSouth() {
        return northOrSouth;
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
        return super.toString() + this.northOrSouth;
    }
}
