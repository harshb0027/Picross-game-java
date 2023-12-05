package piccross;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.*;
/**
 * 
/**--------------------	UPDATED VERSION -------- ASSIGNMENT-03------------------------------- 
 * Assessment:- Assignment01
 * Student Name:- Joshua Ayyasamy
 * Section:- 302
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
 * This class GameClient is the client part of the CS architecture
 * 
 * 
 * 
 * Working of the code:- 
 * execute button of the server will run the server and will wait for the clients to connect
 * For each of the client a new thread is generated with the help of runnable 
 * @author harsh
 * @version 1.1
 * 
 *
 */
public class GameClient {
static final String END = "end";
static final String SEPARATOR = "#";
static final String NEWGAME= "NEWGAME";
static final String SENDGAME = "P1";
static final String RECEIVEGAME = "P2";
	JTextArea textArea = new JTextArea();
	//Controller r = new Controller();
	JButton newButton = new JButton("New Game");
	JButton sendButton = new JButton("Send Game");
	JButton receiveButton = new JButton("Receive Game");
	JButton playButton = new JButton("Play");
	JButton endButton = new JButton("End");
	JTextField userNameT = new JTextField("Harsh");
	JTextField serverNameT = new JTextField("localhost");
	JTextField portNumberT = new JTextField("1000");
	JButton connectButton;
	Protocol protocol = new Protocol();
	PrintStream dat;
	String nameOfClient;
	String numberOfClients;
	String newGame;
	String receiveGame;
	String receiveGameProtocol;
	String gameToPlay;
	String consoleData;
	String userName = userNameT.getText();
	String serverData;
	String clientData;
	Socket clientSocket;
	String clientNewGame;
	String playGameProtocol;
	BufferedReader bufferedReader;
	PrintStream printStream;
	PrintWriter printWriter;
	String clientSendGame;
	String clientId;
	String clientReceiveGame;
	
	
	
	/**
	 * the static main method of the whole class where the whole functionality is executed for the client
	 * @param args -- the string array
	 * @throws IOException -- the exception caught between the communication between client and server
	 */
	public static void main(String[]args) throws IOException {
		GameClient g = new GameClient();
		g.main();
		
	}
	
	/**
	 * the main method where the client will play with all the buttons of the UI
	 * @throws IOException - exception caught during the input and output streams
	 */
	public void main() throws IOException {
		clientUI();
		connectButton.addActionListener(new ActionListener() {
			/**
			 * client is going to connect to the server part of the game
			 * @param -- e -- event to be performed
			 */
			public void actionPerformed(ActionEvent e) {
				//creating a client socket from the values of server name and the port number				
				try {
					//putting the server name and the port to the socket for the connection
					clientSocket = new Socket(serverNameT.getText(), Integer.parseInt(portNumberT.getText()));					
					//reading the buffer or reading the server buffer
					bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					printWriter = new PrintWriter(clientSocket.getOutputStream());
					nameOfClient = userNameT.getText();
					textArea.append("Sending the new client name:- " + nameOfClient + "\n\n");
					System.out.println("Sending the new client name:- "+ nameOfClient);
					printWriter.println(nameOfClient);
					printWriter.flush();
					if(!(nameOfClient.equals(END) == true)) {
						printWriter.println(nameOfClient);
						printWriter.flush();
						textArea.append("Reading the client data of: " + nameOfClient + "\n");
						clientData = bufferedReader.readLine();
						textArea.append("Client data receiced from Server: " + clientData + "\n");
						System.out.println("Reading the client data from server:- " + clientData);
						clientId = String.valueOf(clientData.charAt(0));
					}
					//clientSocket.close();
				}catch(Exception e1) {
					System.out.println(e1);
				}
				
				//connectButton.setEnabled(false);
			}
		});
		
		/**
		 * Protocol set for creating the new game is "NEWGAME"
		 * button is creating a new game
		 */
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New button clicked!!!!");
				textArea.append("New button clicked\nCreating a new MVC Game......\n");
				//the protocol class returning the attached new game pattern to the client data 
				
				clientNewGame = clientData + SEPARATOR + "NEWGAME";	
				//In the protocol class a new game is generated 
				newGame = protocol.newConfigurationAccordingToProtocol(clientNewGame, null);
				textArea.append("New game is:- " + newGame  + "\n");
				System.out.println("Creating a new MVC game");
				System.out.println("New game is:- " + newGame + "\n");
			}
		});
			
		
		/**
		 * protocol set for sending game is P1
		 * it will prepare the game to send by adding the client id and the protocol and attaching the 
		 * new game string to it
		 */
		
		sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("\nSend Button....");
				textArea.append("\nSend Button....\n");
				int b = (NEWGAME+SEPARATOR).length();
				newGame = newGame.substring(b);
				textArea.append("Sending  game " + newGame + " to the server..\n");
				//Preparing the game to send to the server
				clientSendGame = clientId + SEPARATOR + SENDGAME + SEPARATOR + newGame;
				System.out.println("Game to send to server is:- " + clientSendGame);
				//sending the pattern of the game to the server
				printWriter.println(clientSendGame);
				printWriter.flush();				
			}
		});		
		
		
		/**
		 * protocol for receiving the game is P2. It will send the client information and the protocol and
		 * will get the new game from the server
		 */
		receiveButton.addActionListener(new ActionListener() {
			/**
			 * protocol for receiving the game is P2. It will send the client information and the protocol and
			 * will get the new game from the server
			 * @param e -- the event to be performed
			 */
			public void actionPerformed(ActionEvent e) {
				textArea.append("\nReceive Button -- Receiving a game from the server....\n");
				try {
					clientReceiveGame = clientId + SEPARATOR + RECEIVEGAME;
					printWriter.println(clientReceiveGame);
					printWriter.flush();
					receiveGame = bufferedReader.readLine();					
					textArea.append("Game Received:- "+ receiveGame + "\n");
					System.out.println("Game Received:- "+ receiveGame + "\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		/**
		 * finally the client will play the game
		 */
		playButton.addActionListener(new ActionListener() {
			/**
			 * finally the client will play the game
			 * @param e -- the event to be performed
			 */
			public void actionPerformed(ActionEvent e) {
				textArea.append("\nPlay Button....\n");
				System.out.println("The latest game to be played is :- " +receiveGame);
				playTheGame();				
			}
		});
		
		
		endButton.addActionListener(new ActionListener(){
			/**
			 * now the game is ended, the client is out
			 * @param e -- the event to be performed
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					clientSocket.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				System.out.println("END");
				printWriter.println(END);
				printWriter.flush();
			}
		});
	
		
		
		
		
		}
		
	/**
	 * execution of the game board and the client will play the game
	 */
	public void playTheGame() {
		GameView gameView = new GameView(10000, receiveGame);
		gameView.splashExecution();
		String score = gameView.pointsOfTheGame();
		System.out.println("Score:- " + score);
	}
	
	
	

	/**
	 * The GUI of the client section
	 */
	public void clientUI() {
		JFrame clientFrame = new JFrame("client");
		clientFrame.setMinimumSize(new Dimension(500, 400));
		//clientFrame.setPreferredSize(new Dimension(500, 500));
		JPanel clientPanel = new JPanel(new GridLayout(4,0,2,2));
		JLabel label01 = new JLabel(new ImageIcon(getClass().getResource("piccorssLogoclient.png")));
		JPanel p1 = new JPanel(new GridLayout(1,8));
		JPanel p2 = new JPanel(new GridLayout(1,5));
		p1.setPreferredSize(new Dimension(200, 10));
		JLabel l1 = new JLabel("User:-");
		
		JLabel l2 = new JLabel("Server:- ");
		
		JLabel l3 = new JLabel("Port:- ");
		
		connectButton = new JButton("Connect");
		//connectButton.setActionCommand("Connect Button");
		//connectButton.addActionListener(r);
		
	
		
		
		JButton sendDataButton = new JButton("Send Data");
		
		textArea= new JTextArea(10, 15);
		textArea.setMargin(new Insets(0,5,5,0)); 			//set text area margins
		textArea.setEditable(false);						//the user cannot edit the textarea.

		//textArea.setPreferredSize(new Dimension(500, 100));
		//textArea.setAutoscrolls(true);
		//p1.add(l1);
		p1.add(l1);
		p1.add(userNameT);
		p1.add(l2);
		p1.add(serverNameT);
		p1.add(l3);
		p1.add(portNumberT);
		p1.add(connectButton);
		p1.add(endButton);
		p1.setMaximumSize(new Dimension(200, 10));
		
		p2.add(newButton);
		p2.add(sendButton);
		p2.add(receiveButton);
		p2.add(sendDataButton);
		p2.add(playButton);
		
		clientPanel.add(label01);
		clientPanel.add(p1);
		clientPanel.add(p2);
		clientPanel.add(new JScrollPane(textArea));
		clientFrame.add(clientPanel);
		clientFrame.setVisible(true);
		clientFrame.setResizable(true);
		
		
	}
}
