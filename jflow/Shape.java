package jflow;

public class Shape {
    public int x;
    public int y;

    /**
     * Shape of matrix
     * @param arr
     */
    public Shape(double[][] arr) {
        this.x = arr[0].length;
        this.y = arr.length;
    }
    /**
     * Matrix shape of vector
     * @param arr
     */
    public Shape(double[] arr) {
        this.x = 1;
        this.y = arr.length;
    }

    // access
    public String toString() {
        return ("("+this.y+", "+this.x+")");
    }

    public boolean equals(Shape s) {
        if ( this.x == s.x && this.y == s.y ) {
            return true;
        }
        return false;
    }
}
