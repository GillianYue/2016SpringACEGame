package mainPac;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {
	public BufferedImage bird, icon, mountains, sun, terrainpiece, terrainlayer, tori, kero,
	notes, flower, hearts, pine, oak, stone, log, mushroom, spore, treeMonster, spring, leafPile,
	fruits, treeBoss, leaf, bugs, bTerrainpiece, bTerrainlayer, bossSign;
	
	public ImageLoader(){
		bird=loadImage("Pics/bird.png", bird);
		icon=loadImage("Pics/icon.png", icon);
		mountains=loadImage("Pics/mountains.png", mountains);
		sun=loadImage("Pics/sun.png", sun);
		terrainpiece=loadImage("Pics/terrainpiece.png", terrainpiece);
		terrainlayer=loadImage("Pics/terrainlayer.png", terrainlayer);
		tori=loadImage("Pics/tori.png", tori);
		kero=loadImage("Pics/kero.png", kero);
		notes=loadImage("Pics/notes.png", notes);
		flower=loadImage("Pics/flowermonsters.png", flower);
		hearts=loadImage("Pics/hearts.png", hearts);
		pine=loadImage("Pics/pine.png", pine);
		oak=loadImage("Pics/oak.png", oak);
		stone=loadImage("Pics/stone.png", stone);
		log=loadImage("Pics/log.png", log);
		mushroom=loadImage("Pics/mushrooms.png", mushroom);
		spore=loadImage("Pics/spore.png", spore);
		treeMonster=loadImage("Pics/treeMonster.png", treeMonster);
		spring=loadImage("Pics/spring.png", spring);
		leafPile=loadImage("Pics/pileLeaf.png", leafPile);
		fruits=loadImage("Pics/fruits.png", fruits);
		treeBoss=loadImage("Pics/treeBoss.png", treeBoss);
		leaf=loadImage("Pics/leaf.png", leaf);
		bugs=loadImage("Pics/bugs.png", bugs);
		bTerrainpiece=loadImage("Pics/bossterrainpiece.png", bTerrainpiece);
		bTerrainlayer=loadImage("Pics/bossterrainlayer.png", bTerrainlayer);
		bossSign=loadImage("Pics/bossSign.png", bossSign);
	}
	
	public BufferedImage loadImage(String fileName, BufferedImage image){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream input;
		input= cl.getResourceAsStream(fileName);
		try {
			image=ImageIO.read(input);
			System.out.println("IOsuccess! "+fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	
}
