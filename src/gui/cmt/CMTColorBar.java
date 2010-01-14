package gui.cmt;

import java.awt.*;
//import java.util.*;
import javax.swing.*;

//import adt.*;

/**
 * <code>CMTColorBar</code> is the panel that holds the system status color bar indicator.
 * @author  lc
 * @version February 2008
 */

public class CMTColorBar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color color;
	private static int width = 50;
	private static int height = 50;
	
	
	public CMTColorBar() {
		super(new BorderLayout());
		this.color = Color.GREEN;
	}
	
	public CMTColorBar (Color color) { 
		super(new BorderLayout());
		this.color = color;
	}
	
	public void updateColor(Color color) {
		this.color = color;
		repaint();
	}
	
	public Dimension getPreferredSize(){
	    return new Dimension(width,height);  
	}

	public Dimension getMinimumSize(){
	    return new Dimension(width,height);  
	}	
	
	public Color getColor() {
		return this.color;
	}

	public void paintComponent (Graphics g) { 
		super.paintComponent (g);
		//String col = ((Level)categories.elementAt(category)).getCategory();
		//System.out.println("color is " + color.toString());
		g.setColor(color);
		g.fillRect(0, 0, width, height); 
		
	} // paintComponent

	
	public static void main(String args[]){
		JFrame mainFrame = new JFrame("ColorBar test");
	    mainFrame.setSize(width, height);
	    CMTColorBar sh = new CMTColorBar(Color.yellow);
		mainFrame.setContentPane(sh);
		mainFrame.setVisible(true);
		//sh.updateColor(Color.red);
	}
}


	
	

