import java.util.Random;

/**
 * Creates 10 each of a random latitude, random longitude, and random weather information using the prior latitude, longitude, and random highs and lows (adjusted for latitude).
 * Displays those objects.
 * Then alters the high/low of the last object and redisplays.
 *
 * @author Joseph T. Parsons <josephtparsons@gmail.com>
 */
public class Driver {
    public static void main(String args[]) {
        /* Create utility objects. */
        Random rand = new Random();



        /* Test Invalid Objects */
        try {
            new Latitude(60, 90, "S"); System.out.println("Invalid latitude found.");
        } catch (Exception ex) { System.out.println("Invalid latitude test passed."); }

        try {
            new Latitude(60, 60, "E"); System.out.println("Invalid latitude found.");
        } catch (Exception ex) { System.out.println("Invalid latitude test passed."); }

        try {
            new Latitude(30, -5, "S"); System.out.println("Invalid latitude found.");
        } catch (Exception ex) { System.out.println("Invalid latitude test passed."); }

        try {
            new Latitude(100, 30, "S"); System.out.println("Invalid latitude found.");
        } catch (Exception ex) { System.out.println("Invalid latitude test passed."); }

        try {
            new Latitude(-10, 30, "S"); System.out.println("Invalid latitude found.");
        } catch (Exception ex) { System.out.println("Invalid latitude test passed."); }
        
        try {
            new Longitude(60, 90, "E"); System.out.println("Invalid Longitude found.");
        } catch (Exception ex) { System.out.println("Invalid Longitude test passed."); }

        try {
            new Longitude(60, 60, "S"); System.out.println("Invalid Longitude found.");
        } catch (Exception ex) { System.out.println("Invalid Longitude test passed."); }

        try {
            new Longitude(30, -5, "E"); System.out.println("Invalid Longitude found.");
        } catch (Exception ex) { System.out.println("Invalid Longitude test passed."); }

        try {
            new Longitude(200, 30, "E"); System.out.println("Invalid Longitude found.");
        } catch (Exception ex) { System.out.println("Invalid Longitude test passed."); }

        try {
            new Longitude(-10, 30, "E"); System.out.println("Invalid Longitude found.");
        } catch (Exception ex) { System.out.println("Invalid Longitude test passed."); }



        /* Generate random latitudes and longitudes.
         * While the coordinates are random in their range, we ensure that we alternate between cardinal directions in each, resulting in equal numbers of both. */
        Latitude[] latitudes = new Latitude[10];
        for (int i = 0; i < latitudes.length; i++) {
            latitudes[i] = new Latitude(rand.nextInt(90), rand.nextInt(60), i % 2 == 0 ? "N" : "S");
        }

        Longitude[] longitudes = new Longitude[10];
        for (int i = 0; i < longitudes.length; i++) {
            longitudes[i] = new Longitude(rand.nextInt(180), rand.nextInt(60), i % 2 == 0 ? "E" : "W");
        }


        /* Generate weather informations use the previously create latitutudes and longitudes.
         * We create semi-random highs and lows here: they should roughly correspond (in celsius) with what you'd expect given the latitude. */
        WeatherInformation[] weatherInformations = new WeatherInformation[10];
        for (int i = 0; i < weatherInformations.length; i++) {
            weatherInformations[i] = new WeatherInformation(
                latitudes[i],
                longitudes[i],
                -.5 * (latitudes[i].getDegree()) - rand.nextInt(20), // This is reasonably close to what you'd expect, assuming celsius.
                .5 * (90 - latitudes[i].getDegree()) + rand.nextInt(20) // This is reasonably close to what you'd expect, assuming celsius.
            );

            System.out.println(weatherInformations[i]);
        }


        /* Test changing the minimum and maximum temperature of the last weather information. */
        System.out.println("A calamity has befallen... this place!");

        weatherInformations[weatherInformations.length - 1].setMinTemperature(-90);
        weatherInformations[weatherInformations.length - 1].setMaxTemperature(110);

        System.out.println(weatherInformations[weatherInformations.length - 1]);
        System.out.println("(They're probably all dead. That or raptured. Iunno how this stuff works.)");
    }
}
