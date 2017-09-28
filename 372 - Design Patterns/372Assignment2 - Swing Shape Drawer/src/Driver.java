import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/*
 * A GUI that allows rectangles and circles of two different colors to be drawn.
 * Saves/loads state, if possible.
 */
public class Driver extends JFrame implements ActionListener {//inheriting JFrame
    private static enum Action {RECTANGLE, CIRCLE};

    /* Buttons You Can Press */
    JButton redButton = new JButton("Red");
    JButton greenButton = new JButton("Green");
    JButton blueButton = new JButton("Blue");
    JButton rectangleButton = new JButton("Rectangle");
    JButton circleButton = new JButton("Circle");
    JButton exitButton = new JButton("Exit");

    /* Other UI Elements */
    private JTextArea listArea = new JTextArea(10, 10);
    private FiguresPanel figuresPanel = new FiguresPanel();
    private FiguresPanel outerFrame = new FiguresPanel();
    private JTextArea textArea = new JTextArea();
    private JScrollPane textPanel = new JScrollPane(textArea);
    private JPanel datePanel = new JPanel((new BorderLayout()));

    /* The Currently-Selected Action/Colour */
    private Action action = Action.RECTANGLE;
    private Color colour = Color.RED;


    /**
     * A List of Objects Being Displayed
     * Add or Remove and They Will be Redrawn Accordingly on Repaint
     * Invoke {@link Driver#whenObjectsToDrawChanged()} if you change this.
     * Use {@link Driver#addObjectToDraw(Figure)} to add item to this. */
    private static ArrayList<Figure> objectsToDraw = new ArrayList<Figure>();


    /*
     * The panel that shows/draws figures stored in the objectsToDraw array.
     * It also detects mouse clicks, adding objects to draw when a click occurs.
     */
    public class FiguresPanel extends JPanel implements MouseListener {
        FiguresPanel() {
            super(new BorderLayout());

            this.setBorder(new EmptyBorder(10, 10, 10, 10));
            this.add(new JLabel((new Date()).toString()), BorderLayout.PAGE_END);
        }

        /**
         * Draws objects stored in objectsToDraw
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Figure f : objectsToDraw) {
                f.draw(g);
            }
        }

        /**
         * Waits for the mouse click and creates the appropriate figures.
         */
        @Override
        public void mouseClicked(MouseEvent event) {
            System.out.println("Click Action: " + action);
            Point point = event.getPoint(); // The position of the mouse when the event was triggered (when the user clicked)

            switch (action) {
                case RECTANGLE:
                    addObjectToDraw(new Rectangle(point.x, point.y, 80, 50, colour));
                    break;

                case CIRCLE:
                    addObjectToDraw(new Circle(point.x, point.y, 50, 50, colour));
                    break;
            }
        }

        /**
         * Not used */
        @Override
        public void mouseEntered(MouseEvent arg0) { }
        /**
         * Not used */
        @Override
        public void mouseExited(MouseEvent arg0) { }
        /**
         * Not used */
        @Override
        public void mousePressed(MouseEvent arg0) { }
        /**
         * Not used */
        @Override
        public void mouseReleased(MouseEvent arg0) { }
    }


    Driver() {
        super("Figures GUI");
        readState(); // We wait until here because reading state invokes non-static methods.

        /* Associate Buttons with Listener */
        redButton.addActionListener(this);
        greenButton.addActionListener(this);
        blueButton.addActionListener(this);
        rectangleButton.addActionListener(this);
        circleButton.addActionListener(this);
        exitButton.addActionListener(this);

        /* Set the Window to a 3x1 Grid Layout */
        super.setLayout(new GridLayout(0,3));

        /* Set the Button Panel to a 3x2 Grid Layout */
        JPanel buttonsPanel = new JPanel(new GridLayout(2,2));

        textArea.setLineWrap(true);

        /* Add Buttons to Button Panel */
        buttonsPanel.add(redButton);
        buttonsPanel.add(greenButton);
        buttonsPanel.add(blueButton);
        buttonsPanel.add(rectangleButton);
        buttonsPanel.add(circleButton);
        buttonsPanel.add(exitButton);

        /* Add Panels to Window */
        super.add(figuresPanel);
        super.add(buttonsPanel);
        super.add(textPanel);

        /* Add Mouse Listener for Drawing Shapes */
        figuresPanel.addMouseListener(outerFrame);

        /* Set Window Properties/Make Visible */
        super.setPreferredSize(new Dimension(800, 200));
        super.pack();
        super.setVisible(true);
    }


    /**
     * Listener for all buttons.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "Rectangle":
                this.action = Action.RECTANGLE;
                break;

            case "Circle":
                this.action = Action.CIRCLE;
                break;

            case "Red":
                this.colour = Color.RED;
                break;

            case "Green":
                this.colour = Color.GREEN;
                break;

            case "Blue":
                this.colour = Color.BLUE;
                break;

            case "Exit":
                writeState();
                System.exit(1);
                break;
        }

        System.out.println("Button Event: " + event.getActionCommand());
        System.out.println(this.colour + "; " + this.action);
    }


    /**
     * Update the text field content based on the current objects being drawn.
     */
    public void refreshTextPanel() {
        StringBuilder textPanelContent = new StringBuilder();
        for (Figure f : objectsToDraw) {
            textPanelContent.append(f.toString() + System.getProperty("line.separator"));
        }

        textArea.setText(textPanelContent.toString());
    }


    /**
     * Adds an object for drawing, performing all ancillary tasks as well.
     * @param figure The figure to be drawn.
     */
    public void addObjectToDraw(Figure figure) {
        objectsToDraw.add(figure);
        whenObjectsToDrawChanged();
    }


    /**
     * Updates everything that needs to be updated when objectsToDraw is changed.
     */
    public void whenObjectsToDrawChanged() {
        figuresPanel.repaint(); // Force repaint. Also occurs when a window resizes, and probably would occur automatically if I knew what I was doing (I don't)
        refreshTextPanel(); // Update the text log. Again, if I understood MVC, this would probably be automatic. (I don't really want to waste time learning Swing MVC when everybody is moving over to JavaFX.)
    }


    /**
     * Reads the program state, serialising drawn objects to the disk.
     */
    public void readState() {
        try {
            FileInputStream in = new FileInputStream("372Assignment2_Persistence.bin");
            ObjectInputStream ois = new ObjectInputStream(in);
            int numObjects = ois.readInt();

            for (int i = 0; i < numObjects; i++) {
                addObjectToDraw((Figure) ois.readObject());
            }
        } catch (IOException e) {
            System.out.println("Problem reading: " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e);
        }
    }


    /**
     * Writes the program state, serialising drawn objects to the disk.
     */
    public void writeState() {
        try {
            File file = new File(new File(System.getProperty("user.dir")), "372Assignment2_Persistence.bin");
            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(out);

            oos.writeInt(objectsToDraw.size());
            for (Figure f : objectsToDraw) {
                oos.writeObject(f);
            }

            oos.flush();
        } catch (Exception e) {
            System.out.println("Unable to serialise a value: " + e);
        }
    }


    /**
     * Exists solely as the entry point for the program.
     */
    public static void main(String[] args) {
        new Driver();
    }
}