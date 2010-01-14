// Graphical User Interface
package gui.ahp;

//imports
import gui.examples.ConsequenceModelingExample;

import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.


import java.awt.*;
import java.awt.event.*;
//import java.util.*;
import java.io.*;


// Abstract Data Type

import adt.ahp.Alternative;
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;


/**
 * <code>JAHP</code> the main JFrame to show and modify a Decisionnal Hierarchy
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 8, 2003 initial idea
 * @version March 26, 2003
 */
public class JAHP extends JFrame implements ActionListener{

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  JMenu mFile,mHelp; // Menus
  JMenuItem miLoad,miSave,miQuit,miAbout; //MenuItems
  File home; //home directory
  File home_icons; // directory where icons could be found
  File home_example; //directory where examples could be found 
  File default_file; //default file loaded
  File file_mail; //mail icon
  File file_edit; //edit icon
  File imageFile1; //photo author
  
    // data
  private Hierarchy h;
  private Criteria current_criteria;
  //private Alternative current_alternative;

  // Panels
  JSplitPane jsp;

  private Rightpanel rp;
  private AlternativesPanel asp;
  //private AlternativePanel ap;

  private Leftpanel lp;
  private CriteriaPanel csp;
  private CriteriumPanel cp;

  public Criteria getSelectedPath() {
	  Criteria sel = csp.getSelectedPath();
	  if (sel != null)
		  return sel;
	  else 
		  return null;
  }
  /*
   *Method to show a new Criterium
   */
  public void updateSHOWCRITERIUM(Criteria c){
//System.out.println("updateShowCriterium");	  
    current_criteria=c;
    jsp.remove(rp);
    rp.updateSHOWCRITERIUM(c);
    jsp.setRightComponent(rp);
  }

  /*
   *Method to show a new Alternative
   */
  public void updateSHOWALTERNATIVE(Alternative alt){
//System.out.println("udpateShowAlternative");	  
    //current_alternative=alt;
    jsp.remove(rp);
    rp.updateSHOWALTERNATIVE(alt);
    jsp.setRightComponent(rp);
  }

  /*
   *Method to show when a new Alternative is added
   */
  public void updateafteraddALTERNATIVE(Alternative alt){
    jsp.remove(rp);
    rp.updateafteraddALTERNATIVE(alt);
    jsp.setRightComponent(rp);
    if (current_criteria.isLl()) updateSHOWCRITERIUM(current_criteria);
    
  }

  /*
   *Method to show when a new Alternative is deleted
   */  
  public void updateafterdelALTERNATIVE(){
    jsp.remove(rp);
    rp.updateafterdelALTERNATIVE();
    jsp.setRightComponent(rp);
    if (current_criteria.isLl()) updateSHOWCRITERIUM(current_criteria);
  }

  /*
   *Method to show when a new Criterium is added
   */
  public void updateafteraddCRITERIUM(Criteria c){
    current_criteria=c;
    jsp.remove(rp);
    rp.updateafteraddCRITERIUM(c);
    jsp.setRightComponent(rp);
  }

  /*
   *Method to show when a new Criterium is deleted
   */
  public void updateafterdelCRITERIUM(){
    jsp.remove(rp);
    rp.updateafterdelCRITERIUM();
    jsp.setRightComponent(rp);
  }

  /*
   *Method to show a new Alternative
   */
  public void updateALTERNATIVE(){
    jsp.remove(lp);
    lp.updateALTERNATIVE();
    jsp.setLeftComponent(lp);
    //Systemout.println("JAHP update alt");
   
  }


  /*
   *Method to show when a  Criterium is modified
   */
  public void updateaftermodifyCRITERIUM(){
    updateSHOWCRITERIUM(current_criteria);
  }

  /*
   *Method to show when an Alternative is modified
   */
  public void updateaftermodifyALTERNATIVE(){
    if (current_criteria.isLl()) updateSHOWCRITERIUM(current_criteria);
  }


  /**
   * Creates a new  <code>JAHP</code> instance.
   * @param the Decision <code>Hierarchy</code> 
   */
  public JAHP(Hierarchy h) {
    super("Java Analytic Hierarchy Process");
    
    // window 
    addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {
	  //System.exit(0);
		((JFrame)e.getSource()).dispose();
	}
      });
	
    this.h=h;

    //current_criteria=(Criteria) (h.getCriteria()).get(0);
    current_criteria=h.getGoal();


    //File DATA
    home= new File("..");// export JAHP_PATH
    home_icons=new File(home,"icons");
    home_example=new File(home,"examples");
    default_file= new File(home_example,"essai.ahp");
    file_mail=new File(home_icons,"ComposeMail24.gif");
    file_edit=new File(home_icons,"Edit24.gif");
    imageFile1=new File(home_icons,"morge.png");

    // Make Menu
    JMenuBar menuBar = new JMenuBar();
    // File Menum
    mFile = new JMenu("File");
    miLoad = new JMenuItem("Load...  ");
    miLoad.addActionListener(this);
    mFile.add(miLoad);
    miSave = new JMenuItem("Save...  ");
    miSave.addActionListener(this);
    mFile.add(miSave);
    miQuit = new JMenuItem("Quit     ");
    miQuit.addActionListener(this);
    mFile.add(miQuit);
    menuBar.add(mFile);
    // Help Menu
    mHelp = new JMenu("Help");
    miAbout = new JMenuItem("About...  ");
    miAbout.addActionListener(this);
    mHelp.add(miAbout);
    menuBar.add(mHelp);
    this.setJMenuBar(menuBar);


    Criteria crit = h.getGoal();
    Criteria c = crit;
    // get the first alternative
    while (c.getAlternatives() == null) {
    	c = c.getSubCriteriaAt(0);
    }
    //System.out.println("crit " + crit.print());
    //Vector alts = c.getAlternatives();
    //Alternative alt = (Alternative) alts.get(0);
    this.asp=new AlternativesPanel(h,this);
    //this.ap=new AlternativePanel(crit,alt,this, asp);
    this.cp=new CriteriumPanel(h.getGoal(),h,this);
    this.rp=new Rightpanel(h,cp,asp,this);
    
    this.csp=new CriteriaPanel(h,this);
    this.lp=new Leftpanel(h,csp,asp,this);    

    // construct the SpliPane = ContentPan        
    jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,lp,rp);

    jsp.setOneTouchExpandable(true);
    jsp.setDividerLocation(150);

    setContentPane(jsp);
  }


  /**
   * Describe <code>getPreferredSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getPreferredSize(){
    return new Dimension(500,650);  
  }

  /**
   * Describe <code>getMinimumSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getMinimumSize(){
    return new Dimension(400,550);  
  }


  /**
   * <code>actionPerformed</code> method to handle an action event.
   *
   * @param event an <code>ActionEvent</code> value
   */
  public void actionPerformed(ActionEvent event){
    Object source = event.getSource();
    if (source == miLoad) { //LOAD
      load();
    } else if (source == miSave) {//SAVE
      save();
    } else if (source == miQuit) {//QUIT
      //System.exit(0);
    	this.dispose();
    } else if (source == miAbout) {//ABOUT
      ShowAbout();
    }
  }


  /**
   * <code>ShowAbout</code> method to show a dialog frame (About...).
   *
   */
  void ShowAbout() {
    //(new About(this,file_mail,imageFile1)).setVisible(true);
  }


  /**
   * <code>save</code> method to show a save dialog frame.
   *
   */
  void save() {
	// NOTE:  JFileChooser was too slow, so switched to a
	// FileDialog

	FileDialog f = new FileDialog(this, "Save", FileDialog.SAVE);
    // try to add a filter
    //f.setFilenameFilter(new AHPFilter());
    f.setDirectory(home.getPath());
    f.setVisible(true);                        // Display dialog and wait for response

    if (f.getFile() != null) {
    	this.save(new File(f.getDirectory() + "/" + f.getFile()));
    }
    f.dispose();                     // Get rid of the dialog box	
  }

  /**
   * <code>load</code> method to show a load dialog frame.
   *
   */
  void load() {
		FileDialog f = new FileDialog(this, "Load", FileDialog.LOAD);
	    // try to add a filter
	    //f.setFilenameFilter(new AHPFilter());
	    f.setDirectory(home.getPath());
	    f.setVisible(true);                        // Display dialog and wait for response

	    if (f.getFile() != null) {
	    	this.load(new File(f.getDirectory() + "/" + f.getFile()));
	    }
	    f.dispose();                     // Get rid of the dialog box	
  }


  /**
   * Describe <code>load</code> method to load a new node
   *
   * @param f a <code>java.io.File</code> value
   */
  public void load(java.io.File f){
    
    try{
      FileInputStream fis =new FileInputStream(f);
      ObjectInputStream o = new ObjectInputStream(fis);	
      this.h = (Hierarchy)o.readObject();
      o.close();
      fis.close();
    } catch (EOFException eofe) {
    } catch (IOException ioe) {
      System.err.println(ioe);
    } catch (ClassNotFoundException cnfe) {
      System.err.println(cnfe);
    }


    jsp.remove(lp);
    jsp.remove(rp);

    //current_alternative=(Alternative)h.getAlternatives().get(0);
    current_criteria=h.getGoal();
    Criteria c = current_criteria;
    while (c.getAlternatives() == null) {
    	c = c.getSubCriteriaAt(0);
    }
    //this.ap=new AlternativePanel(current_criteria,(Alternative) (Alternative) (c.getAlternatives()).get(0),this);
    this.cp=new CriteriumPanel(h.getGoal(),h,this);
    //this.rp=new Rightpanel(h,cp,ap,this);
    this.asp=new AlternativesPanel(h,this);
    this.csp=new CriteriaPanel(h,this);
    this.lp=new Leftpanel(h,csp,asp,this);    

    // construct the SpliPane = ContentPan        
    jsp.setLeftComponent(lp);
    jsp.setRightComponent(rp);
    
    
  }


  /**
   *  <code>save</code> method to save a hierarchy
   *
   * @param f a <code>java.io.File</code> value
   */
  public void save(java.io.File f){
    try{
      FileOutputStream fos = new FileOutputStream(f); 
      ObjectOutputStream o = new ObjectOutputStream(fos);
      o.writeObject(this.h);
      o.close();
      fos.close();
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  }


  /**
   * <code>main</code> method to test this class.
   * @param Criteria :  command line
   * 
   */
  public static void main(String[] args) {
    // create a frame
    //AHPExample test=new AHPExample();
	ConsequenceModelingExample test = new ConsequenceModelingExample();
    Hierarchy h =new Hierarchy();
    h=test.getHierarchyExample();
    JAHP mainFrame = new JAHP(h);
    mainFrame.pack();
    mainFrame.setVisible(true);
  }
}
