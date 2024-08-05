/** ReactionTest.java
  * @description A Reaction test game that took an unbelievable amount of hours making after giving up on chicken game :c
	* IMPORTANT: References will be listed at the bottom with a given number to its reference. ex: "#ref 1" means its referencing the first reference at the bottom of the code followed by author, followed by reason/use
  * @author Matthew Lungu
  * @version 1.0, 5/7/23
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReactionTest extends JFrame {
			// Attributes
   private JPanel panel;
   private JLabel label;
   private boolean uClick;
   private long startTime;
   private JTextArea directions;
   private JButton play, end, endButton, tryAgainButton;
   private Font directionFont = new Font("Comic Sans MS", Font.BOLD, 20);
	
			// Methods
	
				/** Main method **/
    public static void main(String[] args) {
        ReactionTest game = new ReactionTest();
        game.titleScreen();
    }
	
	
	/** Makes the title screen when first running the program **/
    public void titleScreen() {
        	// Create a new JFrame
        JFrame frame = new JFrame("Reaction Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));

					// Create the main panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        	// Create a new JLabel to display the directions
        JLabel directionsLabel = new JLabel("<html>Directions:<br/> The screen will turn turn red after you hit begin. Once it turns green, you must click it as fast as you can.<html>"); // #ref 7
        directionsLabel.setHorizontalAlignment(JLabel.CENTER);
        directionsLabel.setFont(directionFont);

					// Create a new panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        makeButtons();
        buttonPanel.add(play);
        buttonPanel.add(end);

        	// Add the components to the main panel
        panel.add(directionsLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        	// Add the main panel to the content pane of the JFrame
        frame.getContentPane().add(panel);

        	// Size and display the JFrame
        frame.pack();
        frame.setVisible(true);
    }
	
	
				/** Starts the game**/
    public void startTest() {
					// Removes everything from the panel to start the test (Put here for replayability)
        panel.removeAll();

        	// Clear the screen by setting the panel color to red
        panel.setBackground(Color.RED);	// #ref 1

        	// Add a mouse listener to the panel to detect clicks
        panel.addMouseListener(new ReactionTimeMouseListener()); // #ref 2

        	// Change the panel color to green after a random time
        Timer timer = new Timer((int) (Math.random() * 4000) + 1000, new ActionListener() { // #ref 4
       public void actionPerformed(ActionEvent e) { 
                panel.setBackground(Color.GREEN);
							uClick = true; // Sets it so user can click screen after screen turns green
							startTime = System.currentTimeMillis(); //	#ref 3
            }
        });
        timer.setRepeats(false); // #ref 5
        timer.start();
    }
	
	
			/** Makes the result screen **/
		public void resultScreen(long reactionTime) {
					// Create a panel for the results
    		JPanel resultPanel = new JPanel(new BorderLayout());

    			// Create a JLabel to display the reaction time
				JLabel resultLabel = new JLabel("Your reaction time was " + (reactionTime / 1000.0) + " seconds!"); //divides the time by 1k b/c its in milliseconds
    		resultLabel.setFont(directionFont);
				resultLabel.setHorizontalAlignment(JLabel.CENTER);

    			// Add the result label to the result panel
				resultPanel.add(resultLabel, BorderLayout.CENTER);

    			// Create a new panel for the buttons
				JPanel buttonPanel = new JPanel(new FlowLayout()); // #ref 6
				makeButtons();
        buttonPanel.add(tryAgainButton);
        buttonPanel.add(endButton);

					// Add the button panel to the result panel
    		resultPanel.add(buttonPanel, BorderLayout.SOUTH);

					// Add the result panel to the main panel and revalidate the frame
				panel.removeAll();
    		panel.add(resultPanel, BorderLayout.CENTER);
    		panel.revalidate(); //needed after removing all
		}

	
			/** Makes buttons **/
    public void makeButtons() {
				tryAgainButton = new JButton("Try Again");
				endButton = new JButton("End");
        play = new JButton("Begin");
        end = new JButton("Stop");
			  tryAgainButton.setFont(directionFont);
        endButton.setFont(directionFont);
        play.setFont(directionFont);
        end.setFont(directionFont);
				//tryAgainButton.setPreferredSize(new Dimension(100, 50));
        //endButton.setPreferredSize(new Dimension(100, 50));
        play.setPreferredSize(new Dimension(100, 50));
        end.setPreferredSize(new Dimension(100, 50));

        	// Button listener
        ButtonListener listener = new ButtonListener();
        play.addActionListener(listener);
        end.addActionListener(listener);
				tryAgainButton.addActionListener(listener);
        endButton.addActionListener(listener);	
    }
	
	
		private class ReactionTimeMouseListener extends MouseAdapter { // # Also #ref 2
				/** Responses to mouse presses */
    	public void mousePressed(MouseEvent e) {
        if (uClick) {
            long endTime = System.currentTimeMillis(); // #ref 3 
            long reactionTime = endTime - startTime;
            resultScreen(reactionTime);
            uClick = false;
        }
    	}
		}


    private class ButtonListener implements ActionListener {
        	/** Responses to button presses */
        public void actionPerformed(ActionEvent event) {

            Object o = event.getSource();
            JButton b = (JButton) o;

            if (b == end) {
                System.exit(0);
            } else {
								panel.removeAll();
                startTest();
								
            }
        }
    }
}

/* =========== REFERENCES =========== 

	1. https://stackoverflow.com/questions/1081486/setting-background-color-for-a-jframe (Auth: iwanttoprogram)(Used to setbackground to a specific color vs. hardcoding the RGB values)
	2. https://www.tutorialspoint.com/awt/awt_mouseadapter.htm (Auth: no author :c)(Used to recieve mouse events aka take mouse click input)
	3. https://www.tutorialspoint.com/computer-elapsed-time-of-an-operation-in-milliseconds-in-java#:~:text=To%20compute%20the%20elapsed%20time,currentTimeMillis()%20method. (Auth: none :c)(Used to get time elapsed)
	4. https://www.javatpoint.com/java-actionlistener (No Auth listed)(Used to start time after screen turns green and get action listener aka mouse click)
	5. https://docs.oracle.com/javase/8/docs/api/javax/swing/Timer.html (auth: none)(Used to set timer to non repeatable aka sends only one action event)
	6. https://www.javatpoint.com/FlowLayout (Auth: none listed :c)(Used to set the buttons right beside each other, I had trouble trying to figure it out because one button was just taking the whole bottoms of the screen)
	7. https://stackoverflow.com/questions/1090098/newline-in-jlabel#:~:text=Surround%20the%20string%20with%20%3Chtml,lines%20with%20.&text=just%20a%20little%20correction%3A%20use,any%20closing%20tags)(Auth: freitass & Nathan)(Used to set a newline in JLabel aka break line aka \n doesn't work >:c)

*/
