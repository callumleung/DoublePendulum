

public class DoublePendulum {

    /**
     * Create the two pendulums of the system.
     */
    private Particle3D p1;
    private Particle3D p2;
    /**
     * Fields for params that appear in the equation of motion but only need to be calculated once.
     */

    private double alpha, OnePlusAlpha, OnePlusAlphaGamma, AlphaBeta, Gamma, OneOverBeta;

    /**
     * Dimensionality of the system.
     */
    private static final int Dim = 4;

    /**
     * a value used in calculating the potential energy of the system.
     */
    public static final double GRAVITY = 9.8;

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

    /**
     * a method to advance the state of the system by performing repeated integrations according to the Runge-Kutta algorithm.
     * @param n the number of integrations to perform.
     * @param t the timestep to be used.
     */

    public void iterate(int n, double t){

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
