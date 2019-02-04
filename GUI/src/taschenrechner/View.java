package taschenrechner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class View implements ActionListener{

	//Namen der Einzelnen Buttons - aufgeteilt in 4 verschiedene Kategorien 
	private String[] bracket	= {"(",")"};
	private String[] top2 		= {"clr","rtn","/"};
	private String[] number 	= {"7","8","9","*","4","5","6","+","1","2","3","-"};
	private String[] bottom 	= {",","0","="};
	//Textfeld
	JTextField output;
	
	private boolean result = false;
	
	
	/*
	 * TextFeld erstellung für die Visuelle Ausgabe der Zahlen
	 */
	public JTextField getTextField() {
		output = new JTextField();
		output.setEditable(false);
		output.setHorizontalAlignment(SwingConstants.RIGHT);
		output.setBackground(Color.WHITE);
		output.setMinimumSize(new Dimension(420, 55));
		return output;
	}
	
	//Ändert den angezeigten Text
	public void setText(String text) {
		this.output.setText(text);
	}
	//Gibt den vorhanden Text im Textfeld zurücl
	public String getText() {
		return this.output.getText();
	}
	
	public JPanel getBracketPanel() {
		return createGridPanel(bracket, 1, 2);
	}
	
	public JPanel getTop2Panel() {
		return createGridPanel(top2, 1, 3);
	}
	
	public JPanel getNumbersPanel() {
		return createGridPanel(number, 3, 4);
	}
	
	public JPanel getBottomPanel() {
		return createGridPanel(bottom, 1, 3);
	}
	
	/*
	 * Automatische GridPanel erstellung -> String[] für die Buttons, Spaltenangaben und Zeilenangaben
	 * @return JPanel
	 */
	private JPanel createGridPanel(String[] buttonText, int rows, int col) {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(420, (45*rows)));
		GridLayout gl = new GridLayout(rows, col, 3, 3);
		panel.setLayout(gl);
		for(int i = 0; i < buttonText.length;i++) {
			JButton button = new JButton(buttonText[i]);
			button.setMnemonic(getMnemonic(button)); 
			button.addActionListener((e ->{
				actionPerformed(e);
			}));
			panel.add(button); 
		}
		return panel;
	}

	private boolean isResult() {
		return result;
	}
	
	private void setResult(boolean result) {
		this.result = result;
	}

	@Override
	/*
	 * gibt die Jeweilige Information des gedrückten Buttons weiter -> 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		StringBuilder sb;
		if(isResult()) {
			sb = new StringBuilder();
			setText(""+sb.append(e.getActionCommand()));
			setResult(false); 
		}else if(e.getActionCommand().equals("clr")) {
			setText("");
		}else if(e.getActionCommand().equals("rtn")) {
			setText(getText().substring(0, getText().length()-1));
		}else if(e.getActionCommand().equals("=")) {
			sb = new StringBuilder(getText());
			setText(getSum(sb.toString()));
			setResult(true);
		}else {
			sb = new StringBuilder(getText());
			sb.append(e.getActionCommand());
			setText(sb.toString());
		}
	}
	
	public int getMnemonic(JButton button) {
		switch(button.getText()){
		case",":
			return KeyEvent.VK_COMMA;
		case "(":
			return KeyEvent.VK_8;
		case ")":
			return KeyEvent.VK_9;
		case "clr":
			return KeyEvent.VK_BACK_SPACE;
		case "rtn": 
			return KeyEvent.VK_LEFT;
		case "/":
			return KeyEvent.VK_W;
		case "0":
			return KeyEvent.VK_NUMPAD0;
		case "1":
			return KeyEvent.VK_NUMPAD1;
		case "2":
			return KeyEvent.VK_NUMPAD2;
		case "3":
			return KeyEvent.VK_NUMPAD3;
		case "4":
			return KeyEvent.VK_NUMPAD4;
		case "5":
			return KeyEvent.VK_NUMPAD5;
		case "6":
			return KeyEvent.VK_NUMPAD6;
		case "7":
			return KeyEvent.VK_NUMPAD7;
		case "8":
			return KeyEvent.VK_NUMPAD8;
		case "9":
			return KeyEvent.VK_NUMPAD9;
		case "=":
			return KeyEvent.VK_ENTER;
		case "+":
			return KeyEvent.VK_A;
		case "*":
			return KeyEvent.VK_S;
		case "-":
			return KeyEvent.VK_D;
		}
		return 0;
	}
	
	//Gibt String an Taschenrechner weiter und prüft im nachgang ob nach dem Komma 0-Stellen sind, welche man rausstreichen kann 
	private String getSum(String math) {
		boolean canBeInt = true;
		TaschenrechnerOperator rechner = new TaschenrechnerOperator();
		math = rechner.getResult(math.replaceAll("[,]", ".").replaceAll("[a-zA-Z]", ""));
		for(int i = math.indexOf(""+'.')+1;i<=math.length()-1;i++) {
			if(math.charAt(i)!='0') {
				canBeInt = false;
			}
		}
		if(canBeInt) {
			return math.substring(0, math.indexOf(""+'.'));
		}
		return math;
	}
}

