// Graphical User Interface
package gui.ahp;

import gui.examples.AHPExample;

//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;

//import adt.ahp.Alternative;
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;

//import java.beans.*; //property change stuff
//import java.util.Vector;
import java.awt.*;
import java.awt.event.*;



//import javax.swing.JOptionPane;
//import javax.swing.JDialog;
import javax.swing.JButton;
//import javax.swing.JRadioButton;
//import javax.swing.ButtonGroup;
import javax.swing.JLabel;
//import javax.swing.ImageIcon;
//import javax.swing.BoxLayout;
//import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.*;
//import java.beans.*; //Property change stuff
//import java.awt.*;
//import java.awt.event.*;


public class AddConstructedScaleDialog extends JDialog implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfname;
	private JEditorPane epdesc;
	private JButton bOK, bCancel;
	private String constructedScale;
	private String description;
	
    /** Creates the GUI shown inside the frame's content pane. */
    public AddConstructedScaleDialog() {
        //super(new BorderLayout());
               
        JPanel p = new JPanel(new BorderLayout());
        
        //Create the components.
        JPanel pname = new JPanel(new BorderLayout());
        JLabel lname = new JLabel("Constructed Scale");
        tfname = new JTextField(20);
        tfname.setBorder(BorderFactory.createBevelBorder(1));
        
        pname.add(lname, BorderLayout.NORTH);
        pname.add(tfname, BorderLayout.CENTER);
        
        JPanel pdesc = new JPanel(new BorderLayout());
        JLabel ldesc = new JLabel("Descripton");
        epdesc = new JEditorPane();
        
//      Put the editor pane in a scroll pane.
        JScrollPane editorScrollPane = new JScrollPane(epdesc);
        editorScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(250, 50));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
        editorScrollPane.setBorder(BorderFactory.createBevelBorder(1));
     
        
        pdesc.add(ldesc, BorderLayout.NORTH);
        pdesc.add(editorScrollPane, BorderLayout.CENTER);

        //Lay them out.
        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        pname.setBorder(padding);
        pdesc.setBorder(padding);
        
        JPanel pbuttons = new JPanel(new FlowLayout());
        bOK = new JButton("OK");
        bCancel = new JButton("Cancel");
        
        pbuttons.add(bOK);
        pbuttons.add(bCancel);
        
        bOK.addActionListener(this);
        bCancel.addActionListener(this);
        
        p.add(pname, BorderLayout.NORTH);
        p.add(pdesc, BorderLayout.CENTER);     
        p.add(pbuttons, BorderLayout.SOUTH);
        
        this.setContentPane(p);
        
        this.setTitle("New Constructed Scale");
        this.setAlwaysOnTop(true);
        this.setModal(true);
    }


    /**
     * <code>actionPerformed</code> method invoked when a button is clicked
     *
     * @param e a <code>ActionEvent</code> value
     */
    public void actionPerformed(ActionEvent e){
      if (e.getSource()==bOK){
        //System.out.println("constructed scale is " + tfname.getText());
        //System.out.println("description is " + epdesc.getText());
        constructedScale = tfname.getText();
        description = epdesc.getText();
      }
      this.setVisible(false);
      this.dispose();
     
    }
    
    public String getConstructedScale() {
    	return constructedScale;
    }
    public String getDescription() {
    	return description;
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
    	        
        //Create and set up the content pane.
        AddConstructedScaleDialog newDialog = new AddConstructedScaleDialog();
        newDialog.pack();
        newDialog.setVisible(true);
        

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        AHPExample test=new AHPExample();
        Hierarchy h=test.getHierarchyExample();
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}