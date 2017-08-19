package md3;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SetPane extends JFrame{
	
	private static final long serialVersionUID = 297413206809719230L;
	
	private static JTextArea terminalArea=new JTextArea("~ ");
	
	/**
	 * invite de commande accepte les commande du creation reader<br>
	 * inclut un prompt "~ "<br>
	 * ajout d'une menu bar intégrée à l'ecran pour OS X<br>
	 * 
	 * 
	 * @author guest
	 * 
	 * @version 3.0
	 */
	public SetPane(){
		this.setSize(700, 450);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Terminal");
		
		if(System.getProperty("os.name").equals("Mac OS X")){// menu bar intégrée
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			
	        try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
	        	        
			JMenuBar menuBar = new JMenuBar();
			
			JMenu file = new JMenu("File");
			
			JMenuItem item1 = new JMenuItem("Export");
			JMenuItem item2 = new JMenuItem("Import");
			
			item1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					object.Export.export(object.Export.toString(Frame.Points, Frame.Edge, Frame.Polygon, Frame.Camera));
				}
			});
			
			file.add(item1);
			file.add(item2);
			
			menuBar.add(file);
			setJMenuBar(menuBar);
			
			this.add(menuBar);
		}
		
		terminalArea.setBackground(Color.BLACK);// initialisation de la JTextArea couleur fond
		terminalArea.setFont(new Font("Arial", Font.BOLD, 14));// type police
		terminalArea.setForeground(Color.GREEN);// couleur police
		
		terminalArea.addKeyListener(new terminalListener());
		terminalArea.setEditable(false);
		
		this.add(terminalArea);
	}
	
	/**
	 * Listener du terminal permet d'afficher les caracteres acceptés<br>
	 * dans effacer ou d'executer une commande<br>
	 * 
	 * @version 2.0
	 * 
	 * @author guest
	 * 
	 */
	private class terminalListener implements KeyListener{
		
		public void keyReleased(KeyEvent e){}
		
		public void keyTyped(KeyEvent e) {}

		public void keyPressed(KeyEvent e) {
			String a=" azertyuiopqsdfghjklmwxcvbn,;.1234567890)-(AZERTYUIOPQSDFGHJKLMWXCVBN\"/";
			char[] allowedChar=a.toCharArray();
			
			// tableau des chars autorisés
			
			if(e.getKeyCode()==8){// pour suppression
				String text=terminalArea.getText();
				if(text.charAt(text.length()-1)!=' ' && text.charAt(text.length()-2)!='~'){
					terminalArea.setText(terminalArea.getText().substring(0, terminalArea.getText().length()-1));
				}
			}
			
			if(e.getKeyCode()==10){// run de la commande si entrer
				int tildCompteur=0;
				int tildNumber=0;
				
				String command="";
				
				// compte du nombre de ~
				for(int i=0; i<terminalArea.getText().length(); i++){
					if(terminalArea.getText().charAt(i)=='~'){
						tildNumber++;
					}
				}
				
				for(int i=0; i<terminalArea.getText().length(); i++){
					if(terminalArea.getText().charAt(i)=='~'){
						tildCompteur++;
					}
					if(tildCompteur>=tildNumber){
						command+=terminalArea.getText().charAt(i);
					}
				}
				
				command=command.replaceAll("~ ", "");// suppresion du ~
				
				Frame.reader.buildCommand(command);// execution de la commande
				terminalArea.setText(terminalArea.getText()+"\n~ ");// rajout ligne
			}else{
				// sinon verification puis eventuellement affichage
				for(int i=0; i<allowedChar.length; i++){
					if(e.getKeyChar()==allowedChar[i]){
						terminalArea.setText(terminalArea.getText()+e.getKeyChar());
					}
				}
				
			}
			
			
		}
	}
	
	public static void help(){
		terminalArea.setText(terminalArea.getText()+"\ncreatePoint();\ncreateEdge();\ncreateCube();\n"
							 + "createTestSimulation();\ncreatePolygon();\ncreateMap();\nrepaintFrame();\nsetCamera();\ngenerate();\n"
							 + "alwayRepaint();\ndontRepaint();\nhelp();");
	}
	
}
