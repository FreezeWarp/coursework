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
 * Simulation of DFS graph algorithm on graph.
 * Takes an input file as source graph and outputs to the same filename with _out appended before the file ext.
 * Additionally, uses GraphDrawer to output a visual representation of the input graph, for easier debugging.
 *
 * @author Joseph T. Parsons
 */


public class ProgC {
    /**
     * Passes to ProgBGraphical, which launches file opener.
     */
    public static void main(String[] args) {
        // Commented code here would be used to support command line stuff. Since I'm not doing that, better to make sure exceptions fire normally.

//        try {
            ProgCGraphical.main(args);
/*        } catch (Exception ex) {
            System.out.println("Unable to launch JavaFX. Reading file from first parameter and assuming measurement mode.");
            System.out.println(args);

            File[] files = new File[3];`1
            files[0] = (args.length > 0 ? new File(args[0]) : null);
            files[1] = null;
            files[2] = null;
            ProgC.passData(files, true);
        }*/
        
        System.exit(0);
    }


    /**
     * Reads in data from the first file handle (using readNodesFromFile) and then sets the second file as the output source. Then performs DFS algorithm on input data.
     *
     * @param fileHandles Two file handles, the first reading, and the second for writing.
     */
    public static void passData(File[] fileHandles) {
        Collection<Node> parsedData;
        boolean measurementMode = false; // Would be used to support execution measurement, but not presently implemented.

        if (fileHandles != null) {
            if ((parsedData = readNodesFromFile(fileHandles[0])) != null) {
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

                GraphDrawer graphDrawer = new GraphDrawer(fileHandles[2]);

                try {
                    graphDrawer.drawGraph(parsedData); // Draw the initial, uncoloured phase of the graph.
                } catch (Exception ex) {
                    System.out.println("GraphDrawer failed.");
                }


                long startTime = System.nanoTime(); // For time measurement.
                Simulation.main(new ArrayList<Node>(parsedData), graphDrawer);

                graphDrawer.close(); // Close up the graph drawer, ensuring the final image data is written.

                if (measurementMode) { // If in measurement mode, switch back to stdout and print the time it took to run.
                    System.setOut(stdout);
                    System.out.println("Time for " + parsedData.size() + " nodes: " + (System.nanoTime() - startTime));
                }
            } else {
                System.out.println("Unable to parse file. Exiting.");
            }
        }
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
            Path outImage = Paths.get(fileName.substring(0, extensionDelimiter) + "_out" + ".png"); // For graph output.

            System.out.println("Output File Will Be: " + outFile.toAbsolutePath());
            System.out.println("Output Image Will Be: " + outImage.toAbsolutePath());
            System.out.println();

            try {
                /* Delete Output File if It Currently Exists */
                Files.deleteIfExists(outFile);

                return new File[]{file, outFile.toFile(), outImage.toFile()};
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

                if (columns.length != nodeNames.size() + 1) { // Detect for more values than possible for a given line.
                    System.err.println("Warning! An invalid number of inputs was detected: " + columns.length + " found, " + (nodeNames.size() + 1) + " expected. The rightmost n inputs will be used, with a lack of connection being assumed for any remaining inputs as needed.");
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
                        System.out.println("Iunno. Something went wrong. Better fix it. ");
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