import java.util.*;

/**
 * Created by joseph on 26/06/17.
 */
public class Node implements Iterable<Node> {
    /* In Traversal */
    enum NodeColor {
        white, grey, black;
    }
    NodeColor color = NodeColor.white;
    Node previous;

    /* Before Traversal */
    int startTime;
    int endTime;
    String name;
    HashMap<Integer, List<Node>> connections = new HashMap<Integer, List<Node>>();

    public Node(String name) {
        this.name = name;
    }

    public Node(Node node) {
        this.name = node.name;
        this.endTime = node.endTime;
        this.startTime = node.startTime;
    }

    public void registerConnection(Node node, int distance) {
        if (this.connections.containsKey(distance)) {
            this.connections.get(distance).add(node);
        }
        else {
            List<Node> list = new LinkedList<Node>();
            list.add(node);
            this.connections.put(distance, list);
        }
    }

    public String toString() {
        return name;
    }

    public int getEndTime() {
        return endTime;
    }

    /**
     * An iterator that iterates through the list of Map entries (values).
     *
     * @return the next value in the singletonMap
     */
    public List<Node> getConnections() {
        Collection<List<Node>> values = connections.values();
        List<Node> listValues = new LinkedList<Node>();

        for (List<Node> list : values) {
            listValues.addAll(list);
        }

        return listValues;
    }

    public Iterator<Node> iterator() {
        return getConnections().iterator();
    }
}
