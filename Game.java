package game;


import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Game module
 * 
 * @author FerrariHD
 *
 */
public class Game {
  public static ArrayList<Block> platforms = new ArrayList<>();
  public static ArrayList<Block> bonuses = new ArrayList<>();
  private HashMap<KeyCode, Boolean> keys = new HashMap<>();

  public static final int BLOCK_SIZE = 30;
  public static final int TANK_SIZE = 25;

  public static Pane appRoot = new Pane();
  public static Pane gameRoot = new Pane();

  public Character player;
  public Enemy autoPlayer;
  public Spawner spawn = new Spawner();

  public int damage = 50;

  public int scoreToBonus = 0;

  public char gameModeReplay = '0';

  private ReplaysController results;

  public int movementSpeed = 3;

  private int newBlock;
  public static int spawnSpeed = 200;

  private Label ScoreLabel;
  private Label HealthLabel;

  public static String gameMode;

  public int gameScore = 0;

  public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  public ArrayList<Shell> shells = new ArrayList<Shell>();
  private AnimationTimer timer;

  Image gameOverImg =
      new Image(getClass().getResourceAsStream("GAME OVER.png"));
  ImageView gameOverView = new ImageView(gameOverImg);

  Rectangle pauseBG =
      new Rectangle(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, Color.BLACK);

  Image blocksImg = new Image(getClass()
      .getResourceAsStream("NES_-_Battle_City_-_General_Sprites.png"));

  /**
   * random map and GUI
   */
  private void initContent() {
    Rectangle bg =
        new Rectangle(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, Color.BLACK);
    pauseBG.setOpacity(0.5);
    HealthLabel = new Label("Health: 100");
    ScoreLabel = new Label("Score: 0");
    Font font = new Font(25);
    ScoreLabel.setFont(font);
    HealthLabel.setFont(font);
    ScoreLabel.setTextFill(Color.RED);
    HealthLabel.setTextFill(Color.RED);
    ScoreLabel.setTranslateY(25);
    gameRoot.getChildren().addAll(bg, HealthLabel, ScoreLabel);
    if (gameMode != "Replay") {
      for (int i = 0; i < Map.level.length; i++) {
        String line = Map.level[i];
        for (int j = 0; j < line.length(); j++) {
          if (line.charAt(j) == ' ') {
            results.saveChar(' ');
            continue;
          }
          if (line.charAt(j) == 'B') {
            new Block(Block.BlockType.WALL, j * BLOCK_SIZE, i * BLOCK_SIZE,
                blocksImg);
            results.saveChar('V');
          } else if (line.charAt(j) == 'R') {
            //randBlock();
            checkHitTask checkHittask = new checkHitTask();
            Thread checkHitThread = new Thread(checkHittask);
            checkHitThread.start();
            try {
              checkHitThread.join();
            } catch (InterruptedException e) {
              System.out.println("Hello");
              e.printStackTrace();
            }
            
            switch (newBlock) {
              case 1:
                new Block(Block.BlockType.WATER, j * BLOCK_SIZE, i * BLOCK_SIZE,
                    blocksImg);
                results.saveChar('W');
                break;
              case 2:
                new Block(Block.BlockType.WALL, j * BLOCK_SIZE, i * BLOCK_SIZE,
                    blocksImg);
                results.saveChar('V');
                break;
              case 3:
                new Block(Block.BlockType.BRICK, j * BLOCK_SIZE, i * BLOCK_SIZE,
                    blocksImg);
                results.saveChar('B');
                break;
              case 4:
                new Block(Block.BlockType.GRASS, j * BLOCK_SIZE, i * BLOCK_SIZE,
                    blocksImg);
                results.saveChar('G');
                break;
              case 5:
                new Block(Block.BlockType.BRICK, j * BLOCK_SIZE, i * BLOCK_SIZE,
                    blocksImg);
                results.saveChar('B');
                break;
              case 6:
                new Block(Block.BlockType.GRASS, j * BLOCK_SIZE, i * BLOCK_SIZE,
                    blocksImg);
                results.saveChar('G');
                break;
              case 7:
                new Block(Block.BlockType.WATER, j * BLOCK_SIZE, i * BLOCK_SIZE,
                    blocksImg);
                results.saveChar('W');
                break;
              default:
                results.saveChar(' ');
            }
          }
        }
      }

    } else {
      char M;
      for (int i = 0; i < Map.level.length; i++) {
        String line = Map.level[i];
        for (int j = 0; j < line.length(); j++) {
          M = results.loadChar();
          switch (M) {
            case 'W':
              new Block(Block.BlockType.WATER, j * BLOCK_SIZE, i * BLOCK_SIZE,
                  blocksImg);
              break;
            case 'V':
              new Block(Block.BlockType.WALL, j * BLOCK_SIZE, i * BLOCK_SIZE,
                  blocksImg);
              break;
            case 'B':
              new Block(Block.BlockType.BRICK, j * BLOCK_SIZE, i * BLOCK_SIZE,
                  blocksImg);
              break;
            case 'G':
              new Block(Block.BlockType.GRASS, j * BLOCK_SIZE, i * BLOCK_SIZE,
                  blocksImg);
              break;
          }
        }
      }
    }

    Image alpha =
        new Image(getClass().getResourceAsStream("ALPHA WORK IN PROGRESS.png"));
    ImageView alphaView = new ImageView(alpha);
    alphaView.setTranslateX(675);
    alphaView.setTranslateY(683);
    gameRoot.getChildren().add(alphaView);
    /**
     * choose GameMusic
     */
    Music.chooseMusic(1);
    appRoot.getChildren().addAll(gameRoot);
  }

  /**
   * randBlock from 0 to 14
   */
  private void randBlock() {
    newBlock = (int) ((Math.random() * 1000) % 15);
  }

  /*
   * update player or autoPlayer
   */
  private void update() {
    if (gameMode == "Normal") {
      Character.setCooldown();
      if (isPressed(KeyCode.UP)) {
        player.setScaleX(1);
        player.animation.play();
        player.moveY(-movementSpeed);
        player.setRotate(270);
        Character.setCurrentAxis(1);
        Music.activateEngineSound(true);
        results.saveChar('U');
        return;
      } else if (isPressed(KeyCode.DOWN)) {
        player.setScaleX(1);
        player.animation.play();
        player.moveY(movementSpeed);
        player.setRotate(90);
        Character.setCurrentAxis(2);
        Music.activateEngineSound(true);
        results.saveChar('D');
        return;
      } else if (isPressed(KeyCode.LEFT)) {
        player.setScaleX(-1);
        player.animation.play();
        player.moveX(-movementSpeed);
        player.setRotate(0);
        Character.setCurrentAxis(3);
        Music.activateEngineSound(true);
        results.saveChar('L');
        return;
      } else if (isPressed(KeyCode.RIGHT)) {
        player.setScaleX(1);
        player.animation.play();
        player.moveX(movementSpeed);
        player.setRotate(0);
        Character.setCurrentAxis(4);
        Music.activateEngineSound(true);
        results.saveChar('R');
        return;
      }
      if (isPressed(KeyCode.SPACE) && Character.getCooldown() <= 0) {
        Character.cooldown = 100;
        shells.add(new Shell(Character.shotAxis, player.getTranslateX(),
            player.getTranslateY(), "Player"));
        gameRoot.getChildren().add(shells.get(shells.size() - 1));
        Music.shot();
        results.saveChar('S');
        results.saveChar((char) Character.shotAxis);
        return;
      } else if (isPressed(KeyCode.ESCAPE)) {
        pause();
      }
      results.saveChar(' ');
    } else {
      autoPlayer.randomMove();
      if (autoPlayer.getDy() == -1) {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveY(-movementSpeed);
        autoPlayer.setRotate(270);
        Music.activateEngineSound(true);
        results.saveChar('U');
      }
      if (autoPlayer.getDy() == 1) {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveY(movementSpeed);
        autoPlayer.setRotate(90);
        results.saveChar('D');
      }
      if (autoPlayer.getDx() == -1) {
        autoPlayer.setScaleX(-1);
        autoPlayer.animation.play();
        autoPlayer.moveX(-movementSpeed);
        autoPlayer.setRotate(0);
        results.saveChar('L');
      }
      if (autoPlayer.getDx() == 1) {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveX(movementSpeed);
        autoPlayer.setRotate(0);
        results.saveChar('R');
      }
      if (autoPlayer.setShootTimer() == 1) {
        shells.add(new Shell(autoPlayer.getShotAxis(),
            autoPlayer.getTranslateX(), autoPlayer.getTranslateY(), "Player"));
        gameRoot.getChildren().add(shells.get(shells.size() - 1));
        Music.shot();
      }
    }
  }

  /**
   * update for our enemies
   */
  private void updateBot() {
    for (int i = 0; i < enemies.size(); i++) {
      enemies.get(i).randomMove();
      if (enemies.get(i).setShootTimer() == 1) {
        shells.add(new Shell(enemies.get(i).getShotAxis(),
            enemies.get(i).getTranslateX(), enemies.get(i).getTranslateY(),
            "Enemy"));
        gameRoot.getChildren().add(shells.get(shells.size() - 1));
      }
      if (enemies.get(i).getDy() == -1) {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveY(-movementSpeed);
        enemies.get(i).setRotate(270);
        results.saveChar('U');
      }
      if (enemies.get(i).getDy() == 1) {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveY(movementSpeed);
        enemies.get(i).setRotate(90);
        results.saveChar('D');
      }
      if (enemies.get(i).getDx() == -1) {
        enemies.get(i).setScaleX(-1);
        enemies.get(i).animation.play();
        enemies.get(i).moveX(-movementSpeed);
        enemies.get(i).setRotate(0);
        results.saveChar('L');
      }
      if (enemies.get(i).getDx() == 1) {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveX(movementSpeed);
        enemies.get(i).setRotate(0);
        results.saveChar('R');
      }
    }
  }

  /**
   * read replay and update player or autoplayer
   */
  private void updateReplay() {
    char D = results.loadChar();
    if (gameModeReplay == 'N') {
      if (D == 'U') {
        player.setScaleX(1);
        player.animation.play();
        player.moveY(-movementSpeed);
        player.setRotate(270);
        Music.activateEngineSound(true);
      }
      if (D == 'D') {
        player.setScaleX(1);
        player.animation.play();
        player.moveY(movementSpeed);
        player.setRotate(90);
        Music.activateEngineSound(true);
      }
      if (D == 'L') {
        player.setScaleX(-1);
        player.animation.play();
        player.moveX(-movementSpeed);
        player.setRotate(0);
        Music.activateEngineSound(true);
      }
      if (D == 'R') {
        player.setScaleX(1);
        player.animation.play();
        player.moveX(movementSpeed);
        player.setRotate(0);
        Music.activateEngineSound(true);
      }
      if (D == 'S') {
        char C = results.loadChar();
        shells.add(new Shell(C, player.getTranslateX(), player.getTranslateY(),
            "Player"));
        gameRoot.getChildren().add(shells.get(shells.size() - 1));
        Music.shot();
      } else if (isPressed(KeyCode.ESCAPE)) {
        pause();
      }
    } else {
      if (D == 'U') {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveY(-movementSpeed);
        autoPlayer.setRotate(270);
        autoPlayer.shotAxis = 1;
        Music.activateEngineSound(true);
      }
      if (D == 'D') {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveY(movementSpeed);
        autoPlayer.setRotate(90);
        autoPlayer.shotAxis = 2;
        Music.activateEngineSound(true);
      }
      if (D == 'L') {
        autoPlayer.setScaleX(-1);
        autoPlayer.animation.play();
        autoPlayer.moveX(-movementSpeed);
        autoPlayer.setRotate(0);
        autoPlayer.shotAxis = 3;
        Music.activateEngineSound(true);
      }
      if (D == 'R') {
        autoPlayer.setScaleX(1);
        autoPlayer.animation.play();
        autoPlayer.moveX(movementSpeed);
        autoPlayer.setRotate(0);
        autoPlayer.shotAxis = 4;
        Music.activateEngineSound(true);
      }
      if (autoPlayer.setShootTimer() == 1) {
        shells.add(new Shell(autoPlayer.getShotAxis(),
            autoPlayer.getTranslateX(), autoPlayer.getTranslateY(), "Player"));
        gameRoot.getChildren().add(shells.get(shells.size() - 1));
        Music.shot();
      }
    }
  }

  /**
   * read replay and update our enemies
   */
  private void updateBotReplay() {
    for (int i = 0; i < enemies.size(); i++) {
      if (enemies.get(i).setShootTimer() == 1) {
        shells.add(new Shell(enemies.get(i).getShotAxis(),
            enemies.get(i).getTranslateX(), enemies.get(i).getTranslateY(),
            "Enemy"));
        gameRoot.getChildren().add(shells.get(shells.size() - 1));
      }
      char D = results.loadChar();
      if (D == 'U') {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveY(-movementSpeed);
        enemies.get(i).setRotate(270);
        enemies.get(i).shotAxis = 1;
      }
      if (D == 'D') {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveY(movementSpeed);
        enemies.get(i).setRotate(90);
        enemies.get(i).shotAxis = 2;
      }
      if (D == 'L') {
        enemies.get(i).setScaleX(-1);
        enemies.get(i).animation.play();
        enemies.get(i).moveX(-movementSpeed);
        enemies.get(i).setRotate(0);
        enemies.get(i).shotAxis = 3;
      }
      if (D == 'R') {
        enemies.get(i).setScaleX(1);
        enemies.get(i).animation.play();
        enemies.get(i).moveX(movementSpeed);
        enemies.get(i).setRotate(0);
        enemies.get(i).shotAxis = 4;
      }
    }
  }

  /*
   * move shell and check collision with map
   */
  private void updateShell() {
    for (int i = 0; i < shells.size(); i++) {
      shells.get(i).move();
      for (Node platform : Game.platforms) {
        if (shells.get(i).getBoundsInParent()
            .intersects(platform.getBoundsInParent())) {
          if (shells.get(i).getShotOwner() == "Player") {
            Music.wallHit();
          }
          gameRoot.getChildren().remove(shells.get(i));
          shells.remove(shells.get(i));
          break;
        }
      }
    }
  }

  private void checkNewBonus() {
    if (scoreToBonus == 3) {
      scoreToBonus = 0;
      new Block(Block.BlockType.BONUS, 10 * BLOCK_SIZE, 10 * BLOCK_SIZE,
          blocksImg);
      HealthLabel.setText("Health: " + player.health);
    }
  }

  /*
   * check shells collision with player/enemies
   */
  private void checkHit() {
    for (int i = 0; i < shells.size(); i++) {
      if (shells.get(i).getShotOwner() == "Player") {
        for (int j = 0; j < enemies.size(); j++) {
          if (shells.get(i).getBoundsInParent()
              .intersects(enemies.get(j).getBoundsInParent())) {
            gameRoot.getChildren().remove(shells.get(i));
            shells.remove(shells.get(i));
            /**
             * "1" - that means, that health <= 0
             */
            if (enemies.get(j).updateHealth(damage) == 1) {
              gameRoot.getChildren().remove(enemies.get(j));
              enemies.remove(enemies.get(j));
              Music.tankExplosion();
              gameScore++;
              scoreToBonus++;
              ScoreLabel.setText("Score: " + gameScore);
              break;
            }
            Music.tankHit();
            break;
          }
        }
      } else {
        if (gameMode == "Normal" || gameModeReplay == 'N') {
          if (shells.get(i).getBoundsInParent()
              .intersects(player.getBoundsInParent())) {
            gameRoot.getChildren().remove(shells.get(i));
            shells.remove(shells.get(i));
            /**
             * if true - gameover
             */
            if (player.updateHealth(damage) == 1) {
              gameRoot.getChildren().remove(player);
              Music.tankExplosion();
              gameOver();
            }
            HealthLabel.setText("Health: " + player.health);
            Music.tankHit();
          }
        } else if (gameMode == "Auto" || gameModeReplay == 'A') {
          if (shells.get(i).getBoundsInParent()
              .intersects(autoPlayer.getBoundsInParent())) {
            gameRoot.getChildren().remove(shells.get(i));
            shells.remove(shells.get(i));
            /**
             * if true - gameover
             */
            if (autoPlayer.updateHealth(damage) == 1) {
              gameRoot.getChildren().remove(autoPlayer);
              Music.tankExplosion();
              gameOver();
            }
            HealthLabel.setText("Health: " + autoPlayer.health);
            Music.tankHit();
          }
        }
      }
    }
  }

  private boolean isPressed(KeyCode key) {
    return keys.getOrDefault(key, false);
  }

  /**
   * main method
   */
  public void startGame(Stage primaryStage, ReplaysController replay) {
    if (gameMode != "Replay") {
      this.results = new ReplaysController();
    } else {
      this.results = replay;
    }

    if (gameMode != "Replay") {
      results.createResultFile();
      if (gameMode == "Normal") {
        results.saveChar('N');
      } else if (gameMode == "Auto") {
        results.saveChar('A');
      }
    }
    if (gameMode == "Replay") {
      gameModeReplay = results.loadChar();
    }

    Scene scene = new Scene(appRoot, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    scene.setFill(Color.BLACK);
    primaryStage.setScene(scene);
    initContent();
    
    if (gameMode == "Auto") {
      autoPlayer = new Enemy();
      Glow glow = new Glow();
      glow.setLevel(0.75);
      autoPlayer.setEffect(glow);
      enemies.add(new Enemy());
      gameRoot.getChildren().add(autoPlayer);
      gameRoot.getChildren().add(enemies.get(0));
    } else if (gameMode == "Normal") {
      player = new Character();
      enemies.add(new Enemy());
      gameRoot.getChildren().add(player);
      gameRoot.getChildren().add(enemies.get(0));
    }
    if (gameMode == "Replay") {
      if (gameModeReplay == 'A') {
        autoPlayer = new Enemy();
        Glow glow = new Glow();
        glow.setLevel(0.75);
        autoPlayer.setEffect(glow);
        enemies.add(new Enemy());
        gameRoot.getChildren().add(autoPlayer);
        gameRoot.getChildren().add(enemies.get(0));
      } else {
        player = new Character();
        enemies.add(new Enemy());
        gameRoot.getChildren().add(player);
        gameRoot.getChildren().add(enemies.get(0));
      }
    }
    scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
    scene.setOnKeyReleased(event -> {
      keys.put(event.getCode(), false);
      player.animation.stop();
    });
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (gameMode != "Replay") {
          update();
          updateBot();
        } else {
          updateReplay();
          updateBotReplay();
        }
        updateShell();
        
        checkHit();
        
        
        checkNewBonus();
        for (Node bonus : bonuses) {
          if (player.getBoundsInParent()
              .intersects(bonus.getBoundsInParent())) {
            player.updateHealth(-50);
            HealthLabel.setText("Health: " + player.health);
            gameRoot.getChildren().remove(bonus);
            bonuses.clear();
            break;
          }
        }
        if (gameMode != "Replay") {
          if ((spawn.addNewEnemy()) == true) {
            enemies.add(new Enemy());
            gameRoot.getChildren().add(enemies.get(enemies.size() - 1));
            results.saveChar('n');
          } else {
            results.saveChar(' ');
          }
        }
        if (gameMode == "Replay") {
          if (results.loadChar() == 'n') {
            enemies.add(new Enemy());
            gameRoot.getChildren().add(enemies.get(enemies.size() - 1));
          }
        }
        /**
         * if player movement == 0
         */
        if (gameMode != "Replay") {
          if (Character.getCurrentAxis() == 0) {
            Music.activateEngineSound(false);
          }
          Character.setCurrentAxis(0);
        }
      }
    };
    timer.start();

  }

  /**
   * pause GUI
   */
  public void pause() {
    timer.stop();
    Music.activateEngineSound(false);
    MenuItem cont = new MenuItem("ПРОДОЛЖИТЬ");
    MenuItem mainMenu = new MenuItem("ГЛАВНОЕ МЕНЮ");
    MenuItem exitGame = new MenuItem("ВЫХОД");
    SubMenu pauseMenu = new SubMenu(cont, mainMenu, exitGame);
    cont.setOnMouseClicked(event -> {
      gameRoot.getChildren().removeAll(pauseBG, pauseMenu);
      timer.start();
    });
    mainMenu.setOnMouseClicked(event -> {

    });
    exitGame.setOnMouseClicked(event -> {
      System.exit(0);
    });
    gameRoot.getChildren().addAll(pauseBG, pauseMenu);
  }

  /**
   * GameOver GUI
   */
  public void gameOver() {
    timer.stop();
    Music.activateEngineSound(false);
    MenuItem mainMenu = new MenuItem("ГЛАВНОЕ МЕНЮ");
    MenuItem exitGame = new MenuItem("ВЫХОД");
    SubMenu gameOverMenu = new SubMenu(mainMenu, exitGame);
    mainMenu.setOnMouseClicked(event -> {

    });
    exitGame.setOnMouseClicked(event -> {
      System.exit(0);
    });
    gameRoot.getChildren().addAll(gameOverView, gameOverMenu);
  }

  public class checkHitTask implements Runnable {

    @Override
    public void run() {
      newBlock = (int) ((Math.random() * 1000) % 15);
    }
  }

}

