/**
 * @author Miłosz Demendecki s24611
 */
import javax.swing.*;
import java.awt.*;

public class PSM_Task1 {

    private static boolean radians = true;

    private static JFrame frame = new JFrame("Calculate sin(x) using Taylor Series");
    private static JPanel buttonPanel = new JPanel();
    private static JPanel panel = new JPanel();
    private static JLabel xLabel = new JLabel("Enter the value of x (in radians):");
    private static JLabel resultLabel = new JLabel();
    private static JLabel derivativesLabel = new JLabel("Enter the number of derivatives:");
    private static JTextField xField = new JTextField();
    private static JTextField derivativesField = new JTextField();
    private static JButton changeRadiansOrDegreesButton = new JButton("Change to degrees");
    private static JButton calculateButton = new JButton("Calculate");

    public static void main(String[] args) {
        createFrame();
    }

    /**
     * Function that calculates value of factorial of integer z
     * @param z in our case z is always equal to 2 * n * 1 where n is number of each consecutive derivative
     *          e.g. if we 3 derivatives are given n will be equal consecutively to 0, 1 and 2
     * @return returns factorial of z number
     */
    private static int factorial(int z) {
        int f = 1;
        for (int i = 2; i <= z; i++) {
            f *= i;
        }
        return f;
    }

    /**
     * Function that calculates derivative for each consecutive n where n is number of derivative
     * @param x number that passed user in radians (it is always changed to radians)
     * @param n number that responds to each consecutive derivative
     * @return returns derivative for parameter x and n
     */
    private static double derivative(double x, int n) {
        return Math.pow(-1, n) / factorial(2 * n + 1) * Math.pow(x, 2 * n + 1);
    }

    /**
     * Function that calculates result of Taylor Series and displays information about it
     * @param x number that passed user in radians (it is always changed to radians)
     * @param nDerivatives number of derivatives passed by the user as integer
     */
    private static void taylorSeries(double x, int nDerivatives) {
        double result = 0;

        for (int n = 0; n < nDerivatives; n++) {
            result += derivative(x, n);
        }
        if(Double.isNaN(result)) {
            resultLabel.setText("Cannot display answer because of NaN result, try again");
        } else if(nDerivatives <= 0) {
            resultLabel.setText("Try again number of derivatives must be higher than 0");
        } else if(nDerivatives == 1) {
            resultLabel.setText("Sin(x) for " + nDerivatives + " derivative = " + result);
        } else {
            resultLabel.setText("Sin(x) for " + nDerivatives + " derivatives = " + result);
        }
    }

    /**
     * Function that adds ActionListeners to buttons and adds functionality to change degrees into radians
     * depending on users choice
     */
    private static void addActionsToButtons() {
        calculateButton.addActionListener(e -> {
            double x;
            int numberOfDerivatives;
            try {
                numberOfDerivatives = Integer.parseInt(derivativesField.getText());
                if(radians) {
                    x = Double.parseDouble(xField.getText());
                } else {
                    x = Double.parseDouble(xField.getText()) * Math.PI / 180;
                }
                taylorSeries(x, numberOfDerivatives);
            } catch (NumberFormatException ee) {
                resultLabel.setText("Try calculating again, but now provide only numbers");
            }
        });

        changeRadiansOrDegreesButton.addActionListener(e -> {
            if(radians) {
                radians = false;
                changeRadiansOrDegreesButton.setText("Change to radians");
                xLabel.setText("Enter the value of x (in degrees):");
            } else {
                radians = true;
                changeRadiansOrDegreesButton.setText("Change to degrees");
                xLabel.setText("Enter the value of x (in radians):");
            }
        });
    }

    /**
     * Function creates frame for the user to interact with
     */
    private static void createFrame() {
        Font font = new Font(xLabel.getFont().getName(), Font.PLAIN, 30);
        xLabel.setFont(font);
        xField.setFont(font);
        derivativesLabel.setFont(font);
        derivativesField.setFont(font);
        resultLabel.setFont(font);
        calculateButton.setFont(font);
        changeRadiansOrDegreesButton.setFont(font);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));


        panel.add(xLabel);
        panel.add(xField);

        panel.add(derivativesLabel);
        panel.add(derivativesField);

        panel.add(resultLabel);

        buttonPanel.add(changeRadiansOrDegreesButton);
        buttonPanel.add(calculateButton);

        addActionsToButtons();

        frame.setSize(800,400);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel,BorderLayout.NORTH);
        frame.add(buttonPanel,BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}