import java.util.*;

/**
 * Given an input graph (which is just a collection of nodes that presumably have connections to each-other), calculate various DFS properties.
 * This will list node names in order processed, print the DFS start and finish time, calculate edge types of DFS tree based on start/finish times, print the topological sort of nodes, and then -find the strongly-connected components.
 *
 * @author Joseph T. Parsons
 */
public class Simulation {
    public static void main(List<Node> graph) {
        main(graph, null);
    }


    public static void main(List<Node> graph, GraphDrawer graphDrawer) {
        /* Print Node Names In Order Processed */
        for (Node node : graph) {
            System.out.print(node.name);
        }
        System.out.println();
        System.out.println();



        /* Run DFS */
        DFS dfs = new DFS(graph, graphDrawer); // Run the DFS algorithm, marking nodes as we go with previous and color.



        /* Print Start and Finish Times */
        for (Node node : graph) {
            System.out.println(node.name + " " + node.startTime + " " + node.endTime);
        }
        System.out.println();



        /* Determine Edges */
        for (Node node : graph) {
            // Sort the node's connections alphabetically (by default, they are sorted by distance)
            List<Node> alphabeticalConnections = new LinkedList<Node>(node.getConnections());
            Collections.sort(alphabeticalConnections, Comparator.comparing(Node::getName));

            // Iterate through alphabetically-sorted connections
            for (Node adj : alphabeticalConnections) {
                System.out.println(node.name + adj.name + " " +
                        (node.equals(adj) ? "S" : // This is an edge case that was detected from erroneous input, but is nonetheless useful to have.
                            (node.startTime > adj.startTime // You can look up the classifications, but if we visited this node before the adjacent node, the connection is either C or B. Otherwise, T or F.
                                    ? (node.endTime > adj.endTime ? "C" : "B")
                                    : (adj.previous.equals(node) ? "T" : "F") // Notably, this isn't the only way of doing this (we could mark Tree nodes as we come across them instead), but is arguably more general-purpose and cleaner.
                            )
                        )
                );
            }
        }
        System.out.println();



        /* Print Topological Sort */
        // Sort the Nodes Using a SortedMap and endTime as key
        SortedMap<Integer, Node> topologicalSort = new TreeMap<Integer, Node>(); // Tree maps are sorted by key, hence we'll iterate these back in order of the key.
        for (Node node : graph) { // For every node in the graph...
            topologicalSort.put(-1 * node.endTime, node); // -1 as an easy hack to reverse the order.
        }

        // Iterate Through the SortedMap's values
        for (Node node : topologicalSort.values()) { // For every node in the sorted list...
            System.out.print(node.name);
        }
        System.out.println();
        System.out.println();



        /* Strongly Connected Components
         * We do this by inverting our existing connections data to minimise coupling, but you could also have just read the input graph flipped from its file. */
        // Create New Graph of Nodes
        Map<String, Node> graphT = new HashMap<String, Node>();
        for (Node node : graph) { // For every node in the graph...
            graphT.put(node.name, new Node(node)); // keeps name, start, and end times; color and previous attributes are reset, since these exist only as temporary DFS helpers.
        }

        // Add Vertexes to the Graph (Reversing Previous Connections)
        for (int i = 0; i < graph.size(); i++) { // For every node in the graph...
            for (Node connection : graph.get(i)) { // For every node connected to the node...
                graphT.get(connection.name).registerConnection(graphT.get(graph.get(i).name), Integer.MAX_VALUE - connection.endTime); // Note that to ensure the correct order of execution, we have the distances in flipped order. There are other approaches to this, but I thought it was the cleanest.
            }
        }

        // Sort the New Graph Values In Reverse Order of End Time
        List<Node> graphTSorted = new LinkedList<Node>(graphT.values()); // Get them in an easily sortable format. (Not yet sorted.)
        Collections.sort(graphTSorted, (o1, o2) -> o2.getEndTime() - o1.getEndTime()); // Sort the collection in the *reverse* order of ending time.

        // Run DFS on the Sorted Reversed Graph
        DFS dfsT = new DFS(graphTSorted);

        // Iterate Through the Forests That Were Found During the DFS
        for (List<Node> dfsTree : dfsT.getDfsForest()) {
            Collections.sort(dfsTree, Comparator.comparing(Node::getName)); // Sort the collection in alphabetical order. (I could probably do this better, but I only just noticed I wasn't doing this a bit before deadline.)

            for (Node node : dfsTree) {
                System.out.print(node);
            }
            System.out.println();
        }
    }
}
