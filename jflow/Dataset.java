package jflow;

public class Dataset {
    // training data
    public double[][] train_data;
    public double[][] train_labels;
    // eval data
    public double[][] eval_data;
    public double[][] eval_labels;

    public Dataset(double[][] train_data, double[][] train_labels) {
        this.train_data = train_data;
        this.train_labels = train_labels;
        this.eval_data = null;
        this.eval_labels = null;
    }


}
