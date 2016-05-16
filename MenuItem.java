package game;


import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * menu buttons
 * 
 * @author FerrariHD
 *
 */
public class MenuItem extends StackPane {
  public MenuItem(String name) {
    Glow glow = new Glow();
    glow.setLevel(1.0);
    Rectangle bg = new Rectangle(280, 34, Color.TRANSPARENT);
    bg.setEffect(glow);
    bg.setOpacity(1);
    bg.setArcHeight(7.5);
    bg.setArcWidth(7.5);
    bg.setStrokeWidth(1.1);
    bg.setStroke(Color.WHITE);

    Text text = new Text(name);
    text.setFill(Color.WHITE);
    text.setFont(Font.font("Candara", FontWeight.MEDIUM, 18));

    setAlignment(Pos.CENTER);
    getChildren().addAll(bg, text);

    ScaleTransition tr = new ScaleTransition(Duration.millis(25), bg);
    setOnMouseEntered(event -> {
      bg.setStroke(Color.YELLOW);
      tr.setToX(1.075);
      tr.setToY(1.2);
      tr.play();
    });
    setOnMouseExited(event -> {
      tr.stop();
      tr.setToX(1);
      tr.setToY(1);
      tr.play();
      bg.setStroke(Color.WHITE);
    });
  }
}
