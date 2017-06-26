import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by joseph on 26/06/17.
 */
public class DFS {
    int time = 0;
    List<List<Node>> dfsForest = new LinkedList<List<Node>>();

    public void search(Collection<Node> graph) {
        for (Node node : graph) {
            if (node.color == Node.NodeColor.white) {
                dfsForest.add(new LinkedList<Node>());

                dfsVisit(graph, node);
            }
        }
    }

    private void dfsVisit(Collection<Node> graph, Node node) {
    /* Keep Track of Strongly Connected Components
     * We could process the previous nodes as well, but that would be a lot harder than doing it inside dfsVisit. */
        dfsForest.get(dfsForest.size() - 1).add(node);

        node.startTime = ++time;
        node.color = Node.NodeColor.grey;

        for (Node adj : node) {
            if (adj.color == Node.NodeColor.white) {
                adj.previous = node;

                dfsVisit(graph, adj);
            }
        }

        node.color = Node.NodeColor.black;
        node.endTime = ++time;
    }

    public List<List<Node>> getDfsForest() {
        return dfsForest;
    }
}
