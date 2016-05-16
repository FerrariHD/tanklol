package game;


import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * our music and sound effects
 * 
 * @author FerrariHD
 *
 */
public class Music {

  static MediaPlayer mediaplayer;
  static MediaPlayer mediaplayer2;
  static MediaPlayer engineSound;
  static AudioClip enterSound;
  static AudioClip returnSound;
  static AudioClip shot;
  static AudioClip wallhit;
  static AudioClip tankhit;
  static AudioClip tankexplosion;

  public void initSound() {
    Media gameMusicFile = new Media(
        getClass().getResource("AnOrangePlanet(loop).wav").toString());
    Media musicMainMenuFile = new Media(
        getClass().getResource("AnOrangePlanet-Percussion.wav").toString());
    Media engineOneFile =
        new Media(getClass().getResource("engine.wav").toString());

    mediaplayer = new MediaPlayer(musicMainMenuFile);
    mediaplayer.setVolume(0.7);
    mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);

    mediaplayer2 = new MediaPlayer(gameMusicFile);
    mediaplayer2.setVolume(0.7);
    mediaplayer2.setCycleCount(MediaPlayer.INDEFINITE);

    engineSound = new MediaPlayer(engineOneFile);
    engineSound.setVolume(1);
    engineSound.setCycleCount(MediaPlayer.INDEFINITE);

    enterSound = new AudioClip(getClass().getResource("enter.wav").toString());
    enterSound.setVolume(0.3);

    returnSound = new AudioClip(getClass().getResource("esc.wav").toString());
    returnSound.setVolume(0.3);

    shot = new AudioClip(getClass().getResource("shot.wav").toString());
    shot.setVolume(1);

    wallhit = new AudioClip(getClass().getResource("wallHit2.wav").toString());
    wallhit.setVolume(0.5);

    tankhit = new AudioClip(getClass().getResource("tankHit.wav").toString());
    tankhit.setVolume(0.8);

    tankexplosion =
        new AudioClip(getClass().getResource("tankExplosion.wav").toString());
    tankexplosion.setVolume(0.5);
  }

  public static void chooseMusic(int number) {
    switch (number) {
      case 0:
        mediaplayer.play();
        break;
      case 1:
        mediaplayer.stop();
        mediaplayer2.play();
        break;
    }
  }

  public static void enterSound() {
    enterSound.play();
  }

  /**
   * when we click "back"
   */
  public static void returnSound() {
    returnSound.play();
  }

  public static void shot() {
    shot.play();
  }

  public static void wallHit() {
    wallhit.play();
  }

  public static void tankHit() {
    tankhit.play();
  }

  public static void tankExplosion() {
    tankexplosion.play();
  }

  public static void activateEngineSound(boolean status) {
    if (status == true) {
      engineSound.play();
    } else {
      engineSound.stop();
    }
  }
}
