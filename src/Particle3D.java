
import java.io.*;
import java.util.Scanner;

/**
 *Computer Modelling Exercise 3
 *@author C. McGeorge s1321516
 *@author C. Leung s1310284
 *
 */


@SuppressWarnings("unused")
public class Particle3D {
    /**Properties
     */
   
    private Vector3D position = new Vector3D();
    private Vector3D velocity = new Vector3D();	
    private double mass;
    private String label; 	
    
    //setters and getters

    /**Get the position of a particle 
     *@return a vector that represents the position
     */

    public Vector3D getPosition(){return position;}
    /**Get the velocity 
     *@return a vector that gives the 3D velocity
     */
    public Vector3D getVelocity(){return velocity;}
    /**Get the masss
     *@return a double describes the mass
     */
    public double getMass(){return mass;}
    /**Get the name
     *@return a string
     */
    public String getLabel(){return label;}
    /**Set the position of a particle
     *@param p is a vector that describe sthe position of the particle
     */
    public void setPosition(Vector3D p) {this.position=p;}
    /**Set the velocity of the particle
     *@param v is a vector describing the velocity of the particle
     */
    public void setVelocity(Vector3D v) {this.velocity=v;}
    /**Set the mass of the particle 
     *@param m is a double representing the mass of the particle
     */
    public void setMass (double m) {this.mass=m;}
    /**Set the name of the particle 
     *@param l is a string representing the name of the particle 
     */
    public void setLabel(String l){this.label=l;}

    //Constructors

    /**A default constructor is required to indicate that the properties are not initialised
     *The constructor will set all properties to appropriate defaults.
     */
    public Particle3D() {
	this.setPosition(new Vector3D());
	this.setVelocity(new Vector3D());
	this.setMass(Double.NaN);
	this.setLabel(new String());
    }
 
    /**An explicit constructor is required to construct a new Particle3D with 
     *explicitly given position, velocity and mass
     *
     *@param m is a double that defines mass
     *@param p is a vector that defines the position
     *@param v is a vector that defines the velocity
     *@param l is a string tat represents the name of the particle
     */
    public Particle3D(double m, Vector3D p, Vector3D v, String l) {
	this.setMass(m);
	this.setPosition(p);
	this.setVelocity(v);
	this.setLabel(l);
    }

    //put scanner here

    public static void setParticle3D(Particle3D part, String filename){
	//	BufferedReader input = new BufferedReader (new FileReader (filename));
    String input = filename; 
    Scanner scan = new Scanner(input);
    double mass1 = scan.nextDouble();    
    part.setMass(mass1);
    Vector3D P1=new Vector3D();
    P1.setX(scan.nextDouble());
    P1.setY(scan.nextDouble());
    P1.setZ(scan.nextDouble());
    part.setPosition(P1);
    Vector3D V1=new Vector3D();		
    V1.setX(scan.nextDouble());
    V1.setY(scan.nextDouble());
    V1.setZ(scan.nextDouble());
    part.setVelocity(V1);
    }


    /**Returns a useful string representation of Particle3D
     *this can be used to print a Particle3D instance using the %s
     *format idnetifier
     */
    public String toString(){
	Vector3D pos=getPosition();
	return this.getLabel() + pos.getX() + pos.getY() + pos.getZ() ;
}

    //Instance Methods

    /**Find the kinetic energy of Particle3D (1/2m*v^2)
     *@return a double that gives the value of the kinetic energy
     */
    public double kineticEnergy() { return 0.5 * mass * Vector3D.dotVector3D(velocity, velocity);}
    /**Time integration: evolve the velocity according to 
     *dv=f/m*dt
     *@param dt is the infinitesimal time step
     *@param force is a vector that describes the current force on the particle
     */
    public void leapVelocity(double dt, Vector3D force) {
	velocity =Vector3D.addVector3D( velocity, Vector3D.multVector3D(force,dt/mass));
    }

    public void leapVelocity(double dt,Vector3D force1, Vector3D force2){
	velocity = Vector3D.addVector3D( velocity, Vector3D.multVector3D(Vector3D.addVector3D(force1,force2),dt/(2*mass)));
    }

    /**Time integration to evolve the position
     *dx=v*dt+0.5*a*dt^2
     *@param dt is the infinitesimal time step
     *@param force is a vector that describes the current force 
     */
    
    public void leapPosition(double dt, Vector3D force) {
	 //Double c= 1/(2*mass)*dt*dt;

	 //define each individual term in the above equation
	 Vector3D cForce = Vector3D.multVector3D(force,dt*dt*(1/(2*mass)));
	 Vector3D vdt= Vector3D.multVector3D(velocity,dt);
	 Vector3D sum = Vector3D.addVector3D(vdt, cForce);

	 //final form of the above equation to be evauluated
	position=Vector3D.addVector3D( position,sum);
       }

    public void leapPosition(double dt){
    	position = Vector3D.addVector3D( position, Vector3D.multVector3D(velocity,dt));
     }
     
    /** a method to evaluate the relative separation 
     *i.e. r(t+dt)-r(t)
     */
    public static Vector3D relativePosition(Particle3D A, Particle3D B){
	    return Vector3D.subVector3D(B.getPosition(), A.getPosition());
	    }

    public static Vector3D relativeVelocity(Particle3D A, Particle3D B){

        return Vector3D.subVector3D(B.getVelocity(), A.getVelocity());
    }
    



};
