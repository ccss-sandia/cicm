// Graphical User Interface
package gui.ahp;

//imports
import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.*;
import java.io.*;



/**
 * Dialog Window to know some information about authors..
 * @author Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 */
public class About extends JDialog {


  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * Constructor
   * @param frame the main one
   * @param tree to modify
   * @param file_mail which contain mail icon
   * @param imageFile1 author1
   */
  public About(JFrame frame,File file_mail,File imageFile1) {
		super(frame, "About..", true);

		//Toolkit toolkit = Toolkit.getDefaultToolkit();
		//Image image1 = toolkit.getImage(image1ageFile1.toString());

		//System.err.println(file_mail.toString());
		ImageIcon icon = new ImageIcon(file_mail.toString());

		
		JPanel panel = new JPanel(new BorderLayout(20,20));
		//System.err.println(imageFile1.toString());
		ImageIcon photo =  new ImageIcon(imageFile1.toString());

		JLabel photol= new JLabel(photo);
		//JPanel label =  new JPanel(new BorderLayout(20,20));
		JLabel label2 = new JLabel("Based off the JAHP application written by Maxime MORGE  morge@emse.fr",icon, JLabel.CENTER);

		//label1.setBorder(new EmptyBorder(5,5,5,5));


		panel.add(photol,BorderLayout.NORTH);
		panel.add(label2,BorderLayout.CENTER);

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ferme();
			}
		});
		panel.add(ok,BorderLayout.SOUTH);
		this.setContentPane(panel);
		this.pack();
	}

  /**
   * Method to close the Window 
   * @param Graphics g
   */
  void ferme() {
    this.setVisible(false);
  }
}
/**
 * ImagePanel Class to show picture.
 * @author Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 */
class ImagePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image image1;
    Image image2;

    public ImagePanel(Image image1,Image image2) {
        this.image1 = image1;
	this.image2 = image2;
    }

  /**
   * Method to paint the Component 
   * @param Graphics g
   */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background

        //Draw image at its natural size first.
        g.drawImage(image1, 10, 0, 110,140,this); //85x62 image
	g.drawImage(image2, 180, 0, 110,140,this); //85x62 image
        //Now draw the image scaled.
        //g.drawImage(image, 90, 0, 300, 62, this);
    }

  /**
   * Method to overload container method about size 
   * @see Container
   * @return dimension
   */
  public Dimension getMaximumSize(){
    return (new Dimension(320,140));
  }

  /**
   * Method to overload container method about size 
   * @see Container
   * @return dimension
   */    
  public Dimension getMinimumSize(){
    return (new Dimension(320,140));
  }

  /**
   * Method to overload container method about size 
   * @see Container
   * @return dimension
   */
  public Dimension getPreferredSize(){
    return (new Dimension(320,140));
  }



}




