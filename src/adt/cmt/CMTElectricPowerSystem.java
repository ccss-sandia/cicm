package adt.cmt;

import java.util.Vector;

import adt.ahp.Alternative;

public class CMTElectricPowerSystem {
	
	
	// get the constructed scale value for Economics - Lost Revenue
	public static String getEconomicsLostRevenue(int lostRevenue) {
		String val = "";
		//	**********************HARD CODED*******************************/
		if (lostRevenue < 10000 && lostRevenue >= 1000) {
			val = "Thousands of Dollars";
		} else if (lostRevenue >= 10000 && lostRevenue < 100000) {
			val = "Tens of Thousands of Dollars";
		} else if (lostRevenue >= 100000 && lostRevenue < 1000000) {
			val = "Hundreds of Thousands of Dollars";
		} else if (lostRevenue >= 1000000 && lostRevenue < 10000000) {
			val = "Millions of Dollars";
		} else if (lostRevenue >= 10000000 && lostRevenue < 100000000) {
			val = "Tens of Millions of Dollars";
		} else if (lostRevenue >= 100000000) {
			val = "Hundreds of Millions of Dollars";
		} else {
			val = "No Impact";
 		}
		return val;
	}

	// get the constructed scale value for Economics - Repair/Replace
	public static String getEconomicsRepairReplace(int repairCost) {
		String val = "";
		/*************************HARD CODED********************************/
  		if (repairCost < 10000 && repairCost >= 1000) {
  			val = "Thousands of Dollars";
  		} else if (repairCost >= 10000 && repairCost < 100000) {
			val = "Tens of Thousands of Dollars";
		} else if (repairCost >= 100000 && repairCost < 1000000) {
			val = "Hundreds of Thousands of Dollars";
		} else if (repairCost >= 1000000 && repairCost < 10000000) {
			val = "Millions of Dollars";
		} else if (repairCost >= 10000000 && repairCost < 100000000) {
			val = "Tens of Millions of Dollars";
		} else if (repairCost >= 100000000) {
			val = "Hundreds of Millions of Dollars";
		} else {
			val = "No Impact";
		}
		return val;
	}
	
	// get the constructed scale value for Image - Public
	public static String getImagePublic(Vector cs, int duration, int numResidential, int numCommercial, int numSmIndustrial, int numLgIndustrial) {
		// HARD CODED LOOKUP TABLE
		int resLevel = 0, comLevel = 0, smIndLevel = 0, lgIndLevel = 0;
		if (duration <= 10) {
  			if (numResidential >= 0 && numResidential < 500) {
  				resLevel = 0;
  			} else if (numResidential >= 500 && numResidential < 10000) {
  				resLevel = 1;
  			} else if (numResidential >= 10000 && numResidential < 1000000) {
  				resLevel = 2;
  			} else { // numResidential >= 1000000
  				resLevel = 3;
  			}

  			if (numCommercial >= 0 && numCommercial < 500){
  				comLevel = 0;
  			} else if (numCommercial >= 500 && numCommercial < 5000) {
  				comLevel = 1;
  			} else if (numCommercial >= 5000 && numCommercial < 25000) {
  				comLevel = 2;
  			} else if (numCommercial >= 25000 && numCommercial < 100000) {
  				comLevel = 3;
  			} else { // numCommercial >= 100000
  				comLevel = 4;
  			}
		    						
  			if (numSmIndustrial >= 0 && numSmIndustrial < 500){
  				smIndLevel = 0;
  			} else if (numSmIndustrial >= 500 && numSmIndustrial < 1000) {
  				smIndLevel = 1;
  			} else if (numSmIndustrial >= 1000 && numSmIndustrial < 5000) {
  				smIndLevel = 2;
  			} else if (numSmIndustrial >= 5000 && numSmIndustrial < 10000) {
  				smIndLevel = 3;
  			} else { // numSmIndustrial >= 10000
  				smIndLevel = 4;
  			}
	   						
  			if (numLgIndustrial >= 0 && numLgIndustrial < 15){
  				lgIndLevel = 0;
			} else if (numLgIndustrial >= 15 && numLgIndustrial < 50) {
				lgIndLevel = 1;
			} else if (numLgIndustrial >= 50 && numLgIndustrial < 100) {
		    	lgIndLevel = 2;
		    } else { // numLgIndustrial >= 100
		    	lgIndLevel = 3;
		    }    					
		} else if (duration > 10 && duration <= 24) {
			if (numResidential >= 0 && numResidential < 500) {
				resLevel = 0;
			} else if (numResidential >= 500 && numResidential < 10000) {
		    	resLevel = 1;
		    } else if (numResidential >= 10000 && numResidential < 500000) {
		    	resLevel = 2;
		    } else if (numResidential >= 500000 && numResidential < 1000000) {
		    	resLevel = 3;
		    } else { // numResidential >= 1000000
		    	resLevel = 4;
		    }

			if (numCommercial >= 0 && numCommercial < 500){
		    	comLevel = 0;
		    } else if (numCommercial >= 500 && numCommercial < 5000) {
		    	comLevel = 1;
		    } else if (numCommercial >= 5000 && numCommercial < 25000) {
		    	comLevel = 2;
		    } else if (numCommercial >= 25000 && numCommercial < 100000) {
		    	comLevel = 3;
		    } else { // numCommercial >= 100000
		    	comLevel = 4;
		    }
		    					
		    if (numSmIndustrial >= 0 && numSmIndustrial < 500){
		    	smIndLevel = 0;
		    } else if (numSmIndustrial >= 500 && numSmIndustrial < 1000) {
		    	smIndLevel = 1;
		    } else if (numSmIndustrial >= 1000 && numSmIndustrial < 5000) {
		    	smIndLevel = 2;
		    } else if (numSmIndustrial >= 5000 && numSmIndustrial < 10000) {
		    	smIndLevel = 3;
		    } else { // numSmIndustrial >= 10000
		    	smIndLevel = 4;
		    }
		    					
		    if (numLgIndustrial >= 0 && numLgIndustrial < 10){
		    	lgIndLevel = 0;
		    } else if (numLgIndustrial >= 10 && numLgIndustrial < 50) {
		    	lgIndLevel = 1;
		    } else if (numLgIndustrial >= 50 && numLgIndustrial < 100) {
		    	lgIndLevel = 2;
		    } else { // numLgIndustrial >= 100
		    	lgIndLevel = 3;
		    }    					
		    				
	   } else { // duration > 24
			if (numResidential >= 0 && numResidential < 100) {
				resLevel = 0;
			} else if (numResidential >= 100 && numResidential < 500) {
				resLevel = 1;
			} else if (numResidential >= 500 && numResidential < 100000) {
				resLevel = 2;
			} else if (numResidential >= 100000 && numResidential < 500000) {
				resLevel = 3;
			} else { // numResidential >= 500000
				resLevel = 4;
			}
	    					
			if (numCommercial >= 0 && numCommercial < 300){
				comLevel = 0;
			} else if (numCommercial >= 300 && numCommercial < 1000) {
		    	comLevel = 1;
		    } else if (numCommercial >= 1000 && numCommercial < 10000) {
		    	comLevel = 2;
		    } else if (numCommercial >= 10000 && numCommercial < 80000) {
		    	comLevel = 3;
		    } else { // numCommercial >= 80000
		    	comLevel = 4;
		    }
		    					
		    if (numSmIndustrial >= 0 && numSmIndustrial < 200){
		    	smIndLevel = 0;
		    } else if (numSmIndustrial >= 200 && numSmIndustrial < 500) {
		    	smIndLevel = 1;
		    } else if (numSmIndustrial >= 500 && numSmIndustrial < 1000) {
		    	smIndLevel = 2;
		    } else if (numSmIndustrial >= 1000 && numSmIndustrial < 5000) {
		    	smIndLevel = 3;
		    } else { // numSmIndustrial >= 5000
		    	smIndLevel = 4;
		    }
		    					
		    if (numLgIndustrial >= 0 && numLgIndustrial < 1){
		    	lgIndLevel = 0;
		    } else if (numLgIndustrial >= 1 && numLgIndustrial < 25) {
		    	lgIndLevel = 1;
		    } else if (numLgIndustrial >= 25 && numLgIndustrial < 70) {
		    	lgIndLevel = 2;
		    } else if (numLgIndustrial >= 70 && numLgIndustrial < 100) {
		    	lgIndLevel = 3;
		    } else { // numLgIndustrial >= 100
		    	lgIndLevel = 4;
		    }    					
		}
		    				
		// get the largest level
		int max = resLevel;
		if (comLevel > max)
			max = comLevel;
		if (smIndLevel > max)
			max = smIndLevel;
		if (lgIndLevel > max)
			max = lgIndLevel;
		    				
		int num = cs.size()-1;
		return ((Alternative)cs.get(num-max)).getComment();
	}			
		    			
	// get the constructed scale value for Image - Political
	public static String getImagePolitical(Vector cs, int duration, int numResidential, int numCommercial, int numSmIndustrial, int numLgIndustrial) {
		// HARD CODED LOOKUP TABLE
		int resLevel = 0, comLevel = 0, smIndLevel = 0, lgIndLevel = 0;
		if (duration <= 10) {
			if (numResidential >= 0 && numResidential < 500000) {
				resLevel = 0;
			} else if (numResidential >= 500000 && numResidential < 1000000) {
				resLevel = 1;
			} else { // numResidential >= 1000000
				resLevel = 2;
			}

			if (numCommercial >= 0 && numCommercial < 30000){
				comLevel = 0;
			} else  { //numCommercial >= 30000
  				comLevel = 1;
  			} 
		    						
  			if (numSmIndustrial >= 0 && numSmIndustrial < 5000){
  				smIndLevel = 0;
  			} else { // numSmIndustrial >= 5000
  				smIndLevel = 1;
  			}
		    					
  			if (numLgIndustrial >= 0 && numLgIndustrial < 35){
  				lgIndLevel = 0;
  			} else if (numLgIndustrial >= 35 && numLgIndustrial < 75) {
  				lgIndLevel = 1;
  			} else { // numLgIndustrial >= 75
  				lgIndLevel = 2;
  			}    					
  		} else if (duration > 10 && duration <= 24) {
  			if (numResidential >= 0 && numResidential < 300000) {
  				resLevel = 0;
  			} else if (numResidential >= 300000 && numResidential < 800000) {
  				resLevel = 1;
  			} else { // numResidential >= 800000
  				resLevel = 2;
  			}

  			if (numCommercial >= 0 && numCommercial < 25000){
		    	comLevel = 0;
		    } else if (numCommercial >= 25000 && numCommercial < 50000) {
		    	comLevel = 1;
		    } else { // numCommercial >= 50000
		    	comLevel = 2;
		    }
		    					
		    if (numSmIndustrial >= 0 && numSmIndustrial < 2500){
		    	smIndLevel = 0;
		    } else if (numSmIndustrial >= 2500 && numSmIndustrial < 7000) {
		    	smIndLevel = 1;
		    } else { // numSmIndustrial >= 7000
		    	smIndLevel = 2;
		    }
		    					
		    if (numLgIndustrial >= 0 && numLgIndustrial < 25){
		    	lgIndLevel = 0;
		    } else if (numLgIndustrial >= 25 && numLgIndustrial < 50) {
		    	lgIndLevel = 1;
		    } else { // numLgIndustrial >= 50
		    	lgIndLevel = 2;
		    }    					
		    					
  		} else { // duration > 24
			if (numResidential >= 0 && numResidential < 30000) {
				resLevel = 0;
			} else if (numResidential >= 30000 && numResidential < 300000) {
				resLevel = 1;
		    } else if (numResidential >= 300000 && numResidential < 1000000) {
		    	resLevel = 2;
		    } else { // numResidential >= 1000000
		    	resLevel = 3;
		    }

			if (numCommercial >= 0 && numCommercial < 3000){
				comLevel = 0;
			} else if (numCommercial >= 3000 && numCommercial < 30000) {
				comLevel = 1;
			} else if (numCommercial >= 30000 && numCommercial < 100000) {
				comLevel = 2;
			} else { // numCommercial >= 100000
				comLevel = 3;
			}
		    					
			if (numSmIndustrial >= 0 && numSmIndustrial < 1000){
				smIndLevel = 0;
			} else if (numSmIndustrial >= 1000 && numSmIndustrial < 5000) {
				smIndLevel = 1;
			} else if (numSmIndustrial >= 5000 && numSmIndustrial < 10000) {
				smIndLevel = 2;
			} else { // numSmIndustrial >= 10000
				smIndLevel = 3;
			}
		    					
			if (numLgIndustrial >= 0 && numLgIndustrial < 10){
				lgIndLevel = 0;
			} else if (numLgIndustrial >= 10 && numLgIndustrial < 40) {
				lgIndLevel = 1;
			} else if (numLgIndustrial >= 40 && numLgIndustrial < 100) {
				lgIndLevel = 2;
			} else { // numLgIndustrial >= 100
				lgIndLevel = 3;
			}    					
		}
		    				
		// get the largest level
		int max = resLevel;
		if (comLevel > max)
			max = comLevel;
		if (smIndLevel > max)
			max = smIndLevel;
		if (lgIndLevel > max)
			max = lgIndLevel;

		int num = cs.size()-1;
		return ((Alternative)cs.get(num-max)).getComment();
	}				
	
	// get the constructed scale value for Image - Customer
	public static String getImageCustomer(Vector cs, int duration, int numResidential, int numCommercial, int numSmIndustrial, int numLgIndustrial) {
		// HARD CODED LOOKUP TABLE
		int resLevel = 0, comLevel = 0, smIndLevel = 0, lgIndLevel = 0;
		if (duration <= 10) {
			resLevel = 0;
		    						
			if (numCommercial >= 0 && numCommercial < 10){
   				comLevel = 0;
			} else if (numCommercial >= 10 && numCommercial < 100) {
				comLevel = 1;
			} else if (numCommercial >= 100 && numCommercial < 1000) {
				comLevel = 2;
			} else if (numCommercial >= 1000 && numCommercial < 10000) {
				comLevel = 3;
			} else if (numCommercial >= 10000 && numCommercial < 100000) {
				comLevel = 4;
			} else { // numCommercial >= 100000
				comLevel = 5;
			}
		    					
			if (numSmIndustrial >= 0 && numSmIndustrial < 3){
				smIndLevel = 0;
			} else if (numSmIndustrial >= 3 && numSmIndustrial < 30) {
				smIndLevel = 1;
			} else if (numSmIndustrial >= 30 && numSmIndustrial < 300) {
				smIndLevel = 2;
			} else if (numSmIndustrial >= 300 && numSmIndustrial < 3000) {
				smIndLevel = 3;
			} else { // numSmIndustrial >= 3000
				smIndLevel = 4;
			}
		    						
			if (numLgIndustrial >= 0 && numLgIndustrial < 2){
				lgIndLevel = 0;
			} else if (numLgIndustrial >= 2 && numLgIndustrial < 15) {
				lgIndLevel = 1;
			} else { // numLgIndustrial >= 15
				lgIndLevel = 2;
			}    					
		} else if (duration > 10 && duration <= 24) {
			resLevel = 0;

			if (numCommercial >= 0 && numCommercial < 5){
				comLevel = 0;
			} else if (numCommercial >= 5 && numCommercial < 50) {
				comLevel = 1;
			} else if (numCommercial >= 50 && numCommercial < 500) {
				comLevel = 2;
			} else if (numCommercial >= 500 && numCommercial < 5000) {
				comLevel = 3;
			} else if (numCommercial >= 5000 && numCommercial < 50000) {
				comLevel = 4;
			} else { // numCommercial >= 50000
				comLevel = 5;
			}
		    					
			if (numSmIndustrial >= 0 && numSmIndustrial < 2){
				smIndLevel = 0;
			} else if (numSmIndustrial >= 2 && numSmIndustrial < 20) {
				smIndLevel = 1;
			} else if (numSmIndustrial >= 20 && numSmIndustrial < 200) {
				smIndLevel = 2;
			} else if (numSmIndustrial >= 200 && numSmIndustrial < 2000) {
				smIndLevel = 3;
			} else { // numSmIndustrial >= 2000
				smIndLevel = 4;
			}
		    					
			if (numLgIndustrial >= 0 && numLgIndustrial < 1){
				lgIndLevel = 0;
			} else if (numLgIndustrial >= 1 && numLgIndustrial < 10) {
				lgIndLevel = 1;
			} else if (numLgIndustrial >= 10 && numLgIndustrial < 100) {
				lgIndLevel = 2;
			} else { // numLgIndustrial >= 100
				lgIndLevel = 3;
			}    					
		    						
		} else { // duration > 24
			resLevel = 0;
		    				
			if (numCommercial >= 0 && numCommercial < 3){
				comLevel = 0;
			} else if (numCommercial >= 3 && numCommercial < 30) {
				comLevel = 1;
			} else if (numCommercial >= 30 && numCommercial < 300) {
				comLevel = 2;
			} else if (numCommercial >= 300 && numCommercial < 3000) {
				comLevel = 3;
			} else if (numCommercial >= 3000 && numCommercial < 30000) {
				comLevel = 4;
			} else { // numCommercial >= 30000
				comLevel = 5;
			}
		    					
			if (numSmIndustrial >= 0 && numSmIndustrial < 1){
				smIndLevel = 0;
			} else if (numSmIndustrial >= 1 && numSmIndustrial < 4) {
				smIndLevel = 1;
			} else if (numSmIndustrial >= 4 && numSmIndustrial < 40) {
				smIndLevel = 2;
			} else if (numSmIndustrial >= 40 && numSmIndustrial < 400) {
				smIndLevel = 3;
			} else if (numSmIndustrial >= 400 && numSmIndustrial < 4000) {
				smIndLevel = 4;
			} else { // numSmIndustrial >= 4000
				smIndLevel = 5;
			}
		    					
			if (numLgIndustrial >= 0 && numLgIndustrial < 1){
				lgIndLevel = 0;
			} else if (numLgIndustrial >= 1 && numLgIndustrial < 20) {
				lgIndLevel = 2;
			} else { // numLgIndustrial >= 20
				lgIndLevel = 3;
			}    					
		    				
		}
		    					
		// 	get the largest level
		int max = resLevel;
		if (comLevel > max)
			max = comLevel;
		if (smIndLevel > max)
			max = smIndLevel;
		if (lgIndLevel > max)
			max = lgIndLevel;
	    			
		int num = cs.size()-1;
		return ((Alternative)cs.get(num-max)).getComment();
		    			
	}

	// get the constructed scale value for Health & Safety - General Public
	public static String getHealthSafetyGeneralPublic(Vector cs, int duration, int numResidential, int numCommercial, int numSmIndustrial, int numLgIndustrial) {
		// HARD CODED LOOKUP TABLE
		int resLevel = 0, comLevel = 0, smIndLevel = 0, lgIndLevel = 0;
		if (duration <= 10) {
			if (numResidential >= 0 && numResidential < 500000) {
				resLevel = 0;
			} else if (numResidential >= 500000 && numResidential < 800000) {
				resLevel = 1;
			} else if (numResidential >= 800000 && numResidential < 1000000) {
				resLevel = 2;
			} else { // numResidential >= 1000000
				resLevel = 3;
			}
		} else if (duration > 10 && duration <= 24) {
			if (numResidential >= 0 && numResidential < 300000) {
				resLevel = 0;
			} else if (numResidential >= 300000 && numResidential < 500000) {
				resLevel = 1;
			} else if (numResidential >= 500000 && numResidential < 800000) {
				resLevel = 2;
			} else if (numResidential >= 800000 && numResidential < 1000000) {
				resLevel = 3;
			} else { // numResidential >= 1000000
				resLevel = 4;
			}	    					
		} else { // duration > 24
			if (numResidential >= 0 && numResidential < 100000) {
				resLevel = 0;
			} else if (numResidential >= 100000 && numResidential < 300000) {
				resLevel = 1;
			} else if (numResidential >= 300000 && numResidential < 500000) {
				resLevel = 2;
			} else if (numResidential >= 500000 && numResidential < 800000) {
				resLevel = 3;
			} else if (numResidential >= 800000 && numResidential < 1000000) {
				resLevel = 4;
			} else { // numResidential >= 1000000
				resLevel = 5;
			}
		}
		    							    				
		int num = cs.size()-1;
		return ((Alternative)cs.get(num-resLevel)).getComment();
	}
		    					
	// get the constructed scale value for Health & Safety - Utility Workers
	public static String getHealthSafetyUtilityWorkers(Vector cs, int utilityWorkerLevel) {
		int num = cs.size()-1;
		return ((Alternative)cs.get(num-utilityWorkerLevel)).getComment();
		
	}
	
	// get the constructed scale value for Environment - Fauna
	public static String getEnvironmentFauna(Vector cs, int faunaLevel) {
		int num = cs.size()-1;
		return ((Alternative)cs.get(num-faunaLevel)).getComment();
	}
}