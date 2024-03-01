package tetris;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Reproducer
{
	private Clip clip;
	private boolean play = false;

	public void loadSound(String route)
	{
		try{
			File fileSound = new File(route);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileSound);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void reproduce()
	{
		if(clip != null && play == false)
		{
			clip.setFramePosition(0);
			clip.start();
			setPlay(true);
		}
	}

	public void stop()
	{
		if(clip != null && clip.isRunning() && play == true)
		{
			clip.stop();
			setPlay(false);
		}	
	}

	public boolean getPlay()
	{
		return play;
	}
	public void setPlay(boolean play)
	{
		this.play = play;
	}    
}
