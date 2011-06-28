package GUITools;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class GUITools {

	private GUITools(){}
	
	public static Font getRandomFont(int size) {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		String[] fontFamilies = ge.getAvailableFontFamilyNames();
		Random randomGenerator = new Random();
		return new Font(
				fontFamilies[randomGenerator.nextInt(fontFamilies.length)],
				Font.BOLD, size);
	}

	public static Color getRandomColor() {
		Random randomGenerator = new Random();
		return new Color(randomGenerator.nextInt(255),
				randomGenerator.nextInt(255), randomGenerator.nextInt(255));
	}

	public static Font loadTTFFontFromFile(String fileName) {
		Font font = null;
		try {
			File f = new File (fileName); 
			System.out.println(f.getAbsolutePath());
			FileInputStream in = new FileInputStream (f);
			System.out.println("b");
			Font dynamicFont = Font.createFont (Font.TRUETYPE_FONT, in);
			System.out.println("c");
			return dynamicFont.deriveFont (32f);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("plain jane");
			font = new Font("serif", Font.PLAIN, 24);
		}
		return font;
	}

	public static void setNativeLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
	}

	public static void setJavaLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting Java LAF: " + e);
		}
	}

	public static void setMotifLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			System.out.println("Error setting Motif LAF: " + e);
		}
	}
	
	
	
	
}
