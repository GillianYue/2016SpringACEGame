package audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BGMPlayer {
Clip L1;
	public BGMPlayer(){
		try{
		File level1file = new File ("src/bgm/level1.wav");
		AudioInputStream audioInputStream = 
	AudioSystem.getAudioInputStream(level1file);
		L1 = AudioSystem.getClip();
		L1.open(audioInputStream);
		loop(L1);
		}catch(Exception ex){
			System.out.println(":("+ex.getMessage());
		}
	}
	
	
	public void loop(Clip bgm){
		bgm.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopLoop(Clip bgm){
		bgm.loop(0);
		bgm.stop();
	}
}
