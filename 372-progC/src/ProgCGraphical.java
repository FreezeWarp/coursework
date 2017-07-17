import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import java.io.File;

/**
 * Graphical components of the program -- solely the file opener.
 * This is not required for Program C; ProgC.java will open from the first command line argument instead, if JavaFX fails to launch (e.g. in a virtual console environment).
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

        Platform.exit();
    }
}