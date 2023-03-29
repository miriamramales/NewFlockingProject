/** Helper class to store xy locations */
public class Point {
    /** Making public for each access. */
    public int x = 0;
    public int y = 0;
    /**
     * Setting the Y and X values
     * @param xx
     * @param yy
     */
    public Point(int xx, int yy) {
        x = xx;
        y = yy;
    }
    /** Print statment for x and y values  */
    public String toString() {
        return "["+x+","+y+"]";
    }
}