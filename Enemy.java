package game;


import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * AI and autoPlayer
 * 
 * @author FerrariHD
 *
 */
public class Enemy extends Pane {
  Image tankImg = new Image(getClass()
      .getResourceAsStream("NES_-_Battle_City_-_General_Sprites.png"));
  ImageView imageView = new ImageView(tankImg);
  int width = 25;
  int height = 25;
  long movementTimer = 0;
  int dx = 1, dy = 0;
  int randAxis = 0;
  int health = 100;
  int shotAxis;
  int cooldown = 100;
  public SpriteAnimation animation;

  public int setShootTimer() {
    cooldown--;
    if (cooldown <= 0) {
      cooldown = 100;
      return 1;
    } else {
      return 0;
    }
  }

  public void randomMove() {
    movementTimer++;
    if (movementTimer > 75) {
      movementTimer = 0;
      randAxis = (int) ((Math.random() * 100) % 4);
      if (randAxis == 0) {
        dx = 1;
        dy = 0;
        shotAxis = 4;
      }
      if (randAxis == 1) {
        dx = -1;
        dy = 0;
        shotAxis = 3;
      }
      if (randAxis == 2) {
        dy = 1;
        dx = 0;
        shotAxis = 2;
      }
      if (randAxis == 3) {
        dy = -1;
        dx = 0;
        shotAxis = 1;
      }
    }
  }

  public int getDx() {
    return dx;
  }

  public int getDy() {
    return dy;
  }

  public int getShotAxis() {
    return shotAxis;
  }

  public int updateHealth(int damage) {
    health -= damage;
    if (health <= 0) {
      return 1;
    } else {
      return 0;
    }
  }

  public Enemy() {
    imageView.setFitHeight(25);
    imageView.setFitWidth(25);
    imageView.setViewport(new Rectangle2D(224, 32, width, height));
    animation = new SpriteAnimation(this.imageView, Duration.millis(200), 0, 0,
        224, 32, width, height);
    setTranslateX(50);
    setTranslateY(300);
    getChildren().addAll(this.imageView);
  }

  public void moveX(int value) {
    boolean movingRight = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
      for (Node platform : Game.platforms) {
        if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
          movementTimer++;
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
          movementTimer++;
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

