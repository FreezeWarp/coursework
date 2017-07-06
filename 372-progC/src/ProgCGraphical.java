import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Renderer;
import guru.nidi.graphviz.model.*;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Factory.to;

/**
 * Graphical components of p    rogram -- solely the file opener. If unavailable, file can be specified as the sole argument to the program.
 */
public class ProgCGraphical extends Application {
    public static Stage primaryStage;
    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        File[] fileHandles = ProgC.openFile(primaryStage);
        ProgC.passData(fileHandles, this.getParameters().getUnnamed().contains("--measurementMode"));
    }

    public static void drawGraph(File file, ArrayList<Node> nodes) {
        Graph g = graph("example1").directed();

        for (Node localNode : nodes) {
            List<Map.Entry<Integer, Node>> distances = localNode.getConnectionsWithDistances();

            // We convert this to an array of Strings, as successive link() calls from the same with() will overwrite eachother (for some reason).
            guru.nidi.graphviz.model.Link[] newList = new guru.nidi.graphviz.model.Link[distances.size()];

            for (int i = 0; i < distances.size(); i++) {
                newList[i] = to(node(distances.get(i).getValue().getName())).with(Label.of(distances.get(i).getKey().toString()));
            }

            g = g.with(node(localNode.getName()).link(newList));
        }

        Renderer render = Graphviz.fromGraph(g).height(1000).width(1600).render(Format.PNG);
        try {
            render.toFile(file);
        } catch (Exception ex) {
            System.out.println("Failed to write image.");
        }

        Image image = SwingFXUtils.toFXImage(render.toImage(), null);

        ImageView iv1 = new ImageView();
        iv1.setImage(image);

        Group root = new Group();
        Scene scene = new Scene(root);
        HBox box = new HBox();
        box.getChildren().add(iv1);
        root.getChildren().add(box);

        primaryStage.setTitle("ImageView");
        primaryStage.setWidth(1600);
        primaryStage.setHeight(1000);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}