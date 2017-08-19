package object;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CreationReader {
	
	private String everything;
	
	private boolean alwayRepaint=false;
		
	public CreationReader(){
		
	}
	
	/**
	 * methode de generation des points en fonction du fichier
	 * 
	 * accepte les doubles<br>
	 * 
	 * commande disponible :<br>
	 * - createPoint<br>
	 * - createEdge<br>
	 * - createPolygon<br>
	 * - createMap<br>
	 * - repaintFrame<br>
	 * - setCamera<br>
	 * - reset<br>
	 * - generate<br>
	 * - createTestSimulation<br>
	 * - alwayRepaint (reactualise la fenetre apres chaques commandes)<br>
	 * - dontReapaint (ne reactualise pas la fenetre apres chaques commandes)<br>
	 * 
	 * @version 1.1
	 */
	public boolean generate(String path){
		FileReader inputReader=null;
		try {
			inputReader=new FileReader(path);
		} catch (FileNotFoundException e1) {}
		BufferedReader br=new BufferedReader(inputReader);
		try {
		    StringBuilder sb=new StringBuilder();
		    String line;
		    line = br.readLine();
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		} catch (IOException e) {
		} finally {
		    try{
		    	br.close();
		    } catch (IOException e) {}
		}
		buildCommand(everything);
		return(true);
	}
	
	/**
	 * methode de recuperation d'une ligne de commande(finisant par un ;)
	 * 
	 * @version 1.1
	 * @param input
	 */
	public void buildCommand(String input){
		String command="";
		for(int i=0; i<input.length(); i++){
			if(input.charAt(i)!=';'){
				command+=input.charAt(i);
			}else{
				command=command.replaceAll("[\r\n]+", "");
				command=command.replaceAll(" ", "");
				runCommand(command);
				command="";
			}
		}
		
	}
	
	/**
	 * execute une commande<br> 
	 * 
	 * commande disponible :<br>
	 * - createPoint<br>
	 * - createEdge<br>
	 * - createPolygon<br>
	 * - createMap<br>
	 * - repaintFrame<br>
	 * - setCamera<br>
	 * - reset<br>
	 * - generate<br>
	 * - createTestSimulation<br>
	 * - alwayRepaint (reactualise la fenetre apres chaques commandes)<br>
	 * - dontReapaint (ne reactualise pas la fenetre apres chaques commandes)<br>
	 * <br>
	 * accepte les doubles
	 * 
	 * @param command
	 * 
	 * @version 1.1
	 */
	private void runCommand(String command){
		String name=getCommandName(command);
		String[] param=getCommandParamTab(command);
		double[] intParam=getCommandParamTabInt(command);
		if(name.equals("createPoint")){
			md3.Frame.createPoint(intParam[0], intParam[1], intParam[2]);
		}
		
		if(name.equals("createEdge")){
			md3.Frame.createEdge((int)intParam[0], (int)intParam[1]);
		}
		
		if(name.equals("createCube")){
			md3.Frame.createCube((int)intParam[0], (int)intParam[1]);
		}
		
		if(name.equals("createPolygon")){
			md3.Frame.createPolygon((int)intParam[0], (int)intParam[1], (int)intParam[2], extractString(param[3]));
		}
		
		if(name.equals("createMap")){
			md3.Frame.createMap((int)intParam[0], (int)intParam[1]);
		}
		
		if(name.equals("repaintFrame")){
			md3.Frame.repaintFrame();
		}
		
		if(name.equals("setCamera")){
			md3.Frame.setCamera((int)intParam[0], intParam[1]);
		}
		
		if(name.equals("reset")){
			md3.Frame.reset();
		}
		
		if(name.equals("generate")){
			md3.Frame.reader.generate(extractString(param[0]));
		}
		
		if(name.equals("createTestSimulation")){
			md3.Frame.createTestSimulation();
		}
		
		if(name.equals("alwayRepaint")){
			alwayRepaint=true;
		}
		
		if(name.equals("dontRepaint")){
			alwayRepaint=false;
		}
		
		if(name.equals("help")){
			md3.SetPane.help();
		}
		
		if(alwayRepaint && !name.equals("repaintFrame")){
			md3.Frame.repaintFrame();
		}
		
	}
	
	
	
	/**
	 * retourne le tableau de int avec les parametre de type de double
	 * 
	 * @param command
	 * 
	 * @return le tableau de double issue du tableau de string des param en fonction
	 * de la commande donnee
	 * 
	 * @version 1.1
	 */
	private double[] getCommandParamTabInt(String command){
		String[] param=getCommandParamTab(command);
		double[] intParam=new double[param.length];
		for(int i=0; i<param.length; i++){
			boolean isInt=true;
			for(int j=0; j<param[i].length(); j++){
				if(((int)param[i].charAt(j)<48 || (int)param[i].charAt(j)>57) && (int)param[i].charAt(j)!=45 && (int)param[i].charAt(j)!=46){
					isInt=false;
				}
				if(isInt==true){
					intParam[i]=Double.parseDouble(param[i]);
				}
			}
		}
		return intParam;
	}
	
	/**
	 * 
	 * retourne la chaine de caractere (balisee par des ") comprise dans param
	 * 
	 * @param param
	 * 
	 * @return la chaine encadree de " contenu dans param
	 * 
	 * @version 1.0
	 */
	private String extractString(String param){
		boolean on=false;
		String str="";
		for(int i=0; i<param.length(); i++){
			if(param.charAt(i)=='\"'){
				if(on==false){
					on=true;
					i++;
				}else{
					on=false;
					break;
				}
			}
			if(on==true){
				str+=param.charAt(i);
			}
		}
		return str;
	}
	
	/**
	 * recuperation des parametre de la commande entouré par des (
	 * 
	 * @param command
	 * 
	 * @return parametre de la commande
	 * 
	 * @version 1.2
	 */
	private String getCommandParam(String command){
		String param="";
		int compteurParen=1;// compteur de parenthese
		for(int i=command.indexOf('(')+1; i<command.length(); i++){
			if(command.charAt(i)=='('){
				compteurParen++;
			}
			if(command.charAt(i)==')'){
				compteurParen--;
			}
			if(compteurParen>0){
				param+=command.charAt(i);
			}else{
				break;
			}
		}
		return param;
	}
	
	/**
	 * retourne la liste des parametre en fonction de la commande donnee separe par des ,
	 * 
	 * @param command
	 * 
	 * @return liste des parametre dans un tableau
	 * 
	 * @version 1.0
	 */
	private String[] getCommandParamTab(String command){
		String param=getCommandParam(command);
		int paramNumber=getOccurence(param,',')+1;
		String[] paramTab=new String[paramNumber];
		
		for(int i=0; i<paramNumber; i++){
			paramTab[i]="";// initialisation du tableau
		}
		
		int positionInTab=0;
		for(int i=0; i<param.length(); i++){
			if(param.charAt(i)!=','){
				paramTab[positionInTab]+=param.charAt(i);
			}else{
				positionInTab++;
			}
		}
		return paramTab;
	}
	
	/**
	 * recuperation du nom de la commande
	 * 
	 * @param command
	 * @version 1.0
	 * @return nom de la commande
	 */
	private String getCommandName(String command){
		String name="";
		for(int i=0; i<command.length(); i++){
			if(command.charAt(i)!='('){
				name+=command.charAt(i);
			}else{
				break;
			}
		}
		return name;
	}
	
	
	/**
	 * retourne le nombre d'occurence d'un char dans une chaine
	 * 
	 * @param id
	 * @param c
	 * @return nombre d'occurence de c dans id
	 */
	private int getOccurence(String id, char c){
		int compteur=0;
		for(int i=0;i<id.length(); i++){
			if(id.charAt(i)==c){
				compteur++;
			}
		}
		return compteur;
	}
	
}
