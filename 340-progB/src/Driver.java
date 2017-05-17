import java.util.*;

/**
 * Created by joseph on 17/05/17.
 */
public class Driver {
    static Map <List, Integer> connections2 = new HashMap<List, Integer>();
    static Map <List, String> connectionRoutes = new HashMap<List, String>();
    static Map<String, Integer> connections = new HashMap<String, Integer>();

    public static void main(String args[]) {
        /* Connections will be a map of different city connections and their distances.
         * The key will be a concatenation of the alphabetical first city, a dash, and the alphabetical last city.
         * The value will be the distance between the two cities. */
        connections.put("Dul-Mpl", 155);
        connections.put("Dul-Rch", 227);
        connections.put("Dul-SC", 146);
        connections.put("Mpl-Rch", 83);
        connections.put("Mpl-SC", 68);
        connections.put("Rch-SC", 152);

        /*
         * List should end with k
         */

        List<String> cities = new ArrayList<String>(Arrays.asList("Dul", "Rch", "Mpl", "SC"));
        //String startCity = "Dul";
        //String endCity = "SC";
        //String[] visit = {"Rch", "Mpl"};

        //int minimumDistance = connections.get(startCity + "-" + endCity);

        for (int cityNumber = 1; cityNumber < cities.size(); cityNumber++) {
            setConnection(Arrays.asList(cities.get(cityNumber)), connections.get(cities.get(0) + "-" + cities.get(cityNumber)), cities.get(0) + "-" + cities.get(cityNumber));
        }

        System.out.println();

        // First visit 2 cities, then 3, then 4, ...
        for (int visitCount = 2; visitCount < cities.size(); visitCount++) {
            System.out.println("s = " + visitCount);

            // Find every combination of size visitCount
            Combinations.combinations(cities.subList(1, cities.size()), visitCount).forEach((citySet)->{
                //System.out.println("City Set: " + citySet);

                // Find the minimum subset for the citySet
                for (int k = 0; k < citySet.size(); k++) {
                    int minimum = 0xFFFFFFF;
                    String route = "";

                    for (int m = 0; m < citySet.size(); m++) {
                        if (k == m) continue;

                        List testList = new ArrayList<Object>(citySet);
                        testList.remove(citySet.get(k));
                        testList.remove(citySet.get(m));
                        testList.add(citySet.get(m));

                        //System.out.println("Test List: " + testList);

                        int test = getDistance2(testList) + getDistance((String) citySet.get(k), (String) citySet.get(m));

                        if (test < minimum) {
                            minimum = test;
                            route = getRoute(testList) + "-" + citySet.get(k);
                        }
                    }

                    List citySetNew = new ArrayList<Object>(citySet);
                    citySetNew.remove(citySet.get(k));
                    citySetNew.add(citySet.get(k));
                    setConnection(citySetNew, minimum, route);
                }
            });

            System.out.println();
        }

        System.out.println("Opt:");
        int minimum = 0xFFFFFFF;
        for (int i = 1; i < cities.size(); i++) {
            List testList = new ArrayList<Object>(cities.subList(1, cities.size()));
            testList.remove(cities.get(i));
            testList.add(cities.get(i));

            int test = getDistance2(testList) + getDistance(cities.get(0), cities.get(i));
            System.out.println("C({" + testList + "}, " + testList.get(testList.size() - 1) + ") to 0 = " + test);

            if (test < minimum) minimum = test;
        }

        System.out.println("Opt = " + minimum);
    }


    public static void setConnection(List set, int value, String route) {
        List setNew = new ArrayList<String>(set);
        Collections.sort(setNew.subList(0, setNew.size() - 1));
        connections2.put(setNew, value);
        connectionRoutes.put(setNew, route);

        System.out.println("C({" + setNew + "} , " + set.get(set.size() - 1) + ") = " + value + "; route = " + route);
    }


    public static int getDistance2(List set) {
        List setNew = new ArrayList<String>(set);
        Collections.sort(setNew.subList(0, setNew.size() - 1));
        Integer value = connections2.get(setNew);

        if (value == null) {
            throw new IndexOutOfBoundsException("No distance found for: " + set);
        }

        return value;
    }


    public static String getRoute(List set) {
        List setNew = new ArrayList<String>(set);
        Collections.sort(setNew.subList(0, setNew.size() - 1));
        String value = connectionRoutes.get(setNew);

        if (value == null) {
            throw new IndexOutOfBoundsException("No distance found for: " + set);
        }

        return value;
    }


    public static int getDistance(String place1, String place2) {
        if (connections.containsKey(place1 + "-" + place2)) {
            return connections.get(place1 + "-" + place2);
        }
        else if (connections.containsKey(place2 + "-" + place1)) {
            return connections.get(place2 + "-" + place1);
        }
        else {
            throw new IndexOutOfBoundsException("No distance found for: " + place2 + ", " + place1);
        }
    }
}
