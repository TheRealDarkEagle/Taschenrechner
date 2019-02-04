package taschenrechner;

import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



@SuppressWarnings("serial")
public class Model extends JFrame {

	private View view = new View();
	
	public Model() {
		initUI();
	}
	
	/*
	 * baut die Komponenten zusammen
	 */
	private void initUI() {
		//Äußeres Panel
		Container pane = getContentPane();
		GroupLayout gl = new GroupLayout(pane);
		pane.setLayout(gl);
		//Abstende von den Containern und dem äußeren Rand
		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);
		//TextFeld für die Ausgabe
		JTextField output = view.getTextField();
		//Die jeweiligen Panels für die jeweiigen Buttons
		JPanel brackets = view.getBracketPanel();
		JPanel remove = view.getTop2Panel();
		JPanel numbers = view.getNumbersPanel();
		JPanel last = view.getBottomPanel();
		//Anordnung der jeweiligen Panels durch den Layout Manager
		gl.setHorizontalGroup(gl.createParallelGroup().addComponent(output).addComponent(brackets).addComponent(remove).addComponent(numbers).addComponent(last));
		gl.setVerticalGroup(gl.createSequentialGroup().addComponent(output).addComponent(brackets).addComponent(remove).addComponent(numbers).addComponent(last));
		pack();
		setTitle("Basic - Taschenrechner");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}


	/*
	 * Main methode -> Fenster wird erst nach "Fertigstellung aller Komponenten" visualisiert -> "invokeLater"
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			Model taschenrechner = new Model();
			taschenrechner.setVisible(true);
		});
	}

	

	
	
}

	
