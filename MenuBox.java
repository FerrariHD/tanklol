package game;


import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * 
 * @author FerrariHD
 *
 */
public class MenuBox extends Pane {
  static SubMenu subMenu;

  public MenuBox(SubMenu subMenu) {
    MenuBox.subMenu = subMenu;

    getChildren().addAll(subMenu);
  }

  public void setSubMenu(SubMenu subMenu) {
    getChildren().remove(MenuBox.subMenu);
    MenuBox.subMenu = subMenu;
    getChildren().add(MenuBox.subMenu);
  }
  
  public void setItem(Node item) {
    getChildren().add(item);
  }
}
