package GUITools;

import java.awt.*;
import java.awt.event.*;
//import static java.awt.GraphicsDevice.WindowTranslucency.*;
//import com.sun.awt.AWTUtilities;
import javax.swing.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class DatePicker extends JFrame implements ActionListener{
	int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
	int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
	private final String[] dayOfTheWeekHeader = { " Sun", " Mon", " Tue", " Wed", " Thur", " Fri", " Sat" };
	String dateFormat = "EEEE, MMMM d, yyyy";
	JLabel l = new JLabel("", JLabel.CENTER);
	String day = "";
	JButton[] button = new JButton[49];
	JButton previous, next, today;
	JTextField dateField;

	public DatePicker(JFrame parent, JTextField dateField){
		this.dateField = dateField;
		JPanel p1 = new JPanel(new GridLayout(7, 7));
		p1.setPreferredSize(new Dimension(430, 120));

		for (int x = 0; x < button.length; x++){
			final int selection = x;
			button[x] = new JButton();
			//button[x].setFocusPainted(false);
			if (x > 6)button[x].addActionListener(this);
					
			if (x < 7) {
				p1.add(new JLabel(this.dayOfTheWeekHeader[x]));
			}else{
				p1.add(button[x]);
			}
		}
		
		try {
		    Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
		    Method method = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class); 
		    method.invoke(null, this, 0.90f);
		} catch (Exception exc) {
		    exc.printStackTrace();
		}
		
		
		
		JPanel p2 = new JPanel(new GridLayout(1, 3));
		previous = new JButton("<<");
		previous.addActionListener(this);
		next = new JButton(">>");
		next.addActionListener(this);
		
		JPanel monthSelect = new JPanel();
		monthSelect.setLayout(new FlowLayout());
		monthSelect.add(previous);
		monthSelect.add(next);
		
		p2.add(monthSelect);
		p2.add(l);
		this.today = new JButton();
		this.today.addActionListener(this);
		this.today.setText("Today");
		
		p2.add(today);
	
		this.add(p1, BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(parent);
		displayDate();
		this.setVisible(false);
		
	}

	public void displayDate(){
		for (int x = 7; x < button.length; x++){
			button[x].setText("");
			button[x].setEnabled(false);
			button[x].setVisible(false);
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year, month, 1);
		int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		
		for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++){
			button[x].setText("" + day);
			if(Integer.parseInt(button[x].getText()) > 0){
				button[x].setEnabled(true);
				button[x].setVisible(true);
			}
		}
		l.setText(sdf.format(cal.getTime()));
		this.setTitle("Date Picker");
	}

	public String getPickedDate(){
		if (day.equals(""))return day;
		SimpleDateFormat sdf = new SimpleDateFormat(this.dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, Integer.parseInt(this.day));
		return sdf.format(cal.getTime());
	}
	
	public void toggleVisable(){
		this.setVisible(!this.isVisible());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int buttonNumber = 0; buttonNumber<button.length; buttonNumber++){
			if(e.getSource().equals(button[buttonNumber])){
				this.day = button[buttonNumber].getActionCommand();
				this.dateField.setEnabled(true);
				this.toggleVisable();
				this.dateField.setText(this.getPickedDate());
			}
		}
		if(e.getSource().equals(this.previous)){
			this.month--;
			this.displayDate();
		}
		if(e.getSource().equals(this.next)){
			this.month++;
			this.displayDate();
		}
		if(e.getSource().equals(this.today)){
			Calendar calendar = Calendar.getInstance();
			this.day = calendar.get(Calendar.DAY_OF_MONTH) +""; 
			this.dateField.setEnabled(true);
			this.toggleVisable();
			this.dateField.setText(this.getPickedDate());
		}
		
	}
}
