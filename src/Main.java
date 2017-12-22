import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {

        if (args.length < 3) {
            System.err.println("Please enter valid command line arguments.");
            System.exit(-1);
        }
        double p1y = Double.valueOf(args[0]);
        double p2y = Double.valueOf(args[1]);
        double p2vx = Double.valueOf(args[2]);

        //TODO: main program goes here

        //create the double pendulum object
        DoublePendulum dp = new DoublePendulum(
                //Particle 1: unit mass, 20cm below origin, 0 velocity, no label
                new Particle3D(1.0, new Vector3D(0.0, p1y, 0.0), new Vector3D(0.0, 0.0, 0.0), null),
                //Particle 2: unit mass, 40cm below origin, 4m/s velocity in the positive x direction, no label
                new Particle3D(1.0,  new Vector3D(0.0, p2y, 0.0), new Vector3D(p2vx, 0.0, 0.0), null)
        );


        //Iterate the equations of motion using a time step of 1ms in blocks of 100 iterations, giving output every 0.1 seconds.
        //print 100 lines of output containing: time, kinetic energy, potential and total energy.
        double timestep = 0.00001;
        int numIntegrations = 100;
        int numOutput= 3000;

        BufferedWriter output =  new BufferedWriter(new FileWriter("DoublePendulum.out"));

        for (int i = 0; i < numOutput; i++){

            dp.iterate(numIntegrations, timestep);

            //get particle positions
            Particle3D p1 = dp.getPendulum1();
            Particle3D p2 = dp.getPendulum2();

            //get Kinetic energy
            double ke = p1.kineticEnergy() + p2.kineticEnergy();

            //get potential energy
            double v = DoublePendulum.GRAVITY * (p1.getMass()*p1.getPosition().getY() + p2.getMass()*p2.getPosition().getY() );
            //System.out.println(p1.getPosition().getY() + ", " +p2.getPosition().getY() );

            double tot = ke + v;
            output.write(dp.getTime() + ", " + ke + ", " + v + ", " + tot);
            //output.write(dp.getTime() +"\t"+ String.valueOf(p1.kineticEnergy()));
            output.newLine();

        }

        output.close();
    }




}
