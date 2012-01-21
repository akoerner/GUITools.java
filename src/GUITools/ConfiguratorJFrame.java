package GUITools;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

public class ConfiguratorJFrame extends JFrame implements ActionListener{
	
	
	private JButton saveButton;
	private JLabel messageLabel1, messageLabel2;
	private JScrollPane scrollPane;
	private JPanel messagePanel, scrollPanel;
	private ArrayList<RoundJTextField> settings;
	
	Configurator configurator;
	
	public ConfiguratorJFrame(Configurator configurator){
		this.configurator = configurator;
		this.setup();
	}
	
	private void setup(){
		
		
		this.saveButton = new JButton("Save");
		this.saveButton.addActionListener(this);
		
		this.scrollPanel = new JPanel();
		this.scrollPanel.setLayout(new FlowLayout());
		
		this.scrollPane = new JScrollPane (this.scrollPanel);
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
		
		this.messageLabel1 = new JLabel();
		this.messageLabel2 = new JLabel();
		
		this.messagePanel = new JPanel();
		this.messagePanel.setLayout(new FlowLayout());
		this.messagePanel.add(this.messageLabel1);
		this.messagePanel.add(this.messageLabel2);
		
		this.settings = new ArrayList<RoundJTextField>();
		
		//this.scrollPane.add(this.scrollPanel);
		
		this.scrollPanel.add(this.messagePanel);
		
		Enumeration keys = this.configurator.getKeys();
		String key;
		String value;
		JPanel tempJPanel;
		JLabel tempJLabel;
		RoundJTextField tempTextField;
		
		while(keys.hasMoreElements()){
			
			key = (String) keys.nextElement();
			value = this.configurator.getProperty(key);
			tempTextField = new RoundJTextField(30);
			
			tempTextField.setName(key);
			this.settings.add(tempTextField);
			
			if(value == null){
				tempTextField.setText("");
			}else{
				tempTextField.setText(value);
			}
			
			tempJLabel = new JLabel(GUITools.javaCamelCaseToHuman((String)key));
			
			tempJPanel = new JPanel();
			tempJPanel.setLayout(new FlowLayout());
			tempJPanel.add(tempJLabel);
			tempJPanel.add(tempTextField);
			
			this.scrollPanel.add(tempJPanel);
		}
		new JFrameDragger(this);
		this.scrollPanel.add(this.saveButton);
		
		
		///this.setLayout(new FlowLayout());
		//this.setUndecorated(true);
		//new JFrameDragger(this);
		//this.setLayout(new FlowLayout());
		
		this.messageLabel1.setText("message 1");
		this.messageLabel2.setText("message 2");
		
		this.add(this.scrollPane);
		//this.getContentPane().add(scrollPane);
		//this.scrollPanel.add(this.scrollPane);
		//this.add(this.scrollPanel);
		this.scrollPanel.setPreferredSize(new Dimension(500, 300));
		this.scrollPane.setPreferredSize(new Dimension(500, 300));
		this.getContentPane().setPreferredSize(new Dimension(500, 300));
		this.pack();
	}
	
	public void toggleVisable(){
		this.setVisible(!this.isVisible());
	}
	
	public void setPositon(int x, int y){
		this.setLocation(x, y);
	}
	
	public void setMessage1(String message1){
		this.messageLabel1.setText(message1);
	}
	
	public void setMessage2(String message2){
		this.messageLabel1.setText(message2);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand() == saveButton.getText()){
			
			String key, value;
			
			for(int counter = 0; counter<this.settings.size(); counter++){
				key = ((RoundJTextField)this.settings.get(counter)).getName();
				value = ((RoundJTextField)this.settings.get(counter)).getText();
				this.configurator.setProperty(key, value);
			}
			this.configurator.save();
			this.toggleVisable();
		}
		
	}
	
}
