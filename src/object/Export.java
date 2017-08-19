package object;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface Export{
	
	/**
	 * 
	 * liste les commandes a appliquer dans une chaine de caractères
	 * 
	 * @param polygon[]
	 * @param edge[]
	 * @param point[]
	 * @param camera[]
	 * 
	 * @return la chaine de caracteres correspondant a la creation de tout les points, edges,
	 * polygons et a la position de la camera 
	 */
	public static String toString(Points[] point, Edge[] edge, Polygon[] polygon, double[] camera){
		String output="";
		try{
			for(Points p:point){
				String command="createPoint";
				command+="("+p.getX3D()+","+p.getY3D()+","+p.getZ3D()+");";
				output+=command+"\n";
			}
		}catch(java.lang.NullPointerException a){}
		
		try{
			for(Edge e:edge){
				String command="createEdge";
				command+="("+e.getP1()+","+e.getP2()+");";
				output+=command+"\n";
			}
		}catch(java.lang.NullPointerException a){}
		
		try{
			for(Polygon py:polygon){
				String command="createPolygon";
				command+="("+py.getP1()+","+py.getP2()+","+py.getP3()+",\""+py.getPath()+"\");";
				output+=command+"\n";
			}
		}catch(java.lang.NullPointerException a){}
		
		output+="setCamera(0,"+camera[0]+");\n";
		output+="setCamera(1,"+camera[1]+");\n";
		output+="setCamera(2,"+camera[2]+");\n";
		output+="setCamera(3,"+camera[3]+");\n";
		output+="setCamera(4,"+camera[4]+");\n";
		output+="setCamera(5,"+camera[5]+");\n";
		
		return output;
	}
	
	/**
	 * exporte la chaine de caractere en parametre, le fichier exporté se nommera export.txt 
	 * 
	 * @param la chaine de caractere à exporter
	 */
	public static void export(String str){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("files/export.txt")));
			writer.write(str);
			writer.close();
		}catch (IOException e){}
	}
	
}
