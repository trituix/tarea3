import java.applet.AudioClip;
import java.applet.Applet;
import java.io.*;
import java.nio.file.Paths;

public class Sound{
	private AudioClip sound;

	public Sound(String filename)
	{
		try{
			this.sound = Applet.newAudioClip(Paths.get(System.getProperty("user.dir"), "sounds", filename).toUri().toURL());
		}
		catch(Exception e)
		{

		}
	}

	public void playOnce()
	{
		try{
			this.sound.play();
		}
		catch(Exception e)
		{
			System.out.print("Aca deberia sonar");
		}
	}
}