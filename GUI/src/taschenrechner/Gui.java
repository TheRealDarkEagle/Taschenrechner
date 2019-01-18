package taschenrechner;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import test.TaschenrechnerOperator1;


@SuppressWarnings("serial")
public class Gui extends JFrame implements ActionListener{

	private int width = 420;
	private JTextField output;
	private StringBuilder sb;
	private boolean gotResult = false;
	
	public Gui() {
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
		//TextFeld für die Ausgabe
		JTextField output = getTextField();

		//Abstende von den Containern und dem äußeren rand
		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);
		
		//Namen der Einzelnen Buttons - aufgeteilt in 4 verschiedene Teile 
		String[] bracket = {"(",")"};
		String[] top2 = {"clr","rtn","/"};
		String[] number = {"7","8","9","*","4","5","6","+","1","2","3","-"};
		String[] bottom = {",","0","="};
		//Die jeweiligen Panels für die jeweiigen Buttons
		JPanel brackets = createGridPanel(bracket, 1, 2);
		JPanel remove = createGridPanel(top2, 1, 3);
		JPanel numbers = createGridPanel(number, 3, 4);
		JPanel last = createGridPanel(bottom, 1, 3);
		//Anordnung der jeweiligen Panels durch den Layout Manager
		gl.setHorizontalGroup(gl.createParallelGroup().addComponent(output).addComponent(brackets).addComponent(remove).addComponent(numbers).addComponent(last));
		gl.setVerticalGroup(gl.createSequentialGroup().addComponent(output).addComponent(brackets).addComponent(remove).addComponent(numbers).addComponent(last));
		pack();
		setTitle("Basic - Taschenrechner");
		setLocation(3050, 240);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	/*
	 * Automatische GridPanel erstellung -> String[] für die Buttons, Spaltenangaben und Zeilenangaben
	 * @return JPanel
	 */
	private JPanel createGridPanel(String[] buttonText, int rows, int col) {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(width, 45*(1*rows)));
		GridLayout gl = new GridLayout(rows, col, 3, 3);
		panel.setLayout(gl);
		for(int i = 0; i < buttonText.length;i++) {
			JButton button = new JButton(buttonText[i]);
			button.addActionListener((e ->{
				actionPerformed(e);
			}));
			panel.add(button); 
		}
			
		
		return panel;
	}

	/*
	 * TextFeld erstellung für die Visuelle Ausgabe der Zahlen
	 */
	private JTextField getTextField() {
		output = new JTextField();
		output.setHorizontalAlignment(SwingConstants.RIGHT);
		output.setBackground(Color.WHITE);
		output.setMinimumSize(new Dimension(width, 55));
		output.setEditable(false);
		return output;
	}
	
	/*
	 * Main methode -> Fenster wird erst nach "Fertigstellung aller Komponenten" visualisiert -> "invokeLater"
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			Gui test = new Gui();
			test.setVisible(true);
		});
	}

	
	@Override
	/*
	 * gibt die Jeweilige Information des gedrückten Buttons weiter -> 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(isResult()) {
			sb = new StringBuilder();
			output.setText(""+sb.append(e.getActionCommand()));
			setResult(false); 
		}else if(e.getActionCommand().equals("clr")) {
			output.setText("");
		}else if(e.getActionCommand().equals("rtn")) {
			output.setText(output.getText().substring(0, output.getText().length()-1));
		}else if(e.getActionCommand().equals("=")) {
			output.setText(getSum(sb.toString()));
			setResult(true);
		}else {
			sb = new StringBuilder(output.getText());
			sb.append(e.getActionCommand());
			output.setText(sb.toString());
		}
	}

	private boolean isResult() {
		return gotResult;
	}
	
	private void setResult(boolean gotResult) {
		this.gotResult = gotResult;
	}
	
	private String getSum(String math) {
		TaschenrechnerOperator1 rechner = new TaschenrechnerOperator1();
		return rechner.getSum(output.getText().replaceAll("[,]", "."));
	}
}
