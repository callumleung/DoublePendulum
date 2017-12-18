public class Main {


    public static void main(String[] args) {
        //TODO: main program goes here

        //create the double pendulum object
        DoublePendulum dp = new DoublePendulum(
                //Particle 1: unit mass, 20cm below origin, 0 velocity, no label
                new Particle3D(1.0, new Vector3D(0.0, -0.2, 0.0), new Vector3D(0.0, 0.0, 0.0), null),
                //Particle 2: unit mass, 40cm below origin, 4m/s velocity in the positive x direction, no label
                new Particle3D(1.0,  new Vector3D(0.0, -0.4, 0.0), new Vector3D(4.0, 0.0, 0.0), null)
        );


    }


}
