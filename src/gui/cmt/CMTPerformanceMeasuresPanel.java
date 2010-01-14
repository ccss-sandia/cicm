package gui.cmt;

import gui.examples.ConsequenceModelingExample;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
//import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.*;

import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
import javax.swing.table.*;
//import javax.swing.border.*;
import javax.swing.table.TableModel;
import javax.swing.event.*;

import adt.ahp.*;
import adt.cmt.*;


public class CMTPerformanceMeasuresPanel extends JPanel implements TableModelListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean DEBUG = false;
    public double pi;    
    //private Vector cs;
    TableModel model;
    CMTSystem m;
    CMT window;
    MyTableModel tm;
    
    public CMTPerformanceMeasuresPanel(CMT window, CMTSystem s) {
        super(new GridLayout(1,0));
//System.out.println("CMTPerformanceMeasuresPanel........" + s.getSelectedComponent());        
        this.m = s;
        this.window = window;
        
        tm = new MyTableModel(m);
        JTableX table = new JTableX(tm);
        table.setPreferredScrollableViewportSize(new Dimension(500, 130));
        this.model = table.getModel();
        
        model.addTableModelListener(this);
        //table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Set up column sizes.
        initColumnSizes(table);    
    
        //this.cs = tm.cs;
        
        //add a selection box column's cell editors/renderers.
        setLevelColumn(table, tm.cs);

        //Add the scroll pane to this panel.
        add(scrollPane);
        
        setDefaultValues();
    }
    
    
    public void setDefaultValues() {
    	SystemComponent s = m.getComponent(m.getSelectedComponent());
    	
    	int lostRevenue = s.getLostRevenue();
    	int repairCost = s.getRepairCost();
    	int duration = s.getTime();
    	int numResidential = s.getNumResidentialAffected();
    	int numCommercial = s.getNumCommericalAffected();
    	int numSmIndustrial = s.getNumSmallMedIndustrialAffected();
    	int numLgIndustrial = s.getNumLargeIndustrialAffected();
    	int utilityWorkerLevel = s.getUtilityWorkerLevel();
    	int faunaLevel = s.getFaunaLevel();
    	try {
		  	Vector cs = s.getConstructedScales();
			    		
	//	  	System.out.println("CMTPerformanceMeasuresPanel selected component: " + s.getName());    	
		  	if (s != null) {
		  		Object[][] m = s.getPerformanceMeasures();
		  		for (int i=0; i < m.length; i++) {
		//  			System.out.println("value is...." + m[i][0] + "..............." + m[i][1]);
		  			String val = "";
		  			if (((String)m[i][0]).equals("Economics - Lost Revenue")) {
		  				val = CMTElectricPowerSystem.getEconomicsLostRevenue(lostRevenue);
		  			} else if (((String)m[i][0]).equals("Economics - Repair/Replace")) {
	    				val = CMTElectricPowerSystem.getEconomicsRepairReplace(repairCost);
		    		} else if (((String)m[i][0]).equals("Image - Public")) {
		    			val = CMTElectricPowerSystem.getImagePublic((Vector)cs.get(i), duration, numResidential, numCommercial, numSmIndustrial, numLgIndustrial);		    			
		    		} else if (((String)m[i][0]).equals("Image - Political")) {	    				
	    				val = CMTElectricPowerSystem.getImagePolitical((Vector)cs.get(i), duration, numResidential, numCommercial, numSmIndustrial, numLgIndustrial);	
		    		} else if (((String)m[i][0]).equals("Image - Customer")) {
		    			val = CMTElectricPowerSystem.getImageCustomer((Vector)cs.get(i), duration, numResidential, numCommercial, numSmIndustrial, numLgIndustrial);
		    		} else if (((String)m[i][0]).equals("Health & Safety - General Public")) {
		    			val = CMTElectricPowerSystem.getHealthSafetyGeneralPublic((Vector)cs.get(i), duration, numResidential, numCommercial, numSmIndustrial, numLgIndustrial);		    					
		    		} else if (((String)m[i][0]).equals("Health & Safety - Utility Workers")) {
		    			val = CMTElectricPowerSystem.getHealthSafetyUtilityWorkers((Vector)cs.get(i), utilityWorkerLevel);		    					
		    		} else if (((String)m[i][0]).equals("Environment - Fauna")) {
		    			val = CMTElectricPowerSystem.getEnvironmentFauna((Vector)cs.get(i), faunaLevel);
		    		} else {				
		    			val = (String)m[i][1];
		    		}
		  			m[i][1] = val;
		  			tm.setValueAt(val, i, 1);
		    	}
		  		s.setPerformanceMeasures(m);
		  	} 
		  	
		} catch (NullPointerException e) {}
    }

    public void tableChanged(TableModelEvent e) {
    	int row = e.getFirstRow();
    	int column = e.getColumn();
    	TableModel model = (TableModel)e.getSource();
    	//String colName = model.getColumnName(column);
    	Object data = model.getValueAt(row, column);

    	//System.out.println("Selected component is ... " + m.selectedComponent);
    	SystemComponent s = m.getComponent(m.selectedComponent);
    	if (s != null) {
    		s.setPerformanceMeasureAt(row, data.toString());
    		
    		window.updateHealth();
    		double pi = m.getSystemPI() * 100;
    		int p = (int)pi;
    		
    		window.cp.comphealth.updateHealth(s.getPI(), s.getThreatLevel(), m.getLostRevenue(s));
    		window.status.getBarChart().update(m.selectedComponent, p, window.cp.comphealth.getColor());
    	}
    	//System.out.println("row is " + row + " and col is " + column + " and data is " + s.getPerformanceMeasureAt(row));
    	
    }
    
    /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumnSizes(JTableX table) {
        MyTableModel model = (MyTableModel)table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        //Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < 2; i++) {
            column = table.getColumnModel().getColumn(i);

            comp = headerRenderer.getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
            	getTableCellRendererComponent(table, "true", false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;

            if (DEBUG) {
                System.out.println("Initializing width of column "
                                   + i + ". "
                                   + "headerWidth = " + headerWidth
                                   + "; cellWidth = " + cellWidth);
            }

            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }

    public void setLevelColumn(JTableX table, Vector alts) {    	
    	RowEditorModel rm = new RowEditorModel();
    	table.setRowEditorModel(rm);
    	
    	for (int i=0; i < table.getRowCount(); i++) {	
            //Set up the editor for the cells.
    		JComboBox c = new JComboBox();
    		Vector a = (Vector)alts.elementAt(i);
    		for (int j=0; j < a.size(); j++) {
    			Alternative alt = (Alternative)a.elementAt(j);
    			c.addItem(alt.getComment());
    		}
    		//System.out.println("combo box is " + c.toString());
    		DefaultCellEditor ed = new DefaultCellEditor(c);
    		rm.addEditorForRow(i,ed);
    		
    		//System.out.println("added editor for row " + i);

    		//Set up tool tips for the sport cells.
        //    DefaultTableCellRenderer renderer =
        //            new DefaultTableCellRenderer();
        //    renderer.setToolTipText("Click for combo box");
        //    rm.setCellRenderer(renderer);
    		
    	}        	
    }
    
    class MyTableModel extends AbstractTableModel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] columnNames = {"Performance Measure", "Level"};
        
        private Object[][] perfMeasures;
        Vector cs = new Vector();

        public MyTableModel(CMTSystem m) {
        	perfMeasures = m.getPerformanceMeasures();
        	cs = m.getConstructedScales();
        	
        	//System.out.println("Performance Measures: ");
        	//for (int i=0; i < perfMeasures.length; i++) {
        	//	System.out.println(i + ": " + perfMeasures[i][0] + " scales: " + cs.get(i).toString());
        	//} 
        }

        public int getColumnCount() {
            return 2;
        }

        public int getRowCount() {
            return perfMeasures.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return perfMeasures[row][col];
        }

        /*
         * JTableX uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
        	return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            perfMeasures[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + perfMeasures[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
    
    public class RowEditorModel {
    	
    	private Hashtable<Integer, TableCellEditor> data;
    	
    	public RowEditorModel() {
    		data = new Hashtable<Integer, TableCellEditor>();
    	}
    	
    	public void addEditorForRow(int row, TableCellEditor e) {
    		data.put(new Integer(row), e);
    	}
    	
    	public void removeEditorForRow(int row) {
    		data.remove(new Integer(row));
    	}
    	
    	public TableCellEditor getEditor(int row) {
    		return (TableCellEditor)data.get(new Integer(row));
    	}
    	
    }
    
    public class JTableX extends JTable {
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected RowEditorModel rm;
    	
    	public JTableX() {
    		super();
    		rm = null;
    	}
    	
    	public JTableX(TableModel tm) {
    		super(tm);
    		rm = null;
    	}
    	
    	public JTableX(TableModel tm, TableColumnModel cm) {
    		super(tm,cm);
    		rm = null;
    	}
    	
    	public JTableX(TableModel tm, TableColumnModel cm, ListSelectionModel sm) {
    		super(tm, cm, sm);
    		rm = null;
    	}
    	
    	public JTableX(int rows, int cols) {
    		super(rows, cols);
    		rm = null;
    	}
    	
    	public JTableX(final Vector rowData, final Vector columnNames) {
    		super(rowData, columnNames);
    		rm = null;
    	}
    	
    	public JTableX(final Object[][] rowData, final Object[] colNames) {
    		super(rowData, colNames);
    		rm = null;
    	}
    	
    	// new constructor
    	public JTableX(TableModel tm, RowEditorModel rm) {
    		super(tm, null, null);
    		this.rm = rm;
    	}
    	
    	public void setRowEditorModel(RowEditorModel rm) {
    		this.rm = rm;
    	}
    	
    	public RowEditorModel getRowEditorModel() {
    		return rm;
    	}
    	
    	public TableCellEditor getCellEditor(int row, int col) {
    		TableCellEditor tmpEditor = null;
    		if(rm!=null) {
    			tmpEditor=rm.getEditor(row);
    		}
    		if (tmpEditor!=null) {
    			return tmpEditor;
    		}
    		return super.getCellEditor(row,col);
    	}
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Performance Measures");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	ConsequenceModelingExample test = new ConsequenceModelingExample();
        Hierarchy h = test.getHierarchyExample();      
        
        CMTSystem s = new CMTSystem(h);
        
        //Create and set up the content pane.
        CMTPerformanceMeasuresPanel newContentPane = new CMTPerformanceMeasuresPanel(null, s);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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

