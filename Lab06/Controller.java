/*
 * Controller.java
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.text.DecimalFormat;
import java.util.EventListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * The controller class for the temp converter application.
 * @author Amy Larson (with Erik Steinmetz)
 */
public class Controller implements ActionListener, ChangeListener {
    

    /** The model of the circles. */
    private final CircleModel model = new CircleModel();
    /** The gui for the simulation. */
    private final SimulationGUI view;
    
    /** Default constructor to set up the viewer
     */
    public Controller() {
        view = new SimulationGUI(this,model);
        model.setSim(view);
        view.setVisible(true);
        model.start();
    }
    
    /**
     * Performs the actions for each of the JButtons
     * @param ae The event which occurred, identifying which button was pushed.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        //System.out.println("Action called: " + ae);
        if( ae.getActionCommand().equals("Set Up")) {

            // Set the model on pause
            model.pause();

            // Create the circles based on count
            Integer count = Integer.valueOf(this.view.count.getText());
            model.setCount(count);

            // Set the speed of the simulation
            Integer speed = Integer.valueOf(this.view.speed.getText());
            model.setSpeed(speed);
        }
        else if( ae.getActionCommand().equals( "Stop")) {
            model.pause();
        }

        else if( ae.getActionCommand().equals( "Play")) {
            model.play();
        
        }
    }

    @Override
    /**Performs the actions for each of the JSliders  */
    public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();
        if(source instanceof JSlider){
            JSlider slider = (JSlider)source;
            String name = slider.getName();

            if (name.equals("c")){
                int c = slider.getValue();
                if(c == 0){
                    model.setCoherance(1);
                } else{
                    model.setCoherance(c);
                }
            }/**coherance slider*/

            if (name.equals("a")){
                int a = slider.getValue();
                if(a == 0){
                    model.setAlignment(1);
                } else{
                    model.setAlignment(a);
                }
            }/**alignment slider*/
        
            if (name.equals("s")){
                int s = slider.getValue();
                if(s == 0){
                    model.setSeperation(1);
                } else{
                    model.setSeperation(s);
                } 
            }/**seperation slider*/

            if (name.equals("vs")){
                model.setVisualRange(slider.getValue());
            }
        
        
        
        }//if source
        
    }


}
