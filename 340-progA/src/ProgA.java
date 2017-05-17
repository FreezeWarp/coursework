import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Reads from a file in a list of numbers, separated into rows and delimited by spaces (as defined by \s+ regex).
 * Outputs summation of each row of numbers to a new file; if input filename is x.y, output filename is x_out.y
 * (Note: when run through Idea, file chooser defaults to project root. When run through javac+java, file chooser defaults to executing directory.)
 *
 * @author Joseph T. Parsons <josephtparsons@gmail.com>
 */
public class ProgA extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        /* Display Chooser and Select File */
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);


        /* Don't Do Anything if No File Selected */
        if (file == null) {
            System.out.println("No file selected. Exiting.");
        }

        else if (!file.exists()) {
            System.out.println("File does not exist. Exiting.");
        }

        else {
            System.out.println("Input File Is: " + file.getAbsolutePath());


            /* Calculate Output File Name Using Input File Name */
            String fileName = file.getAbsolutePath();
            int extensionDelimiter = fileName.lastIndexOf('.');
            Path outFile = Paths.get(fileName.substring(0, extensionDelimiter) + "_out" + fileName.substring(extensionDelimiter, fileName.length()));

            System.out.println("Output File Will Be: " + outFile.toAbsolutePath());


            try {

                /* Delete Output File if It Currently Exists */
                Files.deleteIfExists(outFile);


                /* Run through each line of the input file from a stream */
                Files.lines(Paths.get(file.getPath())).forEach((line)->{
                    int total = 0;

                    for (String item : line.split("\\s+")) {
                        try {
                            total += Integer.parseInt(item);
                        } catch (Exception ex) {
                            try {
                                total += (int) Double.parseDouble(item);

                                System.err.println("A double was encountered: " + item + ". It will be added as an integer.");
                            } catch (Exception ex2) {
                                System.err.println("An error was encountered parsing this value: " + item + ". It will be ignored.");
                            }
                        }
                    }

                    try {
                        Files.write(outFile, (total + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    } catch (IOException ex) {
                        System.err.println("IOException occured trying to write file. Attempts will continue with further input.");
                    }

                    System.out.println("Line Total: " + total);
                });

            } catch (IOException ex) {
                System.err.println("IOException occurred trying to read file. Giving up.");
            }
        }

        Platform.exit(); // Don't keep running after completion.
        System.exit(0);
    }
}
