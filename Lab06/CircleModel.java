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
    private int stepSize = 200;
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
        for (int i = 0; i < 20; i++) {
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
                    System.out.println(n.toString());
                    System.out.println("----");
                    cohesion(n);
                    alignment(n);
                    seperation(circles.get(i), n);
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
            // Set the location, which prompts the viewer to newly display the circle
            // circles.get(i).setLocation(circles.get(i).getXY().x,
            // circles.get(i).getXY().y);
        }
    }

    public void setCoherance(int c){
        this.coheranceSlideValue = c;
    }

    public void setAlignment(int a){
        this.alignSlideValue = a;
    }

    public void setSeperation(int s){
        this.desiredSeperation = s;
    }

    public void setVisualRange(int vr){
        this.seeRadius = vr;
    }

    public ArrayList<Circle> getCircles() {
        return circles;
    }

    public void setSim(SimulationGUI sim) {
        simulation = sim;
    }

    /** Reset circles */
    public void setCount(int circleCount) {
        System.out.println("Making circles!");
        // Must be in bounds. Only 20 circles in the list.
        if (circleCount < 2) {
            circleCount = 2;
        } else if (circleCount > 20) {
            circleCount = 20;
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


    public void changeDirection(Circle circle, Circle other) {
        if (circle.getXY().x == other.getXY().x) {
            circle.setDirectionY(circle.getXY().y * -1);
        }
        if (circle.getXY().y == other.getXY().y) {
            circle.setDirectionX(circle.getXY().x * -1);
        }
    }

    public ArrayList<Circle> getNeighbors(Circle c, ArrayList <Circle> circles){
        ArrayList <Circle> neighbors = new ArrayList<>();
        for(int i = 0; i < count; i++){
            if(c != circles.get(i)){
                double d = c.distance(circles.get(i));
                System.out.println(d);
                if( d <= seeRadius){
                    neighbors.add(circles.get(i));
                }
            }
        }
        return neighbors;
    }
    
    public void cohesion(ArrayList<Circle> neighbors){

        Vector sum = new Vector(0,0);
        for(int i = 0; i < neighbors.size();i++){
            sum = sum.add(neighbors.get(i).getXY());
            //System.out.println(sum.toString());
        }
        

        if(neighbors.size() > 0){
            sum = sum.div(neighbors.size());
        }
        for(int j = 0; j < neighbors.size() ; j++){
            double changeX = neighbors.get(j).getXY().x - sum.getVectorX();
            double changeY = neighbors.get(j).getXY().y - sum.getVectorY();
            Vector newValue = new Vector(changeX, changeY);
            newValue = newValue.normalize();
            newValue = newValue.div(coheranceSlideValue);
            neighbors.get(j).setDirectionX((circles.get(j).getXDirection() + newValue.getVectorX()));
            neighbors.get(j).setDirectionY((circles.get(j).getYDirection() + newValue.getVectorY()));
        }

    }
    

    public void alignment(ArrayList<Circle> neighbors) {
        
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
        
        for(int j = 0; j < neighbors.size(); j++){
            steer = steer.sub(s, neighbors.get(j).getDirection());
            System.out.println(steer.toString());
            steer = steer.div(alignSlideValue);
            System.out.println(steer.toString());
            System.out.println("----");
            neighbors.get(j).setDirectionX(neighbors.get(j).getXDirection() + steer.getVectorX());
            neighbors.get(j).setDirectionY(neighbors.get(j).getYDirection() + steer.getVectorY());
        }   
    }

    public void seperation(Circle thisCircle, ArrayList<Circle> neighbors){
        
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
            steer = steer.div(count);
            steer = steer.normalize();
            steer = steer.div(desiredSeperation);
        }

        for(int j = 0; j < neighbors.size(); j++){
            neighbors.get(j).setDirectionX(neighbors.get(j).getXDirection() + steer.getVectorX());
            neighbors.get(j).setDirectionY(neighbors.get(j).getYDirection() + steer.getVectorY());
        }  
    }

}//end of CircleModel
