import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


/**
 * Simulation of algorithms (right now, Held Karp) on map data sets.
 * Can run graphically (using JavaFX) or non-graphically (if JavaFX fails to launch).
 * Takes an input file as source graph and outputs to the same filename with _out appended before the file ext.
 *
 * @author Joseph T. Parsons
 */


public class ProgC {
    /**
     * Passes to ProgBGraphical if possible, or opens the first command line argument as the file if ProgBGraphical fails.
     */
    public static void main(String[] args) {
        try {
            ProgCGraphical.main(args);
        } catch (Exception ex) {
            System.out.println("Unable to launch JavaFX. Reading file from first parameter and assuming measurement mode.");
            System.out.println(args);

            File[] files = new File[2];
            files[0] = new File(args[0]);
            files[1] = null;
            ProgC.passData(files, true);
        }
    }


    /**
     * Reads in data from the first file handle (using readCitiesFromFile) and then sets the second file as the output source. Then performs Held Karp algorithm on input data.
     *
     * @param fileHandles Two file handles, the first reading, and the second for writing.
     * @param measurementMode If true, this will disable most output. At present, only works on Linux. (It would be trivial to add Windows support -- use NUL instead /dev/null -- but since this was just for me to analyse run time, I didn't want to waste the time.)
     */
    public static void passData(File[] fileHandles, boolean measurementMode) {
        Collection<Node> parsedData;
        measurementMode = false;

        if (fileHandles != null) {
            if ((parsedData = readNodesFromFile(fileHandles[0])) != null) {
                System.err.println(parsedData);
                PrintStream stdout = System.out;

                if (measurementMode) { // Forward output to /dev/null to avoid overhead in measurement mode.
                    try {
                        System.setOut(new PrintStream("/dev/null"));
                    } catch (java.io.IOException ex) {
                        System.out.println("Cannot open /dev/null. measurementMode is not supported on your system.");
                    }
                }
                else { // Write all output to the second file handle.
                    try {
                        System.setOut(new PrintStream(fileHandles[1]));
                    } catch (java.io.IOException ex) {
                        System.out.println("Unable to register output handler. Exiting.");
                    }
                }

                long startTime = System.nanoTime(); // For time measurement.
                Simulation.main(new ArrayList<Node>(parsedData));

                if (measurementMode) { // If in measurement mode, switch back to stdout and print the time it took to run.
                    System.setOut(stdout);
                    System.out.println("Time for " + parsedData.size() + " nodes: " + (System.nanoTime() - startTime));
                }
            } else {
                System.out.println("Unable to parse file. Exitting.");
            }
        }

        Platform.exit(); // Don't keep running after completion.
        System.exit(0);
    }


    /**
     * Standard method to open a file. I will probably just reuse this in all programs.
     * @param primaryStage The JavaFX stage to use for opening.
     * @return A pair of file objects, the first belonging to the input file, the second a file to output to.
     */
    public static File[] openFile(Stage primaryStage) {
         /* Display Chooser and Select File */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // See, this is default in Linux. As it would be in any reasonable operating system. In Windows, it is not.
        File file = fileChooser.showOpenDialog(primaryStage);


        /* Don't Do Anything if No File Selected */
        if (file == null) {
            System.out.println("No file selected. Exiting.");
            return null;
        } else if (!file.exists()) {
            System.out.println("File does not exist. Exiting.");
            return null;
        } else {
            System.out.println("Input File Is: " + file.getAbsolutePath());

            /* Calculate Output File Name Using Input File Name */
            String fileName = file.getAbsolutePath();
            int extensionDelimiter = fileName.lastIndexOf('.');
            Path outFile = Paths.get(fileName.substring(0, extensionDelimiter) + "_out" + fileName.substring(extensionDelimiter, fileName.length()));

            System.out.println("Output File Will Be: " + outFile.toAbsolutePath());
            System.out.println();

            try {
                /* Delete Output File if It Currently Exists */
                Files.deleteIfExists(outFile);

                return new File[]{file, outFile.toFile()};
            } catch (IOException ex) {
                return null;
            }
        }
    }


    /**
     * Reads the city distance graph (as a matrix) from an input file.
     *
     * @param inFile An input file object to read from.
     * @return A map of direct distances, and a list of cities.
     */
    public static Collection<Node> readNodesFromFile(File inFile) {
        Map<String, Node> nodes = new HashMap<String, Node>();
        List<String> nodeNames = new ArrayList<String>();

        try {
            /* Build the List of city names from the first line */
            Files.lines(Paths.get(inFile.getPath())).findFirst().map((line) -> {
                String[] columns = line.trim().split("\\s+"); // Real talk, though, why would you use anything other than the space regex to split by spaces?
                for (int columnNum = 0; columnNum < columns.length; columnNum++) {
                    if (columns[columnNum].matches("[\\-\\.]+")) continue; // Allow the first column name to be a punctuation filler.

                    nodeNames.add(columns[columnNum]);
                    nodes.put(columns[columnNum], new Node(columns[columnNum]));
                }

                return line; // Basically a NOOP
            });

            /* Run through each line of the input file (except the first) from a stream */
            Files.lines(Paths.get(inFile.getPath())).skip(1).forEach((line)->{
                String[] columns = line.trim().split("\\s+");

                if (columns.length != nodeNames.size()) { // Detect for more values than possible for a given line.
                    System.err.println("Warning! An invalid number of inputs was detected. The rightmost n inputs will be used, with a lack of connection being assumed for any remaining inputs as needed.");
                }

                for (int columnNum = 1; columnNum < columns.length; columnNum++) {
                    try {
                        Node sourceNode = nodes.get(columns[0]);
                        Node destNode = nodes.get(nodeNames.get(nodeNames.size() - columns.length + columnNum));

                        if (sourceNode == null) {
                            System.err.println("Invalid node name: " + columns[0]);
                            //return null;
                        }
                        else if (destNode == null) {
                            System.err.println("Invalid node name in first line. This is probably a development error.");
                            //return null;
                        }
                        else {
                            if (!columns[columnNum].matches("[\\.\\-]+")) {
                                try {
                                    sourceNode.registerConnection(destNode, Integer.parseInt(columns[columnNum]));
                                } catch (Exception ex2) {
                                    System.err.println("An error was encountered parsing this value: " + columns[columnNum] + ". It will be ignored, but may result in incorrect output.");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("Iunno.");
                    }
                }
            });

            return nodes.values();
        } catch (IOException ex) {
            System.err.println("IOException occurred trying to read file. Giving up.");
        }

        return null;
    }
}