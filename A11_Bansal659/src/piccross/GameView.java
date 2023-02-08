package piccross;

/**--------------------	UPDATED VERSION -------- ASSIGNMENT-01------------------------------- 
 * Assessment:- Assignment01
 * Student Name:- Harsh Bansal
 * Section:- CST8221_300_302
 * Lab Professor Name:- Prof. Daniel Cormier
 * Submission Date:- November 13, 2021
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
 *  REFERENCE FOR THE MENUS AND MENU ITEMS:- LAB CODE PROVIDED BY PROF. DANIEL AND PROF. PAULO
 *  REFERENCE FOR THE COLOR CHOOSER DIALOG BOX -- ORACLE WEBSITE
 *  Special thanks for the project:- Lecture notes, lab session demos, weekly code shared by Prof. Paulo Sousa, Prof. Daniel Cormier 
 *  and Prof. Svillen Ranev , Prof. Daniel's Shared Announcements regarding the MVC Model
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/** 
 * @author harsh
 * @version 2
 *This class holds the game view of the UI. It has the methods to build the menu, initialize the game, 
 *splash screen, and the color chooser option.
 */
public class GameView extends JWindow implements ActionListener, MenuListener {
	private static JDialog dialogBox; /** the dialog box about*/	
	private static final long serialVersionUID = 1L;
	GameModel test01;
	private String gamePattern;
	//Protocol p;
	/**
	 * Default - non-paraneterized constructor
	 */
	public GameView() {
		
	}
	
	/**
	 * The constructor that that the parameter for the duration of the splash screen
	 * @param duration -- teh duration of the splash screen
	 */
	public GameView(int duration, String a) {
		gamePattern = a;
		test01 = new GameModel(a); /**game model object having all the logic behind the game*/
		
	}	
	
	
	/**
	 * The method splashScreen() is of return type void and it displays the window in the border layouts. This 
	 * screen shows the picross image in the sense that teh game is loading which is clearly shown by the progress
	 * bar from class JProgressBar and the third component is the title which identifies the team.
	 * 
	 */
	public void splashScreen() {
		JPanel screenPanel = new JPanel(new BorderLayout());		
		//calculating the screen's size for intact positioning
		int width =  534+10; 
	    int height = 360+10;
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screen.width-width)/2;
	    int y = (screen.height-height)/2;
	    
	    //set the location and the size of the window
	    setBounds(x,y,width,height);
	    
	    
	    /*setting the image for the splash*/
	    JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("piccrossLogo.jpg")));
	    
	    /*making the title label to identify the student*/
	    JLabel titleLabel = new JLabel("HARSH BANSAL SPLASH SCREEN INTERFACE -- ASSIGNMENT01");
	    titleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD,15));
	    titleLabel.setForeground(Color.RED);
	    //setting the message
	    JLabel progressMessage = new JLabel();
	    JProgressBar progressBar=new JProgressBar();
	    
	    /*giving the border to the image*/
	    imageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
	    
	    /*styling the progress bar*/
	    progressBar.setBounds(x,y,width,height);//Setting Location and size
        progressBar.setBorderPainted(true);//Setting border painted property
        progressBar.setStringPainted(true);//Setting String painted property
        progressBar.setBackground(Color.WHITE);//setting background color
        progressBar.setForeground(Color.BLACK);//setting foreground color
        //progressBar.setValue(0);
        
	    
		screenPanel.add(imageLabel, BorderLayout.NORTH);
		screenPanel.add(progressBar, BorderLayout.CENTER);
		
		screenPanel.add(titleLabel, BorderLayout.SOUTH);
		
		
		int i=0;//Creating an integer variable and intializing it to 0
		 
		/*while loop for the execution of the progress bar*/
        while( i<=100)
        {
            try{
                Thread.sleep(100);//delaying execution for 50 milliseconds
                progressBar.setValue(i);//Setting value of Progress Bar
                progressMessage.setText("LOADING "+Integer.toString(i)+"%");//Setting text of the message JLabel
                i++;               
                setContentPane(screenPanel); //setting the screen pane
        		setVisible(true); //setting frame visibility
        		//if the progress reaches 100 then the frame should be displosed off 
        		 if(i==100) {
                     dispose();
                     setVisible(false);
        		 }
        		 
            }catch(Exception e){
                e.printStackTrace();
            }//end catch
        }//end while
        
       
	}//end method
	

	/**
	 * 
	 * SPECIAL THANKS --
	 * REFERENCE FOR THE CODE PIECE:- LAB DEMO CODE SHARED BY PROF. PAULO AND PRO. DANIEL ON THE TOPIC OF
	 * MENUS AND DIALOGS
	 * 
	 * This method creates the menu on the menu bar. It calls the function buildMenuItems() to create the 
	 * list of menu items.
	 * 
	 * @param parent       if the parent is a instance of JMenu it adds items to the
	 *                     menu. if the parent is a string it creates the menu and
	 *                     then adds items to the menu.
	 * @param items        list of references to menu items names (strings). If the
	 *                     references null, a separator is added.
	 * @param eventHandler event handler for the menu items.
	 * @return a reference to JMenu with optional menu items. null if parent is not
	 *          an instance of String or JMenu, or items is null
	 * 
	 */
	public JMenu buildMenu(Object parent, Object[] items, Object eventHandler) {
		JMenu m = null;
		if (parent instanceof JMenu)
			m = (JMenu) parent;
		else if (parent instanceof String)
			m = new JMenu((String) parent);
		else
			return null;
		if (items == null)
			return null;
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null)
				m.addSeparator();
			else
				m.add(buildMenuItem(items[i], eventHandler));
		}

		return m;
	}
	
	/**
	 * 
	 * SPECIAL THANKS --
	 * REFERENCE FOR THE CODE PIECE:- LAB DEMO CODE SHARED BY PROF. PAULO AND PRO. DANIEL ON THE TOPIC OF
	 * MENUS AND DIALOGS
	 * 
	 * 
	 * This method creates a menu item that is further put into the menu.
	 * 
	 * @param item -- the menu item object created -- if the parent object is an instance of menu item
	 * then it creates and handles the event but if the parent is a string then it creates a menu
	 * @param eventHandler -- eventhandler for the menu items
	 * @return --r which is the menu item.
	 */

	public JMenuItem buildMenuItem(Object item, Object eventHandler) {
		JMenuItem r = null;
		if (item instanceof String)
			r = new JMenuItem((String) item);
		else if (item instanceof JMenuItem)
			r = (JMenuItem) item;
		else
			return null;

		if (eventHandler instanceof ActionListener)
			r.addActionListener((ActionListener) eventHandler);
		else
			return null;
		return r;
	}
	
	
	/**
	 * This method acts like the main or the executing method for the solash screen. It indicates that if the 
	 * length of duration is less than default duration i.e. 10000 then set to it and if any duration is set, 
	 * then set ot only that duration otherwise set the duration to the default duration i.e. 10 sec or 10000msec 
	 * @param args -- string array for setting the duration
	 */
	public void splashExecution() {		
		int duration = 10000;
		/*
		if(args.length == 3){
	    	try{
	    		duration = Integer.parseInt(args[3]);
	    	}catch (NumberFormatException mfe){
	    	  System.out.println("Wrong command line argument: must be an integer number");
	    	  System.out.println("The default duration 10000 milliseconds will be used");
	    	  //mfe.printStackTrace();	
	    	} 
		}*/
	    //Create the screen object
	    
	    //Display the screen 
	   //splashScreen();
	   GameView splashWindow = new GameView(duration, gamePattern);
	   //splashWindow.dispose();
	    //newGame(splashWindow);
	    
	    //Connecting point between game UI and splash screen
	    
	   
	}//end method
	
	
	/**
	 * Method that initializes the new game 
	 * @param g -- an object of GameView class
	 */
	public void newGame(GameView g) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {  
            	//new GameView().menu();
                new GameController(gamePattern); 
                //g.dispose();
                
                
            }
        });
	}
	
	/**
	 * returning the points scored
	 * @return pointsScored.getText() = the points that the player got
	 */
	public String pointsOfTheGame() {
		String points = new GameController(gamePattern).getPointsLabel().getText();
		System.out.println("Game controller--points:- " + points);
		return points;
	
	}
	
	
	
	/**
	 * The method that creates the dialog box which is created using JDialog and putting the frame in 
	 * that dialog box
	 * Ok button disposes the dialog box
	 */
	
	public void aboutDialog() {
		JFrame f = new JFrame();
		f.setLayout(new BorderLayout());
		//d.setSize(500, 500);
		dialogBox = new JDialog(f, "About Dialog", true);
		dialogBox.setLayout(new FlowLayout());
		JButton b = new JButton("OK");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogBox.setVisible(false);
			}
		});
		dialogBox.add(new JLabel(new ImageIcon(getClass().getResource("piccrossLogo.jpg"))));
		dialogBox.add(b);
		dialogBox.pack();
		dialogBox.setSize(700, 500);
		dialogBox.setVisible(true);
	}
	
	@Override
	/**
	 * auto -generated methods by implementing the interface 
	 * @param mouse event object when the option is selected 
	 */
	public void menuSelected(MenuEvent e) {
		
		System.out.println("Menu Selected");
	}
	
	@Override
	/**
	 * @param mouse event object when the option is deselected
	 */
	public void menuDeselected(MenuEvent e) {
		
		System.out.println("Menu Deselected");
		
	}

	@Override
	/**
	 * @param mouse event object when the option of the event is cancelled
	 */
	public void menuCanceled(MenuEvent e) {
		
		System.out.println("Menu Cancelled");
		
	}

	@Override
	/**
	 * the actionPerformed() method to record the action performed by the actionlistener
	 */
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
