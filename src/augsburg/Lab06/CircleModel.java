package augsburg.Lab06;

/*
 * Circle Model.java
 */

import java.util.ArrayList;
import java.lang.Thread;

/**
 * Models a collection of circles roaming about impacting other circles.
 * 
 * @author Amy Larson (with Erik Steinmetz)
 */
public class CircleModel extends Thread {

    private ArrayList<Circle> circles = new ArrayList<>();

    /** Time in ms. "Frame rate" for redrawing the circles. */
    private int stepSize = 10;
    /** Current number of circles visible in the window. */
    private int count = 0;
    /** Pauses simulation so circles do not move */
    private boolean paused = true;

    private SimulationGUI simulation;

    private int seeRadius = 10;

    private double desiredSeperation = 50;

    private int coheranceSlideValue = 50;

    private int alignSlideValue = 50;

    /** Default constructor. */
    public CircleModel() {
        // All circels that might appear in the graphics window are created, but are not
        // visible.
        for (int i = 0; i < 100; i++) {
            circles.add(new Circle());
        }
    }

    @Override
    public void run() {
        // Forever run the simulation
        while (true) {
            // Move things only if the simulation is not paused
            if (!paused) {

                advanceCircles();
                for (int i = 0; i < count; i++){
                    ArrayList <Circle> n = getNeighbors(circles.get(i), circles);
                    for (int j = 0; j < n.size(); j++){
                        Vector c = cohesion(n.get(j), n);
                        Vector a = alignment(n.get(j), n);
                        Vector s = seperation(circles.get(i), n);
                        newDirection(n.get(j), a, c, s);
                    }
                    
                }
                
                simulation.getContentPane().repaint();
        
            }
            try {
                Thread.sleep(stepSize);
            } catch (Exception e) {

            }
        }
    }
    /** Pause the simulation - circles freeze. */
    public void pause() {
        paused = true;
    }
    /** Circles move again */
    public void play() {
        System.out.println("Playing now");
        paused = false;

    }
    /** Move circles to next location */
    public void advanceCircles() {
        for (int i = 0; i < count; i++) {
            // Advance each circle
            circles.get(i).step();
        
        }
    }
    /** Sets the value of the Coherance Slider 
     *the paramenter int gets a number for the slider to set
     *@param c
     * */
    public void setCoherance(int c){
        this.coheranceSlideValue = c;
    }
    /** Sets the value of the Alignment Slider 
     *the paramenter int gets a number for the slider to set
     *@param a
      */
    public void setAlignment(int a){
        this.alignSlideValue = a;
    }
    /** Sets the value of the Sepration Slider 
     *the paramenter int gets a number for the slider to set
     *@param S 
     */
    public void setSeperation(int s){
        this.desiredSeperation = s;
    }
     /** Sets the value of the VisualRange Slider 
     *the paramenter int gets a number for the slider to set
     *@param VR
      */
    public void setVisualRange(int vr){
        this.seeRadius = vr;
    }
     /** Creates a new Array list   
      * @return the new circles in the array list 
      */
    public ArrayList<Circle> getCircles() {
        return circles;
    }
    /** Set the  value of the Gui simulation 
     * @param Sim 
    */
    public void setSim(SimulationGUI sim) {
        simulation = sim;
    }
    /** Reset circles */
    public void setCount(int circleCount) {
        System.out.println("Making circles!");
        // Must be in bounds. Only 20 circles in the list.
        if (circleCount < 2) {
            circleCount = 2;
        } else if (circleCount > 100) {
            circleCount = 100;
        }
        // Reset "count" circles, making them visible
        count = circleCount;
        for (int i = 0; i < count; i++) {
            circles.get(i).reset();
        }
        // Hide the rest
        for (int i = count; i < 20; i++) {
            circles.get(i).hideCircle();
        }
    }
    /** Set speed of simulation from 1 (slow) to 5 (fast) */
    public void setSpeed(int newSpeed) {
        // speed is between 1 (slow) and 5 (fastest)
        // low speed = high step size
        if (newSpeed < 1) {
            newSpeed = 1;
        } else if (newSpeed > 5) {
            newSpeed = 5;
        }
        stepSize = (6 - newSpeed) * 160; // 80 to 400ms
    }
    /** Calculates the change in X and Y to get new circle
     * @param circle
     * @param other
      */
    public void changeDirection(Circle circle, Circle other) {
        if (circle.getXY().x == other.getXY().x) {
            circle.setDirectionY(circle.getXY().y * -1);
        }
        if (circle.getXY().y == other.getXY().y) {
            circle.setDirectionX(circle.getXY().x * -1);
        }
    }
    /** Creates an Array list that checks the circle  that is next to another circle 
     * and loops through the Circle array list
     * @param c
     * @param circles
     * @return neighbors
     */
    public ArrayList<Circle> getNeighbors(Circle c, ArrayList <Circle> circles){
        ArrayList <Circle> neighbors = new ArrayList<>();
        for(int i = 0; i < count; i++){
            if(c != circles.get(i)){
                double d = c.distance(circles.get(i));
                if( d <= seeRadius){
                    neighbors.add(circles.get(i));
                }
            }
        }
        return neighbors;
    }
    /**
     * Checks to see if the circle has neighbors and if it does 
     * the circles will pull on one another until they are at the center of the orginal 
     * neighbors space.
     * @param neighbors
     * @return Vector new value
     */
    public Vector cohesion(Circle n, ArrayList<Circle> neighbors){

        Vector sum = new Vector(0,0);
    
        for(int i = 0; i < neighbors.size();i++){
            sum = sum.add(neighbors.get(i).getXY());
            //System.out.println(sum.toString());
        }
        
        if(neighbors.size() > 0){
            sum = sum.div(neighbors.size());
        }

        double changeX = n.getXY().x - sum.getVectorX();
        double changeY = n.getXY().y - sum.getVectorY();
        Vector newValue = new Vector(changeX, changeY);
        newValue = newValue.normalize();
        newValue = newValue.div(coheranceSlideValue);
        //neighbors.get(j).setDirectionX((circles.get(j).getXDirection() + newValue.getVectorX()));
        //neighbors.get(j).setDirectionY((circles.get(j).getYDirection() + newValue.getVectorY()));
        return newValue;

    }
    /**
     * makes it so that each circle goes toward the same direction as their neighbors
     * @param Circle n
     * @param neighbors
     * @return Vector steer
     */
    public Vector alignment(Circle n, ArrayList<Circle> neighbors) {
        
        Vector sum = new Vector(0,0);

        for (int i = 0; i < neighbors.size(); i++) {
            sum = sum.add(neighbors.get(i).getDirection());
        }

        if(neighbors.size() > 0){
            sum = sum.div(neighbors.size());
            sum = sum.normalize();
        }

        Vector steer = new Vector(0,0);

        Point s = new Point((int)sum.getVectorX(), (int)sum.getVectorY());
        
        steer = steer.sub(s, n.getDirection());
        steer = steer.weightSlide(alignSlideValue);
        //neighbors.get(j).setDirectionX(neighbors.get(j).getXDirection() + steer.getVectorX());
        //neighbors.get(j).setDirectionY(neighbors.get(j).getYDirection() + steer.getVectorY());
        return steer;
    }
    /**
     * Creates a boundary between circles from their neighbors so that there are no colisions
     * @param thisCircle
     * @param neighbors
     * @return Vector steer
     */
    public Vector seperation(Circle thisCircle, ArrayList<Circle> neighbors){
        
        Vector steer = new Vector(0,0);
        Vector diff = new Vector(0,0);
        Point diffPoint;
        int count = 0;

        for(Circle other : neighbors){
            double distance = thisCircle.distance(other);
            if ((distance > 0) && (distance < desiredSeperation)){
                diff = diff.sub(thisCircle.getXY(), other.getXY());
                diff = diff.normalize();
                diff = diff.div(distance);
                diffPoint = new Point((int)diff.getVectorX(), (int)diff.getVectorY());
                steer = steer.add(diffPoint);
                count ++;
            }
        }
        if (count > 0){
            //average
            steer = steer.div(count);
            steer = steer.normalize();
            steer = steer.weightSlide(desiredSeperation);
        }

        return steer;
    
    }
    /** Adds values of all vectors being passed together and adds it to the 
     * current direction of the Circle that is being passed in the method.
     * 
     * @param Circle n
     * @param Vector a
     * @param Vector c
     * @param Vector s
     */
    public void newDirection(Circle n, Vector a, Vector c, Vector s){
        n.setDirectionX(n.getXDirection() + a.getVectorX() + c.getVectorX() + s.getVectorX());
        n.setDirectionY(n.getYDirection() + a.getVectorY() + c.getVectorY() + s.getVectorY());
    }

}//end of CircleModel
