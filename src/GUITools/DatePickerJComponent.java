package GUITools;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.joda.time.DateTime;

public class DatePickerJComponent extends JComponent implements MouseListener{
	
	private RoundJTextField dateTextField;
	private String promptText;
	private DatePicker datePicker;
	private DateTime oldStartDate= null;
	private DateTime oldEndDate= null;
	
	public DatePickerJComponent(String promptText){
		this.promptText = promptText;
		this.initilize();
	}


	public void initilize(){
		this.setLayout(new FlowLayout());
		dateTextField = new RoundJTextField(16);
		dateTextField.setEditable(false);
		dateTextField.setEnabled(true);
		dateTextField.addMouseListener(this);
		dateTextField.setText(this.promptText);
		this.add(dateTextField);
		
		this.setVisible(true);
		datePicker = new DatePicker(this.dateTextField);
		
	}
	
	public DateTime getPickedDate(){
		if(this.dateTextField.getText().equalsIgnoreCase(this.promptText) || this.dateTextField.getText().equalsIgnoreCase("")){
			return null;
		}else{
			String pattern = "E, MMMM d, yyyy";
			DateFormat df = new SimpleDateFormat(pattern); // This line
			
			try {
				return  (new DateTime(df.parse(this.dateTextField.getText())));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		return null;
	}
	
	public boolean hasChanged(){
		return this.datePicker.hasChanged();
	}
	
	public void resetHasChanged(){
		this.datePicker.resetHasChanged();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == dateTextField){
			this.dateTextField.setEnabled(false);
			this.datePicker.toggleVisable();
		}	
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		Cursor curser = new Cursor(Cursor.HAND_CURSOR);
		this.getRootPane().setCursor(curser);
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		Cursor curser = new Cursor(Cursor.DEFAULT_CURSOR);
		this.getRootPane().setCursor(curser);
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public String getPromptText() {
		return promptText;
	}


	public void setPromptText(String promptText) {
		this.dateTextField.setText(promptText);
		this.promptText = promptText;
	}
}
