import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;

/**
 * Graphical components of program -- solely the file opener. If unavailable, file can be specified as the sole argument to the program.
 */
public class ProgCGraphical extends Application {
    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        File[] fileHandles = ProgC.openFile(primaryStage);
        ProgC.passData(fileHandles, this.getParameters().getUnnamed().contains("--measurementMode"));
    }
}