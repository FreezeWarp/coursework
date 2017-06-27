import java.util.*;

/**
 * Created by joseph on 26/06/17.
 */
public class Simulation {
    public static void main(List<Node> graph) {
        /* Print Node Names In Order Processed
         * (This could, I believe, be a different order, since Java foreach doesn't gurantee order, but it'll probably be right.) */
        for (Node node : graph) {
            System.out.print(node.name);
        }
        System.out.println();
        System.out.println();



        /* Run DFS */
        DFS dfs = new DFS();
        dfs.search(graph);



        /* Print Start and Finish Times */
        for (Node node : graph) {
            System.out.println(node.name + " " + node.startTime + " " + node.endTime);
        }
        System.out.println();



        /* Determine Edges */
        for (Node node : graph) {
            // Sort the node's connections alphabetically (by default, they are sorted by distance)
            List<Node> alphabeticalConnections = new LinkedList<Node>(node.getConnections());
            Collections.sort(alphabeticalConnections, Comparator.comparing(Node::toString));

            // Iterate through alphabetically-sorted connections
            for (Node adj : alphabeticalConnections) {
                System.out.println(node.name + adj.name + " " +
                        (node.startTime > adj.startTime
                                ? (node.endTime > adj.endTime ? "C" : "B")
                                : (adj.previous.equals(node) ? "T" : "F")
                        )
                );
            }
        }
        System.out.println();



        /* Print Topological Sort */
        // Sort the Nodes Using a SortedMap and endTime as key
        SortedMap<Integer, Node> topologicalSort = new TreeMap<Integer, Node>();
        for (Node node : graph) {
            topologicalSort.put(-1 * node.endTime, node); // -1 as an easy hack to reverse the order.
        }

        // Iterate Through the SortedMap's values
        for (Node node : topologicalSort.values()) {
            System.out.print(node.name);
        }
        System.out.println();
        System.out.println();



        /* Strongly Connected Components */
        // Create New Graph of Nodes
        Map<String, Node> graphT = new HashMap<String, Node>();
        for (Node node : graph) {
            graphT.put(node.name, new Node(node)); // keeps name, start, and end times; color and previous attributes are reset.
        }

        // Add Vertexes to the Graph (Reversing Previous Connections
        for (int i = 0; i < graph.size(); i++) {
            for (Node connection : graph.get(i)) {
                graphT.get(connection.name).registerConnection(graphT.get(graph.get(i).name), Integer.MAX_VALUE - connection.endTime);
            }
        }

        // Sort the New Graph Values In Reverse Order of End Time
        List<Node> graphTSorted = new LinkedList<Node>(graphT.values());
        Collections.sort(graphTSorted, (o1, o2) -> o2.getEndTime() - o1.getEndTime()); // Sort the collection in the *reverse* order of ending time.

        // Run DFS on the Sorted Reversed Graph
        DFS dfsT = new DFS();
        dfsT.search(graphTSorted);

        // Iterate Through the Forests That Were Found During the DFS
        for (List<Node> dfsTree : dfsT.getDfsForest()) {
            for (Node node : dfsTree) {
                System.out.print(node);
            }
            System.out.println();
        }
    }
}
