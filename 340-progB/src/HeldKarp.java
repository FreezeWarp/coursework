import java.util.*;

/**
 * Performs Held Karp travelling salesman solution based on instructor-provided pseudocode (same as the code from Wikipedia, I believe). I'd give a better summary, if I understood exactly what HeldKarp does.
 * TODO: Prune the connectionRoutes and connections hashmaps when entries of a certain size are no longer needed -- that is, whenever we advance visitCount in the outer for loop. It's not really a problem for us until exceeding somewhere around 10 cities, so I didn't bother doing it.
 * (General cleanup would also be nice to meet my normal standards, but this was a pain to write, and I really don't want to spend any extra time. ...Admittedly, I only probably spent 8-12 hours on it, but that's a lot!)
 *
 * @author Joseph T. Parsons
 */
public class HeldKarp {
    /**
     * A map connecting sets of cities and the optimal distance needed to visit all of them.
     */
    Map<List, Integer> connections = new HashMap<List, Integer>();

    /**
     * A map connecting set of cities and the optimal route needed to visit all of them.
     * TODO: eventually, should be merged into its own datatype with connections, but that would take more time than I want to spend on this.
     */
    Map<List, String> connectionRoutes = new HashMap<List, String>();

    /**
     * A map of the direct distances between two cities, indexed by a string key in the format "city1-city2"
     */
    Map<String, Integer> directDistances;

    /**
     * A list of city names.
     */
    List<String> cities;


    /**
     * Runs a HeldKarp routine from the input list of cities and map of direct distances.
     *
     * @param cities The list of city names.
     * @param directDistances The distances required to visit cities, indexed by a string key in the format "city1-city2"
     */
    public HeldKarp(List<String> cities, Map<String, Integer> directDistances) {
        this.directDistances = directDistances;
        this.cities = cities;

        /*
         * Set initial directDistances between the origin city (which will be the first city index) and all other cities.
         */
        for (int cityNumber = 1; cityNumber < cities.size(); cityNumber++) {
            String cityKey = cities.get(0) + "-" + cities.get(cityNumber);

            setConnection(
                Arrays.asList(cities.get(cityNumber)), // The list of cities -- in this case, only one.
                directDistances.get(cityKey), // The distance to get between the cities.
                cityKey // The route taken.
            );
        }

        System.out.println();

        // First visit 2 cities, then 3, then 4, ...
        for (int visitCount = 2; visitCount < cities.size(); visitCount++) {
            System.out.println("s = " + visitCount);

            // Find every combination of size visitCount
            Permute.combinations(cities.subList(1, cities.size()), visitCount).forEach((citySet)->{
                // Find the minimum subset for the citySet
                for (int k = 0; k < citySet.size(); k++) { // Performs the for-all k
                    int minimum = Integer.MAX_VALUE;
                    String route = "";

                    for (int m = 0; m < citySet.size(); m++) { // Performs the minimum operation
                        if (k == m) continue; // I honestly don't know why.

                        // Create the city list to test.
                        List cityList = new ArrayList<Object>(citySet);
                        cityList.remove(citySet.get(k)); // Subtract k from the cityList, since we are using the subsolution of citySet - {k} to solve this problem efficiently.

                        cityList = getCitySet(cityList, citySet.get(m));

                        int minimumCheck = getDistanceFromSet(cityList) + getDirectDistance((String) citySet.get(k), (String) citySet.get(m));

                        // Record the minimum.
                        if (minimumCheck < minimum) {
                            minimum = minimumCheck;
                            route = getRouteFromSet(cityList) + "-" + citySet.get(k);
                        }
                    }

                    // Shift K to the end, to make it our destination.
                    setConnection(getCitySet(
                        new ArrayList<Object>(citySet),
                        citySet.get(k)
                    ), minimum, route);
                }
            });

            System.out.println();
        }


        /* Find the best path, where we end on a specific city and then return to our start city. */
        int minimum = Integer.MAX_VALUE; // Record the minimum distance from all combinations.
        int minimumRoute = 0; // Record the city number that produced the minimum.

        System.out.println("Opt:");
        for (int i = 1; i < cities.size(); i++) {
            List citySetTest = getCitySet(
                new ArrayList<Object>(cities.subList(1, cities.size())),
                cities.get(i)
            );

            // Display each path tested.
            int test = getDistanceFromSet(citySetTest) + getDirectDistance(cities.get(0), cities.get(i));
            System.out.println("C({" + citySetTest + "}, " + citySetTest.get(citySetTest.size() - 1) + ") to 0 = " + test +
                    "; route = " + getRouteFromSet(citySetTest) + "-" + cities.get(0));

            // Record the optimal path.
            if (test < minimum) {
                minimum = test;
                minimumRoute = i;
            }
        }

        System.out.println("Opt = " + minimum + "; route = " + getRouteFromSet(getCitySet(cities.subList(1, cities.size()), cities.get(minimumRoute))) + "-" + cities.get(0));
    }


    /**
     * Gets a special list representation that can be used to store a result of C().
     * (Right now, it's just a normal list, except the destination is moved to the last position in the list.)
     * (Yes, it really should be an object. This is a lot easier, and I don't want to introduce any extra abstractions to avoid making this more complicated than it already is.)
     *
     * @param citySet A list of cities containing all cities being visited.
     * @param destination The destination city specifically. Doesn't have to be in citySet, but can be.
     * @return A special representation that can be used with other functions.
     */
    private List getCitySet(List citySet, String destination) {
        // Copy the list. Don't want to mutate the original.
        List setNew = new ArrayList<String>(citySet);

        // We move the destination to the end of the list.
        setNew.remove(destination);
        setNew.add(destination);

        // And sort the remainder of the list, so we don't have duplicate representations.
        Collections.sort(setNew.subList(0, setNew.size() - 1));

        // (Again, yes, and object would be better. But I want to keep this as simple as possible.)

        return setNew;
    }


    /**
     * Registers the optimal route for visiting the cities in set (formatted as returned from getCitySet).
     *
     * @param set The set of cities visited, as created by {@link HeldKarp#getCitySet(List, String)}.
     * @param value The optimum distance for visiting the cities in set.
     * @param route The route taken to visit the cities in set.
     */
    private void setConnection(List set, int value, String route) {
        connections.put(set, value);
        connectionRoutes.put(set, route);

        System.out.println("C({" + set + "} , " + set.get(set.size() - 1) + ") = " + value + "; route = " + route);
    }


    /**
     * Gets the distance needed to visit the cities in set. IS NOT GENERATED: the result must have already been registered with {@link HeldKarp#setConnection(List, int, String)} to be retrievable here.
     *
     * @param set The cities visited, as returned by {@link HeldKarp#getCitySet(List, String)}
     * @return The optimal distance needed to visit those cities.
     */
    private int getDistanceFromSet(List set) {
        Integer value = connections.get(set);

        if (value == null) {
            throw new IndexOutOfBoundsException("No distance found for: " + set);
        }

        return value;
    }


    /**
     * Gets the route used to optimally visit the cities in set. IS NOT GENERATED: the result must have already been registered with {@link HeldKarp#setConnection(List, int, String)} to be retrievable here.
     *
     * @param set The cities visited, as returned by {@link HeldKarp#getCitySet(List, String)}
     * @return The optimal route to visit those cities.
     */
    private String getRouteFromSet(List set) {
        String value = connectionRoutes.get(set);

        if (value == null) {
            throw new IndexOutOfBoundsException("No distance found for: " + set);
        }

        return value;
    }


    /**
     * Gets the distance between two cities. Order of parameters does not matter.
     *
     * @param place1 The name of the first city.
     * @param place2 The name of the second city.
     * @return The distance between them.
     */
    private int getDirectDistance(String place1, String place2) {
        // Obviously, I'm just trying both hashes, but the "right" way to do this is to sort them alphabetically.
        if (directDistances.containsKey(place1 + "-" + place2))
            return directDistances.get(place1 + "-" + place2);

        else if (directDistances.containsKey(place2 + "-" + place1))
            return directDistances.get(place2 + "-" + place1);

        else
            throw new IndexOutOfBoundsException("No distance found for: " + place2 + ", " + place1);
    }
}

