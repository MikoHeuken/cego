package KNIFFEL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.swing.*;

public class kniffel_loadGameUI extends JFrame implements ActionListener{
  
  private JButton[] gamesToLoadButtons;
  private String[] fileNames;
  private String location = "./KNIFFEL/savedRounds";

  public kniffel_loadGameUI(){
    getFiles();
    for(String file : fileNames){
      System.out.println(isdiscardedGame(file));
    }
  }

  public File[] getFiles(){
    File folder = new File(location);

    // Array von File-Objekten für alle Dateien im Ordner erstellen
    File[] files = folder.listFiles(new FilenameFilter() {
    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(".txt"); // Nur Textdateien berücksichtigen
    }
    });

    // Array für Dateinamen mit der richtigen Größe erstellen
    fileNames = new String[files.length];

    // Dateinamen aus den File-Objekten extrahieren und speichern
    for (int i = 0; i < files.length; i++) {
      fileNames[i] = files[i].getName();
    }

    return files;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
        
  }

  public static boolean isdiscardedGame(String textFileName){
    String fileName = "./KNIFFEL/savedRounds/" + textFileName;
 
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String firstLine = reader.readLine();
      String[] firstLineContents = firstLine.split(",");
      return Boolean.valueOf(firstLineContents[0]);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
}

  public static void main(String[] args){
    new kniffel_loadGameUI();
  }
}
