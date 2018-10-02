

public class Land{
	float[][] initialSun;
	float[][] shadedSun;
	int width;
	int height;
	// to do
	// sun exposure data here

	static float shadefraction = 0.1f; // only this fraction of light is transmitted by a tree

	Land(int dx, int dy) {
		width = dx;
		height = dy;
		initialSun = new float[dy][dx];
		shadedSun = new float[dy][dx];

		// to do
	}

	// return the number of landscape cells in the x dimension
	int getDimX() {
		return width;
	}
	
	// return the number of landscape cells in the y dimension
	int getDimY() {
		return height;
	}
	
	// Reset the shaded landscape to the same as the initial sun exposed landscape
	// Needs to be done after each growth pass of the simulator
	void resetShade() {
		for (int y =0; y<height; y++){
			for (int x=0; x<width; x++){
				shadedSun[y][x]= initialSun[y][x];
			}
		}
	}
	
	// return the sun exposure of the initial unshaded landscape at position <x,y?
	float getFull(int x, int y) {
		return initialSun[y][x];
	}
	
	// set the sun exposure of the initial unshaded landscape at position <x,y> to <val>
	void setFull(int x, int y, float val) {
		initialSun[y][x] = val;
	}
	
	// return the current sun exposure of the shaded landscape at position <x,y>
	float getShade(int x, int y) {
		return shadedSun[y][x];
	}
	
	// set the sun exposure of the shaded landscape at position <x,y> to <val>
	void setShade(int x, int y, float val){
		shadedSun[y][x]=val;
	}
	
	// reduce the sun exposure of the shaded landscape to 10% of the original
	// within the extent of <tree>
	void shadow(Tree tree){
		int yEnd = tree.getEndY(height);
		int xEnd = tree.getEndX(width);

		for (int y=tree.getStartY(); y<= yEnd; y++){
			for (int x=tree.getStartX(); x<=xEnd; x++){
				float shade = shadedSun[y][x]*shadefraction;
				setShade(x,y,shade);
			}
		}
	}

	public int getHeight(){
		return height;
	}

	public int getWidth(){
		return width;
	}

/*	public void printArr(){
		for (int y =0; y<height; y++){

			for (int x=0; x<width; x++){
				System.out.print(initialSun[y][x]+" ");
			}
			System.out.println("");
		}
	}

	public void printShadedArr(){
		for (int y =0; y<height; y++){

			for (int x=0; x<width; x++){
				System.out.print(shadedSun[y][x]+" ");
			}
			System.out.println("");
		}
	}
*/
}