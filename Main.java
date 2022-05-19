import java.util.ArrayList;
import java.util.HashSet;

import jflow.Layer;
import jflow.Model;
import jflow.activations.Tanh;

import java.awt.Color;

import lib.Display;
import lib.Point;
import lib.PointSets;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Create Dataset
        HashSet<Point> pointSet = PointSets.bullseye(75, 100);
        Point[] points = pointSet.toArray(new Point[pointSet.size()]);
        // PREPROCESS TRAINING DATA
        double[][] train_data   = new double[pointSet.size()][2];
        double[][] train_labels = new double[pointSet.size()][1];
        for (int i = 0; i < points.length; i++) {
            train_data[i][0] = points[i].x;
            train_data[i][1] = points[i].y;
            if (points[i].color.equals(Color.BLUE)) {
                train_labels[i][0] = 1.0;
            } else {
                train_labels[i][0] = -1.0;
            }
        }

        // CREATE MODEL
        ArrayList<Layer> layers = new ArrayList<>();
        layers.add(new Layer(2, 4, new Tanh()));
        layers.add(new Layer(4, 2, new Tanh()));
        layers.add(new Layer(2, 1, new Tanh()));
        Model model = new Model(layers);
        
        // DISPLAY MODEL OVER TIME
        Display.main(
            model,
            pointSet,
            train_data,
            train_labels,
            3000,
            1
        );

    }
}
