import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

public class Driver extends JFrame{//inheriting JFrame
    JFrame f;
    Driver(){
        JButton redButton = new JButton("Red");
        JButton greenButton = new JButton("Green");
        JButton blueButton = new JButton("Blue");
        JButton rectangleButton = new JButton("Rectangle");
        JButton circleButton = new JButton("Circle");
        JButton exitButton = new JButton("Exit");

        JFrame outerFrame = new JFrame();
        outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        outerFrame.setLayout(new GridLayout(0,3));

        JPanel innerFrame1 = new JPanel((new BorderLayout()));
        innerFrame1.setBorder(new EmptyBorder(10, 10, 10, 10));
        innerFrame1.add(new JLabel((new Date()).toString()), BorderLayout.PAGE_END);

        JPanel innerFrame2 = new JPanel(new GridLayout(2,2));
        innerFrame2.add(redButton);
        innerFrame2.add(greenButton);
        innerFrame2.add(blueButton);
        innerFrame2.add(rectangleButton);
        innerFrame2.add(circleButton);
        innerFrame2.add(exitButton);

        JPanel innerFrame3 = new JPanel(new GridLayout(1,0));
        innerFrame3.add(new JTextArea());

        outerFrame.add(innerFrame1);
        outerFrame.add(innerFrame2);
        outerFrame.add(innerFrame3);

        outerFrame.setPreferredSize(new Dimension(800, 200));
        outerFrame.pack();
        outerFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Driver();
    }
}