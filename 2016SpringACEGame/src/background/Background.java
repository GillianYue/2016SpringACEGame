package background;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import mainPac.ImageLoader;

public class Background{
	BufferedImage background;
	
	public Background(JPanel myPanel, ImageLoader il, BufferedImage myPic){
		background = myPic;
	}

	public void setBackgroundSize(int width, int height, BufferedImage image){
		BufferedImage resized = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = resized.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		background = resized;
	}
	
	public void drawBackground(Graphics g, int x, int y, ImageObserver io){
		g.drawImage(background, x, y, io);
	}
	
	public BufferedImage getBackgroundImage(){
		return background;
	}
	
	public int getBackgroundWidth(){
		return background.getWidth();
	}
	
	public int getBackgroundHeight(){
		return background.getHeight();
	}
}
