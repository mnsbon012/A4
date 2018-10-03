
// Trees define a canopy which covers a square area of the landscape
public class Tree{
	
private
	int xpos;	// x-coordinate of center of tree canopy
	int ypos;	// y-coorindate of center of tree canopy
	float ext;	// extent of canopy out in vertical and horizontal from center
	//float maxExt;	// size of canopy
	int width;	//dimensions of forrest
	int height;
	
	static float growfactor = 1000.0f; // divide average sun exposure by this amount to get growth in extent
	
public
	Tree(int x, int y, float e){
		xpos=x;
		ypos=y;
		ext=0.4f;
	//	width=w;
	//	height=h;
		//maxExt = e;
	}
	
	// return the x-position of the tree center
	int getX() {
		return xpos;
	}
	
	// return the y-position of the tree center
	int getY() {
		return ypos;
	}
	
	// return the extent of the tree
	float getExt() {
		return ext;
	}
	
	// set the extent of the tree to <e>
	void setExt(float e) {
		ext = e;
	}

	// return the average sunlight for the cells covered by the tree
	// also calls on the shadow method in land inputed after calculating exposure
	float sunexposure(Land land){
		// to do
		float totalExposure=0.00f;
		for (int y=getStartY(); y<=getEndY(land.getHeight()); y++){
			for (int x=getStartX();x<=getEndX(land.getWidth());x++){
				totalExposure = totalExposure + land.getShade(x,y); //needs to be safe access to shadedSun
			}
		}
	//	land.shadow(this);
		return totalExposure;
	}
	
	// is the tree extent within the provided range [minr, maxr)
	boolean inrange(float minr, float maxr) {
		return (ext >= minr && ext < maxr);
	}

	public float averageExposure(Land land){
		float totalExposure = sunexposure(land);
		int area =getArea(land);
		float averageExposure = totalExposure/area;
		return averageExposure;

	}
	public int getArea(Land land){
		int width = getEndX(land.getWidth())- getStartX();
		int height = getEndY(land.getHeight())-getStartY();
		return height*width;
	}

	// grow a tree according to its sun exposure
	void sungrow(Land land) {
		// to do
		//float totalExposure = sunexposure(land);		// gets total sunlight for tree
		checkExt(ext);
		float averageExposure =averageExposure(land);
		land.shadow(this);							// then shadows land according to trees extent
		// must now grow tree

		//float averageExposure = totalExposure/(((int)ext*2)^2);
		ext+=averageExposure/growfactor;
		//land.shadow(this);							// then shadows land according to trees extent
		checkExt(ext);
	}

	public void checkExt(float e){
		if (e>20.0f){
			ext=20.0f;
		}
	}


	public int getStartX() {
		int startX = xpos - (int) ext-1;
		if (startX >= 0) {
			return startX;
		}
		return 0;
	}

	public int getStartY() {
		int startY = ypos - (int) ext-1;
		if (startY >= 0) {
			return startY;
		}
		return 0;
	}

	public int getEndX(int width) {
		int endX = xpos + (int) ext+1;
		if (endX >= width){
			endX = width-1;
		}
		return endX;
	}

	public int getEndY(int height) {
		int endY = ypos + (int) ext+1;

		if (endY >= height){
			endY = height-1;
		}
		return endY;
	}
}