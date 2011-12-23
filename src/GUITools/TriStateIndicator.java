package GUITools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.JLabel;


public class TriStateIndicator extends JComponent implements Runnable{
	
	private Color positive = Color.GREEN;
	private Color negative = Color.RED;
	private Color neutral = new Color(255, 191, 0);//FFBF00 Amber
	int diameter = 30;
	
	short state = 0;// positive = 1, negative = -1 and neutral = 0;
	public static final short POSITIVE = 1;
	public static final short NEGATIVE = -1;
	public static final short NEUTRAL = 0;
	
	private Thread runner;
	
	public TriStateIndicator(){
		this.init();
	}
	public TriStateIndicator(int diameter){
		this.diameter = diameter;
		this.init();
	}
	public TriStateIndicator(Color positive, Color negative, Color neutral, int diameter){
		this.positive = positive;
		this.negative = negative;
		this.neutral = neutral;
		this.diameter = diameter;
		this.init();
	}
	
	private void init(){
		this.state = TriStateIndicator.POSITIVE;
		this.start();
	}
	
	public Color getPositive() {
		return positive;
	}
	public Color getNegative() {
		return negative;
	}
	public Color getNeutral() {
		return neutral;
	}
	public int getDiameter() {
		return diameter;
	}
	public short getState() {
		return state;
	}
	public void setPositive(Color positive) {
		this.positive = positive;
	}
	public void setNegative(Color negative) {
		this.negative = negative;
	}
	public void setNeutral(Color neutral) {
		this.neutral = neutral;
	}
	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	public void setState(short state) {
		this.state = state;
	}
	
	//gui methods
	public void paintIndicator(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        int x = 0, y = 0, w = this.diameter/2, h = this.diameter/2;
        
        Ellipse2D circle = new Ellipse2D.Double(x, y, w, h);
        
        if(this.getState() == TriStateIndicator.POSITIVE)g2.setColor(this.positive);
        if(this.getState() == TriStateIndicator.NEGATIVE)g2.setColor(this.negative);
        if(this.getState() == TriStateIndicator.NEUTRAL)g2.setColor(this.neutral);
        
        g2.fill(circle);
        // Stroke with a solid color.
        circle.setFrame(x , y, w, h);
        g2.draw(circle);
	}
	
	public void paintComponent(Graphics g){
		this.paintIndicator(g);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(this.diameter, this.diameter);
	}
	
	public Dimension getMaximumSize(){
		return new Dimension(this.diameter, this.diameter);
	}
	
	//thread methods
	public void start(){
		if(this.runner == null) this.runner = new Thread(this);
		this.runner.start();
	}
	
	public void run(){
		while (runner == Thread.currentThread()){
			this.repaint();
				try{
					Thread.sleep(10000);
				}catch(InterruptedException e){
	                   //log error
				}
		}
	}
}
