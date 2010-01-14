package gui.cmt;

import javax.swing.JDialog;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.*;


public class CMTAddCustomerGroupDialog extends JDialog implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfname;
	private JTextField tfrate;
	private JTextField tfusage;
	private JButton bOK, bCancel;
	private String name;
	private double rate;
	private double usage;
	
    /** Creates the GUI shown inside the frame's content pane. */
    public CMTAddCustomerGroupDialog() {
        JPanel mainPanel = new JPanel(new BorderLayout());
    	JPanel p = new JPanel(new GridLayout(3,2));
        
        //Create the components.
        JLabel lname = new JLabel("Customer Group");
        tfname = new JTextField(20);
        tfname.setBorder(BorderFactory.createBevelBorder(1));
        
        JLabel lrate = new JLabel("Rate");
        tfrate = new JTextField(10);
        tfrate.setBorder(BorderFactory.createBevelBorder(1));

        JLabel lusage = new JLabel("Usage");
        tfusage = new JTextField(10);
        tfusage.setBorder(BorderFactory.createBevelBorder(1));
        
        p.add(lname);
        p.add(tfname);
        p.add(lrate);
        p.add(tfrate);
        p.add(lusage);
        p.add(tfusage);
        
        //Lay them out.
        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        p.setBorder(padding);
        p.setBorder(padding);
        
        JPanel pbuttons = new JPanel(new FlowLayout());
        bOK = new JButton("OK");
        bCancel = new JButton("Cancel");
        
        pbuttons.add(bOK);
        pbuttons.add(bCancel);
        
        bOK.addActionListener(this);
        bCancel.addActionListener(this);
        
        mainPanel.add(p, BorderLayout.NORTH);    
        mainPanel.add(pbuttons, BorderLayout.SOUTH);
        
        this.setContentPane(mainPanel);
        
        this.setTitle("New Customer Group");
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
        //System.out.println("name is " + tfname.getText());
        //System.out.println("rate is " + tfrate.getText());
        //System.out.println("usage is " + tfusage.getText());        
        name = tfname.getText();
        rate = Double.parseDouble(tfrate.getText());
        usage = Double.parseDouble(tfrate.getText());
      }
      this.setVisible(false);
      this.dispose();
     
    }
    
    public String getName() {
    	return name;
    }
    public double getRate() {
    	return rate;
    }
    public double getUsage() {
    	return usage;
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
    	        
        //Create and set up the content pane.
        CMTAddCustomerGroupDialog newDialog = new CMTAddCustomerGroupDialog();
        newDialog.pack();
        newDialog.setVisible(true);
        

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}