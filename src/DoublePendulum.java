

public class DoublePendulum {

    /**
     * Create the two pendulums of the system.
     */
    private Particle3D p1;
    private Particle3D p2;

    /**
     * fields to hold the state of the system
     */
    private double length1, mass1; //length and mass of p1
    private double length2, mass2; //length and mass of p2

    /**
     * Fields for params that appear in the equation of motion but only need to be calculated once.
     */
    private double alpha, OnePlusAlpha, OnePlusAlphaGamma, AlphaBeta, Gamma, OneOverBeta;

    /**
     * a value used in calculating the potential energy of the system.
     */
    public static final double GRAVITY = 9.8;

    private double time = 0.0;

    /**
     * Dimensionality of the system.
     */
    private static final int Dim = 4;

    /**
     * An array to hold the state variables evaluated at a specific time.
     * The size of the array is dependant on the dimensionality of our problem.
     */

    private double[] dydt = new double[Dim];

    /**
     * Interally the state of the system is held as a 4 dimensional array (two dims per pendulum)
     * state[0] = the angle of p1
     * state[1] = the angle of p2
     * state[2] = the angular frequency of p1
     * state[3] = the angular frequency of p2.
     */

    private double[] state = new double[Dim];

    /**
     * Two arrays for holding the previous and intermediate points in the solver.
     */

    private double[] midpt =  new double[Dim];
    private double[] prev = new double[Dim];


    /** Here we initialise the initial conditions of the system. Specify the initial conditions of the pendulum with 2 Particle3D objects.
     * We simulate the double pendulum only in the plane so the z coordinates can be ignored.
     * The first pendulum, p1, is fixed to the origin and the second, p2, is fixed to the first.
     *
     * Here we work in polar coords, the transformation to which is performed within this constructor.
     *
     * @param p1 (initial position, mass, velocity) of pendulum 1
     * @param p2 (initial position, mass, velocity) of pendulum 2
     *
     */

    public DoublePendulum(Particle3D p1, Particle3D p2){

        //get the distance between  the two bobs
        Vector3D p12 = Particle3D.relativePosition(p1, p2);

        //get the velocity of p2 relative to p1
        Vector3D v12 = Particle3D.relativeVelocity(p1, p2);


        mass1 = p1.getMass();
        mass2 = p2.getMass();

        //compute the squares of the lengths initially
        length1 = p1.getPosition().getX() * p1.getPosition().getX() + p1.getPosition().getY() * p1.getPosition().getY();
        length2 = p12.getX() * p12.getX() + p12.getY() * p12.getY();

        //convert the angles
        state[0] = Math.atan2(p1.getPosition().getX(), -p1.getPosition().getY());
        state[1] = Math.atan2(p12.getX(), -p12.getY());

        //angular velocities (r cross v dot k) over 1 squared
        state[2] = ((p1.getPosition().getX() * p1.getVelocity().getY()) - (p1.getPosition().getY() * p1.getVelocity().getX()))/length1;
        state[3] = (p12.getX() * v12.getY() - p12.getY() * v12.getX())/length2;

        //set the lengths equal to  the square roots of the current lengths
        length1 = Math.sqrt(length1);
        length2 = Math.sqrt(length2);

        //compute the parameters that are used in the equation of motions
        alpha = mass1/mass2;
        double beta = length1/length2;
        Gamma = GRAVITY/length1;
        OnePlusAlpha = 1.0 + alpha;
        OnePlusAlphaGamma = OnePlusAlpha * Gamma;
        AlphaBeta = alpha * beta;
        OneOverBeta = 1/beta;

    }

    /**
     * A method to advance the state of the system by performing repeated integrations according to the Runge-Kutta algorithm.
     * We use the 4th order Runge-Kutta here.
     *
     * perform n steps of the integration, with each step being h in length.
     *
     * @param n the number of integrations to perform.
     * @param h the timestep to be used.
     */

    public void iterate(int n, double h){

        //TODO: Write Runge-Kutta algorithim
        //params that used in the equations but are consts and only need to  be calulated once.
        final double HALF = 0.5*h, THIRD = h/3, SIXTH = h/6;

        for (int step =  0; step < n; step++){

            for (int i = 0; i < Dim; i++){
                midpt[i] = prev [i] = state[i];
            }

            //fill dydt with the A vector
            evaluateDyDt(time, midpt);

            //add 1/6 A to the state and load midpt with the intermediate time point y+a/2
            for (int i = 0; i < Dim; i++){
                state[i] += SIXTH * dydt[i];
                midpt[i] = prev[i] + HALF * dydt[i];
            }

            //fill dydt with the b vector
            evaluateDyDt(time + HALF, midpt);
            // add 1/3 B to the state, load midpoint with the intermediate time point y + b/2
            for (int i = 0; i < Dim; i++){
                state[i] += THIRD * dydt[i];
                midpt[i] = prev[i] + HALF * dydt[i];

            }

            //fill dydt with the c vector
            evaluateDyDt(time + HALF, midpt);
            //Add 1/3 c to the state and load a midpoint with the final point y+c
            for (int i = 0;  i < Dim; i++){
                state[i] += THIRD * dydt[i];
                midpt[i] =  prev[i] + h * dydt[i];
            }

            //fill dy/dt with the d vector
            evaluateDyDt(time, midpt);

            //add 1/6 d to the state

            for (int i = 0; i < Dim; i++){
                state[i] += SIXTH * dydt[i];
            }

            //increment the time
            time += h;

        }
    }

    /**
     *  Get the position and velocity of the p2 and return them as a particle3D object.
     *  converting back from  polar coords.
     * @return
     */

    public Particle3D getPendulum1() {

        return new Particle3D(mass1,
                new Vector3D(length1*Math.sin(state[0]), -length1*Math.cos(state[0]), 0.0), //position
                new Vector3D(length1*state[2]*Math.cos(state[0]), length1*state[2]*Math.sin(state[0]), 0.0), //velocity
                null);

    }

    /**
     *  Get the position and velocity of p1 and return them as a particle3D object.
     *  Converting back from polar coords.
     * @return
     */

    public Particle3D getPendulum2() {

        return new Particle3D( mass2,
                new Vector3D( length1*Math.sin(state[0]) + length2*Math.sin(state[1]),
                        -length1*Math.cos(state[0]) - length2*Math.cos(state[1]),
                        0.0),//position
                new Vector3D( length1*state[2]*Math.sin(state[0]) + length2*state[3]*Math.cos(state[1]),
                        length1*state[2]*Math.sin(state[0] + length2*state[3]*Math.sin(state[1])),
                        0.0), //velocity
                null);

   }

    /**
     * return the time elapsed since the start of the simulation.
     * @return
     */
    public double getTime(){

       return time;
   }

    /**
     * A method for calculating the RHS of the equation dy[i]/dt =..., according to the EoM for the double pendulum.
     * This loads the Array dydt for later use in other methods once called.
     *
     * @param t the time point
     * @param y the set of coords, y[i], at which to evalutate the RHS at.
     */
    private void evaluateDyDt(double t, double[] y){

        dydt[0] = y[2]; dydt[1] = y[3];

        double s01 = Math.sin(y[0] - y[1]);
        double c01 = Math.cos(y[0] - y[1]);

        double nu1 = y[2]*y[2]*s01 - Gamma * Math.sin(y[1]);
        double nu2 = OnePlusAlphaGamma * Math.sin(y[0]) + AlphaBeta*y[3]*y[3]*s01;
        double f = 1.0/(1.0 + alpha*s01*s01);

        dydt[2] = - f * (nu2 + alpha*c01*nu1);
        dydt[3] = f * OneOverBeta * (OnePlusAlpha*nu1 + c01*nu2);


   }








}
