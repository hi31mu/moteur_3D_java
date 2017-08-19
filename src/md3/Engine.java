package md3;

public class Engine{
	
	public static double a1=0;
	public static double a2=0;
	
	/**
	 * methode de calcul de la position des points phase 1;
	 * n'a besoin d'etre executée qu'une fois pour une position de la camera
	 * 
	 * @version 1.0
	 */
	public static void calculBase(){
		double c1x=Frame.Camera[3]-Frame.Camera[0];
		double c1y=Frame.Camera[4]-Frame.Camera[1];
		double c1z=Frame.Camera[5]-Frame.Camera[2];
		
		a1=calculAngle(c1x, c1z);
		
		double []b=cartRotate(c1x, c1z, a1);
		
		//double c2x=b[0];
		double c2y=c1y;
		double c2z=b[1];
		
		a2=calculAngle(c2z, c2y);
	}
	
	/**
	 * methode de calcul de la position des points phase 2;
	 * doit etre executée pour chaques points
	 * 
	 * @version 1.0
	 */
	public static void calculPoints(int i){
		double[] b=new double[2];
		
		double p1x=Frame.Points[i].getX3D()-Frame.Camera[0];
		double p1y=Frame.Points[i].getY3D()-Frame.Camera[1];
		double p1z=Frame.Points[i].getZ3D()-Frame.Camera[2];
		
		b=cartRotate(p1x, p1z, a1);
		
		double p2x=b[0];
		double p2y=p1y;
		double p2z=b[1];
				
		b=cartRotate(p2z,p2y,a2);
		
		double p3x=p2x;
		double p3y=b[1];
		double p3z=b[0];
		
		Frame.Points[i].setDis(calculLength(p1x,p1y,p1z));
		
		if(Frame.Euclidian){
			Frame.Points[i].setX2D(p3x/Frame.focaleE);
			Frame.Points[i].setY2D(p3z/Frame.focaleE);
		}else{
			Frame.Points[i].setX2D(calculAngleReal(p3x,p3y)/Frame.focale);
			Frame.Points[i].setY2D(calculAngleReal(p3z,p3y)/Frame.focale);
		}
		
	}
	
	/**
	 * methode de calcul d'angle, calcul l'angle dans un triangle en connaisant les 
	 * longueur des deux cotées différent de l'hypothenuse, correspondant aux coordonées en x et en y
	 * 
	 * @version 1.0
	 */
	public static double calculAngle(double x, double y){
		double a=0.0;
		if(x>0 && y>0)        a=Math.atan(Math.abs(x)/Math.abs(y));
		else if(x>0 && y<0)   a=Math.atan(Math.abs(y)/Math.abs(x))+Math.PI/2;
		else if(x<0 && y<0)   a=Math.atan(Math.abs(x)/Math.abs(y))+Math.PI;
		else if(x<0 && y>0)   a=Math.atan(Math.abs(y)/Math.abs(x))+3*Math.PI/2;
		else if (x==0 && y>0) a=0;
		else if (x==0 && y<0) a=Math.PI;
		else if (x>0 && y==0) a=Math.PI/2;
		else if (x<0 && y==0) a=3*Math.PI*2;
		else a=0;
		
		
		return a;
	}
	
	public static double calculAngleReal(double x, double y){
		double a=0.0;
		if(x>0 && y>0)        a=Math.atan(Math.abs(x)/Math.abs(y));
		else if(x>0 && y<0)   a=Math.atan(Math.abs(y)/Math.abs(x))+Math.PI/2;
		else if(x<0 && y<0)   a=-(Math.atan(Math.abs(y)/Math.abs(x))+Math.PI/2);
		else if(x<0 && y>0)   a=-(Math.atan(Math.abs(x)/Math.abs(y)));
		else if (x==0 && y>0) a=0;
		else if (x==0 && y<0) a=Math.PI;
		else if (x>0 && y==0) a=Math.PI/2;
		else if (x<0 && y==0) a=-(Math.PI*2);
		else a=0;
		
		
		return a;
	}
	
	
	/**
	 * calcul la longueur de l'hypotenuse en 2d
	 * 
	 * @version 1.0
	 * 
	 * @param x longueur du premier coté
	 * @param y longueur du second coté
	 * 
	 * @return longueur de l'hypothenuse
	 */
	public static double calculLength(double x, double y){
		return Math.sqrt(Math.pow(Math.abs(x), 2)+Math.pow(Math.abs(y), 2));
	}
	
	/**
	 * calcul la longueur de l'hypotenuse en 3d
	 * 
	 * @version 1.0
	 * 
	 * @param x longueur du premier coté
	 * @param y longueur du second coté
	 * @param z ecartement en z
	 * 
	 * @return longueur de l'hypothenuse
	 */
	public static double calculLength(double x, double y, double z){
		return Math.sqrt(Math.pow(Math.abs(x), 2)+Math.pow(Math.abs(calculLength(y,z)), 2));
	}
	
	/**
	 * effectue une rotation cartesienne
	 * 
	 * @version 1.0
	 * 
	 * @param x longueur du premier coté
	 * @param y longueur du second coté
	 * @param a angle 
	 * 
	 * @return r 
	 */
	public static double[] cartRotate(double x, double y, double a){
		double a2=calculAngle(x,y);
		double l=calculLength(x, y);
		a2-=a;
		double []r=new double[2];
		r[0]=Math.sin(a2)*l;
		r[1]=Math.cos(a2)*l;
		
		return(r);
	
	}
	
	/**
	 * tire au hasard un nombre entre min et max
	 * 
	 * @version 1.0
	 * 
	 * @param min minimum
	 * @param max maximum
	 * 
	 * @return nombre au hasard
	 */
	public static int random(int min, int max){
		return (int) (Math.random()*(max-min+1)+min);
	}
	
	/**
	 * calcul la normale des polygon grace à un produit vectorielle puis l'angle entre la camera 
	 * la base de la normale l' extremité de celle ci; si cet angle est supérieur à 90 alors la face 
	 * à l'index i ne sera pas affichée
	 * 
	 * @version 1.0
	 * 
	 * @param i index du point
	 * 
	 */
	public static void calculPolygon(int i){
		double[] U=new double[3];
		U[0]=Frame.Points[(int)Frame.Polygon[i].getP2()].getX3D()-Frame.Points[(int)Frame.Polygon[i].getP1()].getX3D();
		U[1]=Frame.Points[(int)Frame.Polygon[i].getP2()].getY3D()-Frame.Points[(int)Frame.Polygon[i].getP1()].getY3D();
		U[2]=Frame.Points[(int)Frame.Polygon[i].getP2()].getZ3D()-Frame.Points[(int)Frame.Polygon[i].getP1()].getZ3D();
				
		double[] V=new double[3];
		V[0]=Frame.Points[(int)Frame.Polygon[i].getP3()].getX3D()-Frame.Points[(int)Frame.Polygon[i].getP1()].getX3D();
		V[1]=Frame.Points[(int)Frame.Polygon[i].getP3()].getY3D()-Frame.Points[(int)Frame.Polygon[i].getP1()].getY3D();
		V[2]=Frame.Points[(int)Frame.Polygon[i].getP3()].getZ3D()-Frame.Points[(int)Frame.Polygon[i].getP1()].getZ3D();
		
		double[] N=new double[3];// produit vectoriel pour calculer la normale du polygon
		N[0]=U[1]*V[2]-U[2]*V[1];
		N[1]=U[2]*V[0]-U[0]*V[2];
		N[2]=U[0]*V[1]-U[1]*V[0];
		
		double[] M=new double[3];
		M[0]=Frame.Points[(int)Frame.Polygon[i].getP1()].getX3D()-Frame.Camera[0];
		M[1]=Frame.Points[(int)Frame.Polygon[i].getP1()].getY3D()-Frame.Camera[1];
		M[2]=Frame.Points[(int)Frame.Polygon[i].getP1()].getZ3D()-Frame.Camera[2];
		
		double[] P=new double[3];
		P[0]=Frame.Points[(int)Frame.Polygon[i].getP1()].getX3D()+N[0];
		P[1]=Frame.Points[(int)Frame.Polygon[i].getP1()].getY3D()+N[1];
		P[2]=Frame.Points[(int)Frame.Polygon[i].getP1()].getZ3D()+N[2];
		
		double[] O=new double[3];
		O[0]=P[0]-Frame.Camera[0];
		O[1]=P[1]-Frame.Camera[1];
		O[2]=P[2]-Frame.Camera[2];
		
		double l1=calculLength(M[0], M[1], M[2]);
		double l2=calculLength(N[0], N[1], N[2]);
		double l3=calculLength(O[0], O[1], O[2]);
		
		double a1=Math.acos((Math.pow(l1, 2)+Math.pow(l2, 2)-Math.pow(l3, 2))/(2*l1*l2));
		
		if(a1<Math.PI/2)
			Frame.Polygon[i].setBackFaceCullingResult(true);
		else
			Frame.Polygon[i].setBackFaceCullingResult(false);
		
	}
	
	/**
	 * Methode de generation de monde
	 * 
	 * @version 1.3
	 * 
	 * @deprecated get2DPerlinNoiseValue
	 * 
	 * @author guest
	 * 
	 * @param x la largeur
	 * @param y la hauteur
	 * 
	 * @return un tableau d'entier de la largeur donnée et ou les entiers symbolise la hauteur
	 */
	public static int[][] generate(int x, int y){
		int niveau0=20;// niveau de l'eau
		
		int start=random(niveau0, niveau0+10);// hauteur de depart
		
		int typeRelief=random(0,2);// type de relief: 0 monte/ 1 descente/ 2 stable
		int isChange=random(0,10);//  variable qui decide du changement de type relief ou non
		
		int[][] tab=new int[x][y];// tableau des reliefs
		
		int[] trajectory=new int[x];// tableau des trajectoires
		
		int actualHigh=start+1;
		
		tab[0][0]=start;
		
		for(int toX=0; toX<x; toX++){// premiere courbe sur axe abscisse
			if(isChange==9){
				typeRelief=random(0,2);
			}
			
			if(typeRelief==0){// pour monte 
				actualHigh++;
				tab[0][toX]=actualHigh;
				trajectory[toX]=0;
			}
			
			if(typeRelief==1){// pour descente
				actualHigh--;
				tab[0][toX]=actualHigh;
				trajectory[toX]=1;
			}
			
			if(typeRelief==2){// pour plat
				tab[0][toX]=actualHigh;
				trajectory[toX]=2;
			}
			
			isChange=random(0,10);
		}
				
		tab[0][0]=start;
		
		actualHigh=start;
		
		for(int toY=0; toY<y; toY++){// deuxieme courbe axe ordonnée
			if(isChange==9){
				typeRelief=random(0,2);
			}
			
			if(typeRelief==0){// pour monte 
				actualHigh++;
				tab[toY][0]=actualHigh;
			}
			
			if(typeRelief==1){// pour descente
				actualHigh--;
				tab[toY][0]=actualHigh;
			}
			
			if(typeRelief==2){// pour plat
				tab[toY][0]=actualHigh;
			}
			
			isChange=random(0,10);
		}
		
		
		for(int i=1; i<y; i++){// complete le tableau
			actualHigh=tab[i][0];
			for(int j=1; j<x; j++){
				typeRelief=random(0,2);
				
				if(typeRelief==0){
					if(actualHigh<tab[i-1][j]+1){
						actualHigh++;
						tab[i][j]=actualHigh;
					}else{
						actualHigh--;
						tab[i][j]=actualHigh;
					}
				}
				
				
				if(typeRelief==1){
					if(actualHigh>tab[i-1][j]-1){
						actualHigh--;
						tab[i][j]=actualHigh;
					}else{
						actualHigh++;
						tab[i][j]=actualHigh;
					}
				}
				
				if(typeRelief==2){
					tab[i][j]=actualHigh;
				}
				
				
			}
		}
		
		
		return (tab);
	}
	
	
	/**
	 * methode de perlin noise
	 * 
	 * @param x largeur en x
	 * @param y largeur en y
	 * @param res resolution
	 * 
	 * @return z du point
	 */
	public static float get2DPerlinNoiseValue(float x, float y, float res){
	    float tempX,tempY;
	    int x0,y0,ii,jj,gi0,gi1,gi2,gi3;
	    float unit = (float) (1.0f/Math.sqrt(2));
	    float tmp,s,t,u,v,Cx,Cy,Li1,Li2;
	    float gradient2[][] = {{unit,unit},{-unit,unit},{unit,-unit},{-unit,-unit},{1,0},{-1,0},{0,1},{0,-1}};
	    
	    int perm[] ={151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,
	    			 142,8,99,37,240,21,10,23,190,6,148,247,120,234,75,0,26,197,62,94,252,219,
	    			 203,117,35,11,32,57,177,33,88,237,149,56,87,174,20,125,136,171,168,68,175,
	    			 74,165,71,134,139,48,27,166,77,146,158,231,83,111,229,122,60,211,133,230,220,
	    			 105,92,41,55,46,245,40,244,102,143,54,65,25,63,161,1,216,80,73,209,76,132,
	    			 187,208,89,18,169,200,196,135,130,116,188,159,86,164,100,109,198,173,186,3,
	    			 64,52,217,226,250,124,123,5,202,38,147,118,126,255,82,85,212,207,206,59,227,
	    			 47,16,58,17,182,189,28,42,223,183,170,213,119,248,152,2,44,154,163,70,221,
	    			 153,101,155,167,43,172,9,129,22,39,253,19,98,108,110,79,113,224,232,178,185,
	    			 112,104,218,246,97,228,251,34,242,193,238,210,144,12,191,179,162,241,81,51,145,
	    			 235,249,14,239,107,49,192,214,31,181,199,106,157,184,84,204,176,115,121,50,45,
	    			 127,4,150,254,138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,
	    			 156,180,12,134,34,254,23,125,277,21,245,43,82,36,213,84,99,53,182,34,90,76,0,
	    			 250,194,29,54,146,43,1,30,218,127,59,234,22,164};
	    
	    //Adapter pour la résolution
	    x /= res;
	    y /= res;
	    
	    //On récupère les positions de la grille associée à (x,y)
	    x0 = (int)(x);
	    y0 = (int)(y);
	    
	    //Masquage
	    ii = x0 & 255;
	    jj = y0 & 255;
	    
	    //Pour récupérer les vecteurs
	    gi0 = perm[ii + perm[jj]] % 8;
	    gi1 = perm[ii + 1 + perm[jj]] % 8;
	    gi2 = perm[ii + perm[jj + 1]] % 8;
	    gi3 = perm[ii + 1 + perm[jj + 1]] % 8;
	    
	    //on récupère les vecteurs et on pondère
	    tempX = x-x0;
	    tempY = y-y0;
	    s = gradient2[gi0][0]*tempX + gradient2[gi0][1]*tempY;
	    
	    tempX = x-(x0+1);
	    tempY = y-y0;
	    t = gradient2[gi1][0]*tempX + gradient2[gi1][1]*tempY;
	    
	    tempX = x-x0;
	    tempY = y-(y0+1);
	    u = gradient2[gi2][0]*tempX + gradient2[gi2][1]*tempY;
	    
	    tempX = x-(x0+1);
	    tempY = y-(y0+1);
	    v = gradient2[gi3][0]*tempX + gradient2[gi3][1]*tempY;
	    
	    
	    //Lissage
	    tmp = x - x0;
	    Cx = 3 * tmp * tmp - 2 * tmp * tmp * tmp;
	    
	    Li1 = s + Cx*(t-s);
	    Li2 = u + Cx*(v-u);
	    
	    tmp = y - y0;
	    Cy = 3 * tmp * tmp - 2 * tmp * tmp * tmp;
	    
	    return Li1 + Cy*(Li2-Li1);
	}


	
	
}
