import java.util.List;

/**
 * Created by Joseph on 24/05/2017.
 */
public class DistanceSet    {
    List<String> cityList;
    String destination;

    public DistanceSet(List<String> set, String destination) {
        this.cityList = set;
        this.destination = destination;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public String getDestination() {
        return destination;
    }
}
