package graph;

import java.awt.*;
import java.util.*;

public class Graph extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// variables needed
	public int top;
	public int bottom;
	public int left;
	public int right;
	public String minLabel;
	public String maxLabel;
	int titleHeight;
	int labelWidth;
	FontMetrics fm;
	int padding = 20;
	String title;
	int min;
	int max;
	Vector<GraphItem> items;
	
	public Graph(String title, int min, int max) {
		this.title = title;
		this.min = min;
		this.max = max;
		items = new Vector<GraphItem>();
	}
	
	public void setMinLabel(String minLabel) {
		this.minLabel = minLabel;
	}
	public void setMaxLabel(String maxLabel) {
		this.maxLabel = maxLabel;
	}
	
	public void reshape(int x, int y, int width, int height) {
		super.reshape(x,y,width,height);
		fm = getFontMetrics(getFont());
		titleHeight = fm.getHeight();
		labelWidth = Math.max(fm.stringWidth(new Integer(min).toString()), 
				fm.stringWidth(new Integer(max).toString())) + 2;
		top = padding + titleHeight;
		bottom = height - padding;
		left = padding + labelWidth;
		right = width - padding;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		System.out.println("====== size of items " + items.size());
		// draw the title
		fm = getFontMetrics(getFont());
		g.drawString(title, (getSize().width - fm.stringWidth(title))/2, top-padding);
		
		// draw the max and min values
		String minLbl = minLabel;
		if (minLbl == null) {
			minLbl = new Integer(min).toString();
		}
		String maxLbl = maxLabel;
		if (maxLbl == null) {
			maxLbl = new Integer(max).toString();
		}
		g.drawString(minLbl, padding, bottom);
		g.drawString(maxLbl, padding, top + titleHeight);
		
		// draw the vertical and horizontal lines
		g.drawLine(left, top, left, bottom);
		g.drawLine(left, bottom, right, bottom);
	}
	
	public void clear() {
		items = new Vector<GraphItem>();
		repaint();
	}
	
	public Dimension preferredSize() {
		return (new Dimension (300,200));
	}
	
	public void addItem(String name, int value, Color col) {
		System.out.println("addItem1");
		items.addElement(new GraphItem(name, value, col));
	}
	
	public void addItem(String name, int value) {
		System.out.println("addItem2");
		items.addElement(new GraphItem(name, value, Color.black));
	}
	
	public void removeItem(String name) {
		System.out.println("removeItem" + items.size());		
		for (int i=0; i < items.size(); i++) {
			if (((GraphItem)items.elementAt(i)).title.equals(name))
				items.removeElementAt(i);
		}
		System.out.println("removeItem" + items.size());
	}
	
}



