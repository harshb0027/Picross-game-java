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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
/**
 * This class has the main interface design of the game. It displays the main UI of the game
 * @author Joshua
 * @version 1.2
 * @since Java 8
 *
 */
public class GameController extends JFrame implements ActionListener, MenuListener {
	
	protected String score = "0"; 		//noting the scoes for the game
	protected String timer = "0";		//time taken foe the game
	protected String unitOfTime = "s";	//time in which unit
	protected String timeElasped = "0";
	private final int NUM_ROWS = 5; 	//rows for the grid
	private final int NUM_COLUMNS = 5;	//columns for the grid
	private JTextArea textArea;			//text area for noting the actions
	Controller buttonHandler = null; 			//object of Controller
	Controller cObject = null;
	Controller gameReset = null;
	int i,j=0, numberOfRows = 1, numberOfColumns = 1;							//counter variables
	private JPopupMenu popUp;
	private int initialDimension= 5;
	private JLabel pointsScored = null;
	private JLabel timeTaken = null;
	GameModel test;
	private String[][] gameBinaryLogic = new String[initialDimension][initialDimension];
	private int count = 0;
	private JButton b = new JButton(" ");
	boolean markPanelSelected = false;
	Protocol p1 = new Protocol();
	
	int counterVariable=0;
	private Color correctColor = Color.GREEN;
	private Color markedColor  = Color.yellow;
	private Color incorrectColor = Color.RED;
	int pointsAchieved = Integer.parseInt(score);
	private JButton[][] button; 
	GameView gameViewClass;
	//0100,00100,11111,01110,01010"
	String gamePattern;
	
	/**button names for matching the button values string value array*/
	private boolean[][] buttonName; 
	/*{
			{"1", "2", "3", "4", "5"}, 
			{"6", "7", "8", "9", "10"},
			{"11", "12", "13", "14", "15"},
			{"16", "17", "18", "19", "20"},
			{"21", "22", "23", "24", "25"}*/
	
	 //button array

	/**button action for the each button*/
	private String[][] buttonAction = {
			{"(1,1)", "(1,2)", "(1,3)", "(1,4)", "(1,5)"},
			{"(2,1)", "(2,2)", "(2,3)", "(2,4)", "(2,5)"},
			{"(3,1)", "(3,2)", "(3,3)", "(3,4)", "(3,5)"},
			{"(4,1)", "(4,2)", "(4,3)", "(4,4)", "(4,5)"},
			{"(5,1)", "(5,2)", "(5,3)", "(5,4)", "(5,5)"}
	};
	
	

	/**
	 * The constructor for the class GameController that is collecting the whole panels in the form of one 
	 * single method i.e. mainPanel() method
	 */
	public GameController(String a) {	
		super("Harsh Bansal Picross game");//giving the title to the frame
		gamePattern = a;
		System.out.println("In game controller:- p1.getGameBoard = " + a);
		test = new GameModel(a);
		button = new JButton[test.getDimension()][test.getDimension()]; //button array
		buttonName =  new boolean[test.getDimension()][test.getDimension()];
		gameViewClass = new GameView();
		mainPanel();

	}

	public JLabel getPointsLabel() {
		return pointsScored;
	}
	
	
	public JLabel getTimeLabel() {
		return timeTaken;
	}
	
	/**
	 * This method is of return tyoe void creates the frame by collecting all the other panels 
	 * It uses the grig bag layout to assemble the 5 panels. As per the requirements the frame is not resizable
	 * 
	 * 
	 */
	public void mainPanel() {
		JPanel firstPanel = new JPanel(new GridLayout(1,1));
		JPanel secondPanel = new JPanel(/*new GridBagLayout()*/);
		JPanel mainPanel = new JPanel(new BorderLayout());
		JMenuBar menu = menu();
		
		firstPanel.add(menu);
		secondPanel.setLayout(new GridBagLayout());
		
		//Setting the grid bag constraints
		GridBagConstraints c = new GridBagConstraints();


		//mainPanel.setPreferredSize(new Dimension(1000, 1000));
		//mainPanel.setMinimumSize(new Dimension(1000, 1000));
		secondPanel.setBackground(Color.DARK_GRAY);
		
		
		
		//the mark panel with the checkbox mark that starts the game
		JPanel markPanel = markPanel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		c.ipadx=1;
		c.ipady=1;
		secondPanel.add(markPanel, c);

		//the leftmost panel for the co-ordinates. It has 5 rows and each rows has three values 
		JPanel left = leftPanel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx=1;
		c.ipady=1;
		secondPanel.add(left,c); //adding to the frame

		//the top panel for the co-ordinates. It has 5 columns and each column has three values 	
		JPanel top = topPanel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 1;
		c.ipadx=1;		
		secondPanel.add(top,c);		//adding to the frame

		//the center grid which is a 5 X 5 grid of buttons. The action for the button pressed is shown in the control panel text area
		JPanel grid = gridPanel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx=1;
		c.ipady=1;
		secondPanel.add(grid, c);  //adding to the frame

		//the right panel which has the piccross icon and the record of points and time elasped as well as the text area
		JPanel right=rightPanel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=2;
		c.gridy=0;
		c.ipadx=1;
		c.ipady=1;
		c.gridheight=2;
		secondPanel.add(right,c); //adding to the frame
		mainPanel.add(firstPanel, BorderLayout.NORTH);
		mainPanel.add(secondPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);  //adding the pane to the frame
		
		//gameWinOrLoss();

		/* Handle close button: means the program should terminate when the user closes the application
		 * ************** Reference for this code: -lab demo code provided by the Professor********************
		 */
		/*creating the object of window listener 
		 * 
		 */
		WindowListener windowListen = new WindowAdapter() {
			//overide the windowClosing() method of the parent class
			@Override
			public void windowClosing(WindowEvent e) {
				GameController.this.dispose(); //disposing the screen and exiting the whole program
				//System.exit(0); //means the program terminated
			}
		};
		// adding this event to the frame
		addWindowListener(windowListen);

		/*making the frame visible by packing the extra space and setting it true for visibility*/ 
		executingFrame();
	}

	/**
	 * This method returns the JPanel object which is basically the grid of 5 rows and 5 columns. The events in the 
	 * grid are handled by the Controller class object that listens all the actions to do for the button. The 
	 * dimensions for the window are set. For creating the actions or to produce the output by the buttons, there is 
	 * an another method created -- createButton(String, String, Controller) that creates the specific button whose
	 * anme is specified int he string array and set the particular action corresponding to that button which is
	 * printed in the text area using getActionCOmmand() method in Controller class.
	 * 
	 * The grid's name is set in the string array and the action for that button is also set corresponding to 
	 * that button name's index. 
	 * @return panel --  the whole grid pane of type JPanel.
	 */
	public JPanel gridPanel() {
		buttonHandler = new Controller();
		JPanel gridPanel = new JPanel(new GridLayout(5,5));
		gridPanel.setPreferredSize(new Dimension(500, 500));
		gridPanel.setBackground(new Color(5, 242, 215));

		for (int i = 0; i < test.getDimension(); i++) {
			for(int j=0; j < test.getDimension(); j++) {
				button[i][j] = createButton(buttonAction[i][j], test.getHint01Values()[i][j], buttonHandler);
				button[i][j].setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
				button[i][j].setBackground(new Color(5, 242, 215));
				
			}
			
		}
		gridPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));

		/*adding the buttons to the grid pane*/
		for (int i = 0; i < test.getDimension(); i++) {
			for(int j = 0; j < test.getDimension(); j++) {
				gridPanel.add(button[i][j]);
			}
			
		}
		
		return gridPanel;		
	}



	/**
	 * The method that returns the right panel of the main frame. It is basically the border layout which has the 
	 * picross image on the north side, 
	 * --the grid layout of Points and Time taken at the south and 
	 * --the border layout for the reset button and the text area at the center of the pane
	 * @return -- the pane of type JPanel
	 */
	public JPanel rightPanel() {
		JPanel rightPanel = new JPanel(new BorderLayout()); 		//creating the right panel 
		rightPanel.setPreferredSize(new Dimension(220, 600));		//setting the size of the panel
		rightPanel.setBackground(Color.BLUE);	//setting bg color of the panel
		rightPanel.setOpaque(true);
		Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 6);		//setting border of the panel
		Border paneBorder = BorderFactory.createLineBorder(Color.BLUE, 5);	
		gameReset = new Controller();

		/*------------------------------------------------------------------------------------------------*/
		/*Putting the picross image to the right panel at the top*/		
		JButton piccrossButton = new JButton(new ImageIcon(getClass().getResource("piccrossNameMin.jpg")));	
		piccrossButton.setPreferredSize(new Dimension(180, 50));
		piccrossButton.setBorder(lineBorder);
		/*adding the label to the north point of the layout*/
		piccrossButton.setActionCommand("Piccross");
		piccrossButton.addActionListener(gameReset);
		ActionListener c = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame p = new JFrame();
				JPanel panel = new JPanel();
				p.setLayout(new FlowLayout());
				p.setPreferredSize(new Dimension(700, 500));
				JLabel label = new JLabel(new ImageIcon(getClass().getResource("piccrossLogo.jpg")));
				panel.add(label);
				p.add(panel);
				p.pack();
				p.setVisible(true);
				
			}
		};
		piccrossButton.addActionListener(c);
		rightPanel.add(piccrossButton, BorderLayout.PAGE_START);
		/*------------------------------------------------------------------------------------------------*/

		/*panel for button and empty box*/
		JPanel buttonAndBox = new JPanel(new BorderLayout());

		/*------------------------------------------------------------------------------------------------*/
		/*Putting the power button image*/
		JButton powerButton = new JButton(new ImageIcon(getClass().getResource("powerButton.png")));
		powerButton.setPreferredSize(new Dimension(180, 150));
		powerButton.setBackground(Color.BLACK);
		powerButton.setBorder(lineBorder);
		powerButton.setBorder(paneBorder);

		buttonAndBox.add(powerButton, BorderLayout.NORTH);
		/*Creating the second component of the buttonAndBox panel*/  
		textArea= new JTextArea(10, 15);
		textArea.setMargin(new Insets(0,5,5,0)); 			//set text area margins
		textArea.setEditable(false);						//the user cannot edit the textarea.

		buttonAndBox.add(new JScrollPane(textArea));		//adding scroll bar to the textArea

		/*Adding the action listener for the reset button producing the output that game is reset*/
		/*This part to produce output is using the Controller object */
		powerButton.setActionCommand("Game Reset");
		powerButton.addActionListener(gameReset);
		ActionListener gameReset = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
				//GameSplash g = new GameSplash(10000);
				SwingUtilities.invokeLater(new Runnable() {
		            public void run() {  
		            	//new GameView().menu();
		                new GameController(gamePattern); 
		                //g.dispose();
		                
		                
		            }
		        });
			}
		};
		powerButton.addActionListener(gameReset);
		
		//buttonAndBox.add(textArea, BorderLayout.SOUTH);
		rightPanel.add(buttonAndBox, BorderLayout.CENTER);
		/*--------------------------------------------------------------------------------------------------*/


		/*-------------------------------------------------------------------------------------------------*/
		/*Grid layout of showing scores and time taken*/
		/*setting up points and time labels*/
		JPanel pointAndTimePanel = new JPanel(new GridLayout(2,2,5,5)); //making the grid*/
		pointAndTimePanel.setPreferredSize(new Dimension(150, 100));
		JLabel point = new JLabel("You scored: ", JLabel.CENTER);

		/*The styles of the grid elements*/
		point.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		point.setForeground(Color.BLUE);

		pointsScored = new JLabel(score, JLabel.CENTER );
		pointsScored.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		//gameWinOrLoss();
		JLabel time = new JLabel("Time Elasped: ", JLabel.CENTER);
		
		
		/*The style of the grid element*/
		time.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		time.setForeground(Color.BLUE);
		 timeElasped = String.valueOf(count);
		timeTaken = new JLabel(timeElasped, JLabel.CENTER);
		ActionListener a = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                timeTaken.setText(String.valueOf(count)+ "s");
                count++;
            }
        };
        Timer timer = new Timer(1000, a);
        timer.start();
       
        
		timeTaken.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		pointAndTimePanel.add(point);
		pointAndTimePanel.add(pointsScored);
		pointAndTimePanel.add(time);
		pointAndTimePanel.add(timeTaken);
		pointAndTimePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));

		rightPanel.add(pointAndTimePanel, BorderLayout.PAGE_END);
		/*---------------------------------------------------------------------------------------------------*/

		rightPanel.setBorder(paneBorder);


		return rightPanel;
	}
	
	/**
	 * The left panel which has the co-ordinates for all the points to mark in the piccross game. It has the 
	 * grid layout with 5 rows and 0 columns and each row is also a grid layout of 0 rows and 3 columns. This
	 * is done with the normal for loop displaying the digits
	 * @return -- the left pane of type JPanel
	 */
	public JPanel leftPanel() {
		JPanel leftPanel = new JPanel(new GridLayout(5,0));
		leftPanel.setPreferredSize(new Dimension(150, 500));
		leftPanel.setBackground(Color.BLUE);

		/*Putting the 5 rows of grids. Loop is repeated
		 *  5 times as each row of the 3 columns of individual grids
		 */
		String[] leftPanelHints = test.getLeftPanelValues();
		for(int i=0; i<test.getDimension();i++) {
			JPanel aRow = new JPanel(new GridLayout(0,numberOfColumns));
			JLabel c1 = new JLabel(leftPanelHints[i], JLabel.CENTER);
			//JLabel c2 = new JLabel("1", JLabel.CENTER);
			//JLabel c3 = new JLabel("4", JLabel.CENTER);
			c1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 23));
			//c2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 23));
			//c3.setFont(new Font(Font.MONOSPACED, Font.BOLD, 23));
			//c2.setForeground(Color.RED);
			//c3.setForeground(Color.RED);
			c1.setForeground(Color.RED);
			aRow.add(c1);
			//aRow.add(c2);
			//aRow.add(c3);
			aRow.setBorder(BorderFactory.createLineBorder(Color.RED, 8));	//setting the border
			aRow.setBackground(Color.WHITE);
			leftPanel.add(aRow);	//adding to the main panel 
		}



		return leftPanel;
	}

	/**
	 * This method also serves as the provider of co-ordinates for the points but is exactly opposite to the 
	 * left panel. Here there is the grid layout of 0 rows and 5 columns and each grid serves as the grid layout of 
	 * 3 rows and 0 columns. This is done with the for loop that at a time creates 5 same grids 
	 * @return-- top panel of type JPanel
	 */
	public JPanel topPanel() {
		JPanel topPanel = new JPanel(new GridLayout(0,5)); //main grid
		topPanel.setPreferredSize(new Dimension(500, 100)); //setting the dimensions
		//topPanel.setBackground(Color.YELLOW);

		/*Putting the 5 columns of grids. Loop is repeated
		 *  5 times as each row of the 3 rows of individual grids
		 */
		String[] topPanelHints = test.getTopPanelValues();
		for(int i=0; i<test.getDimension();i++) {
			JPanel aColumn = new JPanel(new GridLayout(numberOfRows,0));
			JLabel c1 = new JLabel(topPanelHints[i], JLabel.CENTER);
			//JLabel c2 = new JLabel("3", JLabel.CENTER);
			//JLabel c3 = new JLabel("5", JLabel.CENTER);
			c1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 23));
			//c2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 23));
			//c3.setFont(new Font(Font.MONOSPACED, Font.BOLD, 23));
			//c2.setForeground(Color.RED);
			//c3.setForeground(Color.RED);
			c1.setForeground(Color.RED);
			aColumn.add(c1);
			//aColumn.add(c2);
			//aColumn.add(c3);
			aColumn.setBorder(BorderFactory.createLineBorder(Color.RED, 8));	//setting the border
			aColumn.setBackground(Color.WHITE);
			topPanel.add(aColumn);	//adding to the main panel seven times as 7 columns and 0 rows
		}

		return topPanel;
	}

	/**
	 * This has the main checkbox that starts the game. It uses the flow layout.
	 * @return -- JPanel
	 */
	public JPanel markPanel() {
		JPanel markPanel = new JPanel();	
		JCheckBox checkBox = new JCheckBox();
		markPanel.setPreferredSize(new Dimension(100, 100));
		markPanel.setBackground(Color.green);
		//an event is created 
		//cObject = new Controller();

		markPanel.setLayout(new FlowLayout());  //Setting up the Layout manager to FlowLAyout for the checkBox in MarkPanel
		ActionListener a = null;
		checkBox.setText("Mark Panel");
		//markPanelSelected = true;
		
			a = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(markPanelSelected == false) {
						textArea.append("Check Selected\n" );
						markPanelSelected = true;
					}
					else{
						
						textArea.append("Check Unselected\n");
						markPanelSelected = false;
							
						
					}
				}			
			};
			
		
	
		
		//checkBox.setActionCommand("Game Start");
		//event object sent to the registered event listener i.e. the addActionListener()
		
		checkBox.addActionListener(a);
		markPanel.add(checkBox);
		return markPanel;
	}
	
	/**
	 * This method basically creates the button whose value is taken from the array and whose output produced is taken 
	 * from the string array corresponds to that index. Both the array values are passed to the method parameters. 
	 * The controller object as parameter is used as the action listener for the button and in the 
	 * action command it has the string values to display. This method on creating these actions on the button 
	 * return it to the main grid panel.
	 * @param dn -- string value of the button
	 * @param ac -- string value that is teh output when the particular button is pressed
	 * @param hn -- the controller object
	 * @return the button
	 */
	private JButton createButton(String ac, boolean hintItem, Controller hn) {
		JButton button;
		button = new JButton();
		
		//newButton.setFont(new Font(newButton.getFont().getName(), newButton.getFont().getStyle(), 15));
		button.setActionCommand("Pos " + ac + " clicked.");
		ActionListener q = new ActionListener() {
			
			public void actionPerformed(ActionEvent w) {
				
				if(markPanelSelected == true) {
					if(hintItem == false) {
						button.setBackground(markedColor);
						button.setEnabled(false);
						pointsAchieved++;
						pointsScored.setText(String.valueOf(pointsAchieved));
						buttonName[i][j] = true;
						counterVariable++;
						System.out.println("counter = " + counterVariable);
					}
					else {
						button.setBackground(incorrectColor);
						button.setEnabled(false);
						pointsAchieved--;
						pointsScored.setText(String.valueOf(pointsAchieved));
						buttonName[i][j] = false;
						counterVariable++;
						System.out.println("counter = " + counterVariable);
					}
				}
				else {
					if(hintItem == true) {
						button.setBackground(correctColor);
						button.setEnabled(false);
						pointsAchieved++;
						pointsScored.setText(String.valueOf(pointsAchieved));
						buttonName[i][j] = true;
						counterVariable++;
						System.out.println("counter = " + counterVariable);
					}
					else {
						button.setBackground(incorrectColor);
						button.setEnabled(false);
						pointsAchieved--;
						pointsScored.setText(String.valueOf(pointsAchieved));
						buttonName[i][j] = false;
						counterVariable++;
						System.out.println("counter = " + counterVariable);
					}
				}
				if(counterVariable == 25) {
					gameWinOrLoss();
				}
				
			}
			
		};
		
		button.addActionListener(q);
		button.addActionListener(hn);
	
		return button;
	}
	/**
	 * This method is used for the screen that is displayed upon the requested image address
	 * @param imageAddress -- the image to be included in the frame
	 */
	public void resultantScreen(String imageAddress) {
		JFrame f = new JFrame();
		JPanel screenPanel = new JPanel();		
		f.setLayout(new FlowLayout());
		f.setMinimumSize(new Dimension( 800, 500));
		//calculating the screen's size for intact positioning
		/*
		int width =  534+10; 
	    int height = 360+10;
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screen.width-width)/2;
	    int y = (screen.height-height)/2;
	    
	    //set the location and the size of the window
	    setBounds(x,y,width,height);*/
	    
	    
	    /*setting the image for the splash*/
	    JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource(imageAddress)));
	    screenPanel.add(imageLabel);
	    f.add(screenPanel);
	    f.setVisible(true);
	}
	/**
	 * The method is basically the decision that whether the user will win or lose the game
	 * It prints the points earned to the console and paste the win picture or the loose picture accordingly.
	 * 
	 */
	public void gameWinOrLoss() {	
		System.out.println("Points:= " + pointsScored.getText());
		if(pointsScored.getText().equals(String.valueOf(test.getDimension() * test.getDimension()))) {
			resultantScreen("gamepicwinner.png");
		}
		else {
			resultantScreen("gamepicend.png");
		}
	}
	

	
	/**
	 * returning the time taken to play the game
	 * @return timeTaken.getText() -- the string part of the label
	 */
	public String totalTime() {
		return timeTaken.getText();
	}

	/**
	 * This method sets the extra properties of the frame make it visible to the screen.
	 */
	public void executingFrame() {
		pack();
		setVisible(true);
		//setResizable(true);
	}

	
	/**
	 * The menu of the piccross game which has two menu items and 
	 * which prints the new game, provides the solution of the game and which allows the user to change the colors
	 * and thereby it also prints the information about the game in teh about dialog box.
	 * @return -- returns the menu bar
	 */
	public JMenuBar menu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		JMenuItem newItem = new JMenuItem("New",  new ImageIcon(getClass().getResource("piciconnew.gif")));
		JMenuItem solutionItem = new JMenuItem("Solution", new ImageIcon(getClass().getResource("piciconsol.gif")));
		JMenuItem exitItem = new JMenuItem("Exit", new ImageIcon(getClass().getResource("piciconext.gif")));
		
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		JMenuItem colorItem = new JMenuItem("Colors", new ImageIcon(getClass().getResource("piciconcol.gif")));
		JMenuItem aboutItem = new JMenuItem("About", new ImageIcon(getClass().getResource("piciconabt.gif")));
		
		menuBar.add(gameViewClass.buildMenu(gameMenu, new Object[] { newItem ,null, solutionItem, null, exitItem}, this));
		menuBar.add(gameViewClass.buildMenu(helpMenu, new Object[] {colorItem, null, aboutItem}, this));
		
		
		/*functionalities to the buttons*/
		exitItem.addActionListener(new ActionListener() {
			/**
			 * action performed -- exiting the game
			 */
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}
		});
		
		
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameViewClass.aboutDialog();
				
			}
		});
		
		newItem.addActionListener(new ActionListener() {
			/**
			 * action performed i.e. playing the new game 
			 */
			public void actionPerformed(ActionEvent e) {
				dispose();
				GameView g = new GameView(10000, p1.getGameBoard());
				SwingUtilities.invokeLater(new Runnable() {
		            public void run() {  
		            	//new GameView().menu();
		                new GameController(gamePattern); 
		                g.dispose();
		                
		                
		            }
		        });
				
			}
		});
		
		
		solutionItem.addActionListener(new ActionListener() {
			/**
			 * action performed--- i.e. displaying the solution of the game
			 * @param e -- ActionEvent variable that 
			 */
			public void actionPerformed(ActionEvent e) {
				System.out.println("Here you go with the solution:-");
				test.getLeftPanelValues();
				test.getTopPanelValues();
				/*
				 * powerButton.setActionCommand("Game Reset");
		powerButton.addActionListener(gameReset);
				 */
			
				for(int y=0;y<test.getDimension();  y++) {
					solutionItem.setActionCommand(test.getValStrLeft()[y]);
					textArea.append(test.getValStrLeft()[y] + "\n");
					for(int z=0;z<test.getDimension(); z++) {
						if(test.getValStrLeft()[y].charAt(z) == '0') {
							button[y][z].setBackground(markedColor);
							button[y][z].setEnabled(false);
						}
						else if(test.getValStrLeft()[y].charAt(z) == '1') {
							button[y][z].setBackground(correctColor);
							button[y][z].setEnabled(false);
						}
						System.out.print(test.getHint01Values()[y][z] + " ");
					}
					System.out.println();
				}
				
				
			}
		});
		//solutionItem.addActionListener(gameReset);
		
		colorItem.addActionListener(new ActionListener() {
			
			/**
			 * color palette selection and choosing the color
			 */
			public void actionPerformed(ActionEvent e) {
				colorDialog();
			}
		});
		return menuBar;
	}
	

	/**
	 * The color dialog displays the color palette and helps in choosing the color. Basically it is a 
	 * dialog box frame which has the colors displays for the correct, incorrect and marked tiles and 
	 * secondly the buttons from which we can change the colors
	 */
	
	private void colorDialog(){
        JDialog colorDialog = new JDialog(this , "Color Model", true);
        colorDialog.setLayout( new GridLayout(2, 3));

        JButton button_1 = new JButton();
        JButton button_2 = new JButton();
        JButton button_3 = new JButton();

        button_1.setBackground(correctColor);
        button_2.setBackground(markedColor);
        button_3.setBackground(incorrectColor);

        colorDialog.add(button_1);
        colorDialog.add(button_2);
        colorDialog.add(button_3);

        JButton btn_1 = new JButton("Color1:Correct");
        JButton btn_2 = new JButton("Color2:Marked");
        JButton btn_3 = new JButton("Color3:Error");

        colorDialog.add(btn_1);
        colorDialog.add(btn_2);
        colorDialog.add(btn_3);

        btn_1.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                Color newColor = colorSelection(correctColor, colorDialog);
                changeCellColors(correctColor, newColor);
                correctColor = newColor;
                button_1.setBackground(correctColor);
            }
        });
        btn_2.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                Color newColor = colorSelection(markedColor, colorDialog);
                changeCellColors(markedColor, newColor);
                markedColor = newColor;
                button_2.setBackground(markedColor);
            }
        });
        btn_3.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                Color newColor = colorSelection(incorrectColor, colorDialog);
                changeCellColors(incorrectColor, newColor);
                incorrectColor = newColor;
                button_3.setBackground(incorrectColor);
            }
        });
        colorDialog.pack();
        colorDialog.setLocationRelativeTo(null);
        colorDialog.setVisible(true);
    }
	
	
	/**
	 * 
	 * @param initColor -- the color that is choosen on teh console.
	 * @param f -- the dialog where to select and change the color from the palette
	 * @return -- it returns the new color color that is selected
	 */
	 public Color colorSelection(Color initColor, JDialog f){
	        Color color = JColorChooser.showDialog(f, "Choose Color", initColor);
	        if(color == null){
	            color = initColor;
	        }
	        return color;
	    }

	/**
     * Update cell color
     * @param - oldColor
     * @param  - newColor
    */
    public void changeCellColors(Color oldColor, Color newColor){
        for(int i = 0; i < test.getDimension() ; i++){
        	for(int  j=0; j < test.getDimension(); j++) {
        		 if(button[i][j].getBackground() == oldColor){
                     button[i][j].setBackground(newColor);
                 }
        	}
           
        }
    } 

	/**Inner class Controller to record the output of the events such as pressing the buttons on the screen
	 * implementing the ActionListener interface. Here the event is listened and appended to the text area of the
	 * control panel.
	 * @author hp
	 *
	 */
	private class Controller implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			textArea.append(e.getActionCommand() + "\n");
		}
	}


	
	/**
	 * auto -generated methods by implementing the interface 
	 * @param  -- mouse event object when the option is selected 
	 */
	@Override
	public void menuSelected(MenuEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Menu Selected");
	}

	
	/**
	 * The function when the menu is deselected
	 * @param  - mouse event object when the option is deselected
	 */
	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Menu Deselected");
		
	}

	
	/**
	 * When the menu is cancelled
	 * @param mouse event object when the option of the event is cancelled
	 */
	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Menu Cancelled");
		
	}

	@Override
	/**
	 * the actionPerformed() method to record the action performed by the actionlistener
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
