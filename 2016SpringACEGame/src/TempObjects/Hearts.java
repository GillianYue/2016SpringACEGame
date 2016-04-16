package TempObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import mainPac.ImageLoader;

public class Hearts extends tempObject{
	BufferedImage full, half, none;
	int hp;
	public Hearts(int MX, int MY, ImageLoader il, int direction, int HP) {
		super(MX, MY, il, direction);
		hp=HP;
		full=il.hearts.getSubimage(0, 0, 45, 45);
		half=il.hearts.getSubimage(0, 45, 45, 45);
		none=il.hearts.getSubimage(0, 90, 45, 45);
	}

	public void drawHearts(Graphics g){
		if(hp-2>=0){
			g.drawImage(full, 0, 0, null);
		}else if(hp-2==-1){
			g.drawImage(half, 0, 0, null);
		}else{
			g.drawImage(none, 0, 0, null);
		}
		//first heart drawn
		if(hp-4>=0){
			g.drawImage(full, 45, 0, null);
		}else if(hp-4==-1){
			g.drawImage(half, 45, 0, null);
		}else{
			g.drawImage(none, 45, 0, null);
		}
		//second heart drawn
		if(hp-6>=0){
			g.drawImage(full, 90, 0, null);
		}else if(hp-6==-1){
			g.drawImage(half, 90, 0, null);
		}else{
			g.drawImage(none, 90, 0, null);
		}
		//third heart drawn
	}
}
