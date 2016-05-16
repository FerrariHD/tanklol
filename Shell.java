package game;


import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * shell (red gameObject)
 * 
 * @author FerrariHD
 *
 */
public class Shell extends Pane {

  private int direction;
  private int shellSpeed = 7;
  private String shotOwner;

  /**
   * shell constructor
   * 
   * @param dir = direction
   * @param startX = setTranslateX
   * @param startY = setTranslateY
   * @param Owner = who has made a shot (player or bot)
   */
  public Shell(int dir, double startX, double startY, String Owner) {
    shotOwner = Owner;
    direction = dir;
    Glow glow = new Glow();
    glow.setLevel(1.0);
    Rectangle shot = new Rectangle(5, 5, Color.RED);
    shot.setEffect(glow);
    switch (direction) {
      case 1:
        setTranslateX(startX + Game.TANK_SIZE / 2 - 2);
        setTranslateY(startY - 3);
        break;
      case 2:
        setTranslateX(startX + Game.TANK_SIZE / 2 - 2);
        setTranslateY(startY + Game.TANK_SIZE);
        break;
      case 3:
        setTranslateX(startX - 3);
        setTranslateY(startY + Game.TANK_SIZE / 2 - 2);
        break;
      case 4:
        setTranslateX(startX + Game.TANK_SIZE);
        setTranslateY(startY + Game.TANK_SIZE / 2 - 2);
        break;
    }
    getChildren().addAll(shot);
  }

  public String getShotOwner() {
    return shotOwner;
  }

  public void move() {
    switch (direction) {
      case 1:
        this.setTranslateY(this.getTranslateY() - shellSpeed);
        break;
      case 2:
        this.setTranslateY(this.getTranslateY() + shellSpeed);
        break;
      case 3:
        this.setTranslateX(this.getTranslateX() - shellSpeed);
        break;
      case 4:
        this.setTranslateX(this.getTranslateX() + shellSpeed);
        break;
    }
  }
}
