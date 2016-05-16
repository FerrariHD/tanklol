package game;



import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * cut blocks from sprite list
 * 
 * @author FerrariHD
 *
 */
public class Block extends Pane {
  ImageView block;

  public enum BlockType {
    BRICK, WALL, GRASS, WATER, BONUS
  }

  /**
   * 
   * @param blockType
   * @param x = setTranslateX
   * @param y = setTranslateY
   */
  public Block(BlockType blockType, int x, int y, Image blocksImg) {
    block = new ImageView(blocksImg);
    block.setFitWidth(Game.BLOCK_SIZE);
    block.setFitHeight(Game.BLOCK_SIZE);
    setTranslateX(x);
    setTranslateY(y);
    switch (blockType) {
      case WALL:
        block.setViewport(
            new Rectangle2D(512, 32, Game.BLOCK_SIZE, Game.BLOCK_SIZE));
        Game.platforms.add(this);
        break;
      case BRICK:
        block.setViewport(
            new Rectangle2D(512, 0, Game.BLOCK_SIZE, Game.BLOCK_SIZE));
        Game.platforms.add(this);
        break;
      case WATER:
        block.setViewport(
            new Rectangle2D(512, 64, Game.BLOCK_SIZE, Game.BLOCK_SIZE));
        break;
      case GRASS:
        block.setViewport(
            new Rectangle2D(545, 64, Game.BLOCK_SIZE, Game.BLOCK_SIZE));
        break;
      case BONUS:
        block.setViewport(
            new Rectangle2D(672, 222, Game.BLOCK_SIZE, Game.BLOCK_SIZE));
        Game.bonuses.add(this);
        break;
    }
    getChildren().add(block);
    Game.gameRoot.getChildren().add(this);
  }
}
