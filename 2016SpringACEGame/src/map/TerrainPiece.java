package map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class TerrainPiece extends Unit{
	
	boolean haveLayerOnTop;
	BufferedImage terrain;
	BufferedImage layer;
	
	public TerrainPiece (int X, int Y){
		super(X,Y);
		loadterrainPics("Pics/terrainpiece.png", "It'snotthereyet");
	}
	
	public void loadterrainPics(String terrainfileName, String layerfileName){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream input;
		input= cl.getResourceAsStream(terrainfileName);
		try {
			terrain=ImageIO.read(input);
			System.out.println("IOsuccess! "+terrainfileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ClassLoader cl2 = Thread.currentThread().getContextClassLoader();
		InputStream input2;
		input2= cl2.getResourceAsStream(layerfileName);
		try {
			layer=ImageIO.read(input2);
			System.out.println("IOsuccess! "+layerfileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
