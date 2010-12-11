package sound;
// PlayClip.java
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th

/* Load an audio file as a clip, and play it once.
   During its playing, PlayClip will sleep.

   The clips termination causes update() to be called,
   which terminates execution.

   Changes 6th August 2004
     - measure time of clip using getMicrosecondLength() in loadClip()
     - closed the input stream in loadClip()

   Changes 16th September 2004
     - bug: if the supplied WAV file is less than a second long then 
            no sound is played. See the bug report at 
            http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5085008

     Two possible solutions: 

     1) edit the sound to be > 1 sec in length
        We call checkDuration() from loadClip() to check if 
        the clip duration is <= 1 sec

     2) loop the sound until its duration is > 1 sec.
        That is implemented in PlayClipBF.java

*/

import java.io.*;

import javax.sound.sampled.*;

import java.text.DecimalFormat;



public class PlayClip implements LineListener
{
  private final static String SOUND_DIR = "sounds/";

  private Clip clip = null;
  private DecimalFormat df; 



  public PlayClip(String fnm)
  {
    df = new DecimalFormat("0.#");  // 1 dp

    loadClip(SOUND_DIR + fnm);
    play();

    // wait for the sound to finish playing; guess at 10 mins!
  //  System.out.println("Waiting");
  //  try { 
   //   Thread.sleep(600000);   // 10 mins in ms
   // } 
   // catch(InterruptedException e) 
   // { System.out.println("Sleep Interrupted"); }
  } // end of PlayClip()


  public AudioInputStream getAudioInputStream(InputStream is) {

	  
      try {
          if (!is.markSupported()) {
              is = new BufferedInputStream(is);
          }
          // open the source stream
          AudioInputStream source =
              AudioSystem.getAudioInputStream(is);

          AudioFormat playbackFormat = source.getFormat();
          
          // convert to playback format
          return AudioSystem.getAudioInputStream(
              playbackFormat, source);
      }
      catch (UnsupportedAudioFileException ex) {
          ex.printStackTrace();
      }
      catch (IOException ex) {
          ex.printStackTrace();
      }
      catch (IllegalArgumentException ex) {
          ex.printStackTrace();
      }

      return null;
  }
  
  public AudioInputStream getAudioInputStream(String filename) {
      try {
          return getAudioInputStream(
              new FileInputStream(filename));
      }
      catch (IOException ex) {
          ex.printStackTrace();
          return null;
      }
  }
  
  private void loadClip(String fnm)
  {
    try {
      // link an audio stream to the sound clip's file
   //  System.out.println("trying to play "+fnm);
    	AudioInputStream stream = getAudioInputStream(fnm);
                        // getClass().getResource(fnm) );

      AudioFormat format = stream.getFormat();

      // convert ULAW/ALAW formats to PCM format
      if ( (format.getEncoding() == AudioFormat.Encoding.ULAW) ||
           (format.getEncoding() == AudioFormat.Encoding.ALAW) ) {
        AudioFormat newFormat = 
           new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                format.getSampleRate(),
                                format.getSampleSizeInBits()*2,
                                format.getChannels(),
                                format.getFrameSize()*2,
                                format.getFrameRate(), true);  // big endian
        // update stream and format details
        stream = AudioSystem.getAudioInputStream(newFormat, stream);
        System.out.println("Converted Audio format: " + newFormat);
        format = newFormat;
      }

      DataLine.Info info = new DataLine.Info(Clip.class, format);

      // make sure sound system supports data line
      if (!AudioSystem.isLineSupported(info)) {
        System.out.println("Unsupported Clip File: " + fnm);
        System.exit(0);
      }

      // get clip line resource
      clip = (Clip) AudioSystem.getLine(info);

      // listen to clip for events
      clip.addLineListener(this);

      clip.open(stream);    // open the sound file as a clip
      stream.close(); // we're done with the input stream  // new

      clip.setFramePosition(0);
      // clip.setMicrosecondPosition(0);

      checkDuration();
    } // end of try block

   // catch (UnsupportedAudioFileException audioException) {
    //  System.out.println("Unsupported audio file: " + fnm);
    //  System.exit(0);
  //  }
    catch (LineUnavailableException noLineException) {
      System.out.println("No audio line available for : " + fnm);
        System.exit(0);
    }
    catch (IOException ioException) {
      System.out.println("Could not read: " + fnm);
      System.exit(0);
    }
//    catch (Exception e) {
 //     System.out.println("Problem with " + fnm);
  //    System.exit(0);
  //  }
  } // end of loadClip()


  private void checkDuration()
  {
    // duration (in secs) of the clip
	double duration = clip.getMicrosecondLength()/1000000.0;  // new
    if (duration <= 1.0) {
    //  System.out.println("WARNING. Duration <= 1 sec : " + df.format(duration) + " secs");
     // System.out.println("         The clip may not play in J2SE 1.5 -- make it longer");
    }
  //  else
     // System.out.println("Duration: " + df.format(duration) + " secs");
  }  // end of checkDuration()


  private void play()
  { if (clip != null) {
      //System.out.println("Playing...");
      clip.start();   // start playing
    }
  }


  public void update(LineEvent lineEvent)
  // called when the clip's line detects open, close, start, stop events
  {
    // has the clip has reached its end?
    if (lineEvent.getType() == LineEvent.Type.STOP) {
      //System.out.println("Exiting...");
      clip.stop();
      clip.setFramePosition(0);

      lineEvent.getLine().close();
      //System.exit(0);
    }
  } // end of update()

  // --------------------------------------------------

 
} // end of PlayClip.java
