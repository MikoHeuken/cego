package KNIFFEL;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.*;

public class kniffel_loadGameUI extends JFrame implements ActionListener{
  
  private JButton[] gamesToLoadButtons;
  private String[] fileNames;
  private String[] discardedFileNames;
  private String location = "./KNIFFEL/savedRounds";
  private ArrayList<File> discardedGameFiles;

  public kniffel_loadGameUI(){
  //erst werden alle Files aus dem savedRounds geladen
    File[] allFiles = getFiles();
    discardedGameFiles = new ArrayList<>();

    //jedes geladene Spiel wird überprüft, ob es abgebrochen oder beendet wurde; abgebrochene Spiele werden in ein Array geladen
    for(int i = 0; i < fileNames.length; i++){
      if(isDiscardedGame(fileNames[i])){
        discardedGameFiles.add(allFiles[i]);
      }
    }

    //String für die Namen der abgebrochenen Spiele
    discardedFileNames = new String[discardedGameFiles.size()];

    //es werden so viel Buttons angelegt, wie es abgebrochene Spiele gibt
    gamesToLoadButtons = new JButton[discardedFileNames.length];

    //für jedes abgebrochene Spiel, wird ein Button mit dem Datum und der Uhrzeit zum Array hinzugefügt
    for(int i = 0; i < discardedFileNames.length; i++){
      discardedFileNames[i] = discardedGameFiles.get(i).getName();
      String[] filenameData = getFileDate(discardedFileNames[i]);
      gamesToLoadButtons[i] = new JButton(filenameData[2] + "." + filenameData[1] + "." + filenameData[0] + " um " + filenameData[3] + ":" + filenameData[4] + " Uhr");
      gamesToLoadButtons[i].addActionListener(this);
    }

    //fügt alle Inhalte dem mainPanel hinzu
    JPanel mainPanel = new JPanel(new GridLayout(gamesToLoadButtons.length + 1, 1));
    JLabel description = new JLabel("Welches abgebrochene Spiel willst du wählen?");
    description.setHorizontalAlignment(SwingConstants.CENTER);
    description.setVerticalAlignment(SwingConstants.CENTER);
    mainPanel.add(description);

    for(int i = 0; i < gamesToLoadButtons.length; i++){
      mainPanel.add(gamesToLoadButtons[i]);
    }

    //Einstellungen für das Hauptfenster
    add(mainPanel);
    setTitle("Spiel laden");
    setSize(600, 100 * gamesToLoadButtons.length);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    }

  /**
   * lädt alle Files aus dem getSaved Ordner, die Namen werden automatisch in FileNames geschrieben
   * @return bei Bedarf erhält man alle Files aus dem Ordner
   */
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
    for(int  i = 0; i < gamesToLoadButtons.length; i++){
      if(gamesToLoadButtons[i] == e.getSource()){
        startGameFromFile(i);
      }
    }
  }

  /**
   * startet eine unterbrochene Runde neu
   * @param index die Stelle im Array, an dem die Datei mit den Spieldaten liegt
   */
  public void startGameFromFile(int index){
    String fileName = "./KNIFFEL/savedRounds/" + discardedFileNames[index];
    kniffel_player[] loadPlayer = null;
    ArrayList<String> lines = null;

    try {
      lines = (ArrayList<String>) Files.readAllLines(Path.of(fileName));           //liest alle Zeilen ein
    } catch (IOException e) {
      e.printStackTrace();
    }

    loadPlayer = new kniffel_player[lines.size() - 1];
    for(int i = 1; i < lines.size(); i++){
      String[] temp = lines.get(i).split(",");
      int[] pointsInCategories = new int[temp.length - 1];
      for(int j = 1; j < temp.length; j++){
        pointsInCategories[j - 1] = Integer.parseInt(temp[j]);
      }
      //loadPlayer[i - 1] = new kniffel_player(temp[0], pointsInCategories);
      System.out.println(temp[0]);
      //TODO player konstruktor schreiben; an gestrichene werte denken; in kniffel_ui methode um geladenes spiel zu starten,
    }
  }

  public boolean isDiscardedGame(String textFileName){
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

  public String[] getFileDate(String textFileName){
    String[] dateData = textFileName.split("-");
    return dateData;
  }

  public static void main(String[] args){
    new kniffel_loadGameUI();
  }
}
