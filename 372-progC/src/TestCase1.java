import java.util.*;

/**
 * Created by joseph on 26/06/17.
 */
public class TestCase1 {
    public static void main(String args[]) {
        /* Initialise Map */
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");

        nodeA.registerConnection(nodeB, 3);
        nodeA.registerConnection(nodeC, 2);
        nodeA.registerConnection(nodeE, 5);

        nodeB.registerConnection(nodeA, 4);
        nodeB.registerConnection(nodeC, 5);
        nodeB.registerConnection(nodeD, 3);

        nodeD.registerConnection(nodeA, 6);
        nodeD.registerConnection(nodeC, 5);
        nodeD.registerConnection(nodeE, 1);

        nodeE.registerConnection(nodeB, 6);
        nodeE.registerConnection(nodeC, 4);
        nodeE.registerConnection(nodeD, 2);

        List<Node> graph = new LinkedList<Node>();
        graph.add(nodeA);
        graph.add(nodeB);
        graph.add(nodeC);
        graph.add(nodeD);
        graph.add(nodeE);

        Simulation.main(graph);
    }
}
