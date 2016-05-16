package game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class FileList extends ListView<String> {
  private GameInfo[] items;
  public static final int WIDTH = 500;
  public static final int HEIGHT = 350;
  private final int spacingY = 40;

  public FileList(String[] addenItems) {
    setPrefSize(WIDTH, HEIGHT);
    setTranslateX(405);
    setTranslateY(spacingY);
    setOpacity(0.95);
    items = new GameInfo[addenItems.length];
    for (int i = 0; i < addenItems.length; i++) {
      items[i] = new GameInfo(addenItems[i]);
    }
    ObservableList<String> ol = FXCollections.observableArrayList();
    ol.addAll(addenItems);
    setItems(ol);
  }

  public void setFiles(String[] addenItems) {
    ObservableList<String> ol = FXCollections.observableArrayList();
    ol.addAll(addenItems);
    items = new GameInfo[addenItems.length];
    for (int i = 0; i < addenItems.length; i++) {
      items[i] = new GameInfo(addenItems[i]);
    }
    setItems(ol);
  }
}
