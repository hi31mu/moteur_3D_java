package md3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Panel extends JPanel{
	
	private static final long serialVersionUID = 297413206809719230L;
	
	public static BufferedImage image;
	public static double[][] Z;

	public void paintComponent(Graphics g){
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, getSize().width, getSize().height);
		
		Frame.MousePosition[0]=MouseInfo.getPointerInfo().getLocation().x-Frame.panel.getLocationOnScreen().x;
		Frame.MousePosition[1]=MouseInfo.getPointerInfo().getLocation().y-Frame.panel.getLocationOnScreen().y;
		
		double Size=(getSize().width+getSize().height/2);
		
		image=new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB_PRE);
		
		Z=new double[getSize().width][getSize().height];
		
		for(int i=0; i<getSize().width; i++){// initialisation Z buffer
			for(int j=0; j<getSize().height; j++){
				Z[i][j]=-1;
			}
		}
		
		for(int i=0; i<Frame.maxPolygon; i++){
			if(Frame.PolygonIs[i] && Frame.Polygon[i].getBackFaceCullingResult()){
				Z=makeZBuffer(i, Z, Size, getSize().width, getSize().height);
			}
		}
			
		g.drawImage(image, 0, 0, null);
		
		g.setColor(new Color(0,0,0));
		
		// affichage des point / n° point/ arretes
		/*for(int i=0; i<Frame.maxPoints; i++){
			if(Frame.PointsIs[i]){
				g.fillRect((int)(Frame.Points[i].getX2D()*Size-2+getSize().width/2), (int)(Frame.Points[i].getY2D()*Size-2+getSize().height/2), 4, 4);
				g.drawString(Integer.toString(i), (int)(Frame.Points[i].getX2D()*Size-2+getSize().width/2)+7, (int)(Frame.Points[i].getY2D()*Size-2+getSize().height/2));
			}
		}*/
		
		/*for(int i=0; i<Frame.maxEdge; i++){
			if(Frame.EdgeIs[i]){
				g.drawLine((int)(Frame.Points[Frame.Edge[i].getP1()].getX2D()*Size-2+getSize().width/2), (int)(Frame.Points[Frame.Edge[i].getP1()].getY2D()*Size-2+getSize().height/2),
						   (int)(Frame.Points[Frame.Edge[i].getP2()].getX2D()*Size-2+getSize().width/2), (int)(Frame.Points[Frame.Edge[i].getP2()].getY2D()*Size-2+getSize().height/2));
			}
		}*/
	}
	
	
	/**
	 * methode avec une tetxure pour remplir le Zbuffer
	 * 
	 * @deprecated makeZBuffer
	 * 
	 * @param Z buffer
	 * @param buffer image à remplir
	 * @param x1
	 * @param y1
	 * @param s1
	 * @param x2
	 * @param y2
	 * @param s2
	 * @param x3
	 * @param y3
	 * @param s3
	 * @param texture
	 * 
	 * @return Z buffer
	 */
	public static double[][] drawPolygon(double[][] Z, BufferedImage buffer, int x1, int y1, double s1, int x2, int y2, double s2, int x3, int y3, double s3, BufferedImage texture){
		double[][] Points=new double[3][3];
		
		int positionTextureX=0;
		int positionTextureY=0;
				
		Points[0][0]=x1;
		Points[0][1]=y1;
		Points[0][2]=s1;
		
		Points[1][0]=x2;
		Points[1][1]=y2;
		Points[1][2]=s2;
		
		Points[2][0]=x3;
		Points[2][1]=y3;
		Points[2][2]=s3;
		
		int A=Amin3(y1,y2,y3);
		int B;
		int C=Amax3(y1,y2,y3);
		
		if(A+C==1){
			B=2;
		}else{
			if(A+C==2){
				B=1;
			}else{
				B=0;
			}
		}
		
		double AB[] = {Points[B][0]-Points[A][0], Points[B][1]-Points[A][1]};
		double AC[] = {Points[C][0]-Points[A][0], Points[C][1]-Points[A][1]};
		double BC[] = {Points[C][0]-Points[B][0], Points[C][1]-Points[B][1]};
		
		double X1=0;
		double X2=0;
		double X1d=0;
		double X2d=0;
		double Xd=0;
		double dis=0;
		
		for(int j=(int)Points[A][1]; j<=(int)Points[B][1]; j++){
			if(j>0 && j<buffer.getHeight()){
				X1=((j-(int)(Points[A][1]))*AB[0])/AB[1];
				X1d=((j-Points[A][1])/(Points[B][1]-Points[A][1]))*(Points[B][2]-Points[A][2])+Points[A][2];
				
				X2=((j-(int)(Points[A][1]))*AC[0])/AC[1];
				X2d=((j-Points[A][1])/(Points[C][1]-Points[A][1]))*(Points[C][2]-Points[A][2])+Points[A][2];
				
				Xd=Math.abs(X1-X2);
				
				if(X1<X2){
					for(int k=(int)(X1+Points[A][0]); k<(int)(X2+Points[A][0]); k++){
						if(k>0 && k<buffer.getWidth()){
							dis=(((k-(X1+Points[A][0]))/Xd)*(X2d-X1d))+X1d;
							if(Z[k][j]==-1 || Z[k][j]>dis){
								Z[k][j]=dis;
								positionTextureX=(positionTextureX+1)%104;
								buffer.setRGB(k, j, texture.getRGB(positionTextureX,positionTextureY));// ici
							}
						}
					}
					
				}else{
					for(int k=(int)(X2+Points[A][0]); k<(int)(X1+Points[A][0]); k++){
						if(k>0 && k<buffer.getWidth()){
							dis=(((k-(X2+Points[A][0]))/Xd)*(X1d-X2d))+X2d;
							if(Z[k][j]==-1 || Z[k][j]>dis){
								Z[k][j]=dis;
								positionTextureX=(positionTextureX+1)%104;
								buffer.setRGB(k, j, texture.getRGB(positionTextureX,positionTextureY));// ici
							}
						}
					}
					
				}
				
				positionTextureX=0;
				positionTextureY=(positionTextureY+1)%104;
				
			}
			
		}
		
		for(int j=(int)Points[C][1]; j>=(int)Points[B][1]; j--){
			if(j>0 && j<buffer.getHeight()){
				X1=((j-(int)(Points[C][1]))*(-BC[0]))/(-BC[1]);
				X1d=((j-Points[C][1])/(Points[B][1]-Points[C][1]))*(Points[B][2]-Points[C][2])+Points[C][2];
				
				X2=((j-(int)(Points[C][1]))*(-AC[0]))/(-AC[1]);
				X2d=((j-Points[C][1])/(Points[A][1]-Points[C][1]))*(Points[A][2]-Points[C][2])+Points[C][2];
				
				Xd=Math.abs(X1-X2);
				
				if(X1<X2){
					for(int k=(int)(X1+Points[C][0]); k<(int)(X2+Points[C][0]); k++){
						if(k>0 && k<buffer.getWidth()){
							dis=(((k-(X1+Points[C][0]))/Xd)*(X2d-X1d))+X1d;
							if(Z[k][j]==-1 || Z[k][j]>dis){
								Z[k][j]=dis;
								positionTextureX=(positionTextureX+1)%104;
								buffer.setRGB(k, j, texture.getRGB(positionTextureX,positionTextureY));// ici
							}
						}
					}
					
				}else{
					for(int k=(int)(X2+Points[C][0]); k<(int)(X1+Points[C][0]); k++){
						if(k>0 && k<buffer.getWidth()){
							dis=(((k-(X2+Points[C][0]))/Xd)*(X1d-X2d))+X2d;
							if(Z[k][j]==-1 || Z[k][j]>dis){
								Z[k][j]=dis;
								positionTextureX=(positionTextureX+1)%104;
								buffer.setRGB(k, j, texture.getRGB(positionTextureX,positionTextureY));// ici
							}
						}
					}
					
				}
				
				positionTextureX=0;
				positionTextureY=(positionTextureY+1)%104;
				
			}
			
		}
		
				
		return Z;
	}
	
	
	
	/**
	 * methode avec une couleur pour remplir le Z buffer
	 * 
	 * @deprecated makeZBuffer
	 * 
	 * @param Z
	 * @param buffer image a remplir
	 * @param x1
	 * @param y1
	 * @param s1
	 * @param x2
	 * @param y2
	 * @param s2
	 * @param x3
	 * @param y3
	 * @param s3
	 * @param c couleur
	 * @return Z buffer
	 */
	public static double[][] drawPolygon(double[][] Z, BufferedImage buffer, int x1, int y1, double s1, int x2, int y2, double s2, int x3, int y3, double s3, Color c){
		double[][] Points=new double[3][3];
		
		Points[0][0]=x1;
		Points[0][1]=y1;
		Points[0][2]=s1;
		
		Points[1][0]=x2;
		Points[1][1]=y2;
		Points[1][2]=s2;
		
		Points[2][0]=x3;
		Points[2][1]=y3;
		Points[2][2]=s3;
		
		int A=Amin3(y1,y2,y3);
		int B;
		int C=Amax3(y1,y2,y3);
		
		if(A+C==1){
			B=2;
		}else{
			if(A+C==2){
				B=1;
			}else{
				B=0;
			}
		}
		
		double AB[] = {Points[B][0]-Points[A][0], Points[B][1]-Points[A][1]};
		double AC[] = {Points[C][0]-Points[A][0], Points[C][1]-Points[A][1]};
		double BC[] = {Points[C][0]-Points[B][0], Points[C][1]-Points[B][1]};
		
		double X1=0;
		double X2=0;
		double X1d=0;
		double X2d=0;
		double Xd=0;
		double dis=0;
		
		for(int j=(int)Points[A][1]; j<=(int)Points[B][1]; j++){
			if(j>0 && j<buffer.getHeight()){
				X1=((j-(int)(Points[A][1]))*AB[0])/AB[1];
				X1d=((j-Points[A][1])/(Points[B][1]-Points[A][1]))*(Points[B][2]-Points[A][2])+Points[A][2];
				
				X2=((j-(int)(Points[A][1]))*AC[0])/AC[1];
				X2d=((j-Points[A][1])/(Points[C][1]-Points[A][1]))*(Points[C][2]-Points[A][2])+Points[A][2];
				
				Xd=Math.abs(X1-X2);
				
				if(X1<X2){
					for(int k=(int)(X1+Points[A][0]); k<(int)(X2+Points[A][0]); k++){
						if(k>0 && k<buffer.getWidth()){
							dis=(((k-(X1+Points[A][0]))/Xd)*(X2d-X1d))+X1d;
							if(Z[k][j]==-1 || Z[k][j]>dis){
								Z[k][j]=dis;
								buffer.setRGB(k, j, c.getRGB());// ici
							}
						}
					}
					
				}else{
					for(int k=(int)(X2+Points[A][0]); k<(int)(X1+Points[A][0]); k++){
						if(k>0 && k<buffer.getWidth()){
							dis=(((k-(X2+Points[A][0]))/Xd)*(X1d-X2d))+X2d;
							if(Z[k][j]==-1 || Z[k][j]>dis){
								Z[k][j]=dis;
								buffer.setRGB(k, j, c.getRGB());// ici
							}
						}
					}
					
				}
				
			}
			
		}
		
		for(int j=(int)Points[C][1]; j>=(int)Points[B][1]; j--){
			if(j>0 && j<buffer.getHeight()){
				X1=((j-(int)(Points[C][1]))*(-BC[0]))/(-BC[1]);
				X1d=((j-Points[C][1])/(Points[B][1]-Points[C][1]))*(Points[B][2]-Points[C][2])+Points[C][2];
				
				X2=((j-(int)(Points[C][1]))*(-AC[0]))/(-AC[1]);
				X2d=((j-Points[C][1])/(Points[A][1]-Points[C][1]))*(Points[A][2]-Points[C][2])+Points[C][2];
				
				Xd=Math.abs(X1-X2);
				
				if(X1<X2){
					for(int k=(int)(X1+Points[C][0]); k<(int)(X2+Points[C][0]); k++){
						if(k>0 && k<buffer.getWidth()){
							dis=(((k-(X1+Points[C][0]))/Xd)*(X2d-X1d))+X1d;
							if(Z[k][j]==-1 || Z[k][j]>dis){
								Z[k][j]=dis;
								buffer.setRGB(k, j, c.getRGB());// ici
							}
						}
					}
					
				}else{
					for(int k=(int)(X2+Points[C][0]); k<(int)(X1+Points[C][0]); k++){
						if(k>0 && k<buffer.getWidth()){
							dis=(((k-(X2+Points[C][0]))/Xd)*(X1d-X2d))+X2d;
							if(Z[k][j]==-1 || Z[k][j]>dis){
								Z[k][j]=dis;
								buffer.setRGB(k, j, c.getRGB());// ici
							}
						}
					}
					
				}
				
			}
			
		}
		
		
		return Z;
	}
	
	
	/**
	 * methode de Z-Buffering
	 * 
	 * @param index index du polygon à traiter
	 * @param Z buffer à passer en parametre
	 * @param Size de la fenetre
	 * @param width largeur de la fenetre
	 * @param height hauteur de la fenetre
	 * 
	 * @return Z buffer
	 * 
	 * @version 1.5
	 * 
	 * @author Hippolyte
	 * 
	 */
	public static double[][] makeZBuffer(int index, double[][] Z, double Size, double width, double height){
		double x1=Frame.Points[(int)Frame.Polygon[index].getP1()].getX2D()*Size-2;// correspond au point A
		double y1=Frame.Points[(int)Frame.Polygon[index].getP1()].getY2D()*Size-2;
		double dis1=Frame.Points[(int)Frame.Polygon[index].getP1()].getDis();
		
		double x2=Frame.Points[(int)Frame.Polygon[index].getP2()].getX2D()*Size-2;// point B
		double y2=Frame.Points[(int)Frame.Polygon[index].getP2()].getY2D()*Size-2;
		double dis2=Frame.Points[(int)Frame.Polygon[index].getP2()].getDis();
		
		double x3=Frame.Points[(int)Frame.Polygon[index].getP3()].getX2D()*Size-2;// point C
		double y3=Frame.Points[(int)Frame.Polygon[index].getP3()].getY2D()*Size-2;
		double dis3=Frame.Points[(int)Frame.Polygon[index].getP3()].getDis();
		
		double x3s=x3;// point C save
		double y3s=y3;
		double dis3s=dis3;
		
		double xPoint=0.0;// point analysé
		double yPoint=0.0;
		double disPoint=0.0;
		
		double coteDeDepart=Math.sqrt(Math.pow(x1-x3, 2)+Math.pow(y1-y3, 2));
		double t1Add=1/coteDeDepart;// valeur à additionner à t1
		
		double secondCote=Math.sqrt(Math.pow(x2-x3, 2)+Math.pow(y2-y3, 2));
		double t2Add=1/secondCote/2;// valeur à additionner à t2
	    
		//formule X de P = P(t) = X de a+t*(x de b-x de a)
		for(double t1=0.0; t1<=1.0; t1+=t1Add){
			x3=x3s+t1*(x1-x3s);
			y3=y3s+t1*(y1-y3s);
			dis3=dis3s+t1*(dis1-dis3s);
			
			// fonctionne pour une ligne
			for(double t2=0.0; t2<=1.0; t2+=t2Add){// pour une ligne
				xPoint=x2+t2*(x3-x2);
				yPoint=y2+t2*(y3-y2);
				disPoint=dis2+t2*(dis3-dis2);
								
				if(yPoint>-(height/2) && xPoint>-(width/2) && yPoint<height/2 && xPoint<width/2){
					if(disPoint<Z[(int)(xPoint+width/2)][(int)(yPoint+height/2)] || Z[(int)(xPoint+width/2)][(int)(yPoint+height/2)]==-1){
						Z[(int)(xPoint+width/2)][(int)(yPoint+height/2)]=disPoint;
						image.setRGB((int)(xPoint+width/2), (int)(yPoint+height/2), getColorTexture(index, Frame.Polygon[index].getTexture(), t1, t2).getRGB());
					}
				}
			}
		}
		
		return Z;
	}
	
	
	public static double max3(double a, double b, double c){
		if(a>b){
			if(a>c){
				return(a);
			}else{
				return(c);
			}
		}else{
			if(b>c){
				return(b);
			}else{
				return(c);
			}
		}
		
	}
	
	
	public static double min3(double a, double b, double c){
		if(a<b){
			if(a<c){
				return(a);
			}else{
				return(c);
			}
		}else{
			if(b<c){
				return(b);
			}else{
				return(c);
			}
		}
		
	}
	
	
	public static double max2(double a, double b){
		if(a>b){
			return a;	
		}else{
			return b;
		}
	}
	
	
	public static double min2(double a, double b){
		if(a<b){
			return a;	
		}else{
			return b;
		}
	}
	
	
	public static int Amax3(double a, double b, double c){
		if(a>b){
			if(a>c){
				return(0);
			}else{
				return(2);
			}
		}else{
			if(b>c){
				return(1);
			}else{
				return(2);
			}
		}
		
	}
	
	
	public static int Amin3(double a, double b, double c){
		if(a<b){
			if(a<c){
				return(0);
			}else{
				return(2);
			}
		}else{
			if(b<c){
				return(1);
			}else{
				return(2);
			}
		}
		
	}
	
	
	public static int Amax2(double a, double b){
		if(a>b){
			return 0;	
		}else{
			return 1;
		}
	}
	
	
	public static int Amin2(double a, double b){
		if(a<b){
			return 0;	
		}else{
			return 1;
		}
	}
	
	/**
	 * 
	 * permet d'obtenir la couleur du pixel
	 * 
	 * @param index
	 * @param texture
	 * @param t1 valeur t pour l'interpolation linéaire
	 * @param t2 valeur t pour la seconde interpolation linéaire
	 * 
	 * @return la couleur du pixel a x y z de la texture en fonction du polygon à l'index i
	 * 
	 * @version 1.3
	 */
	public static Color getColorTexture(int index, BufferedImage texture, double t1, double t2){
		double x1=Frame.Polygon[index].getDeployedPolygon1X();
		double y1=Frame.Polygon[index].getDeployedPolygon1Y();
		
		double x2=Frame.Polygon[index].getDeployedPolygon2X();
		double y2=Frame.Polygon[index].getDeployedPolygon2Y();
		
		double x3=Frame.Polygon[index].getDeployedPolygon3X();
		double y3=Frame.Polygon[index].getDeployedPolygon3Y();
		
		double coordinateX=0.0;
		double coordinateY=0.0;
		
		double newX3=0.0;
		double newY3=0.0;
		
		newX3=-x3+t1*(x1+x3);
		newY3=-y3+t1*(y1+y3);
		
		coordinateX=x2+t2*(newX3-x2);
		coordinateY=y2+t2*(newY3-y2);
		
		coordinateX/=max3(Frame.Polygon[index].getDist1to2(),
				          Frame.Polygon[index].getDist2to3(),
				          Frame.Polygon[index].getDist3to1());
		coordinateY/=max3(Frame.Polygon[index].getDist1to2(),
		          		  Frame.Polygon[index].getDist2to3(),
		          		  Frame.Polygon[index].getDist3to1());
		
		coordinateX*=texture.getWidth();
		coordinateY*=texture.getHeight();
		
		if(!Frame.Polygon[index].getChoiceTexture()){// si couleur de face et non texture
			return new Color(Frame.Polygon[index].getRed(), Frame.Polygon[index].getGreen(), Frame.Polygon[index].getBlue());
		}else{// si texture
			try{
				return new Color(texture.getRGB((int)coordinateX,(int)coordinateY));
			}catch(java.lang.ArrayIndexOutOfBoundsException az){// si hors de texture donner la couleur du pixel en coordonnées max
				return new Color(texture.getRGB(texture.getWidth()-1, texture.getHeight()-1));
			}
		}
	}
	
}