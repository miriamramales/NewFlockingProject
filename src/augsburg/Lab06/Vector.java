package augsburg.Lab06;

/**Helper class to store new calcuated x and y values */
public class Vector {
    
    private double x;

    private double y;

    /**
     * Constructor creates a vector with the values being passed.
     * @param x
     * @param y
     */
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    /**
     * Returns the x-value of this Vector.
     * @return double X
     */
    public double getVectorX(){
        return this.x;
    }
    /**
     * Returns the y-value of this Vector.
     * @return double Y
     */
    public double getVectorY(){
        return this.y;
    }
    /**
     * Print statement for x and y values of this Vector
     * @return String
     */
    public String toString() {
        return "["+x+","+y+"]";
    }
    /**
     * Creates a vector of the difference between the two points being
     * passed.
     * @param Point p
     * @param Point other
     * @return Vector
     */
    public Vector sub(Point p, Point other){
        double x = p.x - other.x;
        double y = p.y - other.y;
        return new Vector(x,y);
    }
    /**
     * This vector is being normalize and it's new values 
     * are being returned as a new Vector
     * @return Vector 
     */
    public Vector normalize(){
        double angle = Math.atan2(this.y, this.x);
        double x = 2 * Math.cos(angle);
        double y = 2 * Math.sin(angle);
        return new Vector(x,y);
    }
    /**
     * Returns a new vector after the current x and y values are
     * divided by the value being passed.
     * @param double d
     * @return Vector
     */
    public Vector div(double d){
        double x = this.x / d;
        double y = this.y / d;
        return new Vector(x,y);
    }
    /**
     * Adds the values from the Point to the values of the vector and returns 
     * as a new Vector
     * @param Point p
     * @return Vector
     */
    public Vector add(Point p){
        double x = this.x + p.x;
        double y = this.y + p.y;
        return new Vector(x,y);
    }
    /**
     * Vector is being weight by the value of a slider which is being passed.
     * @param double n
     * @return Vector
     */
    public Vector weightSlide(double n){
        double x = this.x * (n/100);
        double y = this.y * (n/100);
        return new Vector(x,y);
    }
    
}
