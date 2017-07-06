import guru.nidi.graphviz.attribute.RankDir;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Renderer;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Label;
import guru.nidi.graphviz.model.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Factory.to;

/**
 * Ah, this was fun! Created for debugging, this creates an image representation of the graph using GraphViz.
 * I'm unfamiliar with Maven; for now, I'm including my full sources for graphviz-java, which I believe then requires proper Maven configuration. Ideally, because all of my IntelliJ configuration is synced, simply opening this with IntelliJ should automatically bring in all requirements.
 * For submission, I'm commenting the entire file out, because the required imports won't be available. But my Git source should include everything.
 *
 * @author Joseph T. Parsons
 */

public class GraphDrawer {
    public static void drawGraph(File file, ArrayList<Node> nodes) {
        Graph g = graph("example1").directed().generalAttr().with(RankDir.LEFT_TO_RIGHT);

        for (Node localNode : nodes) {
            List<Map.Entry<Integer, Node>> distances = localNode.getConnectionsWithDistances();

            // We convert this to an array of Link objects, as successive link() calls from the same with() will overwrite eachother (for some reason). In contrast, a single link() call with an array of Links will not.
            guru.nidi.graphviz.model.Link[] newList = new guru.nidi.graphviz.model.Link[distances.size()];

            for (int i = 0; i < distances.size(); i++) {
                newList[i] = to(node(distances.get(i).getValue().getName())).with(Label.of(distances.get(i).getKey().toString()));
            }

            g = g.with(node(localNode.getName()).link(newList));
        }

        Renderer render = Graphviz.fromGraph(g).height(1024).width(2048).render(Format.PNG);
        try {
            render.toFile(file);
        } catch (Exception ex) {
            System.err.println("Failed to write image: " + ex);
        }
    }
}
