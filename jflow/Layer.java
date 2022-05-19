package jflow;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import jflow.activations.Activation;

/**
 * Each layer object actually stores the weight matrix notated as w^(l+1)
 */
public class Layer {

    public static final double initial_bias = 0.1;

    public int size;        // size ofc
    public int nextSize;    // for calculating weight matrix
    public double[] biases; // biases for summation
    public double[][] weights;
    public Activation activation;

    // ----------CONSTRUCTORS----------
    /**
     * Instantiates with random weight values in range [0,1)
     * @param size
     * @param nextSize
     * @param activation
     */
    public Layer(int size, int nextSize, Activation activation) {
        this.size       = size;
        this.nextSize   = nextSize;
        this.activation = activation;
        
        // instantiate bias vector
        this.biases = new double[this.nextSize];
        Arrays.fill(this.biases, initial_bias);

        // instantiate random weights matrix
        this.weights = new double[nextSize][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < nextSize; j++) {
                int negative = ThreadLocalRandom.current().nextInt(2); // randomly positive or negative
                if (negative == 0 ) { negative = -1; }
                weights[j][i] = ThreadLocalRandom.current().nextDouble() * negative;
            }
        }
    }


    // ----------METHODS----------
    
    public double[] calc_z(double[] in) {
        // check input shape
        if (in.length != this.size) {
            throw new RuntimeException(
                "Input shape does not match layer "+in.length+" -> "+this.size
            );
        }
        
        // unactivated dot product
        double[] product = Calc.add( Calc.dot(this.weights, in), this.biases );

        return product;
    }

}
