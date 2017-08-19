package object;

/**
 * objet Edge contient:<br>
 * 	-un numero de point (1er point du segment)<br>
 *  -un numero de point (2nd point du segment)<br>
 * 
 * @version 1.0
 * 
 * @author Hippolyte
 *
 */
public class Edge{
	
	private int P1;
	private int P2;
	
	/**
	 * methode de creation d'Edge reliant les deux points donnés en parametre
	 * 
	 * @param point1
	 * @param point2
	 * 
	 * 
	 * @author Hippolyte
	 */
	public Edge(int point1, int point2){
		P1=point1;
		P2=point2;
	}
	
	/**
	 * @return numero du premier point
	 */
	public int getP1(){
		return P1;
	}
	
	/**
	 * @return numero du second point
	 */
	public int getP2(){
		return P2;
	}
	
}
