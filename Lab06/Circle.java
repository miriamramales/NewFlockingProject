import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Random;

/** Circle for drawing in a JFrame
 *
 * @author Amy Larson
 */
public class Circle extends JPanel {

    /** Unique id (for debugging) */
    static int nextId = 0;

    
    static int getId() {
        return nextId++;
    }
    private int id;

    /** x and y bounds to keep circles in the playAreas */
    private final int xMINRANGE = 60;
    private final int xMAXRANGE = 740;
    private final int yMINRANGE = 160;
    private final int yMAXRANGE = 740;

    /** Fixed size */
    private int radius = 10;
    
    /** Color specified in RGB */
    private Color color = new Color(10, 10, 10);

    /** Location of the JPanel in which the circle is drawn */
    private Point xy = new Point(0, 0);

    /** Delta of location at each timestep */
    private Point direction = new Point(+1, +1);

    /** Circels have many random components */
    private Random random = new Random();

    /** Drawn in window when visible */
    private boolean visible = false;

    //Getters and Setters
    /**
     * changes the radius of the circle
     * @param radius
     */
    public void changeSize(int radius){
        this.radius = radius;
    }
    /**
     * set the y direction  if the circle 
     * @param d
     */
    public void setDirectionY(double d){
        this.direction.y = (int)d;
    }
    /**
     * set the x direction  if the circle 
     * @param d
     */
    public void setDirectionX(double d){
        this.direction.x = (int)d;
    }
    /**
     * set the colors of the circles
     * @param color
     */
    public void setColor(Color color){
        this.color = color;
    }
    /**
     * sets the y position of the circle
     * @param y
     */
    public void setYPosition(int y){
        this.xy.y = y;
    }
    /**
     * sets the x position of the circle
     * @param x
     */
    public void setXPosition(int x){
        this.xy.x = x;
    }
    /**
     * gets the x direction of the circle
     * @return
     */
    public double getXDirection(){
        return this.direction.x;
    }
    /**
     * gets the y direction of the circle
     * @return
     */
    public double getYDirection(){
        return this.direction.y;
    }
    /**
     * get the xy values of the circle 
     * @return
     */
    public Point getXY() {
        return xy;
    }
    /**
     * gets the direction of the xy values
     * @return
     */
    public Point getDirection(){
        return this.direction;
    }
    /**set the color of circles */
    public Color color(){
        return this.color;
    }
    /**
     * sets the radius of circle
     * @return
     */
    public int radius(){
        return this.radius;
    }

    //Methods

    /** Reassigns member variables to the circle. */
    public void reset() {
        randomXY();
        randomDirection();
        randomColor();
        setLocation(xy.x, xy.y);
        showCircle();
    }
    /** Circle is visible */
    public void showCircle() {
        visible = true;
    }
    /** Circle is not visible */
    public void hideCircle() {
        visible = false;
    }
    /** Default constructor */
    public Circle() {
        id = getId();   // for debugging

        this.setSize(radius*2, radius*2);

        // Make the box/panel on which the circle is drawn transparent
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));

        // Randomly assign values
        randomXY();
        randomDirection();
        randomColor();
    }
    /** Randomly assign its location based on the fixed ranges. */
    public void randomXY() {
        // place at random location
        xy.x = random.nextInt(xMAXRANGE - xMINRANGE) + xMINRANGE;
        xy.y = random.nextInt(yMAXRANGE - yMINRANGE) + yMINRANGE;
    }
    /** Randomly point it in a direction with random "speed" */
    public void randomDirection() {
        // set in a random direction
        direction.x = random.nextInt(6) - 3;
        direction.y = random.nextInt(6) - 3;
    }
    /** Randomly assign the RGB components */
    public void randomColor() {
        // color randomly
        color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
    /** Move the robot the "delta" for 1 timestep */
    public void step() {
        xy.x += direction.x;
        xy.y += direction.y;
        
        if (xy.x < xMINRANGE){
            xy.x = xMINRANGE;
            direction.x *= -1;
        }
        if (xy.x > xMAXRANGE){
            xy.x = xMAXRANGE;
            direction.x *= -1;
        }
        if (xy.y < yMINRANGE){
            xy.y = yMINRANGE;
            direction.y *= -1;
        }
        if (xy.y > yMAXRANGE){
            xy.y = yMAXRANGE;
            direction.y *= -1;
        }
 
    }

    @Override
    public void paintComponent(Graphics g) {
        // This is called every time the circle location is reset in the CircleModel
        // System.out.print(" P"+id);
        setLocation(xy.x,xy.y);
        super.paintComponent(g);
        if (visible) {
            g.setColor(color);
            g.fillOval(0, 0, radius*2, radius*2);
        }
    }
    /**
     * Distance formula 
     * @param other
     * @return
     */
    public double distance(Circle other){
        double changeX = this.xy.x - other.xy.x;
        double changeY = this.xy.y - other.xy.y;
        return Math.pow((Math.pow(changeX,2) + Math.pow(changeY, 2)), 0.5);
        
    }

}