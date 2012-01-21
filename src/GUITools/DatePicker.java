package GUITools;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
//import static java.awt.GraphicsDevice.WindowTranslucency.*;
//import com.sun.awt.AWTUtilities;
import javax.swing.*;

import org.joda.time.DateTime;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class DatePicker extends JFrame implements ActionListener, MouseListener{
	private int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
	private int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
	private final String[] dayOfTheWeekHeader = { " Sun", " Mon", " Tue", " Wed", " Thur", " Fri", " Sat" };
	private String dateFormat = "EEEE, MMMM d, yyyy";
	private JLabel l = new JLabel("", JLabel.CENTER);
	private String day = "";
	private JButton[] button = new JButton[49];
	private JLabel previousYear, previousMonth, nextMonth, nextYear, today;
	private JTextField dateField;
	private JFrame parent;
	
	
	private boolean hasChanged = false;

	public DatePicker(JTextField dateField){
		this.dateField = dateField;
		this.setSize(350, 255);
		this.setResizable(false);
		this.setDefaultLookAndFeelDecorated(false);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		
		new JFrameDragger(this);
		JPanel p1 = new JPanel(new GridLayout(7, 7));
		p1.setPreferredSize(new Dimension(430, 120));
		p1.setMaximumSize(new Dimension(430, 120));
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
		    method.invoke(null, this, 0.85f);
		} catch (Exception exc) {}
		
		
	    
		try {
		    Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
		    Method method = awtUtilitiesClass.getMethod("setWindowShape", Window.class, Shape.class);
		    Ellipse2D shape = new Ellipse2D.Float(0, 0, this.getWidth(), this.getHeight());
		    method.invoke(null, this, shape);
		} catch (Exception exc) {}
		
		
		
		
		JPanel p2 = new JPanel(new GridLayout(1, 3));
		previousMonth = new JLabel("<");
		previousMonth.addMouseListener(this);
		nextMonth = new JLabel(">");
		nextMonth.addMouseListener(this);
		previousYear = new JLabel("<<   ");
		previousYear.addMouseListener(this);
		nextYear = new JLabel("   >>");
		nextYear.addMouseListener(this);
		
		this.previousMonth.setSize(5, 10);
		
		
		
		JPanel monthYearSelect = new JPanel();
		monthYearSelect.setLayout(new FlowLayout());
		monthYearSelect.add(previousYear);
		monthYearSelect.add(previousMonth);
		monthYearSelect.add(nextMonth);
		monthYearSelect.add(nextYear);
		
		p2.add(monthYearSelect);
		p2.add(l);
		this.today = new JLabel();
		this.today.addMouseListener(this);
		this.today.setText("       Today");
		
		p2.add(today);
	
		this.add(p1, BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);
		//this.pack();
		this.setLocationRelativeTo(parent);
		displayDate();
		this.setVisible(false);
		
	}
	
	public boolean hasChanged(){
		return this.hasChanged;
	}
	
	public void resetHasChanged(){
		this.hasChanged = false;
	}
	
	private void setMinimumSize(int i, int j) {
		// TODO Auto-generated method stub
		
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
				this.hasChanged = true;
				this.dateField.setText(this.getPickedDate());
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource().equals(this.previousMonth)){
			this.month--;
			this.displayDate();
		}
		if(arg0.getSource().equals(this.nextMonth)){
			this.month++;
			this.displayDate();
		}
		if(arg0.getSource().equals(this.previousYear)){
			this.year--;
			this.displayDate();
		}
		if(arg0.getSource().equals(this.nextYear)){
			this.year++;
			this.displayDate();
		}
		if(arg0.getSource().equals(this.today)){
			Calendar calendar = Calendar.getInstance();
			this.day = calendar.get(Calendar.DAY_OF_MONTH) +""; 
			this.dateField.setEnabled(true);
			this.toggleVisable();
			this.hasChanged = true;
			this.dateField.setText(this.getPickedDate());
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
}
