package object;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Objet polygon contient:<br>
 *   - numero point 1<br>
 *   - numero point 2<br>
 *   - numero point 3<br>
 *   - red<br>
 *   - green<br>
 *   - blue<br>
 *   - alpha<br>
 *   - texture<br>
 *   - path, le chemin d'acces a la texture<br>
 *   - un boolean pour savoir si le Z-Buffer doit etre appliqué(true) ou peut etre passer grace au calcul de normale (false)<br>
 *   - choice texture indique si on a choisis une texture (true) ou des couleur (false)<br>
 *   - coordonnée X du point 1 du polygon déplié<br>
 *   - coordonnée Y du point 1 du polygon déplié<br>
 *   - coordonnée X du point 2 du polygon déplié<br>
 *   - coordonnée Y du point 2 du polygon déplié<br>
 *   - coordonnée X du point 3 du polygon déplié<br>
 *   - coordonnée Y du point 3 du polygon déplié<br>
 *   - dist entre 1 et 2<br>
 *   - dist entre 2 et 3<br>
 *   - dist entre 3 et 1<br>
 * @version 1.1
 * 
 * @author Hippolyte
 *
 */
public class Polygon{
	
	private int P1;
	private int P2;
	private int P3;
	
	private BufferedImage img=new BufferedImage(104,104, BufferedImage.TYPE_INT_ARGB_PRE);
	private String path;
	
	private boolean print;// un boolean pour savoir si le Z-Buffer doit etre appliqué (true) 
						  // ou peut etre passer grace au calcul de normale (false)
	
	private int Red;
	private int Green;
	private int Blue;
	private int Alpha;
	
	private boolean choiceTexture;// indique si on a choisis une texture (true) ou des couleur (false)
	
	private double X1;// coordonnée point 1 en X
	private double Y1;// coordonnée point 1 en Y
	
	private double X2;// coordonnée point 2 en X
	private double Y2;// coordonnée point 2 en Y
	
	private double X3;// coordonnée point 3 en X
	private double Y3;// coordonnée point 3 en Y
	
	private double dist1to2;
	private double dist2to3;
	private double dist3to1;
	
	/**
	 * 
	 * methode de creation de l'objet polygon avec ses trois point sa texture  
	 * 
	 * @param Point1 (int)
	 * @param Point2 (int)
	 * @param Point3 (int)
	 * @param path de la BufferedImage
	 * 
	 * @author Hippolyte
	 *
	 */
	public Polygon(int point1, int point2, int point3, String path){// texture 
		P1=point1;
		P2=point2;
		P3=point3;
		
		Red=255;
		Green=255;
		Blue=255;
		Alpha=0;
		
		this.path=path;
		
		try{
			img=ImageIO.read(new File(path));
		}catch(IOException e) {}
		
		X1=0.0;
		Y1=0.0;
		
		X2=0.0;
		Y2=0.0;
		
		X2=0.0;
		Y2=0.0;
		
		dist1to2=0.0;
		dist2to3=0.0;
		dist3to1=0.0;
		
		choiceTexture=true;
		
		generatePolygon();
	}
	
	/**
	 * 
	 * methode de creation de l'objet polygon avec ses trois point sa couleur
	 * 
	 * @param Point1 (int)
	 * @param Point2 (int)
	 * @param Point3 (int)
	 * @param couleur (color)
	 * 
	 * @author Hippolyte
	 *
	 */
	public Polygon(int point1, int point2, int point3, Color c){// couleur
		P1=point1;
		P2=point2;
		P3=point3;
		
		Red=c.getRed();
		Green=c.getGreen();
		Blue=c.getBlue();
		Alpha=c.getAlpha();
		
	    img=new BufferedImage(104,104, BufferedImage.TYPE_INT_ARGB_PRE);
	    
	    X1=0.0;
		Y1=0.0;
		
		X2=0.0;
		Y2=0.0;
		
		X2=0.0;
		Y2=0.0;
	    
		dist1to2=0.0;
		dist2to3=0.0;
		dist3to1=0.0;
		
		choiceTexture=false;
	}
	
	
	/**
	 * 
	 * @return premier point du polygone
	 */
	public int getP1(){
		return P1;
	}
	
	/**
	 * 
	 * @return deuxieme point du polygone
	 */
	public int getP2(){
		return P2;
	}
	
	/**
	 * 
	 * @return troisieme point du polygone
	 */
	public int getP3(){
		return P3;
	}
	
	/**
	 * 
	 * @return texture du polygone
	 */
	public BufferedImage getTexture(){
		return img;
	}
	
	/**
	 * 
	 * @return si la face est eliminée par le backface culling (false, on affiche pas)<br>
	 *  ou si elle ne l'est pas (true, on effectue le calcul du Z-Buffer)
	 */
	public boolean getBackFaceCullingResult(){
		return print;
	}
	
	/**
	 * 
	 * @return la quantité de Red sur le polygon , si une texture est appliquée red=255 green=255 blue=255 et alpha=0
	 */
	public int getRed(){
		return Red;
	}
	
	/**
	 * 
	 * @return la quantité de Green sur le polygon , si une texture est appliquée red=255 green=255 blue=255 et alpha=0
	 */
	public int getGreen(){
		return Green;
	}
	
	/**
	 * 
	 * @return la quantité de Blue sur le polygon , si une texture est appliquée red=255 green=255 blue=255 et alpha=0
	 */
	public int getBlue(){
		return Blue;
	}
	
	/**
	 * 
	 * @return la quantité d'Alpha sur le polygon , si une texture est appliquée red=255 green=255 blue=255 et alpha=0
	 */
	public int getAlpha(){
		return Alpha;
	}
	
	/**
	 * 
	 * @return true si une texture a ete choisi ou false si une couleur a ete choisi
	 */
	public boolean getChoiceTexture(){
		return choiceTexture;
	}
	
	/**
	 * 
	 * @return la position en X du point 1
	 */
	public double getDeployedPolygon1X(){
		return (X1);
	}
	
	/**
	 * 
	 * @return la position en Y du point 1
	 */
	public double getDeployedPolygon1Y(){
		return (Y1);
	}
	
	/**
	 * 
	 * @return la position en X du point 2
	 */
	public double getDeployedPolygon2X(){
		return (X2);
	}
	
	/**
	 * 
	 * @return la position en Y du point 2
	 */
	public double getDeployedPolygon2Y(){
		return (Y2);
	}
	
	/**
	 * 
	 * @return la position en X du point 3
	 */
	public double getDeployedPolygon3X(){
		return (X3);
	}
	
	/**
	 * 
	 * @return la position en Y du point 3
	 */
	public double getDeployedPolygon3Y(){
		return (Y3);
	}
	
	/**
	 * 
	 * @return distance entre le point deplie 1 et le 2eme
	 */
	public double getDist1to2(){
		return(dist1to2);
	}
	
	/**
	 * 
	 * @return distance entre le point deplie 2 et le 3eme
	 */
	public double getDist2to3(){
		return(dist2to3);
	}
	
	/**
	 * 
	 * @return distance entre le point deplie 3 et le 1er
	 */
	public double getDist3to1(){
		return(dist3to1);
	}
	
	/**
	 * 
	 * @return le chemin d'acces à la texture
	 */
	public String getPath(){
		return(path);
	}
	
	/**
	 * 
	 * @param nouvelle valeur en X du point 1
	 */
	public void setDeployedPolygon1X(double change){
		X1=change;
	}
	
	/**
	 * 
	 * @param nouvelle valeur en Y du point 1
	 */
	public void setDeployedPolygon1Y(double change){
		Y1=change;
	}
	
	/**
	 * 
	 * @param nouvelle valeur en X du point 2
	 */
	public void setDeployedPolygon2X(double change){
		X2=change;
	}
	
	/**
	 * 
	 * @param nouvelle valeur en Y du point 2
	 */
	public void setDeployedPolygon2Y(double change){
		Y2=change;
	}
	
	/**
	 * 
	 * @param nouvelle valeur en X du point 3
	 */
	public void setDeployedPolygon3X(double change){
		X3=change;
	}
	
	/**
	 * 
	 * @param nouvelle valeur en Y du point 3
	 */
	public void setDeployedPolygon3Y(double change){
		Y3=change;
	}
	
	/**
	 * 
	 * @param nouvelle valeur de dist1to2
	 */
	public void setDist1to2(double change){
		dist1to2=change;
	}
	
	/**
	 * 
	 * @param nouvelle valeur de dist2to3
	 */
	public void setDist2to3(double change){
		dist2to3=change;
	}
	
	/**
	 * 
	 * @param nouvelle valeur de dist3to1
	 */
	public void setDist3to1(double change){
		dist3to1=change;
	}
	
	/**
	 * 
	 * @param valeur pour changer BackFaceCullingResult
	 */
	public void setBackFaceCullingResult(boolean change){
		print=change;
	}
	
	/**
	 * 
	 * génere le polygon déplié correpondant
	 */
	public void generatePolygon(){
		// calculer la position d'un troisieme point en connaisant les position des trois autres
		// avec P1=(0,0); P2=(dist1to2,0); P3=(deduction,deduction)
		
		// puis interpolation lineaire en partant du meme point de depart que le parcours du z buffer
		
		object.Points P1=md3.Frame.Points[getP1()];// points du polygon
		object.Points P2=md3.Frame.Points[getP2()];
		object.Points P3=md3.Frame.Points[getP3()];
		
		// cotés du polygon
		double dist1to2=Math.sqrt(Math.pow(P2.getX3D()-P1.getX3D(), 2)
								 +Math.pow(P2.getY3D()-P1.getY3D(), 2)
								 +Math.pow(P2.getZ3D()-P1.getZ3D(), 2));
		
		double dist2to3=Math.sqrt(Math.pow(P3.getX3D()-P2.getX3D(), 2)
								 +Math.pow(P3.getY3D()-P2.getY3D(), 2)
								 +Math.pow(P3.getZ3D()-P2.getZ3D(), 2));
		
		double dist3to1=Math.sqrt(Math.pow(P1.getX3D()-P3.getX3D(), 2)
								 +Math.pow(P1.getY3D()-P3.getY3D(), 2)
								 +Math.pow(P1.getZ3D()-P3.getZ3D(), 2));
		
		int hypotenuseIndex=md3.Panel.Amax3(dist1to2,dist2to3,dist3to1);// plus long des trois coté
		// 1 correspond à 1 to 2
		// 2 à 2 to 3
		// 3 à 3 to 1
		
		double x1=0.0;// coordonnées du premier point
		double y1=0.0;
		
		double x2=0.0;// du deuxieme
		double y2=0.0;
		
		double x3=0.0;// du troisieme
		double y3=0.0;
		
		double AB=0.0;// cotés du triangle
		double BC=0.0;
		double CA=0.0;
		
		if(hypotenuseIndex==0){
			AB=dist1to2;
			BC=dist2to3;
			CA=dist3to1;
		}
		
		if(hypotenuseIndex==1){
			AB=dist2to3;
			BC=dist3to1;
			CA=dist1to2;
		}
		
		if(hypotenuseIndex==2){
			AB=dist3to1;
			BC=dist1to2;
			CA=dist2to3;
		}
		
		x1=0.0;
		y1=0.0;
		
		x2=x1+dist1to2;
		y2=0.0;
		
		x3=AB-((Math.pow(BC, 2)-Math.pow(CA, 2)+Math.pow(AB, 2))/2)/AB;
		y3=Math.sqrt(Math.pow(CA, 2)-Math.pow(x3, 2));
		
		x3=-x3;// inversion des coordonnées du troisieme point pour correspondre 
		y3=-y3;// a l'image
		
		setDeployedPolygon1X(x1);
		setDeployedPolygon1Y(y1);
		
		setDeployedPolygon2X(x2);
		setDeployedPolygon2Y(y2);
		
		setDeployedPolygon3X(x3);
		setDeployedPolygon3Y(y3);
		
		setDist1to2(dist1to2);
		setDist2to3(dist2to3);
		setDist3to1(dist3to1);
	}
	
}
