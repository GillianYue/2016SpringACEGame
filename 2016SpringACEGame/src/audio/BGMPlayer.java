package audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BGMPlayer {
	Clip L1, L1Boss; //0,1
	public int currentClip;
	Clip[] chirp; //2,3,4,5
	
	public BGMPlayer(){
		L1=loadBGM ("src/bgm/level1.wav", L1);
		L1Boss=loadBGM ("src/bgm/level1BossBattle.wav", L1Boss);
		chirp=new Clip[4];
		chirp[0]=loadBGM ("src/audio/c1.wav", chirp[0]);
		chirp[1]=loadBGM ("src/audio/c2.wav", chirp[1]);
		chirp[2]=loadBGM ("src/audio/c3.wav", chirp[2]);
		chirp[3]=loadBGM ("src/audio/c4.wav", chirp[3]);
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
	
	public void playchirp() {
		int chirpNum = (int)(Math.random()/0.25);
		chirp[chirpNum].loop(1);
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
