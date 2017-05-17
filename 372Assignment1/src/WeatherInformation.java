/**
 * Created by joseph on 12/05/17.
 */
public class WeatherInformation implements WeatherRecord {
    /* Variables should be more descriptive -- not immediately clear this refers to the min/max recorded temperature.
     * (I'm not changing it, since many Java methods expect that the variable name match the getter/setter names.) */

    /**
     * The maximum temperature ever recorded in the location identified by longitude and latitude.
     */
    double maxTemperature;

    /**
     * The minimum temperature ever recorded in the location identified by longitude and latitude.
     */
    double minTemperature;


    /**
     * The longitudinal position of the location corresponding with this record.
     */
    Longitude longitude;

    /**
     * The latitudinal position of the location corresponding with this record.
     */
    Latitude latitude;



    /**
     * Constucts a WeatherInformation object using initial values for latitude, longitude, minTemperature, and maxTemperature.
     *
     * @param latitude Initial value for {@link WeatherInformation#latitude}
     * @param longitude Initial value for {@link WeatherInformation#longitude}
     * @param minTemperature Sets {@link WeatherInformation#minTemperature} using {@link WeatherInformation#setMinTemperature(double)}
     * @param maxTemperature Sets {@link WeatherInformation#maxTemperature} using {@link WeatherInformation#setMaxTemperature(double)}
     */
    public WeatherInformation(Latitude latitude, Longitude longitude, double minTemperature, double maxTemperature) {
        if (minTemperature > maxTemperature) {
            throw new IllegalArgumentException("Minimum temperature must be below maximum temperature.");
        }

        this.latitude = latitude;
        this.longitude = longitude;
        setMinTemperature(minTemperature);
        setMaxTemperature(maxTemperature);
    }



    /**
     * @param maxTemperature {@link WeatherInformation#maxTemperature}
     */
    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    /**
     * @return {@link WeatherInformation#maxTemperature}
     */
    public double getMaxTemperature() {
        return maxTemperature;
    }


    /**
     * @param minTemperature {@link WeatherInformation#minTemperature}
     */
    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    /**
     * @return {@link WeatherInformation#minTemperature}
     */
    public double getMinTemperature() {
        return minTemperature;
    }



    /**
     * @return A string representation of the object, in the format "Weather Information: [
         latitude : 0°0"D
         longitude : 0°0"D
         record high : 0.0
         record low : 0.0
       ], ..."
     */
    public String toString() {
        return "Weather Information: [" + System.lineSeparator() +
                 "  latitude  : "   + this.latitude       + System.lineSeparator() +
                 "  longitude : "   + this.longitude      + System.lineSeparator() +
                 "  record high : " + this.maxTemperature + System.lineSeparator() +
                 "  record low : "  + this.minTemperature + System.lineSeparator() +
                 "]" + System.lineSeparator();
    }
}