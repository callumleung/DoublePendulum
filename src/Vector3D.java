

	/**
 *@author Callum Leung s1310284
 *@author Chloe McGeorge s1321516
 */

public class Vector3D { 

    private double X;

    private double Y;

    private double Z;

    /**getters to define getX etc.. for use in further code
     */ 
    public double getX(){return this.X;}
    
    public double getY(){return this.Y;}

    public double getZ(){return this.Z;}


    /**setters that define setX etc.. for use in further code 
     */
    public void setX(double xx){this.X = xx;}

    public void setY(double yy){this.Y = yy;}

    public void setZ(double zz){this.Z = zz;}

    /**default constructor which constructs a new vector with uninitialised x, y, z components
     */ 
    
    public Vector3D(){
    	this.setX(0);
    	this.setY(0);
    	this.setZ(0);
    	}
    //copy constructor 
    public Vector3D(Vector3D original){
    	this.setX(original.getX());
    	this.setY(original.getY());
    	this.setZ(original.getZ());
    }

    /**constructor that initialises the vector to (xx,yy,zz)
     */
    public Vector3D(double xx,double yy,double zz){
    	this.setX(xx);
    	this.setY(yy);
    	this.setZ(zz);
    	}
   
    /**magnitude squared and magnitude instance methods that return doubles as per the definition of magintude
     */
    public double magSquared(){return this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ();
    }

    public double mag(){return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
    }

    /**return a string representation of the vector elements in a useful format the usual (a,b,c) bracket representation used for vectors
     */
    public String toString() {
    	double x=getX();
    	double y=getY();
	double z=getZ();

			return "("+ x + "," + y + "," + z + ")";
    }

    /**scalar multiply and scalar divide by double instance methods these return vectors as each component is multiplied or divided by a scalar
     */
    public static Vector3D multVector3D (Vector3D a, double b) {
    	return new Vector3D(a.getX()*b, a.getY()*b, a.getZ()*b);
    }

    public static Vector3D divVector3D (Vector3D a, double b) {
    	return new Vector3D(a.getX()/b, a.getY()/b, a.getZ()/b);
    }

    /**dot product of two vectors a,b to give a double being the sum of the components multiplied
     */
    public static Double dotVector3D(Vector3D a, Vector3D b) {
	return (a.getX()*b.getX() +a.getY()*b.getY() + a.getZ()*b.getZ());
    }

    /**calculates the cross product of to vectors a,b to return a vector using the standard definition of a vector cross product for each component
     */
    public static Vector3D crossVector3D(Vector3D a, Vector3D b){
	return new Vector3D(a.getY()*b.getZ()- b.getY()*a.getZ(),b.getX()*a.getZ()- a.getX()*b.getZ(),a.getX()*b.getY()- b.getX()*a.getY());
    }

    /**component-wise calculation of addition and subtraction of two vectors, returning a vector
     */
    public static Vector3D addVector3D(Vector3D a, Vector3D b){
    		return new Vector3D(a.getX()+ b.getX(), a.getY()+b.getY(),a.getZ()+b.getZ());
    }

    public static Vector3D subVector3D(Vector3D a,Vector3D b){
    		return new Vector3D(a.getX()-b.getX(), a.getY()-b.getY(), a.getZ()- b.getZ());
    }
    
    /** comparing the difference between the two vector's components to account for rounding errors to ensure that the vector identites return true up to threee decimal places
     *correct to three decimal places
     */
    public static boolean isTrue(Vector3D left, Vector3D right){
    	if (left.getX() -  right.getX()<= 0.001 && left.getY() - right.getY()<=0.001 && left.getZ() - right.getZ() <=0.001)
    		{return true;}
    	
    	else
    		{return false;}
    }
    	
    
    
}
    
    
    
    
    

