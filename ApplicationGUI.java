
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;  

public class ApplicationGUI {
	
	// constants
	private final static int FRAME_WIDTH = 800;
	private final static int FRAME_HEIGHT = 600;
	private final static int LEFT_PANEL_YCOORD = 50;
	
	// components and service
	private JFrame frame; // Application UI Wrapper
	private DataService dataService; // Import data service
	

	/**
	 * Constructor of the GUI class
	 */
	public ApplicationGUI() {
		this.frame = new JFrame("Consensus Tool v1.0.0");//creating instance of JFrame  
		initWindow(); //self invoking the UI
		dataService = new DataService();
	}
	
	/**
	 * Add simple elements to window
	 */
	private void initWindow() {
			
		//left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0,LEFT_PANEL_YCOORD, FRAME_WIDTH/2, FRAME_HEIGHT-50);
		leftPanel.setBackground(Color.GRAY);
		frame.add(leftPanel);
		
		//right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(FRAME_WIDTH/2, 0, FRAME_WIDTH/2, FRAME_HEIGHT);
		rightPanel.setBackground(Color.DARK_GRAY);
		frame.add(rightPanel);
		
		//left label
		JLabel leftLabel = new JLabel("Enter your data (name + sequece):");
		leftLabel.setHorizontalAlignment(JLabel.CENTER);
		leftLabel.setBounds(0, 0, leftPanel.getWidth(), leftPanel.getY());
		frame.add(leftLabel);
		
		//left text area
		/*JTextArea inputText = new JTextArea("[separate items by new lines] \n"
		 		+ "[White space sensitives] \n"
				+ "[Sequences must have the same length]\n"
				+ "Example: \n"
				+ "human atcatcatc \n"
				+ "cat   attccggtc");
		*/
		
		JTextArea inputText = new JTextArea(""
				+ "human agtctgtcgtct\n"
				+ "cat agccggtaggaa\n"
				+ "rat agcccgtcgggt\n"
				+ "pig agtctgtcgcgt\n"
				+ "dog agctacgtagcc\n");
		//create scroll for input area
		JScrollPane inputBoxScroll = new JScrollPane(
				inputText,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
				);
		inputBoxScroll.setMinimumSize(new Dimension(leftPanel.getWidth(),leftPanel.getHeight()));
		inputBoxScroll.setPreferredSize(new Dimension(leftPanel.getWidth(),leftPanel.getHeight()-80));
		//create border
		Border border = BorderFactory.createLineBorder(Color.BLACK);
	    inputText.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		//doesn't need to add inputText as already included in inputBoxScroll
		leftPanel.add(inputBoxScroll);

		//button to build the data
		JButton buildButton = new JButton();
		buildButton.setText("Build");
		buildButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = inputText.getText();
				
				if(dataService.isValidData(s)) {
					rightPanel.removeAll();
					dataService.insertData(s);
					dataService.generateData();
					
					//Create tabs for processed data 
					JTabbedPane tabbedPane = new JTabbedPane();
					tabbedPane.setTabPlacement(JTabbedPane.LEFT);
					makeTabs(tabbedPane);
			        //rightPanel.add(tabbedPane);
			      //create scroll for input area
					JScrollPane rightBoxScroll = new JScrollPane(
							tabbedPane,
							JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
							JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
							);
					rightBoxScroll.setMinimumSize(new Dimension(rightPanel.getWidth(),rightPanel.getHeight()));
					rightBoxScroll.setPreferredSize(new Dimension(rightPanel.getWidth(),rightPanel.getHeight()-150));
					//doesn't need to add inputText as already included in inputBoxScroll
				    rightPanel.add(rightBoxScroll);
				    
				    //this part is for input and score a sequence based on data
				    JLabel labelPanel = new JLabel("Enter a sequence to calculate score: ");
			        labelPanel.setBounds(0 ,0 , FRAME_WIDTH/2, FRAME_HEIGHT/6);
			        labelPanel.setForeground(Color.WHITE);
					rightPanel.add(labelPanel);
					
					JTextArea inputText = new JTextArea("human agtctgtcgtct");
					//create scroll for input area
					JScrollPane inputBoxScroll = new JScrollPane(
							inputText,
							JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
							JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
							);
					inputBoxScroll.setMinimumSize(new Dimension(leftPanel.getWidth(),60));
					inputBoxScroll.setPreferredSize(new Dimension(leftPanel.getWidth(),60));
					//create border
					Border border = BorderFactory.createLineBorder(Color.BLACK);
				    inputText.setBorder(BorderFactory.createCompoundBorder(border,
				            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
					//doesn't need to add inputText as already included in inputBoxScroll
					rightPanel.add(inputBoxScroll);
					JButton scoreButton = new JButton();
					scoreButton.setText("Score");
					scoreButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							String s = inputText.getText();
							tabbedPane.setSelectedIndex(3);
							
							if(dataService.isValidData(s)) {
								String splitted = s.split(" ")[1];
								tabbedPane.setComponentAt(3, 
										makeTextPanel( s
								+"\nScore based on PWM: "+ dataService.getScorePWMatrix(splitted)
								+"\nScore based on PPM: "+ dataService.getScorePPMatrix(splitted)));
							}
							else {
								System.out.println(dataService.getValidateError());
								showErrorDialog(frame,dataService.getValidateError());
							}
							
							frame.revalidate();
							frame.repaint();
						}});
					rightPanel.add(scoreButton);
				}
				else {
					System.out.println(dataService.getValidateError());
					showErrorDialog(frame,dataService.getValidateError());
				}
				
				frame.revalidate();
				frame.repaint();
		}});
        
		// Button to reset data
		JButton resetButton = new JButton();
		resetButton.setText("Reset");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				inputText.setText("");
				dataService = new DataService();
				rightPanel.removeAll();
				
				frame.revalidate();
				frame.repaint();
			}});
		
		JButton aboutButton = new JButton();
		aboutButton.setText("About");
		aboutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showAboutDialog(frame);
			}});
		
		
		// Buttons
		buildButton.setBounds(100, 100, 100, 50);
		leftPanel.add(buildButton);
		resetButton.setBounds(100, 100, 100, 50);
		leftPanel.add(resetButton);
		aboutButton.setBounds(100, 100, 100, 50);
		leftPanel.add(aboutButton);

		          
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);//600 width and 500 height  
		
		frame.setLayout(null);//using no layout managers  
		frame.setVisible(true);//making the frame visible  	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	/**
	 * This method show an error Popup on the UI
	 * 
	 * @param frame Take in the current frame 
	 * @param error Take in a string to display in the error dialog
	 */
	private static void showErrorDialog(JFrame frame, String error) {
		JOptionPane.showMessageDialog(frame,
				error,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * This method show an Popup on the UI
	 * 
	 * @param frame take in the current frame
	 */
	private static void showAboutDialog(JFrame frame) {
		String s = "This program helps to build and\n"
				+ "analyze consensus sequence and its matrix.\n"
				+ "Author: Hung Tran && Khoa Pham \n"
				+ "CS 123A @ SJSU Fall 2018";
		JOptionPane.showMessageDialog(frame,
				s,
			    "About this program!",
			    JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * This method add Tabs to TabbedPane component
	 * 
	 * @param tabbedPane Take in the JTabbedPane component
	 */
	private void makeTabs(JTabbedPane tabbedPane) {
		JComponent panel1 = makeTextPanel(dataService.getFreqMatrix()
				+"\n"+ dataService.getConsesusSeq()
				+"\n\n"+ dataService.getResidueSeq());
		tabbedPane.addTab("Consensus", null, panel1,
		                  "Consensus Sequence");
         
        JComponent panel2 = makeTextPanel(dataService.getPWMatrix());
        tabbedPane.addTab("PWMatrix", null, panel2,
                "Position Weighted Matrix");
         
        JComponent panel3 = makeTextPanel(dataService.getPPMatrix());
        tabbedPane.addTab("PPMatrix", null, panel3,
                "Position Probability Matrix");       
        
        JComponent panel4 = makeTextPanel("Enter a sequence to calculate score\n"
        		+ "at the bottom!");        
        tabbedPane.addTab("Scoring", null, panel4,
                "Scoring A Sequence");    
	}
	
	/**
	 * This method return a text area 
	 * 
	 * @param text Take in a string to display on the init JTextArea
	 * @return
	 */
	protected JComponent makeTextPanel(String text) {
        JTextArea panel = new JTextArea(text);
        panel.setEditable(false);
        return panel;
    }	
}