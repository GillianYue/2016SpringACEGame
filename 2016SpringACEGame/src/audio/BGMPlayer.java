package audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BGMPlayer {
	Clip L1, L1Boss;  public int currentClip;
	public BGMPlayer(){
		L1=loadBGM ("src/bgm/level1.wav", L1);
		L1Boss=loadBGM ("src/bgm/level1BossBattle.wav", L1Boss);
		loop(L1);
		currentClip=0;
	}
	
	public Clip loadBGM (String fileName, Clip clip){
		try{
			File file = new File (fileName);
			AudioInputStream audioInputStream = 
		AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			return clip;
			}catch(Exception ex){
				System.out.println(":("+ex.getMessage());
				return null;
			}
	}
	
	public void loop(Clip bgm){
		bgm.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopLoop(int clipNum){
		if(clipNum==0){
			L1.loop(0);
			L1.stop();
			System.out.println(L1.isRunning());
		}else if(clipNum==1){
			L1Boss.loop(0);
			L1Boss.stop();
		}
	}
	
	public void changeTo(int clipNum){
		if(clipNum==0){
			stopLoop(currentClip);
			loop(L1);
		}else if(clipNum==1){
			stopLoop(currentClip);
			loop(L1Boss);
		}
		currentClip=clipNum;
	}
}
