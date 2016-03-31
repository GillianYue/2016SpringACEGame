package map;

import java.awt.image.BufferedImage;

public class Unit {
	//tiny pieces of units that consist the map of the game.
	//each piece is an imaginary square that's 10*10 pixels
	int myxCor, myyCor;
	int whatAmI; BufferedImage myImage;
	//0: nothing; 1: terrain; 2: terrainwithlayer; 
	public Unit (int X, int Y){
		myxCor=X;
		myyCor=Y;
	}
	
}
