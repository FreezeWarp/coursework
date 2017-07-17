import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * The code for a DFS search, as well as incidental information resulting from said DFS search.
 * (At present, said incidental information is just the DFS forest. Nodes are marked directly during traversal, instead of copies being kept here. Worse design, but so, so much easier and even cleaner, really.)
 *
 * @author Joseph T. Parsons
 */
public class DFS {
    /**
     * The time observed during DFS operations.
     */
    int time = 0;

    /**
     * The forest found during the DFS search. Every sublist is the parent node of a seperate tree.
     */
    List<List<Node>> dfsForest = new LinkedList<List<Node>>();

    /**
     * If registered, we will invoke graphDrawer.draw() after every color change.
     */
    GraphDrawer graphDrawer;

    /**
     * Initalises DFS and runs {@link DFS#search(Collection)}.
     *
     * @param graph The graph (collection of nodes) to use for searching.
     */
    public DFS(Collection<Node> graph, GraphDrawer graphDrawer) {
        this.graphDrawer = graphDrawer;
        this.search(graph);
    }

    /**
     * Initalises DFS and runs {@link DFS#search(Collection)}.
     *
     * @param graph The graph (collection of nodes) to use for searching.
     */
    public DFS(Collection<Node> graph) {
        this(graph, null);
    }

    /**
     * Performs a DFS search on the graph, marking nodes with color and times.
     *
     * @param graph The graph (collection of nodes) to use for searching.
     */
    public void search(Collection<Node> graph) {
        for (Node node : graph) {
            if (node.color == Node.NodeColor.white) {
                dfsForest.add(new LinkedList<Node>());

                dfsVisit(graph, node);
            }
        }
    }

    /**
     * The recursive bit of {@link DFS#search(Collection)}.
     *
     * @param graph The graph being operated on.
     * @param node The node we recursed from.
     */
    private void dfsVisit(Collection<Node> graph, Node node) {
        /* Keep Track of Strongly Connected Components
         * We could process the previous nodes as well, but that would be a lot harder than doing it inside dfsVisit. */
        dfsForest.get(dfsForest.size() - 1).add(node);

        /* Mark Node */
        node.startTime = ++time;
        node.color = Node.NodeColor.grey; // Mark the node as being processed.

        /* Refresh Graph Drawer to Reflect Color Change */
        if (graphDrawer != null) graphDrawer.drawGraph(graph);

        for (Node adj : node) { // For every adjacent node...
            if (adj.color == Node.NodeColor.white) { // Only process if yet unvisited.
                adj.previous = node; // Mark the previous node as the one we recursed from.

                /* Refresh Graph Drawer to Emphasise Path Being Followed */
                if (graphDrawer != null) graphDrawer.drawGraph(graph, node, adj);

                dfsVisit(graph, adj); // And recurse into that node.
            }
        }

        node.color = Node.NodeColor.black; // Mark the node as finished. (Technically redundant, but used by GraphDrawer.)
        node.endTime = ++time;

        /* Refresh Graph Drawer to Reflect Color Change */
        if (graphDrawer != null) graphDrawer.drawGraph(graph);
    }

    /**
     * @return The DFS forest created from the previous iteration.
     */
    public List<List<Node>> getDfsForest() {
        return dfsForest;
    }
}
