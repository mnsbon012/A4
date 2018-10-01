

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

public class TreeGrow {
	private static long startTime = 0;
	private static int frameX;
	private static int frameY;
	private static ForestPanel fp;
	static JPanel optionsPanel;
	static JButton resetB;
	static JButton pauseB;
	static JButton playB;
	static JButton endB;
	static JLabel yearL;
	static int year;

	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	public static void setupGUI(int frameX,int frameY,Tree [] trees) {
		Dimension fsize = new Dimension(800, 800);
		// Frame init and dimensions
    	JFrame frame = new JFrame("Photosynthesis"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setPreferredSize(fsize);
    	frame.setSize(800, 800);
    	
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      	g.setPreferredSize(fsize);

		fp = new ForestPanel(trees);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		JScrollPane scrollFrame = new JScrollPane(fp);
		fp.setAutoscrolls(true);
		scrollFrame.setPreferredSize(fsize);
	    g.add(scrollFrame);

		optionsPanel = new JPanel();

		resetB = new JButton("Reset");
		pauseB = new JButton("Pause");
		playB = new JButton("Play");
		endB = new JButton("End");
		yearL = new JLabel("year: "+year);		//NOT TEXT FIELD!!


		optionsPanel.add(resetB);
		optionsPanel.add(pauseB);
		optionsPanel.add(playB);
		optionsPanel.add(endB);
		optionsPanel.add(yearL);


		resetB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int t=0; t<trees.length ; t++){
					trees[t].setExt((float)0.4);
				}
				year = 0;
				System.out.println("reset");
			}
		});

		pauseB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("pause");
			}
		});

		playB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread fpt = new Thread(fp);
				fpt.start();
				System.out.println("play");
			}
		});

		endB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				System.out.println("end");
			}
		});

//add buttons to panel then add panel to frAME

      	frame.setLocationRelativeTo(null);  // Center window on screen.
      	frame.add(g); //add contents to window
        frame.setContentPane(g);

        frame.add(optionsPanel);

        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}
	
		
	public static void main(String[] args) {
		SunData sundata = new SunData();
		
		// check that number of command line arguments is correct
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java treeGrow.java intputfilename");
			System.exit(0);
		}
				
		// read in forest and landscape information from file supplied as argument
		sundata.readData(args[0]);
		System.out.println("Data loaded");

/*		sundata.sunmap.printArr();
		sundata.sunmap.shadow(sundata.trees[0]);
		System.out.println("");
		sundata.sunmap.printShadedArr();
	*/
		frameX = sundata.sunmap.getDimX();
		frameY = sundata.sunmap.getDimY();
		setupGUI(frameX, frameY, sundata.trees);
		
		// create and start simulation loop here as separate thread
	}
}