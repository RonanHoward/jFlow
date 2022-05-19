package lib;

import java.awt.Color;

public class Point {
    public double x;
    public double y;
    public Color color;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
    }
    public Point(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setColor(Color color) {
        this.color = color;
    }
}
