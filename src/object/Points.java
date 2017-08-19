package object;


/**
 * 
 * objet Points contient:<br>
 * 	-une position en 3d en x<br>
 *  -une position en 3d en y<br>
 *  -une position en 3d en z<br>
 *  -une position à l'affichage en x<br>
 *  -une position à l'affichage en y<br>
 *  -une distance entre la camera et le point<br>
 *  
 *  
 * @version 1.0
 * 
 * @author guest
 */
public class Points {
	
	private double X3D;// position en 3d
	private double Y3D;
	private double Z3D;
	
	private double X2D;// position à l'affichage
	private double Y2D;
	
	private double Dis;// distance
	
	/**
	 * methode de creation de l'objet Points avec ses 3 coordonées
	 * 
	 * @param x (double)
	 * @param y (double)
	 * @param z (double)
	 * 
	 * @author guest
	 * 
	 */
	public Points(double x, double y, double z){
		X3D=x;
		Y3D=y;
		Z3D=z;
	}
	
	// mutateurs uniquement necessaire pour x y dis (il change à chaque fois que l'on bouge)
	
	/**
	 * mutateur de X2D position en 2d à l'affichage
	 * 
	 * 
	 * @param x
	 */
	public void setX2D(double x){
		X2D=x;
	}
	
	/**
	 * mutateur de Y2D position en 2d à l'affichage
	 * 
	 * @param y
	 */
	public void setY2D(double y){
		Y2D=y;
	}
	
	/**
	 * mutateur de la distance entre le point et la camera
	 * 
	 * @param d
	 * 
	 */
	public void setDis(double d){
		Dis=d;
	}
	
	//accesseurs
	
	/**
	 * @return position en x en 3D du point
	 */
	public double getX3D(){
		return X3D;
	}
	
	/**
	 * @return position en y en 3D du point
	 */
	public double getY3D(){
		return Y3D;
	}
	
	/**
	 * @return position en z en 3D du point
	 */
	public double getZ3D(){
		return Z3D;
	}
	
	/**
	 * @return position en x en 2D du point
	 */
	public double getX2D(){
		return X2D;
	}
	
	/**
	 * @return position en y en 2D du point
	 */
	public double getY2D(){
		return Y2D;
	}
	
	/**
	 * @return distance entre le point et la camera
	 */
	public double getDis(){
		return Dis;
	}
	
}
