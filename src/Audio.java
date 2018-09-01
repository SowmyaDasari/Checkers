import java.io.*;
import sun.audio.*;
//import java.lang.*;

/**
 * A simple Java sound file example (i.e., Java code to play a sound file).
 * AudioStream and AudioPlayer code comes from a javaworld.com example.
 * @author alvin alexander, devdaily.com.
 */
public class Audio
{
	//@SuppressWarnings("restriction")

  public static void main(String[] args) 
  throws Exception
  {
    // open the sound file as a Java input stream
    //String gongFile = "/Users/al/DevDaily/Projects/MeditationApp/resources/gong.au";
    InputStream in = new FileInputStream("caughtball.wav");
    AudioStream audioStream = new AudioStream(in);
    AudioPlayer.player.start(audioStream);
   }

}