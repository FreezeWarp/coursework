import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class FigureGUI extends JFrame implements ActionListener {
    private static enum Action {RECTANGLE, CIRCLE, DATE};
    private JButton redButton = new JButton("Red");
    private JButton rectangleButton = new JButton("Rectangle");
    private JTextArea listArea = new JTextArea(10, 10);
    private GregorianCalendar currentDate;
    private FiguresPanel figuresPanel = new FiguresPanel();
    private Action action = Action.RECTANGLE;
    public class FiguresPanel extends JPanel implements MouseListener {
        @Override
        public void paintComponent(Graphics g) {
            // draw all figures
            // put the date
        }
        /**
         * Waits for the mouse click and creates the appropriate figures.
         */
        @Override
        public void mouseClicked(MouseEvent event) {
            switch(action) {
                case RECTANGLE:
                    // process the mouse click
                    break;
                case CIRCLE:
                    // process the mouse click
                    break;
            }

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
    /**
     * Sets up the entire interface.
     */
    public FigureGUI() {
        super("Figures GUI");
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));
        JPanel listPanel = new JPanel(new GridLayout(1, 1));
        JPanel mainPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(redButton);
        buttonPanel.add(rectangleButton);
        listPanel.add(new JScrollPane(listArea));
        mainPanel.add(figuresPanel);
        redButton.addActionListener(this);
        figuresPanel.addMouseListener(figuresPanel);
		/*
		 * Use a File object to represent the figures file. Check
		 * if it exists using the exists method.
		 *
		 */

        currentDate = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = dateFormat.format(currentDate.getTime());
    }
    /**
     * Listener for all buttons.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
    }
    /**
     * The method creates an FigureGUI object
     *
     * @param args not used
     */
    public static void main(String[] args) {
        new FigureGUI();
    }
}
