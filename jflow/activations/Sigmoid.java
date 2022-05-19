package jflow.activations;

public class Sigmoid implements Activation {
    public double eval(double x) {
        return ( 1 / ( 1 + Math.pow(Math.E,(-1*x)) ) );
    }
    public double[] eval(double[] x) {
        double[] output = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            output[i] = this.eval(x[i]);
        }
        return output;
    }

    public double deval(double x) {
        return this.eval(x) * (1 - this.eval(x));
    }
    public double[] deval(double[] x) {
        double[] output = new double[x.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = this.deval(x[i]);
        }
        return output;
    }
}
