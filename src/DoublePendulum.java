

public class DoublePendulum {

    Particle3D p1;
    Particle3D p2;


    /** Here we initialise the initial conditions of the system.
     * We simulate the double pendulum only in the plane so the z coordinates can be ignored.
     * The first pendulum, p1, is fixed to the origin and the second, p2, is fixed to the first.
     *
     * @param p1 (initial position, mass, velocity) of pendulum 1
     * @param p2 (initial position, mass, velocity) of pendulum 2
     *
     */

    public DoublePendulum(Particle3D p1, Particle3D p2){
        //
        //TODO: write constructor

    }

    public void iterate(int n, double h){

        //TODO: Write Runge-Kutta algorithim

    }

    /**
     *  Get the position and velocity of the p2 and return them as a particle3D object.
     * @return
     */

    public Particle3D getPendulum1() {

        //TODO: construct new Particle3D object with p1's coords
        return null;

    }

    /**
     *  Get the position and velocity of p1 and return them as a particle3D object.
     * @return
     */

    public Particle3D getPendulum2() {

        //TODO: construct new Particle3D object with p2's coords
        return null;

   }

    public double getTime(){
        //TODO: return time
       return 0.0;
   }
}
