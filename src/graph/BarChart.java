package graph;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
//import java.util.*;

public class BarChart extends Graph {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int position;
	int increment;
	public BarChart(String title, int min, int max) {
		super(title, min, max);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if (items.size() > 0) {
			increment = (right - left)/(items.size());
			position = left;
			Color temp = g.getColor();
			for (int i=0; i < items.size(); i++) {			
				GraphItem item = (GraphItem)items.elementAt(i);		
				int adjustedValue = bottom - (((item.value - min) * (bottom - top))/(max - min));			
				g.drawString(item.title, position + (increment - 
					fm.stringWidth(item.title))/2, adjustedValue - 2);
				g.setColor(item.color);
				g.fillRect(position, adjustedValue, increment, bottom - adjustedValue);
				position += increment;
				g.setColor(temp);
			}
		}
	}
	public void add(String name, int value, Color color) {
		this.addItem(name, value, color);
		this.repaint();
	}
	
	public void remove(String name) {
		System.out.println("before remove " + this.items.size());
		this.removeItem(name);
		System.out.println("after remove " + this.items.size());
		//this.repaint();
	}
	
	public void update(String name, int value) {
		System.out.println("==============================================updating");			
		for (int i=0; i < items.size(); i++) {
			GraphItem it = (GraphItem)items.get(i);
			if (it.title == name) {
				it.value = value;
				this.repaint();
				break;
			}
		}
	}
	
	public void update(String name, Color color) {
		System.out.println("==============================================updating" + color);			
		for (int i=0; i < items.size(); i++) {
			GraphItem it = (GraphItem)items.get(i);
			if (it.title == name) {
				it.color = color;
				this.repaint();
				break;
			}
		}
	}
	
	public void update(String name, int value, Color color) {
		System.out.println("==============================================updating" + color);
		for (int i=0; i < items.size(); i++) {
			GraphItem it = (GraphItem)items.get(i);
			if (it.title == name) {
				it.color = color;
				it.value = value;
				this.repaint();
				break;
			}
		}
	}
		
	public boolean containsComponent(String name) {
		for (int i=0; i < items.size(); i++) {
			GraphItem it = (GraphItem)items.get(i);			
			if (it.title.compareTo(name) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String args[]){
		JFrame f = new JFrame();
		BarChart b = new BarChart("My Title", 0, 100);
		b.add("One", 20, Color.blue);
		b.add("Two", 99, Color.green);
		b.add("Three", 54, Color.red);
		b.add("Four", 63, Color.yellow);
		
		f.add(b);
		
		f.pack();
		f.setVisible(true);
	}
}
