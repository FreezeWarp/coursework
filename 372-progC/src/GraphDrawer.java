/*
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.RankDir;
import guru.nidi.graphviz.attribute.SingleAttributes;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Label;
import ork.sevenstates.apng.APNGSeqWriter;
import ork.sevenstates.apng.optimizing.ARGBSlicingSubtractor;

import static guru.nidi.graphviz.model.Factory.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.image.BufferedImage;
*/

import java.io.File;
import java.util.Collection;

/**
 * Ah, this was so fun! Initially created for debugging (and as an excuse to learn more about Java dependencies and packaging, as well as GraphViz itself), this creates an image representation of the graph using GraphViz, and then, because I really wanted to have fun with it, animates it with DFS's execution.
 * Note that I'm using a random APNG library I found on GitHub, which has the important caveat that APNGs are only supported on fairly modern browsers; Firefox, *very* recent versions of Chrome, and so-on: http://caniuse.com/#feat=apng. Edge does not support them (nor, of-course, Internet Explorer), nor does the Windows default file viewer. I will likely transcode to GIF to ensure the demo remains functional for all users, since I am totally using this as a demo in a future job interview.
 *
 * To use this, proper Maven configuration is required for the GraphViz executable in graphviz-java-master and the APNG library in apng-writer-master. In IntelliJ, this is as simple as opening "Maven Projects" and importing the pom.xml file from both folders, and then adding as a project dependency both folders. Additionally, the svg-salamander*.jar file in graphviz-java-master must be added to the project dependencies. Everything should, at this point, work.
 * For submission, I'm commenting the entire file out (and omitting graphviz-java-master), because the required imports won't be available. But my Git source should include everything at https://github.com/FreezeWarp/java-coursework/tree/master/372-progC/
 *
 * @author Joseph T. Parsons
 */

public class GraphDrawer {
    /**
     * The APNG writer object we're using/adapting.
     * {@link APNGSeqWriter} allows us to progressively update the image. In contrast, {@link ork.sevenstates.apng.APNGWriter} requires that the total frames be known in advance.
     */
    //APNGSeqWriter apngWriter;


    /**
     * A basic string list of connections that are "marked" and should be shown as visited. (A reasonably performant way of doing this without being overly complicated.)
     * Invoke {@link GraphDrawer#drawGraph(Collection, Node, Node)} to draw the graph while adding a new connection to this list.
     */
    //Collection<String> markedConnections = new TreeSet<>();


    /**
     * Create a new GraphDrawer, writing to the specified file.
     *
     * @param file The file the output images should be written to.
     */
    public GraphDrawer(File file) {
        //registerFile(file);
    }


    /**
     * Register a file for use with the graph drawer. Must call close() between invocations!
     *
     * @param file The file to write to.
     */
    public void registerFile(File file) {
        /*try {
            apngWriter = new APNGSeqWriter(file, -1, new ARGBSlicingSubtractor(.6d)); // -1 is the differential algorithm, which seems... ideal for this purpose.
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to initalise animated PNG writer; file not found (" + file.getAbsolutePath() + "). Expect errors.");
        } catch (IOException ex) {
            System.err.println("Unable to initalise animated PNG writer; IO exception. Expect errors.");
        }*/
    }


    /**
     * Adds a new animation frame using only a graph's data.
     *
     * @param graph The collection of nodes forming a graph.
     */
    public void drawGraph(Collection<Node> graph) {
        drawGraph(graph, null, null);
    }


    /**
     * Adds a new animation frame using a graph's data as well as a single connection to highlight.
     *
     * @param graph The collection of nodes forming a graph.
     * @param nodeFrom The source node of a connection to highlight.
     * @param nodeTo The destination node of a connection to highlight.
     */
    public void drawGraph(Collection<Node> graph, Node nodeFrom, Node nodeTo) {
        /*Graph g = graph("example1").directed().generalAttr().with(RankDir.LEFT_TO_RIGHT);

        if (nodeFrom != null && nodeTo != null) {
            markedConnections.add(nodeFrom.getName() + ";;" + nodeTo.getName()); // Collisions are possible, but pretty unlikely. Dunno why a nodename would have two semicolons in it, after all.
        }

        for (Node localNode : graph) {
            List<Map.Entry<Integer, Node>> distances = localNode.getConnectionsWithDistances();

            // We convert this to an array of Link objects, as successive link() calls from the same with() will overwrite eachother (for some reason). In contrast, a single link() call with an array of Links will not.
            guru.nidi.graphviz.model.Link[] newList = new guru.nidi.graphviz.model.Link[distances.size()];
            for (int i = 0; i < distances.size(); i++) {
                newList[i] = to(node(distances.get(i).getValue().getName())).with(Label.of(distances.get(i).getKey().toString()));

                if (markedConnections.contains(localNode.getName() + ";;" + distances.get(i).getValue().getName())) {
                    newList[i] = newList[i].with(Style.BOLD);
                }
            }

            // Now apply the relevant styling based on the node's current status in the DFS.
            // TODO: start/end times.
            g = g.with(node(localNode.getName()).link(newList).with(nodeColorToStyle(localNode.color)));
        }

        BufferedImage image = Graphviz.fromGraph(g).width(2048).height(1024).render(Format.PNG).toImage();

        try {
            apngWriter.writeImage(image, new Dimension(2048, 1024), 1, 1);
        } catch (Exception ex) {
            System.err.println("Failed to write image: " + ex);
        }*/
    }


    /**
     * Close the current GraphDrawer, flushing all changes to disk.
     */
    public void close() {
        /*try {
            apngWriter.close();
        } catch (IOException ex) {
            System.err.println("Unable to close animated PNG writer; IO exception. Expect errors:" + ex);
        }*/
    }


    /**
     * Convert from a Node's color information to a set of styles used by Graphviz.
     * (It took way too long to learn the Graphviz library's weird and quirky styling rules. They are not self-explanatory.)
     *
     * @param nodeColor The source nodecolor enum.
     * @return A list of SingleAttributes to use for styling.
     */
    /*
    public static SingleAttributes[] nodeColorToStyle(Node.NodeColor nodeColor) {

        // Note: Style.lineWidth(3).and(Style.FILLED) is the magic that makes a filled, outlined rectangle. Color.xxx is then the color of the outline, while Color.xxx.fill() is the color inside the outline.

        switch (nodeColor) {
            case black:
                return new SingleAttributes[]{Color.GREY, Style.lineWidth(3).and(Style.FILLED), Color.BLACK.fill(), Color.WHITE.font()};

            case grey:
                return new SingleAttributes[]{Color.BLACK, Style.lineWidth(3).and(Style.FILLED), Color.GREY.fill(), Color.BLACK.font()}; // Stupid American spellings... (Most I can deal with, what with being American and all, but you'll never convince me gray is a good spelling.)

            case white:
                return new SingleAttributes[]{Color.BLACK, Style.lineWidth(3).and(Style.FILLED), Color.WHITE.fill(), Color.BLACK.font()};

            default:
                return new SingleAttributes[]{};
        }
    }
    */
}
