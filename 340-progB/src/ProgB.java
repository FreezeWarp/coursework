import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joseph on 17/05/17.
 */
public class ProgB extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        Object[] parsedData;

        if ((parsedData = readCitiesFromFile(primaryStage)) != null) {
            HeldKarp mailman = new HeldKarp((List<String>) parsedData[0], (Map<String, Integer>) parsedData[1]);
        }
        else {

        }

        Platform.exit(); // Don't keep running after completion.
        System.exit(0);
    }

    public Object[] readCitiesFromFile(Stage primaryStage) {
        Map<String, Integer> directDistances = new HashMap<String, Integer>();
        List<String> cities = new ArrayList<String>();

        /* Display Chooser and Select File */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // See, this is default in Linux. As it would be in any reasonable operating system. In Windows, it is not.
        File file = fileChooser.showOpenDialog(primaryStage);


        /* Don't Do Anything if No File Selected */
        if (file == null) {
            System.out.println("No file selected. Exiting.");
            return null;
        }

        else if (!file.exists()) {
            System.out.println("File does not exist. Exiting.");
            return null;
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

                Files.lines(Paths.get(file.getPath())).findFirst().map((line) -> {
                    String[] columns = line.split("\\s+");
                    for (int columnNum = 1; columnNum < columns.length; columnNum++) {
                        cities.add(columns[columnNum]);
                    }

                    return line;
                });

                Files.lines(Paths.get(file.getPath())).skip(1).forEach((line)->{
                    String[] columns = line.split("\\s+");

                    for (String col : columns) System.out.print("Col: " + col + "; ");
                    System.out.println();

                    if (columns.length > cities.size() + 1) {
                        throw new IllegalArgumentException("Too many columns on line.");
                    }

                    for (int columnNum = 1; columnNum < columns.length; columnNum++) {
                        if (columns[0].equals(cities.get(cities.size() - columns.length + columnNum))) {
                            if (!((String) (columns[columnNum])).equals("0"))
                                throw new IllegalArgumentException("Parity violated: 0 expected, " + columns[columnNum] + "found. At position " + columns[0] + ", " + cities.get(cities.size() - 1 - columnNum) + "; column " + columnNum + ". Input data is invalid.");

                            // Otherwise, just ignore the data. We don't need/want it.
                        }

                        else {
                            try {
                                directDistances.put(columns[0] + "-" + cities.get(cities.size() - columns.length + columnNum), Integer.parseInt(columns[columnNum]));
                            } catch (Exception ex) {
                                try {
                                    directDistances.put(columns[0] + "-" + cities.get(cities.size() - columns.length + columnNum), (int) Double.parseDouble(columns[columnNum]));
                                    System.err.println("A double was encountered: " + columns[columnNum] + ". It will be added as an integer.");
                                } catch (Exception ex2) {
                                    System.err.println("An error was encountered parsing this value: " + columns[columnNum] + ". It will be ignored.");
                                }
                            }
                        }
                    }

                    /*try {
                        Files.write(outFile, (total + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    } catch (IOException ex) {
                        System.err.println("IOException occured trying to write file. Attempts will continue with further input.");
                    }

                    System.out.println("Line Total: " + total);*/
                });

                System.out.println("Hashmap:" + directDistances);

                Object[] returnData = {cities, directDistances};
                return returnData;
            } catch (IOException ex) {
                System.err.println("IOException occurred trying to read file. Giving up.");
            }
        }

        return null;
    }
}