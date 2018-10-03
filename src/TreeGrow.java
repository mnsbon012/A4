

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

public class TreeGrow
{
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static ForestPanel fp;
	static JPanel optionsPanel;
	static JButton resetB;
	static JButton pauseB;
	static JButton playB;
	static JButton endB;
	static JLabel yearL;
	static Simulation s;
	//static int year;

	// start timer
	private static void tick()
	{
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock()
	{
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	public static void setupGUI(int frameX,int frameY,Tree [] trees, Land land)
	{
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

		frame.setLocationRelativeTo(null);  // Center window on screen.
		frame.add(g); //add contents to window

		frame.setContentPane(g);

		optionsPanel = new JPanel();

		resetB = new JButton("Reset");
		optionsPanel.add(resetB);

		pauseB = new JButton("Pause");
		optionsPanel.add(pauseB);

		playB = new JButton("Play");
		optionsPanel.add(playB);

		endB = new JButton("End");
		optionsPanel.add(endB);

		yearL = new JLabel("year: ");
		optionsPanel.add(yearL);
		//fp.setJLable(yearL);

		frame.add(optionsPanel);


		resetB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//fp.reset();
				s.reset();
				System.out.println("reset");
			}
		});

		pauseB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//fp.pause
				s.pause();
				System.out.println("pause");
			}
		});

		playB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!s.play){
					s.resume();
				}
				else {
					Thread runner = new Thread(s);
					runner.start();
					System.out.println("play");
				}
			}
		});

		endB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s.end();
				System.exit(0);
				//System.out.println("end");
			}
		});

//add buttons to panel then add panel to frAME

		//frame.add(optionsPanel);

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
		s = new Simulation(sundata);
		setupGUI(frameX, frameY, sundata.trees, sundata.sunmap);


		// create and start simulation loop here as separate thread

	}
}