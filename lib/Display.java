package lib;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import jflow.Calc;
import jflow.Dataset;
import jflow.Model;

public class Display extends JPanel {
    static Model model;
    static HashSet<Point> points;

    // window dimensions
    static final int height = 400;
    static final int width  = 400;
    static final double xLimit = 6;
    static final double yLimit = 6;
    // colors
    static final Color background = Color.WHITE;

    public void paint(Graphics g) {
        Image img = createImage();
        g.drawImage(img, 0, 0, this);
    }

    private double[] scaleFromWindow(int x, int y) {
        final double halfX = width / 2;
        final double halfY = height / 2;
        double outputX = x - halfX; // center x
        double outputY = y - halfY; // center y
        outputX = (outputX / halfX) * xLimit;
        outputY = (outputY / halfY) * yLimit;
        double[] output = {outputX, outputY};
        return output;
    }
    private int[] scaleFromValue(double x, double y) {
        double rx = (x + xLimit) / (2 * xLimit);
        double ry = (y + yLimit) / (2 * yLimit);
        int[] output = {(int)(rx * width), (int)(ry * height)};
        return output;
    }

    private Image createImage() {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        // background
        g.setColor(background);
        g.fillRect(0, 0, width, height);

        // evaluate network
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // calc single network output
                double[] input = scaleFromWindow(x, y);
                double output = Display.model.eval(input)[0];
                g.setColor(Calc.assignColor(output, -1, 1));
                g.fillRect(x, y, 1, 1);
            }
        }

        // tick at each integer on x and y plane
        for (int x = 0; x < width; x += width / (xLimit * 2)) {
            g.setColor(Color.RED);
            g.fillRect(x, 0, 1, 11);
        }
        for (int y = 0; y < width; y += width / (yLimit * 2)) {
            g.setColor(Color.RED);
            g.fillRect(0, y, 11, 1);
        }

        // graph each point in pointset
        for (Point p : points) {
            g.setColor(p.color);
            int[] pos = scaleFromValue(p.x, p.y);
            g.fillRect(pos[0], pos[1], 2, 2);
        }

        return bufferedImage;
    }

    /**
     * Shows static image of network
     * @param m
     * @param p
     */
    public static void main(Model m, HashSet<Point> p) {
        model = m;
        points = p;

        JFrame frame = new JFrame();
        frame.getContentPane().add(new Display());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    /**
     * Shows network training over time
     * @param m
     * @param p
     * @param train_data
     * @param train_labels
     * @param epochs
     * @throws InterruptedException
     */
    public static void main(Model m, HashSet<Point> p, double[][] train_data, double[][] train_labels, int epochs, int msDelay) throws InterruptedException {
        model = m;
        points = p;

        JFrame frame = new JFrame();
        frame.getContentPane().add(new Display());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);

        for ( int i = 0; i < epochs; i++) {
            model.fit(train_data, train_labels);
            TimeUnit.MILLISECONDS.sleep(msDelay);
            frame.repaint();
        }
    }
    /**
     * Shows network training over time
     * @param m
     * @param p
     * @param train_data
     * @param train_labels
     * @param epochs
     * @throws InterruptedException
     */
    public static void main(Model m, HashSet<Point> p, Dataset d, int epochs, int msDelay) throws InterruptedException {
        model = m;
        points = p;

        JFrame frame = new JFrame();
        frame.getContentPane().add(new Display());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);

        for ( int i = 0; i < epochs; i++) {
            model.fit(d);
            TimeUnit.MILLISECONDS.sleep(msDelay);
            frame.repaint();
        }
    }
    /**
     * Shows network training over time
     * @param m
     * @param p
     * @param train_data
     * @param train_labels
     * @param epochs
     * @throws InterruptedException
     */
    public static void main(Model m, HashSet<Point> p, Dataset d, int epochs) throws InterruptedException {
        model = m;
        points = p;

        JFrame frame = new JFrame();
        frame.getContentPane().add(new Display());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);

        for ( int i = 0; i < epochs; i++) {
            model.fit(d);
            frame.repaint();
        }
    }
}
