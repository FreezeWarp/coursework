import java.util.*;

/**
 * Created by Joseph on 17/05/2017.
 */
public class HeldKarp {
    Map<List, Integer> directConnections = new HashMap<List, Integer>();
    Map<List, String> connectionRoutes = new HashMap<List, String>();

    Map<String, Integer> directDistances;
    List<String> cities;

    public HeldKarp(List<String> cities, Map<String, Integer> directDistances) {
        this.directDistances = directDistances;
        this.cities = cities;

        //int minimumDistance = directDistances.get(startCity + "-" + endCity);

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
                //System.out.println("City Set: " + citySet);

                // Find the minimum subset for the citySet
                for (int k = 0; k < citySet.size(); k++) {
                    int minimum = Integer.MAX_VALUE;
                    String route = "";

                    for (int m = 0; m < citySet.size(); m++) {
                        if (k == m) continue;

                        List cityList = new ArrayList<Object>(citySet);

                        cityList.remove(citySet.get(k)); // Subtract k from the cityList

                        // Shift m from the cityList to the end, to signify that it is our destination point
                        cityList.remove(citySet.get(m));
                        cityList.add(citySet.get(m));

                        int minimumCheck = getDistanceFromSet(cityList) + getDirectDistance((String) citySet.get(k), (String) citySet.get(m));

                        if (minimumCheck < minimum) {
                            minimum = minimumCheck;
                            route = getRouteFromSet(cityList) + "-" + citySet.get(k);
                        }
                    }

                    List citySetNew = new ArrayList<Object>(citySet);

                    // Shift K to the end, to make it our destination.
                    citySetNew.remove(citySet.get(k));
                    citySetNew.add(citySet.get(k));
                    setConnection(citySetNew, minimum, route);
                }
            });

            System.out.println();
        }

        System.out.println("Opt:");
        int minimum = Integer.MAX_VALUE;
        for (int i = 1; i < cities.size(); i++) {
            List testList = new ArrayList<Object>(cities.subList(1, cities.size()));

            // Shift i to the end of the list, signifying that it is the destination.
            testList.remove(cities.get(i));
            testList.add(cities.get(i));

            int test = getDistanceFromSet(testList) + getDirectDistance(cities.get(0), cities.get(i));
            System.out.println("C({" + testList + "}, " + testList.get(testList.size() - 1) + ") to 0 = " + test);

            if (test < minimum) minimum = test;
        }

        System.out.println("Opt = " + minimum);
    }


    public void setConnection(List set, int value, String route) {
        List setNew = new ArrayList<String>(set);
        Collections.sort(setNew.subList(0, setNew.size() - 1));
        directConnections.put(setNew, value);
        connectionRoutes.put(setNew, route);

        System.out.println("C({" + setNew + "} , " + set.get(set.size() - 1) + ") = " + value + "; route = " + route);
    }


    public int getDistanceFromSet(List set) {
        List setNew = new ArrayList<String>(set);
        Collections.sort(setNew.subList(0, setNew.size() - 1));
        Integer value = directConnections.get(setNew);

        if (value == null) {
            throw new IndexOutOfBoundsException("No distance found for: " + set);
        }

        return value;
    }


    public String getRouteFromSet(List set) {
        List setNew = new ArrayList<String>(set);
        Collections.sort(setNew.subList(0, setNew.size() - 1));
        String value = connectionRoutes.get(setNew);

        if (value == null) {
            throw new IndexOutOfBoundsException("No distance found for: " + set);
        }

        return value;
    }


    public int getDirectDistance(String place1, String place2) {
        if (directDistances.containsKey(place1 + "-" + place2)) {
            return directDistances.get(place1 + "-" + place2);
        }
        else if (directDistances.containsKey(place2 + "-" + place1)) {
            return directDistances.get(place2 + "-" + place1);
        }
        else {
            throw new IndexOutOfBoundsException("No distance found for: " + place2 + ", " + place1);
        }
    }
}

