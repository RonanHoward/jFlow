# jFlow
jFlow is a framework independent machine learning library for Java 8+ that allows the creation and training of feed forward neural networks. This was created as a school project and is very specialized for classification. More generalized support will be added in the future but for now I need a final project to turn in.

## Usage
The jFlow library contains all classes that are essential to create, train, and debug neural networks. The lib folder contains utilites to visually explore the training algorithm. The lib folder also contains sample generic datasets.
<br>
To create a neural network, create an `ArrayList<Layer>()` variable to hold the layers you want to add to your neural network. You can add new `Layer()` objects to this list by specifying the layer size, next layer size, and the activation function. The first layer in the list will become the input layer and the last specified next layer size will become an additional output layer.
<br>
Create a neural network by using the `Model` constructor and passing the Layer ArrayList as a parameter.
```
// create network with layer sizes [2, 4, 2, 1]

ArrayList<Layer> layers = new ArrayList<>();
layers.add(new Layer(2, 4, new Tanh()));
layers.add(new Layer(4, 2, new Tanh()));
layers.add(new Layer(2, 1, new Tanh()));

Model model = new Model(layers);
```
Remember that your amount of layers equals the size of your `ArrayList<Layer>` variable plus 1.
```
// perceptron model
ArrayList<Layer> layers = new ArrayList<>();
layers.add(new Layer(2, 1, new Tanh()));

Model model = new Model(layers);
```
You can train a model by using the `fit()` method. This will run one epoch of a dataset. It takes 2 arguments `double[][] train_data, double[][] train_labels` which represent lists of vectors that will be forwarded through the network and vectors that correlate with the expected output.
```
// train model with 1000 epochs
for ( int i = 0; i < 1000; i++) {
    model.fit(train_data, train_labels);
}
```
A method will be added in the future to make the fitting process much simpler.

## lib
It's very important to note that, lib classes are only programmed to handle networks with an input size of 2 and an output size of 1. This will be improved to be more generalized as development continues.
<br><br>
The main method of the `Display` class contains 2 main methods.
```
// displays a static image of the model evaluating the HashSet of points
Display.main(
    Model model,
    HashSet<Point> points
);
```
```
// displays a moving graph of how model trains over specified amount of epochs
Display.main(
    Model model,
    HashSet<Point> points,
    double[][] train_data,
    double[][] train_labels,
    int epochs,
    int millisecond_delay
);
```
