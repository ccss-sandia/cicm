// Graphical User Interface
package gui.cmt;

//imports
import gui.ahp.JAHP;
import gui.examples.ConsequenceModelingExample;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

import adt.cmt.*;
import gui.examples.*;

import javax.swing.*;          
import javax.swing.border.*;
//This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

// Abstract Data Type
//import adt.ahp.Alternative;
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;



/**
 * <code>CMT</code> A Consequence Modeling Tool
 * @author  Lozanne  Chavez 
 * @version February 2008
 */
public class CMT extends JFrame implements ActionListener, MouseListener {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  JMenu mFile,mSystem,mHelp; // Menus
  JMenuItem miNew, miLoad,miSave,miQuit,miAbout, miAHP, miClear; //MenuItems
  File home_dir; //home directory
  
  // data
  private Hierarchy h;
  private CMTSystem s;
  private Criteria current_criteria;
  
  // Panels
  public JTabbedPane tp;
  //public CMTSystemHealthPanel health;
  public CMTImageMapPanel map;
  public CMTStatusPanel status;
  public CMTComponentPanel cp;
  public CMTLoadShedPanel lsp;
  public CMTComponentRankingsPanel crp;
  /**
   * Create a new  <code>CMT</code> instance.
   * @param the Value Tree 
   */
  public CMT(Hierarchy h, CMTSystem s) {
    super("Consequence Modeling Tool");
    
    this.setLayout(new BorderLayout());
    
    // window 
    addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {
	  System.exit(0);
	}
      });
	
    this.h=h;
    this.s=s;

    current_criteria = h.getGoal();

    //File DATA
    home_dir = new File("..");// export JAHP_PATH
    
    // Make Menu
    JMenuBar menuBar = new JMenuBar();
    // File Menum
    mFile = new JMenu("File");
    miNew = new JMenuItem("New...  ");
    miNew.addActionListener(this);
    miNew.setEnabled(false);
    mFile.add(miNew);
    miLoad = new JMenuItem("Load...  ");
    miLoad.addActionListener(this);
    mFile.add(miLoad);
    miSave = new JMenuItem("Save...  ");
    miSave.addActionListener(this);
    mFile.add(miSave);
    miClear = new JMenuItem("Clear...  ");
    miClear.addActionListener(this);
    mFile.add(miClear);
    miQuit = new JMenuItem("Quit     ");
    miQuit.addActionListener(this);
    mFile.add(miQuit);
    menuBar.add(mFile);
    
    // System menu
    mSystem = new JMenu("System");
    miAHP = new JMenuItem("AHP");
    miAHP.addActionListener(this);
    mSystem.add(miAHP);
    menuBar.add(mSystem);
  
    
    // Help Menu
    mHelp = new JMenu("Help");
    miAbout = new JMenuItem("About...  ");
    miAbout.addActionListener(this);
    miAbout.setEnabled(false);
    mHelp.add(miAbout);
    menuBar.add(mHelp);
    this.setJMenuBar(menuBar);

    
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel eastPanel = new JPanel(new BorderLayout());
    centerPanel.setPreferredSize(new Dimension(620,950));
    this.add(centerPanel, BorderLayout.WEST);
    this.add(eastPanel, BorderLayout.CENTER);
    
    JPanel mapBorder = new JPanel(new BorderLayout());
    
   Border etchedBdr = BorderFactory.createEtchedBorder();
   Border titledBdr = BorderFactory.createTitledBorder(etchedBdr, " System Diagram ");
   Border emptyBdr  = BorderFactory.createEmptyBorder(5,5,5,5);
   Border compoundBdr=BorderFactory.createCompoundBorder(emptyBdr,titledBdr);
   Border compoundBdr2=BorderFactory.createCompoundBorder(compoundBdr, emptyBdr);
   mapBorder.setBorder(compoundBdr2);

    this.map = new CMTImageMapPanel(s, this);
    map.setSize(600,520);
    map.addMouseListener(this);
    mapBorder.add(map, BorderLayout.CENTER);
    
    this.status = new CMTStatusPanel(this, s, "System");

    //status.setSize(450,520);
    status.addMouseListener(this);
      
    centerPanel.add(mapBorder, BorderLayout.CENTER);
    
   
    //this.add(map, BorderLayout.CENTER);
    
    //this.add(status, BorderLayout.EAST);
    cp = new CMTComponentPanel(s, this, s.getComponent(s.getSelectedComponent()));
    //this.add(cp, BorderLayout.EAST);
    
    tp = new JTabbedPane();
    tp.setSize(450,320);
  
    //tp.addTab("Overview", null, status,
    //                  "Overview of system status...");
    //tp.setMnemonicAt(0, KeyEvent.VK_1);
    
    JScrollPane sp = new JScrollPane(cp);
    //sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    
    tp.addTab("Component", null, sp,
                      "System component information...");
    tp.setMnemonicAt(0, KeyEvent.VK_1);
    
    
    
    crp = new CMTComponentRankingsPanel(s, this);
    JScrollPane rankingSP = new JScrollPane(crp);
    rankingSP.setPreferredSize(new Dimension(500,500));
    tp.addTab("Component Rankings", null, rankingSP, "View the component rankings...");
    tp.addTab("System Health Levels", null, 
    		new CMTSystemHealthLevelsPanel(s.getSystemHealthLevels()), "View the system health levels...");
    tp.addTab("Performance Levels", null,
    		new CMTPerformanceLevelsPanel(s.getPerformanceLevels()), "View the performance levels...");
    tp.addTab("Constructed Scales", null,
    		new CMTConstructedScalesPanel(s.getConstructedScales()), "View the constructed sclales...");
    tp.setEnabledAt(2, false);
    tp.setEnabledAt(3, false);
    tp.setEnabledAt(4, false);
    
    
    //eastPanel.setSize(450,520);
    eastPanel.add(tp, BorderLayout.CENTER);
    //p.add(status, BorderLayout.NORTH);
    centerPanel.add(status, BorderLayout.NORTH);
//    setContentPane(jsp);
  }
  
  public void updateHealth() {
	  status.updateHealth(s.getSystemPI(), s.getSystemThreatLevel());
  }
  
	public void mousePressed(MouseEvent e) {
	//System.out.println("-----------------------------CMT----------------------------");
		/*
		//String comp = map.getSelectedComponent();
		String comp = s.getSelectedComponent();
		System.out.println(comp + " was selected in main");
		//s.setSelectedComponent(comp);
		//status.sus.setComponent(comp);
		Vector comps = (Vector)s.getSystemComponents();
		SystemComponent sc = s.getComponent(comp);
		double weight = sc.getPI();
//		status.health.updateHealth(weight, 2);
 
 */
		//System.out.println("CMT:  Something happened...");
		// update the susceptibility
		//System.out.println("CMT:  about to set component");
		//status.sus.setComponent(s.getSelectedComponent());
		SystemComponent sc = s.getComponent(s.getSelectedComponent());
		//System.out.println("CMT:  selected component is " + sc.getName());
		if (sc != null) {
			s.setImpactedComponent(sc);
			//System.out.println("CMT:  about to set component again");
			//cp.setComponent(sc);
				

	  		// update the system health displays
	  		//this.status.updateHealth(s.getSystemPI(), s.getSystemThreatLevel());
	  		
	        
	  	     // update the component rankings panel
	  	     // crp.update();
			

    		//window.status.b.repaint();
			//updateHealth();
		}
		updateHealth();
		crp.update();
	}

	public void mouseReleased(MouseEvent e) { } // do nothing
	public void mouseClicked(MouseEvent e) { } // do nothing
	public void mouseEntered(MouseEvent e) { } // do nothing
	public void mouseExited(MouseEvent e) { } // do nothing

  /**
   * Describe <code>getPreferredSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getPreferredSize(){
    return new Dimension(800,650);  
  }

  /**
   * Describe <code>getMinimumSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getMinimumSize(){
    return new Dimension(800,550);  
  }


  /**
   * <code>actionPerformed</code> method to handle an action event.
   *
   * @param event an <code>ActionEvent</code> value
   */
  public void actionPerformed(ActionEvent event){
    Object source = event.getSource();
    if (source == miLoad) { //LOAD
      loadScenario();
    } else if (source == miSave) {//SAVE
      save();
    } else if (source == miQuit) {//QUIT
      System.exit(0);
    } else if (source == miAbout) {//ABOUT
      ShowAbout();
    } else if (source == miAHP) { //AHP
      ShowAHP();
    } else if (source == miClear) { // clear
    	clearAll();
    }
  }


  /**
   * <code>ShowAbout</code> method to show a dialog frame (About...).
   *
   */
  void ShowAbout() {
  //  (new About(this,file_mail,imageFile1)).setVisible(true);
  }
  
  void ShowAHP() {
		//ConsequenceModelingExample test = new ConsequenceModelingExample();
	    //Hierarchy h =new Hierarchy();
	    //h=test.getHierarchyExample();
	    JAHP mainFrame = new JAHP(h);
	    mainFrame.pack();
	    mainFrame.setVisible(true);	  
	    
	    // window 
	    mainFrame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		  // update the system
	      double pi = s.getSystemPI() * 100;
	      int p = (int)pi;
	      String comp = s.getSelectedComponent();
	      SystemComponent sc = (SystemComponent)s.getSystemComponents().get(comp);
	      cp.comphealth.updateHealth(sc.getPI(), sc.getThreatLevel(), s.getLostRevenue(sc));
	      updateHealth();
	      status.getBarChart().update(s.selectedComponent, p, status.health.getColor());
		}
	      });
			    
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
    f.setDirectory(home_dir.getPath());
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
	    f.setDirectory(home_dir.getPath());
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
    //System.out.println("file is " + f.getName());
    try{
      FileInputStream fis =new FileInputStream(f);
      ObjectInputStream o = new ObjectInputStream(fis);	
      this.s = (CMTSystem)o.readObject();
      o.close();
      fis.close();
    } catch (EOFException eofe) {
    } catch (IOException ioe) {
      System.err.println(ioe);
    } catch (ClassNotFoundException cnfe) {
      System.err.println(cnfe);
    }


    //jsp.remove(lp);
    //jsp.remove(rp);

    //current_alternative=(Alternative)h.getAlternatives().get(0);
    current_criteria=h.getGoal();
    Criteria c = current_criteria;
    while (c.getAlternatives() == null) {
    	c = c.getSubCriteriaAt(0);
    }
    this.map=new CMTImageMapPanel(s, this);    

    // construct the SplitPane = ContentPan        
    //jsp.setLeftComponent(lp);
    //jsp.setRightComponent(rp);
  }


  /**
   *  <code>save</code> method to save a hierarchy
   *
   * @param f a <code>java.io.File</code> value
   */
  public void save(java.io.File f){
    try{
    	PrintWriter out = new PrintWriter(new FileOutputStream(f));
    	out.println("<outages>");
    	for (int i=0; i < s.getImpactedComponents().size(); i++) {
    		SystemComponent sc = s.getImpactedComponentAt(i);
    		out.println("   <outage>");
    		out.println("      <component>" + sc.getName() + "</component>");
    		Iterator it = sc.getLoadShed().keySet().iterator();
    		while (it.hasNext()) {
    			String comp = (String)it.next();
    			int val = sc.getLoadShed(comp);
    			out.println("      <loadShed>");
    			out.println("         <component>" + comp + "</component>");
    			out.println("         <percentshed>" + val + "</percentshed>");
    			out.println("      </loadShed>");
    		}
    		out.println("   </outage>");
    	}
    	out.println("</outages>");
    	out.close();
      //FileOutputStream fos = new FileOutputStream(f); 
      //ObjectOutputStream o = new ObjectOutputStream(fos);
      //o.writeObject(this.s);
      //o.close();
      //fos.close();
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  }
  
  public void loadScenario() {
		FileDialog f = new FileDialog(this, "Load Scenario", FileDialog.LOAD);
	    //f.setDirectory(home_dir.getPath());
	    f.setVisible(true);                        // Display dialog and wait for response

	    if (f.getFile() != null) {
	    	this.loadScenario(new File(f.getDirectory() + "/" + f.getFile()));
	    }
	    f.dispose();    
  }
  public void clearAll() {
	  	s.selectedComponent = null;  
	  	s.setImpactedComponents(new Vector());
	    map.clear();
	    status.getBarChart().clear();
	    status.health.clear();
	    cp.clear();
  }
  public void loadScenario(java.io.File f) {
	  	clearAll();
	    //System.out.println("opening file " + f.getAbsolutePath());
	    try {
          DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
          Document doc = docBuilder.parse(f);

          // normalize text representation            doc.getDocumentElement ().normalize ();
          //System.out.println ("Root element of the doc is " + 
             //  doc.getDocumentElement().getNodeName());

          	
          NodeList listOfOutages = doc.getElementsByTagName("outage");
          int totalOutages = listOfOutages.getLength();
          //System.out.println("Total no of outages : " + totalOutages);

          for(int s=0; s<listOfOutages.getLength() ; s++){
//System.out.println("reading in outage " + s);          
        	  Node firstComponentNode = listOfOutages.item(s);
              if(firstComponentNode.getNodeType() == Node.ELEMENT_NODE){

                  Element firstComponentElement = (Element)firstComponentNode;

                  NodeList nameList = firstComponentElement.getElementsByTagName("component");
                  Element nameElement = (Element)nameList.item(0);
                  
                  NodeList textNameList = nameElement.getChildNodes();
                  String disrupted_comp = ((Node)textNameList.item(0)).getNodeValue().trim();
                  //System.out.println("Component : " + disrupted_comp);

                  SystemComponent sc = this.s.getComponent(disrupted_comp);
                  this.s.setImpactedComponent(sc);
                                    
                  NodeList loadShedList = firstComponentElement.getElementsByTagName("loadShed");
                  int totalAffectedComponents = loadShedList.getLength();
                  //System.out.println("Total number of affected components: " + totalAffectedComponents);
                   for (int i=0; i < loadShedList.getLength(); i++) {
                	   Node firstAffectedNode = loadShedList.item(i);
                	   if (firstAffectedNode.getNodeType() == Node.ELEMENT_NODE) {
                		   Element firstAffectedElement = (Element)firstAffectedNode;
                		   
                		   NodeList compList = firstAffectedElement.getElementsByTagName("component");
                		   Element compElement = (Element)compList.item(0);
                		   
                		   NodeList textCompList = compElement.getChildNodes();
                		   String comp = ((Node)textCompList.item(0)).getNodeValue().trim();
                		   //System.out.println("     Component : " + comp);            		   
                		   
                		   NodeList percentshedList = firstAffectedElement.getElementsByTagName("percentshed");
                		   Element percentshedElement = (Element)percentshedList.item(0);
                		   
                		   NodeList textpercentshedList = percentshedElement.getChildNodes();
                		   String percentshed = ((Node)textpercentshedList.item(0)).getNodeValue().trim();
                		   //System.out.println("     Percent Shed : " + percentshed);

                		   sc.setLoadShed(comp, Integer.parseInt(percentshed));
                	   }
                	   
                   }
                   this.s.setNumberCommercialCustomersAffected();
                   this.s.setNumberResidentialCustomersAffected();
                   this.s.setNumberSmIndustrialCustomersAffected();
                   this.s.setNumberLgIndustrialCustomersAffected();
                   
                   this.s.setSelectedComponent(sc.getName());
                   
                   // update the image map
                   //System.out.println("--------------------------");
                   this.map.setSelectedComponent(sc);
              }
          }

      }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
           + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

      }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

      }catch (Throwable t) {
        t.printStackTrace ();
      }
      
     // update the component rankings panel
      crp.update();
  }


  /**
   * <code>main</code> method to test this class.
   * @param Criteria :  command line
   * 
   */
  public static void main(String[] args) {
    ConsequenceModelingExample test = new ConsequenceModelingExample();
    Hierarchy h =new Hierarchy();
    h=test.getHierarchyExample();
    
	CMTSystem m = new CMTSystem(h);
    
	m.setImageFile("images/IEEE RTS-96 Grid.jpg");
	m.setComponentParametersFile(new File("images/IEEE RTS-96 Grid.xml"));
       
    SystemLevels lvls = new SystemLevels();
    m.setPerformanceLevels(lvls.getPerformanceLevels());
    m.setSystemHealthLevels(lvls.getSystemHealthLevels());
    
    // create a frame
    CMT mainFrame = new CMT(h, m);
    mainFrame.setSize(1500,910);
    //mainFrame.pack();
    mainFrame.setVisible(true);
  }
}
