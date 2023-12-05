package piccross;
import java.util.StringTokenizer;

import javax.swing.JButton;

/**
 * UPDATED VERSION
 * Assessment:- Assignment01
 * Student Name:- Joshua Ayyasamy
 * Section:- CST8221_300_302
 * Lab Professor Name:- Prof. Daniel Cormier
 * Submission Date:-November 13, 2021
 * This assignment focuses on creating the piccross game's user interface design
 * This class creates and designs the whole user interface. In general, the UI frame has been created using 
 * the GridBagLayout and has 5 panels(mark, left and top for piccross  co-ordinates, main grid at the centre and 
 * right for game reset and displaying the each of action. All of these panels's design is written using methods
 * for each and the main execution method that invoke other methods to create the whole plan
 * 
 *  The design is strong respecting the layout mentioned in the lab presentation. The changes are mentioned
 *  in the .ppt file 
 *  
 *  REFERENCE FOR PRODUCING THE OUTPUT OF THE BUTTON:- Prof. Daniel Cormier's code (LayoutDemo)
 *  
 *  Special thanks for the project:- Lecture notes, lab session demos, weekly code shared by Prof. Paulo Sousa, Prof. Daniel Cormier 
 *  and Prof. Svillen Ranev  
 */

public class GameModel {	
	private StringTokenizer st; //dividing teh string to tokens
	private int dimension = 5;
	private String[] valStrLeft = new String[dimension];
	String[] valStrTop = new String[dimension];
	
	
	private String hint;//= "00100,00100,11111,01110,10110"; //1111,11100,10111,10110,01110
	private boolean[][] hint01 = new boolean[dimension][dimension]; 
	
	private int[] leftPanelRough = new int[dimension];
	private int[] topPanelRough = new int[dimension];
	private String[] leftPanel = new String[dimension];
	private String[] topPanel = new String[dimension];
	int i = 1;
	//Protocol p = new Protocol();
	
	/**
	 * COnstructor of the class
	 */
	public GameModel(String a) {
		//hint = new GameClient().gameToPlay();
		hint = a;
		System.out.println(" model --game is :- " + i + hint);
		i++;
	}
	/*
	public JButton[][] getButton(){
		return button;
	}
	
	public JButton getButton(int i, int j){
		return button[i][j];
	}
	*/
	/**
	 * Computing the left panel values. It basically computes the value from the variable named:- valStrLeft
	 * and performs the basic string arithmetic and concatenations -- mostly playing with the comma-separated 
	 * values.
	 */
	public void leftPanelValues() {
		//hint = new GameClient().gameToPlay();
		System.out.println("game pattern in game model is " + hint);
		st = a(hint);		
		//extracting the tokens
		while(st.hasMoreTokens()) {
			for(int i=0; i < dimension; i++) {
				valStrLeft[i] = st.nextToken();			 
			}
		
		}
		
		
		for(int i = 0; i < dimension; i++) {
			for(int j=0; j < dimension; j++) {
				if(valStrLeft[i].charAt(j) == '0') {
					hint01[i][j] = false;
				}
				else
					hint01[i][j] = true;
			}
		}
		
		
		
		/*System.out.println("Printing the valStrTop -- " );
		
		for(int a  = 0; a < dimension; a++) {
			System.out.println("valStrTop[" + a+ "] = " + valStrTop[a]);
		}
		*/
		
		int[] j = new int[dimension];
		
		for(int i =0; i<dimension; i++) {
			//System.out.println("valStrLeft[" + i + "] = " + valStrLeft[i]);
			//System.out.println("valStrTop[" + i + "] = " + valStrTop[i]);
			j[i] = Integer.parseInt(valStrLeft[i]);
			
			//System.out.println("j[" + i + "] = " + j[i]);
			//System.out.println("k[" + i + "] = " + k[i]);
			
		}
		//System.out.println("Printing the leftPanel contents");
		//"00100,00100,11111,01110,01010"
		for(int i = 0; i < dimension; i++) {
			int counter = 0;
			if(valStrLeft[i] != null) {
				leftPanel[i] = "";
				for(int z=0; z < dimension; z++) {
					if(valStrLeft[i].charAt(z) == '1') {
						counter++;
						leftPanel[i] = leftPanel[i] +  String.valueOf(counter);
					}
					//"00100,00100,11111,01110,01010"
					if(z != 4) {
						if(valStrLeft[i].charAt(z) == '1' && valStrLeft[i].charAt(z+1) == '1') {
							//leftPanel[i] += "" +1;
						}
						else if(valStrLeft[i].charAt(z) != '1' && valStrLeft[i].charAt(z+1) == '1') {
							counter = 0;
							leftPanel[i]= leftPanel[i] +  "" + "," ; //+ String.valueOf(counter);
						}
						
						
					}
					
					
					 
				}
				
				
			}
			
			/*
			sum = 0;
			
			while(j[i] > 0) {				
				sum = sum + j[i]%10;
				j[i] = j[i] / 10;		
			}
			leftPanelRough[i] = sum;
			leftPanel[i] = String.valueOf(leftPanelRough[i]);*/
			//System.out.println("leftPanel[" + i + "] = " + leftPanel[i] );
			
			
			
		}
		
		for(int i=0; i < dimension; i++) {
			if(leftPanel[i].charAt(0) == ',') {
				leftPanel[i] = leftPanel[i].substring(0,0)+' '+leftPanel[i].substring(1);
			}
			if(leftPanel[i].length() > 1 && !(leftPanel[i].contains(","))) {
				leftPanel[i] = String.valueOf(leftPanel[i].charAt(leftPanel[i].length()-1));
	
			}
			if(leftPanel[i].length() > 1 && (leftPanel[i].contains(","))) {
				leftPanel[i] = String.valueOf(leftPanel[i].charAt(leftPanel[i].indexOf(",")-1)) + 
						leftPanel[i].charAt(leftPanel[i].indexOf(',')) + leftPanel[i].charAt(leftPanel[i].length()-1);
	
			}
		}
	
		
		
	}
	
	
	/**
	 * The method that computes the top-panel values from the valStrTop variable. The binary pattern is computed
	 * by taking all the values from the same index of all the values (we can say that the vertical combination
	 * of the 0 and 1's. It also computes the values similar to the left panel values (following teh same logic)
	 */
	public void topPanelValues() {
		for(int i= 0;i < dimension; i++) {
			valStrTop[i] = "";
		}
		
		//extracting the values for the top panel
		for(int z = 0; z<dimension; z++){
			for(int i = 0; i < dimension; i++) {
				
				valStrTop[z] += String.valueOf(valStrLeft[i].charAt(z));
				
			}
			
		}
		int[] k = new int[dimension];
		
		for(int i =0; i<dimension; i++) {
			//System.out.println("valStrLeft[" + i + "] = " + valStrLeft[i]);
			System.out.println("valStrTop[" + i + "] = " + valStrTop[i]);
			k[i] = Integer.parseInt(valStrTop[i]);
			
			//System.out.println("j[" + i + "] = " + j[i]);
			//System.out.println("k[" + i + "] = " + k[i]);
			
		}
		
		//System.out.println("Printing the top panel contents");
		
		for(int i = 0; i < dimension; i++) {
			int counter = 0;
			if(valStrTop[i] != null) {
				topPanel[i] = "";
				for(int z=0; z < dimension; z++) {
					if(valStrTop[i].charAt(z) == '1') {
						counter++;
						topPanel[i] = topPanel[i] +  String.valueOf(counter);
					}
					//"00100,00100,11111,01110,01010"
					if(z != 4) {
						if(valStrTop[i].charAt(z) == '1' && valStrTop[i].charAt(z+1) == '1') {
							//leftPanel[i] += "" +1;
						}
						else if(valStrTop[i].charAt(z) != '1' && valStrTop[i].charAt(z+1) == '1') {
							counter = 0;
							topPanel[i]= topPanel[i] +  "" + "," ; //+ String.valueOf(counter);
						}
						
						
					}
					
					
					 
				}
				
				
			}
			
			
			
			
			
		}
		
		for(int i=0; i < dimension; i++) {
			if(topPanel[i].charAt(0) == ',') {
				topPanel[i] = topPanel[i].substring(0,0)+' '+topPanel[i].substring(1);
			}
			if(topPanel[i].length() > 1 && !(topPanel[i].contains(","))) {
				topPanel[i] = String.valueOf(topPanel[i].charAt(topPanel[i].length()-1));
	
			}
			if(topPanel[i].length() > 1 && (topPanel[i].contains(","))) {
				topPanel[i] = String.valueOf(topPanel[i].charAt(topPanel[i].indexOf(",")-1)) + 
						topPanel[i].charAt(topPanel[i].indexOf(',')) + topPanel[i].charAt(topPanel[i].length()-1);
	
			}
		}
	
		
		
		
		
		/*for(int i = 0; i<dimension; i++) {
		 * sumTop = 0;
			while(k[i] > 0) {				
				sumTop = sumTop + k[i]%10;
				k[i] = k[i] / 10;		
			}
			topPanelRough[i] = sumTop;
			topPanel[i] = String.valueOf(topPanelRough[i]);*/
			//System.out.println("topPanel[" + i + "] = " + topPanel[i]);
			
		}
	
	
	
	/**
	 * The method that divides the strings into tokens. The string point where to separate 
	 * the string is based from comma -separated values in the hint value.
	 * @param args -- teh string arguement passed
	 * @return -- the tokens of strings
	 */
	public StringTokenizer a (String args) {
		StringTokenizer st = new StringTokenizer(args, ",");
		return st;
	}
	
	/**
	 * The method that returns the left panel values
	 * @return -- the string array showing the left panel values
	 */
	public String[] getLeftPanelValues() {
		leftPanelValues();
		return leftPanel;
	}
	
	/**
	 * The method that returns the dimension values
	 * @return -- the int showing the dimension of the board panel
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * The method that returns the top panel values
	 * @return -- the string array showing the top panel values
	 */
	public String[] getTopPanelValues() {
		topPanelValues();
		return topPanel;
	}
	
	/**
	 * The method that returns the hint01 values
	 * @return -- the boolean array showing the hint01 values.....
	 */
	public boolean[][] getHint01Values(){
		return hint01;
	}
	
	
	/**
	 * The method that returns the left panel's binary values
	 * @return -- the string array showing the left panel's binary values
	 */
	public String[] getValStrLeft() {
		return valStrLeft;
	}
}
