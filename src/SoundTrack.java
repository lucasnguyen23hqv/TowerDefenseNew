import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Stream;

public class SoundTrack {
    static String[] name = new String[9];
    static Random rd = new Random();
    static int x = 8;
    public SoundTrack(){
        name[0] = "res/Sound/music1.wav";
        name[1] = "res/Sound/music2.wav";
        name[2] = "res/Sound/music3.wav";
        name[3] = "res/Sound/music4.wav";
        name[4] = "res/Sound/music5.wav";
        name[5] = "res/Sound/music6.wav";
        name[6] = "res/Sound/music7.wav";
        name[7] = "res/Sound/music8.wav";
        name[8] = "res/Sound/music9.wav";
    }
    public static Clip clip;

    public static void play(){
        try{
            int k = rd.nextInt(9);
            File f = new File(name[k]);
            AudioInputStream stream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.loop(1);
            name[k] = name[x];
            x--;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void stop() {
        clip.stop();
        clip.close();
    }
     public static boolean isEnd(){
        if (clip.isRunning());
        return false;
     }
}