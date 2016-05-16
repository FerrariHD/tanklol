package game;


import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Main Player class
 * 
 * @author FerrariHD
 *
 */
public class Character extends Pane {
  Image tankImg = new Image(getClass()
      .getResourceAsStream("NES_-_Battle_City_-_General_Sprites.png"));
  ImageView imageView = new ImageView(tankImg);

  int width = 25;
  int height = 25;
  int health = 100;
  static int currentAxis = 0;
  public SpriteAnimation animation;
  public static int shotAxis = 0;
  public static int cooldown = 100;

  public static void setCooldown() {
    cooldown--;
  }

  public static int getCooldown() {
    return cooldown;
  }

  public static int getCurrentAxis() {
    return currentAxis;
  }

  public static void setCurrentAxis(int axis) {
    currentAxis = axis;
    if (currentAxis != 0) {
      shotAxis = currentAxis;
    }
  }

  public int updateHealth(int damage) {
    health -= damage;
    if (health <= 0) {
      return 1;
    } else {
      return 0;
    }
  }

  public Character() {
    Glow glow = new Glow();
    glow.setLevel(1);
    Light.Distant light = new Light.Distant();
    light.setAzimuth(-135.0);

    Lighting lighting = new Lighting();
    lighting.setLight(light);
    lighting.setSurfaceScale(5.0);
    imageView.setEffect(glow);
    imageView.setEffect(lighting);
    imageView.setFitHeight(25);
    imageView.setFitWidth(25);
    imageView.setViewport(new Rectangle2D(224, 32, width, height));
    animation = new SpriteAnimation(this.imageView, Duration.millis(200), 0, 0,
        224, 32, width, height);
    setTranslateX(400);
    setTranslateY(690);
    getChildren().addAll(this.imageView);
  }

  public void moveX(int value) {
    boolean movingRight = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
      for (Node platform : Game.platforms) {
        if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
          if (movingRight) {
            if (this.getTranslateX() + Game.TANK_SIZE == platform
                .getTranslateX()) {
              this.setTranslateX(this.getTranslateX() - 1);
              return;
            }
          } else {
            if (this.getTranslateX() == platform.getTranslateX()
                + Game.BLOCK_SIZE) {
              this.setTranslateX(this.getTranslateX() + 1);
              return;
            }
          }
        }
      }
      this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
    }
  }

  public void moveY(int value) {
    boolean movingDown = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
      for (Block platform : Game.platforms) {
        if (getBoundsInParent().intersects(platform.getBoundsInParent())) {
          if (movingDown) {
            if (this.getTranslateY() + Game.TANK_SIZE == platform
                .getTranslateY()) {
              this.setTranslateY(this.getTranslateY() - 1);
              return;
            }
          } else {
            if (this.getTranslateY() == platform.getTranslateY()
                + Game.BLOCK_SIZE) {
              this.setTranslateY(this.getTranslateY() + 1);
              return;
            }
          }
        }
        
      }
      this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
    }
  }
}
