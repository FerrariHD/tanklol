package game;


import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * 
 * @author FerrariHD
 *
 */
public class SpriteAnimation extends Transition {
  private final ImageView imageView;

  public SpriteAnimation(ImageView imageView, Duration duration, int count,
      int columns, int offsetX, int offsetY, int width, int height) {
    this.imageView = imageView;
    setCycleDuration(duration);
    setCycleCount(Animation.INDEFINITE);
    this.imageView
        .setViewport(new Rectangle2D(offsetX, offsetY, width, height));

  }

  protected void interpolate() {}

  @Override
  protected void interpolate(double axis) {
    imageView.setViewport(new Rectangle2D(224, 32, 30, 30));

  }
}
