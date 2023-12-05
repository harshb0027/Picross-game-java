package piccross;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.*;

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
 * This class Game Server is the server part of the CS architecture
 * 
 * 
 * 
 * Working of the code:- 
 * execute button of the server will run the server and will wait for the clients to connect
 * For each of the client a new thread is generated with the help of runnable 
 * @author Joshua
 * @version 1.1
 * 
 *
 */

public class GameServer implements Runnable {	
	static Socket socket = null;
	static int nClient = 0, nclients = 0;
	static ServerSocket serverSocket;
	JTextArea textArea = new JTextArea();
	static final String END = "end";
	static final String SENDGAME = "P1";
	static final String RECEIVEGAME  = "P2";
	static final String SEPARATOR = "#";
	static final String NEWGAME = "NEWGAME";
	JButton executeButton;
	JButton endButton = new JButton("End");
	JCheckBox finaliseBox = new JCheckBox("Finalize");
	JTextField l2 = new JTextField();
	String newGameGenerated;
	String gameServerToClient;
	String gamePlayedByClient;
	String newGame;
	ArrayList<String> newGameArrayList = new ArrayList<String>();
	String strcliid;
	String numberOfClients;
	int portNumber;
	String clientId;
	boolean finalizeButton = false;
	/**
	 * main method of the server part which will execute the server
	 * @param args -- the string array
	 * @throws IOException -- catches any IO Exception and terminate the program
	 */
	public static void main(String[]args) throws IOException {
		GameServer s = new GameServer();
		s.main();
	}
	
	/**
	 * main method which is not static which has all the functionality of the client
	 * On clicking teh execute button - -the server will be launched and clients wiill be connected
	 * Connection of each client will generate a new thread
	 * @throws IOException -- catches any of the Io exception and terminates the program
	 */
	public void main() throws IOException {
		serverUI();
		executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				portNumber = Integer.parseInt(l2.getText());
				//Creating a server socket
				try {
					serverSocket = new ServerSocket(portNumber);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//Creating a new thread for every client connected
				Thread thread = new Thread(new GameServer());
				thread.start();
				
				appendToTextArea("Server is alive\n");
				System.out.println("Alive on text area");
				
			}
		});
		
		finaliseBox.addActionListener(new ActionListener() {
			/**
			 * shows if the finalize button is selected or not
			 */
			public void actionPerformed(ActionEvent e) {
				System.out.println("Finalize button.....");
				if(finalizeButton == true) {
					finalizeButton = false;
					textArea.append("Checkbox Unselected -- finalize button:- " + finalizeButton + "\n");
					
				}
				else {
					finalizeButton = true;
					textArea.append("Checkbox Selected -- finalizeButton:- " + finalizeButton + "\n");
			
					
				}
				
			}
		});
		
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("Closing exception");
				}
				System.exit(0);
			}
		});
		
		
	}
	
	/**
	 * Running the server server socket is accepting the client connections increasing teh client's number 
	 * by 1
	 *
	 */
	public void run() {
		for(;;) {
			
			try {
				socket= serverSocket.accept();
				
				nClient = nClient + 1;
				nclients = nclients+1;
				Connection  connection = new Connection(socket, nClient, finalizeButton); 
				connection.start();
			
				System.out.println("Connecting the port to the address:- " + socket.getInetAddress());
				}catch(IOException e1) {
					System.out.println("e1 = ");
					System.out.println(e1);
				}
			}
			
			
		}
		
	
	/**The connection class that has all the functionalities to do when the client finally connects 
	 * It extends from class Thread
	 * @author harsh
	 *
	 */
	class Connection extends Thread {
		Socket s = null;
		int n;
		boolean fButton = true;
		
		/**
		 * The constructor of the class
		 * @param s -- the socket of the server
		 * @param n -- the counter which will increase by 1 upon connection of each client
		 * @param fButton -- the finalize button
		 */
		public Connection(Socket s, int n, boolean fButton) {
			this.s = s;
			this.n = n;
			this.fButton = fButton;
		}
		
		/**
		 * main part of the server class where each client will come and get connected as a separate thread
		 * . This method has several loops from where the strings are identified. 
		 */
		public void run() {
			String nameOfClient = null;
			String clientData = null;
			//PrintStream printStream = null;
			PrintWriter printWriter = null;
			BufferedReader bufferedReader = null;	
			DataInputStream is;
			try {				
				printWriter = new PrintWriter(s.getOutputStream());
				bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));				
				String name = bufferedReader.readLine();
				nameOfClient = name;	
				is = new DataInputStream(s.getInputStream());
				
				while(!(name.equals(END) == true)) {
					
					
					/*The client is connected to the server and server sends the designated client id
					 * 
					 * It takes the client's name afterwards and put the clientId and clientName to the
					 * clientData
					 */
					System.out.println("Client [" + n + "] connected");
					clientId = String.valueOf(n);
					System.out.println("Receiving the client name: ");
					appendToTextArea("Receiving the client name: ");
					nameOfClient = bufferedReader.readLine();
					appendToTextArea(nameOfClient + "\n");
					clientData = clientId + "#" + nameOfClient;
					printWriter.println(clientData);
					printWriter.flush();
					
					//generating the new game by the server
					//the server receives a strign which contains teh string NEWGAME 
					//from here it comes to know that the cleint has created a new game
					//
					String newGameGenerated = bufferedReader.readLine();
					while(newGameGenerated.contains(NEWGAME)) {						
						System.out.println("The new game generated by the client :- " + newGameGenerated);
						/*extracting the string */
						int a = ("NEWGAME" + "#").length();
						newGame = newGameGenerated.substring(a);
						System.out.println("The new game generated by the client :- " + newGame);
						newGameGenerated = bufferedReader.readLine();
					}
					
					//The protocol P1 is received from the client which is the protocol of the 
					//sending the game by the client. Now serbver receives the game
					while(newGameGenerated.contains(SENDGAME)) {						
						System.out.println("The new game generated by the client :- " + newGameGenerated);
						newGame = newGameGenerated;
						newGameGenerated = bufferedReader.readLine();
					}
					
					//The server has to send the game to the client
					while(newGameGenerated.contains(RECEIVEGAME)) {
						System.out.println(newGameGenerated);
						int length01 = (clientId + SEPARATOR +  RECEIVEGAME + SEPARATOR).length();
						gameServerToClient =  newGame.substring(length01);
						System.out.println("Sending a game to the server:- ");						
						printWriter.println(gameServerToClient);
						printWriter.flush();
						System.out.println("Game sent from server to client is:- "+ gameServerToClient);
						newGameGenerated = bufferedReader.readLine();
					}
					
					
					//The client chose the option to play the game and the game board will be launched.
					//Now the client can play the game
					while(newGameGenerated.contains("PLAYGAME")) {
						int length01 = ("#" + clientData + "#" + "NEWGAME" + "#").length();
						gamePlayedByClient = newGame.substring(length01);
						System.out.println("Server sending the game board:- " + gamePlayedByClient);
						printWriter.println(gamePlayedByClient);
						printWriter.flush();
						newGameGenerated = bufferedReader.readLine();
					}
					
					
					//the client chose the option to end the game 
					while(newGameGenerated.contains(END) == true) {
						System.out.print("Disconnecting " + s.getInetAddress() + " -- ");
						//nclients -= 1;
						//newGameGenerated =  bufferedReader.readLine();
						System.out.println(clientData + "!!");
						
						nclients-= 1;
						nClient-=1;
						System.out.println("Current number of clients:- " + nclients);
						System.out.println(fButton);
						if (nclients == 0 && fButton == true) {
							System.out.println("Ending server...");
							s.close();						
							System.out.println("Ending server");
							System.exit(0);						
						}
						newGameGenerated = bufferedReader.readLine();
					}
				
					
					
					
				}
				
			
				
				
				
				
				
				
				
			}catch(Exception e) {				
				System.out.println("Unable to connect");
				
			}
			
		
		}
		
	}
	
	public JTextArea appendToTextArea(String str) {
		JTextArea a = getTheTextArea();
		a.append(str);
		return a;
	}

	public JTextArea getTheTextArea() {
		return textArea;
	}

	public void serverUI() {
		/*UI for the server part*/ 
		JFrame serverFrame = new JFrame("SERVER");
		serverFrame.setMinimumSize(new Dimension(500, 300));
		serverFrame.setPreferredSize(new Dimension(500, 500));
		JPanel serverPanel = new JPanel(new GridLayout(3,0,2,2));
		JLabel label01 = new JLabel(new ImageIcon(getClass().getResource("piccorssLogoServer.png")));
		JPanel p1 = new JPanel(new GridLayout(1,5));
		p1.setPreferredSize(new Dimension(200, 10));
		JLabel l1 = new JLabel("Port:-");
		
		executeButton = new JButton("Execute");
		//JButton resultsButton = new JButton("Results");
		
		
		

		textArea= new JTextArea(10, 15);
		textArea.setMargin(new Insets(0,5,5,0)); 			//set text area margins
		textArea.setEditable(false);						//the user cannot edit the textarea.

		//textArea.setAutoscrolls(true);
		//p1.add(l1);
		p1.add(l1);
		p1.add(l2);
		p1.add(executeButton);
		p1.add(finaliseBox);
		p1.add(endButton);
		p1.setMaximumSize(new Dimension(200, 10));
		serverPanel.add(label01);
		serverPanel.add(p1);
		serverPanel.add(new JScrollPane(textArea));
		serverFrame.add(serverPanel);
		serverFrame.setVisible(true);
		serverFrame.setResizable(true);
		
	
	}
	
}









/*
public void main() {
	serverUI();
	executeButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			portNumber = Integer.parseInt(l2.getText());	
			System.out.println(portNumber);
			 try  {
				 System.out.println(portNumber);
				 ServerSocket serverSocket = new ServerSocket(portNumber);
				 System.out.println(portNumber);
					Socket clientSocket = serverSocket.accept();
					PrintWriter out =
		             new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(
		               new InputStreamReader(clientSocket.getInputStream()));
					 String inputLine, outputLine;
			           System.out.println("Server on");
			            // Initiate conversation with client
			            out.println("harsh");
			            while ((inputLine = in.readLine()) != null) {
			                System.out.println("String Received:= " + inputLine);
			            }
			        } catch (IOException e1) {
			            System.out.println("Exception caught when trying to listen on port "
			                + portNumber + " or listening for a connection");
			            System.out.println(e1.getMessage());
			        }
			
		}			
	});
	
	
	
	
	
}
*/



