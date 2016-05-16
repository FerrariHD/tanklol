package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class ReplaysController {
  private RandomAccessFile file;

  /**
   * Create file for saving with extend .save
   */
  public void createResultFile() {
    try {
      file = new RandomAccessFile(new File(new Date().toString()+".save"), "rw");
    } catch (FileNotFoundException e) {
      System.out.println("File was not created!");
    }
  }

  /**
   * @param fileName name of opening file
   */
  public void openResultFile(String fileName) {
    try {
      file = new RandomAccessFile(new File(fileName), "rw");
    } catch (FileNotFoundException e) {
      System.out.println("File was not opened!");
    }
  }
  
  public void saveChar(char r)
  {
    try {
      file.writeChar(r);
    } catch (IOException e) {
      System.out.println("Error of writing!");
    }
  }
  
  public char loadChar()
  {
    try {
      if (file.getFilePointer() == file.length()) {
        return (char) -1;
      }
      return file.readChar();
    } catch (IOException e) {
      System.out.println("Error of reading!");
    }
    return (char) -1;
  }
  
  /**
   * Close results file
   */
  public void finalize() {
    try {
      file.close();
    } catch (IOException e) {
      System.out.println("File was not closed!");
    }
  }
  
  /**
   * 
   * @return directory content(but only items ".save")
   */
  public static String[] folderContent() {
    File folder = new File(".");
    File[] listOfFiles = folder.listFiles();
    int length = 0;
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].getName().contains(".save")) {
        length++;
      }
    }
    String[] content = new String[length];
    for (int i = 0, j = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].getName().contains(".save")) {
        content[j] = listOfFiles[i].getName();
        j++;
      }
    }
    return content;
  }
}
