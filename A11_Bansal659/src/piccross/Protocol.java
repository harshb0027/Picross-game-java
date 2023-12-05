package piccross;

import java.util.Random;
import java.util.StringTokenizer;
/**--------------------	UPDATED VERSION -------- ASSIGNMENT-03------------------------------- 
 * Assessment:- Assignment01
 * Student Name:- Joshua Ayyasamy
 * Section:- CST8221_300_302
 * Lab Professor Name:- Prof. Daniel Cormier
 * Submission Date:- December 11, 2021
 * 
 * * This assignment focuses on the concept of networking. Interconnection between client and server 
 * that the server will be launched and which will allow clients to connect. 
 * Client can make a new game
 *  send that game to the server
 *  receive that game from the server 
 *  play that game
 *  can be disconnected
 * 
 *This class has the main functionality for creating a new game. It creates a random game and returns that 
 *string.
 * @author Joshua
 * @version 1.1
 * 
 *
 */

public class Protocol {
	String[] games = { 
			"00100,00100,11111,01110,10110", "11111,11100,10111,10110,01110", 
			"00101,00110,11111,00111,00100", "11110,11001,11111,10111,10100"
	};
	Random r=new Random();     
	private String NEWGAME = "NEWGAME";
	
	private static final String SENDGAME = "SENDGAME";
	private static final String PLAYGAME = "PLAYGAME";
	private static final String RECEIVEGAME = "RECEIVEGAME";
	private static final String PROTOCOLSENDGAME = "P1";
	private static final String PROTOCOLRECEIVEGAME = "P2";
	private static final String SEPARATOR = "#";
	
	private StringTokenizer string = null;
	 String stringArray[] = new String[3];
	private int i=0;
	int randomNumber;
	String gameReturned = null;
	String configurationReturned = "";
	private String gamePatternToPlay;
	
	
	public String getGameBoard() {
		return gamePatternToPlay;
	}
	
	/**
	 * The whole configuration according to the protocols
	 * @param str -- the string received and to be worked upon
	 * @param gamePattern -- the binary configuration of the game
	 * @return the configuration of the game program which is different for different protocols
	 */
	public String newConfigurationAccordingToProtocol(String str, String gamePattern) {
		//separating the string into tokens
		string = stringTokenizer(str);
		while(string.hasMoreTokens()) {
			for(int x = 0; x < stringArray.length; x++) {
				stringArray[x] = string.nextToken();
				if(!(string.hasMoreTokens())) {
					break;
				}
			}
		}
		
		configurationReturned = "";
		for(int j=0; j < stringArray.length; j++) {
			/*
			 * if the string at particular location is null
			 */
			if(stringArray[j].equals(null) == true){
				break;
			}
			else {
				/*
				 * if the string at particular location is equal to NEWGAME
				 */
				if(stringArray[j].equals(NEWGAME) == true) {				   
			      	stringArray[stringArray.length -1] = newGameLaunched();
			      	gamePatternToPlay = newGameLaunched();
			      	configurationReturned = NEWGAME + SEPARATOR + stringArray[stringArray.length -1];			      	
				}
				
			}
			
		}		
		return configurationReturned;
	}
	
	/** 
	 * This method will return the new game string from the array of games
	 * @return -- the game string
	 */
	public String newGameLaunched() {	
		randomNumber=r.nextInt(games.length);
      	gameReturned =  games[randomNumber];
      	System.out.println(gameReturned);
		return gameReturned;
	}
	
	
	
	/** 
	 * The string tokenizer fuunction that  will divide the string 
	 * @param a -- the string to be splitted
	 * @return the string tokenizer object
	 */
	public StringTokenizer stringTokenizer(String a) {
		StringTokenizer st = new StringTokenizer(a, "#");
		return st;
	}
	
	

}
