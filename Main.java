import java.util.ArrayList;
import java.util.HashSet;

import jflow.*;
import jflow.activations.*;

import lib.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Create Dataset
        HashSet<Point> pointSet = PointSets.bullseye(125, 175);

        // PREPROCESS TRAINING DATA
        Dataset dataset = PointSets.preprocessPointSet(pointSet);

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
            dataset,
            3000,
            1
        );

        model.printWeights();

    }
}
