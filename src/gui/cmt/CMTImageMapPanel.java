package gui.cmt;

import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.io.*;
import java.awt.event.*;

import adt.cmt.*;

/**
 * <code>CMTImageMap</code> is the panel that contains the image map diagram of the system.
 * @author  Lozanne Chavez 
 * @version February 2008
 */

public class CMTImageMapPanel extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean singleSelect = false;
	Image img; 
	Map components;
	Map<String, Integer> load_shed;
	//Rectangle current_rect;
	//Rectangle current_load_rect;
	SystemComponent current_component;
	//SystemComponent current_load;
	String component;
	
	CMTSystem s;
	int width = 600;
	int height = 500;
	Vector<SystemComponent> outage_components;
	//Vector<SystemComponent> affected_loads;
	CMT window;
	
	CMTImageMapPanel (CMTSystem s, CMT window) { 
	   super(new BorderLayout());
	   
	   this.img = Toolkit.getDefaultToolkit().getImage(s.getImageFile());
	   this.components = s.getSystemComponents();
	   this.s = s;
	   this.window = window;
	   //rects = new Vector<Rectangle>();
	   outage_components = new Vector<SystemComponent>();
	  // affected_loads = new Vector<SystemComponent>();
		    
	   load_shed = new HashMap();
	   addMouseListener(this);
	} 
	
	CMTImageMapPanel (CMTSystem s, CMT window, int width, int height) { 
	   this.width = width;
	   this.height = height;
		
	   new CMTImageMapPanel(s, window);
	} 
	
	public Dimension getpreferredSize(){
	    return new Dimension(width,height);  
	}

	public Dimension getMinimumSize(){
	    return new Dimension(width,height);  
	}	
	public Dimension getMaximumSize() {
		return new Dimension (width, height);
	}

	public void paintComponent (Graphics g) {
		super.paintComponent (g); 
		load_shed = new HashMap();
		
//System.out.println("===== repainting " + rects.size());		
		//g.drawImage(img, 0, 0, this);
		
		int widthConstraint = 600;
		int heightConstraint = 500;
   	    int imgWidth = img.getWidth(this);
   	    int imgHeight = img.getHeight(this);
   	    if ( imgWidth > widthConstraint || imgHeight > heightConstraint ){
   	   	    int newWidth = imgWidth;
   	   	    int newHeight = imgHeight;
   	    	if ( imgWidth > imgHeight ) {
		      // Create a resizing ratio.
		      double ratio = (double) imgWidth / (double) widthConstraint;
		      newHeight = (int) ( (double) imgHeight / ratio );
		    }
		    else {
		      // Create a resizing ratio.
		      double ratio = (double) imgHeight / (double) heightConstraint;
		      newWidth = (int) ( (double) imgWidth / ratio );
		    }
		    g.drawImage(img, 0, 0, widthConstraint, heightConstraint, 0, 0, newWidth, newHeight, this);
   	    } else {
   	    	g.drawImage(img, 0, 0, this);
   	    }
		
		/*
   	    // color the non-load components
		if (current_component != null) {
			g.setColor(new Color(255,0,0,64));
			// toggle selected component
			if (outage_components.contains(current_component)) {
				outage_components.remove(current_component);
			} else {
				outage_components.add(current_component);
			}
			
			for (int i=0; i < outage_components.size(); i++) {
				SystemComponent sc = outage_components.get(i);
				Rectangle r = (Rectangle)sc.getRect();
				System.out.println("rect is " + r);
				g.fillRect(r.x, r.y, r.width, r.height);
			}
			if (singleSelect) {
				outage_components.remove(current_component);
			}
		}*/
   	    
   	    // color the affecte components
   	 if (current_component != null) {
			
			System.out.println("size of outage components is " + outage_components.size());
			System.out.println(outage_components.toString());
			for (int i=0; i < outage_components.size(); i++) {
				SystemComponent sc = outage_components.get(i);
				if (sc.getType() != "Load") {
					Rectangle r = (Rectangle)sc.getRect();
					//System.out.println("rect is " + r);
					g.setColor(new Color(255,0,0,64));
					g.fillRect(r.x, r.y, r.width, r.height);
				
					// now color the affected loads
					Iterator it = sc.getLoadShed().keySet().iterator();
					while(it.hasNext()) {
						String load_name = (String)it.next();
						SystemComponent load = s.getComponent(load_name);
						int amt_shed = sc.getLoadShed(load_name);
						// add this load to the load_shed map
						// so we can keep track of multiple shed on loads
						try {
							System.out.println("trying to get " + load_name);
							amt_shed = load_shed.get(load_name) + amt_shed;					
							System.out.println("load shed is " + amt_shed + " for " + load_name);
							load_shed.put(load_name, amt_shed);
						} catch (NullPointerException e) {
							System.out.println("wasn't there...so adding " + load_name + " amt " + amt_shed);							
							load_shed.put(load_name, amt_shed);
						}
						// 	draw the rectangle around the load
						Rectangle rl = (Rectangle)load.getRect();
						//System.out.println("load rect is " + rl);
						g.setColor(new Color(0, 0, 255, 64));						
						g.fillRect(rl.x, rl.y, rl.width, rl.height);
					}
				}
			}
			Iterator it2 = load_shed.keySet().iterator();
			while(it2.hasNext()) {
				String load_name = (String)it2.next();
				int amt_shed = load_shed.get(load_name);
				SystemComponent load = s.getComponent(load_name);
				Rectangle rl = (Rectangle)load.getRect();
				
				// display the load shed
				String str = Integer.toString(amt_shed) + "%";
				g.setColor(Color.black);
				g.drawChars(str.toCharArray(), 0, str.length(), rl.x, rl.y + rl.height/2);
			}

			if (singleSelect) {
				outage_components.remove(current_component);
			}
		}   	    
/*
   	    // color the load components
		if (current_load != null) {

			// toggle selected component
			if (affected_loads.contains(current_load)) {
				affected_loads.remove(current_load);
			} else {
				affected_loads.add(current_load);
			}
			
			for (int i=0; i < affected_loads.size(); i++) {
				g.setColor(new Color(0, 0, 255, 64));	
				SystemComponent sc = affected_loads.get(i);
				Rectangle r = (Rectangle)sc.getRect();
				System.out.println("load rect is " + r);
				g.fillRect(r.x, r.y, r.width, r.height);
				// display the load shed
				String str = Integer.toString(sc.getLoadShed(sc.getName())) + "%";
				g.setColor(Color.black);
				g.drawChars(str.toCharArray(), 0, str.length(), r.x, r.y + r.height/2);

			}
			if (singleSelect) {
				affected_loads.remove(current_load);
			}
		}
*/

		
	} // paintComponent

	public void clear() {
		//rects = new Vector<Rectangle>();
		//load_rects = new Vector<Rectangle>();
		//current_rect = null;
		//affected_loads = new Vector<SystemComponent>();
		outage_components = new Vector<SystemComponent>();
		//current_load = null;
		current_component = null;
		load_shed = new HashMap();
		repaint();
	}
	public void mousePressed(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
        Iterator it = components.values().iterator();
        
        // find out which component was selected
        while (it.hasNext()) {
        	SystemComponent sc = (SystemComponent)it.next();
			Rectangle rect = (Rectangle) sc.getRect();
			if (rect.contains(x, y)) {
//				 toggle selected component
				if (!outage_components.contains(sc)) {
					outage_components.add(sc);
					component = sc.getName();
					current_component = sc;
					s.setSelectedComponent(sc.getName());
				}
			//		outage_components.remove(sc);
			//		s.removeImpactedComponent(sc);
					//int size = s.getImpactedComponents().size();
					//if (size > 0) {
					//	s.setSelectedComponent(s.getImpactedComponentAt(size-1).getName());
					//}
					//else {
					//	s.setSelectedComponent(null);
					//}
					//current_component = null;
					//component = null;
			//	} else {
					outage_components.add(sc);
					component = sc.getName();
					current_component = sc;
					s.setSelectedComponent(sc.getName());
			//	}
				

				repaint();
				//System.out.println("x is " + x + " y is " + y  + " and selected " + key);
			}

        }	
        //SystemComponent sc = s.getComponent(s.selectedComponent);       
	}

	public void mouseReleased(MouseEvent e) { } // do nothing
	public void mouseClicked(MouseEvent e) { } // do nothing
	public void mouseEntered(MouseEvent e) { } // do nothing
	public void mouseExited(MouseEvent e) { } // do nothing
		
	
	public String getSelectedComponent() {
		return component;
	}
	public void setSelectedComponent(SystemComponent sc) {
System.out.println("---------------> selecting " + sc.getName() + "  " + sc.getType());		
		//Rectangle rect = (Rectangle) sc.getRect();
		//if (sc.getType().equals("Load")) {
		//	affected_loads.add(sc);
		//	//load_rects.add(rect);
		//	//current_load_rect = rect;
		//	current_load = sc;
		//} else {
		outage_components.add(sc);
		
			//rects.add(rect);
			//current_rect = rect;
		
		current_component = sc;
		//}
		repaint();
	}
	
	public static void main(String args[]){
		 File coords = new File("c:/documents and settings/lmchave/desktop/scada tool/images/IEEE RTS-96 Grid Coordinates.txt");
		 
		 CMTSystem s = new CMTSystem(null);
		 s.setImageFile("C:/Documents and Settings/lmchave/Desktop/SCADA Tool/images/IEEE RTS-96 Grid.jpg");
		 s.setComponentParametersFile(coords);
		 
		 JFrame mainFrame = new JFrame("ImageMap test");
		 mainFrame.setSize(600, 525);
		 mainFrame.setContentPane(new CMTImageMapPanel(s, null));
		 mainFrame.setVisible(true);		 
	}
}


	
	

