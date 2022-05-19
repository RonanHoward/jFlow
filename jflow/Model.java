package jflow;

import java.util.ArrayList;
import java.util.Arrays;

import jflow.activations.Activation;
import jflow.activations.Tanh;

public class Model {
    
    public ArrayList<Layer> layers;
    public double learning_rate;

    public Model(ArrayList<Layer> layers) {
        this.layers = layers;
        this.learning_rate = 0.03;
    }

    public void setLearningRate(double rate) {
        this.learning_rate = rate;
    }

    /**
     * Returns 2 2D lists representing unactivated and activated outputs of each layer (except for input layer)
     * @param in
     * @return
     */
    public ArrayList<ArrayList<double[]>> forward(double[] feed) {
        
        ArrayList<double[]> zs = new ArrayList<>();
        ArrayList<double[]> as = new ArrayList<>();
        zs.add(feed);
        as.add(feed);
        
        double[] input = feed;
        
        for (Layer l : this.layers) {                         // for each layer:
            zs.add( l.calc_z(input) );                        //   calculate z and add to `zs`
            as.add( l.activation.eval(zs.get(zs.size()-1)) ); //   activate that value and add it to `as`
            input = as.get(as.size() - 1);                    //   set `input` variable to this activated output
        }
        
        // contains 2 ArrayList<double[]> objects for every z(list 0) and a(list 1) output
        ArrayList<ArrayList<double[]>> output = new ArrayList<>();
        output.add(zs);
        output.add(as);
        return output;

    }

    /**
     * Much faster than forward, only returns ouput
     * @param input
     * @return
     */
    public double[] eval(double[] input) {
        double[] current = input;
        // feed forward
        for (Layer l : this.layers) {
            double[] a = l.activation.eval(
                l.calc_z( current )
            );
            current = a;
        }
        return current;
    }

    /**
     * Returns a list of vectors for the error of each layer in model
     * @param dOut
     * @return
     */
    public ArrayList<double[]> backpropagate_error(ArrayList<ArrayList<double[]>> forward, double[] dOut) {
        ArrayList<double[]> errors = new ArrayList<>();
        for (int i = 0; i <= this.layers.size(); i++) {
            if (i == this.layers.size()) {
                errors.add(new double[this.layers.get(i-1).nextSize]);
            } else {
                errors.add(new double[this.layers.get(i).size]);
            }
        }

        // for each descending layer in the model
        for (int i = this.layers.size(); i >= 0; i--) {

            Activation activation = new Tanh();

            // if this layer is the output
            if (i == this.layers.size()) {
                errors.set(i,
                    // (output - dOut) hadamard activation'(z)
                    Calc.hadamard(
                        Calc.subtract(forward.get(1).get(i), dOut),
                        activation.deval(forward.get(0).get(i))
                    )
                );
            } else {
                errors.set(i,
                    Calc.hadamard(
                        Calc.dot(
                            Calc.transpose(this.layers.get(i).weights),
                            errors.get(i+1)
                        ),
                        activation.deval(forward.get(0).get(i))
                    )
                );
            }

            
        }
        
        return errors;
    }

    /**
     * Applies backpropagation and gradient descent to average error over one epoch
     * @param train_data
     * @param train_labels
     */
    public void fit(double[][] train_data, double[][] train_labels) {
        if (train_data.length != train_labels.length) {
            throw new Error("train_data and train_labels are not equal lengths");
        }

        // ---FORWARD EACH TRAINING EXAMPLE---
        // forwards.get(training example).get(0:zs, 1:as).get(layer)[neuron]
        ArrayList<ArrayList<ArrayList<double[]>>> forwards = new ArrayList<>();
        for (int x = 0; x < train_data.length; x++) {
            forwards.add(this.forward(train_data[x]));
        }

        // ---CALCULATE ERRORS IN EACH EXAMPLE---
        // errors.get(training example).get(layer)[neuron]
        ArrayList<ArrayList<double[]>> errors = new ArrayList<>();
        // for each training example
        for (int x = 0; x < train_data.length; x++) {
            // add the model of errors to errors list
            errors.add(
                this.backpropagate_error(
                    forwards.get(x),
                    train_labels[x]
                )
            );
        }

        // ---CALCULATE AVERAGE DELTA WEIGHT---
        ArrayList<double[][]> delta_w = new ArrayList<>();
        for (Layer l : this.layers) {
            delta_w.add(new double[l.nextSize][l.size]);
            for (double[] foo : delta_w.get(delta_w.size()-1)) {
                Arrays.fill(foo, 0.0);
            }
        }

        // for each training example
        for (int x = 0; x < train_data.length; x++) {
            
            // for each layer object (for each set of weights and biases)
            for (int i = 0; i < this.layers.size(); i++) {
                // get activated weight inputs and output deltas
                double[] a_in  = forwards.get(x).get(1).get(i);
                double[] d_out = errors.get(x).get(i + 1);

                // temporary delta weight matrix
                double[][] dw = new double[d_out.length][a_in.length];
                
                // for each weight
                for (int k = 0; k < a_in.length; k++) {
                    for (int j = 0; j < d_out.length; j++) {
                        dw[j][k] = a_in[k] * d_out[j];
                    }
                }

                // add temporary delta_w matrix to respective layer of delta_w ArrayList
                delta_w.set(i,
                    Calc.add(delta_w.get(i), dw)
                );
            }

        }
        
        // divide each delta matrix by number of training examples
        double scalar = 1.0 / ((double)(train_data.length));
        for (int i = 0; i < delta_w.size(); i++) {
            delta_w.set(i,
                Calc.scalar(delta_w.get(i), scalar)
            );
        }

        // multiply each weight by negative learning rate
        for (int i = 0; i < delta_w.size(); i++) {
            delta_w.set(i, Calc.scalar(delta_w.get(i), this.learning_rate));
            delta_w.set(i, Calc.scalar(delta_w.get(i), -1.0));
        }
        


        // ---CALCULATE DELTA_B---
        ArrayList<double[]> delta_b = new ArrayList<>();
        for (Layer l : this.layers) {
            delta_b.add(new double[l.nextSize]);
            Arrays.fill(delta_b.get(delta_b.size()-1), 0.0);
        }
        // sum all neuron deltas
        for (ArrayList<double[]> example : errors) {
            for (int i = 1; i < example.size(); i++) {
                delta_b.set(i-1,
                    Calc.add(
                        delta_b.get(i-1),
                        example.get(i)
                    )
                );
            }
        }
        // divide by amount of training examples to get average
        for (int i = 0; i < delta_b.size(); i++) {
            delta_b.set(i,
                Calc.scalar(delta_b.get(i), scalar)
            );
        }
        // calculate delta_b
        for (int i = 0; i < delta_b.size(); i++) {
            delta_b.set(i,
                Calc.scalar(
                    Calc.scalar(delta_b.get(i), this.learning_rate),
                    -1.0
                )
            );
        }

        // apply delta_w and delta_b
        for (int i = 0; i < this.layers.size(); i++) {
            this.layers.get(i).weights = Calc.add(this.layers.get(i).weights, delta_w.get(i));
            this.layers.get(i).biases  = Calc.add(this.layers.get(i).biases,  delta_b.get(i));
        }
    }




    public void printWeights() {
        for (int i = 0; i < this.layers.size(); i++){
            System.out.println("WEIGHTS FOR LAYER "+i);
            Calc.log(this.layers.get(i).weights);
        }
    }

}
