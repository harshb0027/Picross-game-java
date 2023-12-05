package piccross;
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

/**
 * The main class of the game
 * @author Joshua
 * @version 1.2
 * 
 *
 */
public class Game {
	
	/**
	 * The main method that instantiates the object of GameView and runs the whole game.
	 * @param args -- string type array
	 */
	public static void main(String[] args) {		
		 GameView gameView = new GameView(10000, new Protocol().getGameBoard());
		gameView.splashExecution();
	
		
	}

}
