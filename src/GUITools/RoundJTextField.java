package GUITools;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class RoundJTextField extends JTextField {  
    public RoundJTextField(int cols) {  
        super(cols);
        this.setOpaque(false);    
        this.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));  
    }  
    protected void paintComponent(Graphics g) {  
        int width = getWidth();  
        int height = getHeight();  
        g.setColor(this.getBackground());  
        g.fillRoundRect(0, 0, width, height, 8, 8);  
        super.paintComponent(g);  
    }  
}  