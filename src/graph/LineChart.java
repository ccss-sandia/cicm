package graph;

import java.awt.*;

public class LineChart extends Graph {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int increment;
	int position;
	
	public LineChart(String title, int min, int max) {
		super(title, min, max);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		increment = (right - left) / (items.size() - 1);
		position = left;
		Color temp = g.getColor();
		GraphItem firstItem = (GraphItem)items.firstElement();
		int firstAdjustedValue = bottom - (((firstItem.value - min) * (bottom - top))/(max - min));
		g.setColor(firstItem.color);
		g.drawString(firstItem.title, position - fm.stringWidth(firstItem.title), firstAdjustedValue - 2);
		g.fillOval(position - 2, firstAdjustedValue - 2, 4, 4);
		g.setColor(temp);
		for (int i=0; i < items.size()-1; i++) {
			GraphItem thisItem = (GraphItem)items.elementAt(i);
			int thisAdjustedValue = bottom - (((thisItem.value - min)*(bottom-top))/(max-min));
			GraphItem nextItem = (GraphItem)items.elementAt(i+1);
			int nextAdjustedValue = bottom - (((nextItem.value - min) * (bottom -top))/(max-min));
			g.drawLine(position, thisAdjustedValue, position +=increment, nextAdjustedValue);
			g.setColor(nextItem.color);
			if (nextAdjustedValue < thisAdjustedValue)
				g.drawString(nextItem.title, position - fm.stringWidth(nextItem.title), nextAdjustedValue + titleHeight + 4);
			else
				g.drawString(nextItem.title, position - fm.stringWidth(nextItem.title), nextAdjustedValue - 4);
			g.fillOval(position - 2, nextAdjustedValue -2, 4, 4);
			g.setColor(temp);
		}
	}
	
}
