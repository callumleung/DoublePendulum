import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {


    public static void main(String[] args) throws IOException {

        //default values
        double p1y = -0.2;
        double p2y = -0.4;
        double p2vx = 4.0;

        try {
            p1y = Double.valueOf(args[0]);
            p2y = Double.valueOf(args[1]);
            p2vx = Double.valueOf(args[2]);
        } catch (Exception ignore) {
            //ignore any exception and proceed with default values
            System.out.println("Invalid command line arguments; Proceeding with default values.");
        }


        //create the double pendulum object
        DoublePendulum dp = new DoublePendulum(
                //Particle 1: unit mass, 20cm below origin, 0 velocity, no label
                new Particle3D(1.0, new Vector3D(0.0, p1y, 0.0), new Vector3D(-1.0, 0.0, 0.0), null),
                //Particle 2: unit mass, 40cm below origin, 4m/s velocity in the positive x direction, no label
                new Particle3D(1.0, new Vector3D(0.0, p2y, 0.0), new Vector3D(p2vx, 0.0, 0.0), null)
        );


        //create the double pendulum viewer
        DPViewer dpView = new DPViewer(dp);

        //set up the frame to be opened on the screen
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Double Pendulum");
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add the pendulum view to the display
        frame.getContentPane().add(dpView);

        //set the frames content and put it on screen
        frame.pack();
        frame.setVisible(true);

        for (;;){
            dp.iterate((int) 1e4, 1.5e-7);
            //update visualisation
            dpView.repaint();
            //synchronising repaint requests and responses
            //pause the simulation until the current system is drawn to the canvas
            synchronized (dp){
                try {
                    dp.wait();
                } catch (Exception ignore){
                }



            }
        }


    }

}
