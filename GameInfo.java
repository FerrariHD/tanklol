package game;

import java.io.File;

public class GameInfo {
  private String fileName = new String();
  private long fileSize;

  public GameInfo(String fileName) {
    this.fileName = fileName;
    this.fileSize = new File(fileName).length();
  }

  public String getFileName() {
    return fileName;
  }

  public long getFileSize() {
    return fileSize;
  }
}
