import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InteractiveDP extends JFrame{

    //current simulation state
    private DoublePendulum dp = null;

    //background thread to run simulation in
    private Thread dpBackground;

    //a copy of the state of the system to prevent concurrency issues
    private Particle3D[] state = new Particle3D[2];

    //default timestep value
    private double timestep = 1.5e-7;

    //user Objects to interact with
    private InteractiveDPViewer viewer = new InteractiveDPViewer(this);
    private InteractiveDPControlPanel controls = new InteractiveDPControlPanel(this);


    /**Constructor for the Interactive Double Pendulum.
     * Here we create a window and arrange the two view classes within.
    */
    public InteractiveDP() {

        //construct the window and view objects
        super("Interactive Double Pendulum");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set default initial conditions
        //Particle 1: unit mass, 20cm below origin, 0 velocity, no labe
        state[0] = new Particle3D(1.0, new Vector3D(0.0, -0.2, 0.0), new Vector3D(0.0, 0.0, 0.0), null);
                //Particle 2: unit mass, 40cm below origin, 4m/s velocity in the positive x direction, no label
        state[1] = new Particle3D(1.0,  new Vector3D(0.0, -0.4, 0.0), new Vector3D(4.0, 0.0, 0.0), null);

        //layout viewer and controller
        getContentPane().add(viewer, BorderLayout.CENTER);
        getContentPane().add(controls, BorderLayout.SOUTH);


        //create the controller object on the swing thread using invokelater
        //
    }

    /**
     * Accessor methods
     *  @param particle 1 is upper bob and 2 is the lower bob
     *
     */
    //sets the mass of a particular particle and ensures that the background thread is not running
    public void setMass(int particle, double mass){
        if (dpBackground != null) return;

        if (particle > 0 && particle < state.length && mass > 0.0) {
            state[particle].setMass(mass);
        }
    }

    public void setPosition(int particle, Vector3D position){
        if (dpBackground != null) return;

        if (particle > 0 && particle < state.length && position != null) {
            state[particle].setPosition(position);
        }

    }

    public void setVelocity(int particle, Vector3D velocity){
        if (dpBackground != null) return;

        if (particle > 0 && particle < state.length && velocity != null) {
            state[particle].setVelocity(velocity);
        }


    }

    public void setTimestep(double step){
        if (dpBackground != null) return;

        timestep = step;
    }

    /**
     * obtain the current state of the simulation.
     * this is thread state so that the state returned is consistent.
     *
     * @return masses, positions and velocities of the two bobs as a Particle3D array
     */
    public Particle3D[] getDpState() {

        if (dpBackground != null) {

            synchronized (dp) {
                state[0] = dp.getPendulum1();
                state[1] = dp.getPendulum2();
            }
        }
        return state;
    }

    public double getTimestep(){
        return timestep;
    }

    /**
     * Start a simulation running in the background.
     * If a simulation is already running this method does nothing.
     */

    public void startSimulation(){
        //do nothing if the the simulation is already running
        if (dpBackground != null) return;

        //set up new simulation iwith specified initial conditions

        dp = new DoublePendulum(state[0], state[1]);

        //  run the simulation in a background thread
        dpBackground = new Thread() {
            @Override
            public void run(){
                //iterate the equations of motion until the thread is stopped.

                //communicate with the swing thread
                javax.swing.SwingUtilities.invokeLater( new Runnable() {
                    @Override
                    void run() {
                        // Insert code to run on Swing thread here
                        // Inform the viewer that the simulation has started
                    }
                } );

                //iterate the EoM's until thread is interrupted
                while (!isInterrupted()){
                    dp.iterate((int) 1e4, 1.5e-7);
                }

                // Below code deals with ending of the simulation and doesn't called until the
                // iteration and therefore the simulation has stopped.


                //update the local copy of the system from that of the DoublePendulum object
                getDpState();

                //communicate with the swing thread
                javax.swing.SwingUtilities.invokeLater( new Runnable() {
                    @Override
                    void run() {
                        // remove reference to the background thread
                        dpBackground = null;

                        //inform view objects that the simulation has finshed
                    }
                } );

            }
        };

        dpBackground.start();



    }

    /**
     * A corresponding stop simulation method.
     * Again we do nothing if the simulation is already stopped.
     */

    public void stopSimulation(){
        if (dpBackground == null) return;
        //otherwise we now stop the thread
        dpBackground.interrupt();
    }




}
