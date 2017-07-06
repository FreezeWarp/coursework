import java.util.*;

/**
 * Created by joseph on 26/06/17.
 */
public class Node implements Iterable<Node> {
    /* Attributes Used for General Graph Information */
    /**
     * The node's name. Should be unique in a graph.
     */
    String name;

    /**
     * A hashmap of connections. It's a little bit funky:
     * We index by the distance travelled (allowing easily-sorted iterations).
     * But to prevent collisions, we store a list of all nodes with the given distance, instead of the nodes themselves.
     */
    Map<Integer, List<Node>> connections = new TreeMap<Integer, List<Node>>();


    /* Attributes Used for DFS Search */
    /**
     * The time the node was visited in a DFS search.
     */
    int startTime;

    /**
     * The time the node was last used in a DFS search.
     */
    int endTime;

    /**
     * Colors the node can be marked as. Technically, grey and black are redundant.
     */
    enum NodeColor {
        white, grey, black;
    }

    /**
     * The color of the node in the execution of a DFS search.
     */
    NodeColor color = NodeColor.white;

    /**
     * The parent node used during a DFS search to visit this one.
     */
    Node previous;


    /**
     * A basic constructor to set a node's name.
     *
     * @param name
     */
    public Node(String name) {
        this.name = name;
    }

    /**
     * A copy constructor that only keeps non-DFS related fields (at present, only the name).
     *
     * @param node
     */
    public Node(Node node) {
        this.name = node.name;
    }

    /**
     * A constructor that performs that copy constructor and also sets the start and end times.
     *
     * @param node
     * @param startTime
     * @param endTime
     */
    public Node(Node node, int startTime, int endTime) {
        this(node);
        this.startTime = startTime;
        this.endTime = endTime;
    }


    /**
     * Add an outgoing connection from this node to the specified node.
     *
     * @param node The node being visited by this node.
     * @param distance The distance of the connection.
     */
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

    /**
     * @return A string representation of the node. Minimal, for now.
     */
    public String toString() {
        return name;
    }

    /**
     * @return {@link Node#name}
     */
    public String getName() {
        return name;
    }

    /**
     * @return {@link Node#endTime}
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * @return {@link Node#endTime}
     */
    public int getStartTime() {
        return startTime;
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


    /**
     * Returns a list of Entries with both distance and outgoing node from this node.
     *
     * @return A list of Entry pairs containing all connections.
     */
    public List<Map.Entry<Integer, Node>> getConnectionsWithDistances() {
        Collection<List<Node>> values = connections.values();
        List<Map.Entry<Integer, Node>> listValues = new LinkedList<>();

        for (Map.Entry<Integer, List<Node>> pair : connections.entrySet()) {
            for (Node node : pair.getValue()) {
                listValues.add(new AbstractMap.SimpleEntry<Integer, Node>(pair.getKey(), node));
            }
        }

        return listValues;
    }


    /**
     * A generic iterator, using getConnections, to return nodes connected to from this one.
     */
    public Iterator<Node> iterator() {
        return getConnections().iterator();
    }
}
