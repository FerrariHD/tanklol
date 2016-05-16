package game;


/**
 * 
 * @author FerrariHD
 *
 */
public class Spawner {

  private int spawnTimer;
  private int access;

  /**
   * chance ~66.7%
   * 
   * @return
   */
  public boolean addNewEnemy() {
    spawnTimer++;
    if (spawnTimer >= Game.spawnSpeed) {
      spawnTimer = 0;
      access = ((int) Math.random() * 100) % 3;
      switch (access) {
        case 0:
          access = 1;
          break;
        case 1:
          access = 1;
          break;
        case 2:
          access = 0;
          break;
      }
      if (access == 1) {
        return true;
      }
    }
    return false;
  }
}
