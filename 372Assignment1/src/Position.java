/**
 * A general degree position object represented by a degree (restricted to [-360, 3600]) and minute (restricted to [-60, 60]).
 *
 * @author Joseph T. Parsons <josephtparsons@gmail.com>
 */
public abstract class Position {
    /**
     * The minimum number of degrees valid for a general degree system, -360. Naturally, -180 and 180, -270 and 90, etc. are the same position, but denote different _direction_.
     * This value can be overridden by subclasses; refer to subclass documentation to see if a different value applies.
     */
    public static final int MIN_DEGREE = -360;

    /**
     * The maximum number of degrees valid for a general degree system, 360.
     * This value can be overridden by subclasses; refer to subclass documentation to see if a different value applies.
     */
    public static final int MAX_DEGREE = 360;


    /**
     * The minimum number of minutes valid for a general degree system, 0.
     */
    public static final int MIN_MINUTE = 0;

    /**
     * The maximum number of minutes valid for a general degree system, 60.
     */
    public static final int MAX_MINUTE = 60;



    /**
     *  The degree component of a geographic coordinate.
     */
    int degree;

    /**
     * The minute component of a geographic coordinate.
     */
    int minute;

    /**
     * The second component of a geographic coordinate (currently unused, but declared for future use).
     */
    int second;



    /**
     * Creates a longitude object from the passed parameters.
     *
     * @param degree Sets {@link Position#degree} using {@link Position#setDegree(int)}
     * @param minute Sets {@link Position#minute} using {@link Position#setMinute(int)}
     */
    public Position(int degree, int minute) {
        setDegree(degree);
        setMinute(minute);
    }



    /**
     * @return {@link Position#degree}
     */
    public int getDegree() {
        return degree;
    }

    /**
     * @param degree {@link Position#degree}.
     * @throws IllegalAccessException if minute is outside of range between {@link Position#MIN_DEGREE} and {@link Position#MAX_DEGREE}.
     */
    public void setDegree(int degree) {
        if (degree < MIN_DEGREE || degree > MAX_DEGREE) {
            throw new IllegalArgumentException("Degree must be between " + MIN_DEGREE + " and " + MAX_DEGREE + ".");
        }

        this.degree = degree;
    }


    /**
     * @return {@link Position#minute}
     */
    public int getMinute() {
        return minute;
    }

    /**
     * @param minute {@link Position#minute}.
     * @throws IllegalAccessException if minute is outside of range between {@link Position#MIN_DEGREE} and {@link Position#MAX_DEGREE}.
     */
    public void setMinute(int minute) {
        if (minute < MIN_MINUTE || minute > MAX_MINUTE) {
            throw new IllegalArgumentException("Minute must be between " + MIN_MINUTE + " and " + MAX_MINUTE + ".");
        }

        this.minute = minute;
    }


    /**
     * @return A string representation of the object, in the format 0°0'
     */
    public String toString() {
        return this.degree + "° " + this.minute + "\"";
    }
}