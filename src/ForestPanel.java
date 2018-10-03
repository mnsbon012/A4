
import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.*;
import java.util.*;


public class ForestPanel extends JPanel implements Runnable {
	Tree[] forest;	// trees to render
	List<Integer> rndorder; // permutation of tree indices so that rendering is less structured
	int year;
	Land land;
	private JLabel yearL;

	ForestPanel(Tree[] trees, Land sunMap) {
		forest=trees;
		year = 0;
		land =sunMap;
		// reordering so that trees are rendered in a more random fashion
//		rndorder = new ArrayList<Integer>();
//		for(int l = 0; l < forest.length; l++)
//			rndorder.add(l);
	}

	public int getYear(){
		return year;
	}


	// display the forest of trees
	@Override
	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		g.clearRect(0,0,width,height);
		    
		// draw the forest in different canopy passes
		Random rnd = new Random(0); // providing the same seed gives trees consistent colouring

		// start from small trees of [0, 2) extent
		float minh = 0.0f; //18.0f;
		float maxh = 2.0f; //20.0f;
		for(int layer = 0; layer <= 10; layer++) {
			for(int t = 0; t < forest.length; t++){
				int rt = rndorder.get(t); 
				if(forest[rt].getExt() >= minh && forest[rt].getExt() < maxh) { // only render trees in current band
					// draw trees as rectangles centered on getX, getY with random greenish colour
					g.setColor(new Color(rnd.nextInt(100), 150+rnd.nextInt(100), rnd.nextInt(100)));
					g.fillRect(forest[rt].getY() - (int) forest[rt].getExt(), forest[rt].getX() - (int) forest[rt].getExt(),
						   2*(int) forest[rt].getExt()+1,2*(int) forest[rt].getExt()+1);
				}
				// g.setColor(Color.black);
				// g.fillRect(forest[rt].getY(), forest[rt].getX(), 1, 1); // draw the trunk
			}
			minh = maxh;  // next band of trees
			maxh += 2.0f;
			//maxh=minh;
			//minh-=2.0f;
		}	
	}
		@Override
	public void run() {

		rndorder = new ArrayList<Integer>();
		for (int n=0; n<forest.length; n++){
			rndorder.add(n);
		}

		java.util.Collections.shuffle(rndorder);
		
		// render loop
		while(true) {
			//paintComponent(getGraphics());
			//repaint();
			//for (Tree t:forest){
			//	t.sungrow(land);
			//}// create parallel class that takes in tree array land array and low and high.. 10000
			// forkJoin
			//year++;
			//land.resetShade();;
			//System.out.println(year);
			//yearL.setText("year: "+year);
	//		repaint();
			// reordering so that trees are rendered in a more random fashion
			rndorder = new ArrayList<Integer>();
			for(int l = 0; l < forest.length; l++)
				rndorder.add(l);
			java.util.Collections.shuffle(rndorder);

			// render loop
			while(true) {
				repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
			}

		}
	}

	public void reset(){
		year = 0;
		for(Tree t:forest){
			t.setExt(0.4f);
		}
		//JLable set
	}

	public void setJLable(JLabel year){
			yearL =year;
	}

}