import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Driver extends JFrame implements ActionListener {//inheriting JFrame
    private static enum Action {RECTANGLE, CIRCLE};
    //private static enum Colour {RED, GREEN, BLUE};

    JButton redButton = new JButton("Red");
    JButton greenButton = new JButton("Green");
    JButton blueButton = new JButton("Blue");
    JButton rectangleButton = new JButton("Rectangle");
    JButton circleButton = new JButton("Circle");
    JButton exitButton = new JButton("Exit");

    private JTextArea listArea = new JTextArea(10, 10);
    private FiguresPanel figuresPanel = new FiguresPanel();
    private Action action = Action.RECTANGLE;
    private Color colour = Color.RED;
    FiguresPanel outerFrame = new FiguresPanel();

    JTextArea textPanel = new JTextArea();

    private ArrayList<Figure> objectsToDraw = new ArrayList<Figure>();

    public class FiguresPanel extends JPanel implements MouseListener {
        @Override
        public void paintComponent(Graphics g) {
            // Note: An alternative would be to have an array of objects to draw at every interval, clearing the Graphics object every time.
            // However, that would be less performant, and serve no purpose in our application here.

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
            Point point = event.getPoint();

            switch (action) {
                case RECTANGLE:
                    objectsToDraw.add(new Rectangle(point.x, point.y, 80, 50, colour));
                    break;
                case CIRCLE:
                    objectsToDraw.add(new Circle(point.x, point.y, 50, 50, colour));
                    break;
            }

            figuresPanel.repaint();
            Driver.this.refreshTextPanel();

        }

        /**
         * Not used
         */
        @Override
        public void mouseEntered(MouseEvent arg0) {
        }

        /**
         * Not used
         */
        @Override
        public void mouseExited(MouseEvent arg0) {
        }

        /**
         * Not used
         */
        @Override
        public void mousePressed(MouseEvent arg0) {
        }

        /**
         * Not used
         */
        @Override
        public void mouseReleased(MouseEvent arg0) {
        }
    }

    Driver() {
        super("Figures GUI");

        redButton.addActionListener(this);
        greenButton.addActionListener(this);
        blueButton.addActionListener(this);
        rectangleButton.addActionListener(this);
        circleButton.addActionListener(this);
        exitButton.addActionListener(this);

        super.setLayout(new GridLayout(0,3));

        //JPanel datePanel = new JPanel((new BorderLayout()));
        JPanel buttonsPanel = new JPanel(new GridLayout(2,2));

        //datePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        //datePanel.add(new JLabel((new Date()).toString()), BorderLayout.PAGE_END);

        textPanel.setLineWrap(true);

        buttonsPanel.add(redButton);
        buttonsPanel.add(greenButton);
        buttonsPanel.add(blueButton);
        buttonsPanel.add(rectangleButton);
        buttonsPanel.add(circleButton);
        buttonsPanel.add(exitButton);

        super.add(figuresPanel);
        super.add(buttonsPanel);
        super.add(textPanel);

        super.setPreferredSize(new Dimension(800, 200));
        super.addMouseListener(outerFrame);
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
                System.exit(1);
                break;
        }

        System.out.println("Button Event: " + event.getActionCommand());
        System.out.println(this.colour + "; " + this.action);
    }

    public void refreshTextPanel() {
        StringBuilder textPanelContent = new StringBuilder();
        for (Figure f : objectsToDraw) {
            textPanelContent.append(f.toString() + System.getProperty("line.separator"));
        }

        textPanel.setText(textPanelContent.toString());
    }

    public static void main(String[] args) {
        new Driver();
    }
}