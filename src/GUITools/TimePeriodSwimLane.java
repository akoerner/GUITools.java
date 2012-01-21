package GUITools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

public class TimePeriodSwimLane extends JComponent{
	
	private Color primaryColor;
	private Color secondaryColor;
	private Color markerColor;
	
	private int startHour;
	private int startMinute;
	
	private int endHour;
	private int endMinute;
	
	private int minutesPerPixel;
	private int laneHightInPixels;
	private int laneWidthInPixels;
	private int cornerRadius = 10;
	
	boolean roundCorners = false;
	boolean halfHourMarkers = true;
	boolean hourMarkers = true;
	boolean showHour = false;
	
	private JPanel swimPanel;
	
	private DateTime startOfDay = new DateTime();
	
	
	ArrayList<Interval> timePeriods = new ArrayList<Interval>();
	
	public TimePeriodSwimLane(int startHour, int startMinute, int endHour, int endMinute, int minutesPerPixel, int laneHightInPixels, Color primaryColor, Color secondaryColor){
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		this.minutesPerPixel = minutesPerPixel;
		this.laneHightInPixels = laneHightInPixels;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.startOfDay = startOfDay.minusMillis(startOfDay.getMillisOfDay());
		
		this.setBackground(primaryColor);
		
		int totalStartMinute = (this.startHour * 60) + this.startMinute;
		int totalEndMinute  = (this.endHour * 60) + this.endMinute;
		int interval = totalEndMinute - totalStartMinute + 1;
		
		if(totalStartMinute > totalEndMinute){
			this.laneWidthInPixels = this.minutesToPixels(24 * 60);
			this.startHour = 0;
			this.startMinute = 0;
			this.endHour = 23;
			this.endMinute = 59;
		}else{
			this.laneWidthInPixels = this.minutesToPixels(interval);
		}
		
		this.setSize(this.laneWidthInPixels, this.laneHightInPixels);
	}
		
	public void paintComponent(Graphics g){
		g.setColor(this.primaryColor);
		for (Interval interval : this.timePeriods){
			Duration d = new Duration(interval);
			int minutesFromStartOfDay = (int) ((interval.getStartMillis() - this.startOfDay.getMillis())/(60.0 *1000.0));
			int startPositionInPixels = this.minutesToPixels(minutesFromStartOfDay);
			int durationInMinutes = (int) ((interval.toDurationMillis() * 1.0)/(60.0 *1000.0));
			int width = this.minutesToPixels(durationInMinutes);
			
			if(this.roundCorners){
				g.fillRoundRect(startPositionInPixels, (2*this.laneHightInPixels-this.laneHightInPixels)/6, width, 2*(this.laneHightInPixels/4)+this.laneHightInPixels/6, this.cornerRadius, this.cornerRadius);
			}else{
				g.fillRect(startPositionInPixels, (2*this.laneHightInPixels-this.laneHightInPixels)/6, width, 2*(this.laneHightInPixels/4)+this.laneHightInPixels/6);
			}
		}
		
		this.drawMarkers(g);
	}
	
	private void drawMarkers(Graphics g){
		int offset = this.startHour * 60;
		int normalizedEndMinute = (this.endHour - this.startHour) * 60 + this.endMinute;
		int minutePointer = 0;
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(this.secondaryColor);
		
		while(minutePointer < normalizedEndMinute){
	
			if(minutePointer == 0 || minutePointer%60 == 0){
				
				if(this.hourMarkers){
					if(minutePointer != 0){
						if(this.showHour){
							g2.draw(new Line2D.Float(this.minutesToPixels(minutePointer), 0, this.minutesToPixels(minutePointer), this.laneHightInPixels-10));
							System.out.println("kk");
							g2.drawString((minutePointer + offset)/60+"", this.minutesToPixels(minutePointer) - this.minutesToPixels(15), this.laneHightInPixels);
						}else{
							
							g2.draw(new Line2D.Float(this.minutesToPixels(minutePointer), 0, this.minutesToPixels(minutePointer), this.laneHightInPixels));
						}
					}else{
						g2.draw(new Line2D.Float(this.minutesToPixels(minutePointer), 0, this.minutesToPixels(minutePointer), this.laneHightInPixels));
					}
				}
			}else{
				if(this.halfHourMarkers){
					g2.draw(new Line2D.Float(this.minutesToPixels(minutePointer), this.laneHightInPixels/3, this.minutesToPixels(minutePointer), (2*this.laneHightInPixels/3)));
				}
			}
			
			minutePointer += 30;
		
		}
		
		
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(this.laneWidthInPixels, this.laneHightInPixels);
	}
	public Dimension getMinimumSize(){
		return new Dimension(this.laneWidthInPixels, this.laneHightInPixels);
	}
	public Dimension getMaximumSize(){
		return new Dimension(this.laneWidthInPixels, this.laneHightInPixels);
	}
	
	public void addTimeInterval(Interval interval){
		timePeriods.add(interval);
	}
	
	public void addTimeInterval(Interval interval, Color color){
		timePeriods.add(interval);
	}
	
	public void addTimeInterval(DateTime start, DateTime end){
		timePeriods.add(new Interval(start, end));
	}
	
	public void setOpaque(boolean isOpaque){
		this.setOpaque(isOpaque);
		super.setOpaque(isOpaque);
	}
	
	public int pixelsToMinutes(int pixels){
		return pixels * this.minutesPerPixel;
		
	}
	
	public int minutesToPixels(int minutes){
		return (int) ((minutes * 1.0)/this.minutesPerPixel);
	}

	public boolean isRoundCorners() {
		return roundCorners;
	}

	public boolean isHalfHourMarkers() {
		return halfHourMarkers;
	}

	public boolean isHourMarkers() {
		return hourMarkers;
	}

	public boolean isShowHour() {
		return showHour;
	}

	public void setRoundCorners(boolean roundCorners) {
		this.roundCorners = roundCorners;
	}

	public void setHalfHourMarkers(boolean halfHourMarkers) {
		this.halfHourMarkers = halfHourMarkers;
	}

	public void setHourMarkers(boolean hourMarkers) {
		this.hourMarkers = hourMarkers;
	}

	public void setShowHour(boolean showHour) {
		this.showHour = showHour;
	}
	
	

}
