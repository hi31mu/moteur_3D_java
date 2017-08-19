package md3;

import java.awt.Color;
import javax.swing.JFrame;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = 297413206809719230L;
	
	public static int[] MousePosition=new int[2];
	
	public static object.Points[] Points=new object.Points[100000];// points
	public static boolean[] PointsIs=new boolean[100000];
	
	public static int maxPoints=0;
	
	public static object.Edge[] Edge=new object.Edge[100000];// edge
	public static boolean[] EdgeIs=new boolean[100000];
	
	public static int maxEdge=0;
	
	public static object.Polygon[] Polygon=new object.Polygon[100000];// polygon
	public static boolean[] PolygonIs=new boolean[100000];
	
	public static int maxPolygon=0;
	
	public static double focale=0.6;
	public static double focaleE=300;
	
	public static double[] Camera=new double[6];
	
	public static boolean Euclidian=false;
	
	public static Panel panel=new Panel();
		
	public static object.CreationReader reader=new object.CreationReader();
	
	public Frame(){
		this.setSize(1200, 720);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(panel);
		
		setCamera(0,0.0);// initialisation des positions de caméra
		setCamera(1,0.0);
		setCamera(2,0.0);
		setCamera(3,0.0);
		setCamera(4,0.0);
		setCamera(5,0.0);
		
		reader.generate("files/generation.txt");
		
		repaintFrame();
		
	}
	
	
	/**
	 * methode de recalcul des points, normale, et Z-buffer<br>
	 * pour le réaffichage
	 * 
	 * @author guest
	 * 
	 * @version 1.0
	 */
	public static void repaintFrame(){
		Engine.calculBase();
		
		for(int i=0; i<maxPoints; i++){
			if(PointsIs[i]){
				Engine.calculPoints(i);
			}
		}
		
		for(int j=0; j<maxPolygon; j++){
			if(PolygonIs[j]){
				Engine.calculPolygon(j);
			}
		}
				
		panel.repaint();
	}
	
	
	public static void createPoint(double x, double y, double z){
		int i=0;
		while(PointsIs[i]){
			i++;
		}
		
		Points[i]=new object.Points(x, y, z);
		PointsIs[i]=true;
		if(i>=maxPoints)maxPoints=i+1;
	}
	
	public static void createEdge(int numPoint1, int numPoint2){
		int i=0;
		while(EdgeIs[i]){ 
			i++;
		}
		Edge[i]=new object.Edge(numPoint1, numPoint2);
		EdgeIs[i]=true;
		if(i>=maxEdge)maxEdge=i+1;
		
	}
		
	public static void createPolygon(int numPoint1, int numPoint2, int numPoint3, String path){
		int i=0;
		while(PolygonIs[i]){
			i++;
		}
		Polygon[i]=new object.Polygon(numPoint1, numPoint2, numPoint3, path);
		PolygonIs[i]=true;
		if(i>=maxPolygon)maxPolygon=i+1;
		
	}
	
	public static void createPolygon(int numPoint1, int numPoint2, int numPoint3, Color c){
		int i=0;
		while(PolygonIs[i]){
			i++;
		}
		Polygon[i]=new object.Polygon(numPoint1, numPoint2, numPoint3, c);
		PolygonIs[i]=true;
		if(i>=maxPolygon)maxPolygon=i+1;
		
	}
	
	/**
	 * methode de creation de cube
	 * 
	 * 
	 * @param center centre du cube 
	 * @param Size dimension coté
	 * 
	 * @author guest
	 */
	public static void createCube(int center, int Size){
		int i=0;
		while(PointsIs[i]){
			i++;
		}
		Size=Size/2;
		createPoint(center+Size, center+Size, center+Size);
		createPoint(center+Size, center+Size, center-Size);
		createPoint(center+Size, center-Size, center+Size);
		createPoint(center+Size, center-Size, center-Size);
		createPoint(center-Size, center+Size, center+Size);
		createPoint(center-Size, center+Size, center-Size);
		createPoint(center-Size, center-Size, center+Size);
		createPoint(center-Size, center-Size, center-Size);
		
		createEdge(4+i, 0+i);
		createEdge(1+i, 0+i);
		createEdge(1+i, 5+i);
		createEdge(4+i, 5+i);
		createEdge(4+i, 6+i);
		createEdge(2+i, 0+i);
		createEdge(1+i, 3+i);
		createEdge(7+i, 5+i);
		createEdge(2+i, 6+i);
		createEdge(2+i, 3+i);
		createEdge(6+i, 7+i);
		createEdge(3+i, 7+i);
		
		createPolygon(2+i, 4+i, 6+i, "files/textures/Herbe.png");
		createPolygon(4+i, 2+i, 0+i, "files/textures/Herbe.png");
		
		createPolygon(3+i, 5+i, 1+i, "files/textures/Pierre.png");
		createPolygon(5+i, 3+i, 7+i, "files/textures/Pierre.png");
		
		createPolygon(2+i, 7+i, 3+i, "files/textures/Herbe.png");
		createPolygon(7+i, 2+i, 6+i, "files/textures/Herbe.png");
		
		createPolygon(0+i, 3+i, 1+i, "files/textures/Pierre.png");
		createPolygon(3+i, 0+i, 2+i, "files/textures/Pierre.png");
		
		createPolygon(4+i, 5+i, 7+i, "files/textures/Herbe.png");
		createPolygon(6+i, 4+i, 7+i, "files/textures/Herbe.png");
		
		createPolygon(5+i, 0+i, 1+i, "files/textures/Pierre.png");
		createPolygon(0+i, 5+i, 4+i, "files/textures/Pierre.png");
	}
	
	/**
	 * methode de creation d'une map
	 * 
	 * 
	 * @param dimension largeur map
	 * @param mod de generation
	 * 
	 * @author guest
	 */
	public static void createMap(int dimension, int mod){
		int x=dimension;// largeur carte
		int y=dimension;
		
		int firstPoint=0;
		
		while(PointsIs[firstPoint]){
			firstPoint++;
		}
		
		if(mod==1){// en fonction du mod choisis un mod de creation
			for(int j=0; j<y; j++){
				for(int i=0; i<x; i++){
					createPoint(i-20, Engine.get2DPerlinNoiseValue(i,j,5), j-20);
				}
			}
		}
		
		if(mod==2){
			for(int j=0; j<y; j++){
				for(int i=0; i<x; i++){
					createPoint(i-20, Engine.get2DPerlinNoiseValue(i,j,2)+Engine.get2DPerlinNoiseValue(i,j,5), j-20);
				}
			}
		}
		
		// creation des polygon en deux phase 
		
		if(mod==1 || mod==2){// eviter les exception liées au manque de points
			for(int j=0; j<y; j++){// creation polygones phase 1
				for(int i=0; i<x-1; i++){
					if(i+(j*y)+y<=x*y-2){
						createPolygon(firstPoint+i+(j*y)+y, firstPoint+i+1+(j*y), firstPoint+i+(j*y), "files/textures/Herbe.png");
					}
				}
			}
			
			for(int j=0; j<y; j++){// phase 2
				for(int i=0; i<x-1; i++){
					if(i+(j*y)+y<=x*y-2){
						createPolygon(firstPoint+i+1+(j*y), firstPoint+i+(j*y)+y, firstPoint+i+1+(j*y)+y, "files/textures/Pierre.png");
					}
				}
			}
			
			for(int i=firstPoint; i<maxPolygon; i++){// creation des edge
				createEdge(Polygon[i].getP1(),Polygon[i].getP2());
				createEdge(Polygon[i].getP2(),Polygon[i].getP3());
				createEdge(Polygon[i].getP1(),Polygon[i].getP3());
			}
		}
	}
	
	/**
	 * 
	 * 
	 * crée une simulation avec un polygon avec une texture Space2.png
	 * pour tester le placage de texture
	 */
	public static void createTestSimulation(){
		reset();
		
		setCamera(0,20.0);
		setCamera(1,40.0);
		setCamera(2,-80.0);
		setCamera(3,0.0);
		setCamera(4,0.0);
		setCamera(5,0.0);
		
		createPoint(0,0,0);
		createPoint(10,0,0);
		createPoint(0,10,0);
		
		createPoint(3,2,-10);
		createPoint(-3,-2,10);
		createPoint(11,9,2);
		
		createPolygon(2,1,0, "files/textures/Space.png");
		createPolygon(5,3,4, "files/textures/Space2.png");
	}
	
	/**
	 * methode modification de la position de la camera
	 * 
	 * @param cameraN index de la valeur a modifier (0 à 5)
	 * @param replace valeur de remplacement
	 * 
	 * @author guest
	 */
	public static void setCamera(int cameraN, double replace){
		Frame.Camera[cameraN]=replace;
	}
	
	/**
	 * methode de reinitialisation des points
	 * 
	 * @author guest
	 */
	public static void reset(){
		Frame.Points=new object.Points[100000];
		Frame.PointsIs=new boolean[100000];
		Frame.maxPoints=0;
		
		Frame.Edge=new object.Edge[100000];
		Frame.EdgeIs=new boolean[100000];
		Frame.maxEdge=0;
		
		Frame.Polygon=new object.Polygon[100000];
		Frame.PolygonIs=new boolean[100000];
		Frame.maxPolygon=0;
	}
}
