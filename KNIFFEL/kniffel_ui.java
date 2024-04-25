package KNIFFEL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class kniffel_ui extends JFrame implements ActionListener {
    private String[] categories = {"1er", "2er", "3er", "4er", "5er", "6er",
                                    "3er Pasch", "4er Pasch", "Full House",
                                    "Kleine Straße", "Große Straße", "Kniffel", "Chance"};

    private JComboBox<Integer>[][] scoreSelectors;
    private JButton nextPlayerButton;
    private JLabel roundLabel;
    private JLabel currentPlayerLabel;
    private JLabel[] upperHalfLabel;
    private JLabel[] bonusLabel;
    private JLabel[] totalUpperHalfLabelUp;
    private JLabel[] totalUpperHalfLabelDown;                                                                             
    private JLabel[] lowerHalfLabel;
    private JLabel[] totalPointsLabel;
    private int numberOfPlayers;
    private int currentPlayerIndex = 0;
    private kniffel_player[] player;
    private int roundNr;

    /**
     * Konstruktor für das UI für Kniffel
     * @param player die Spieler welche in diesem Spiel teilnehmen
     */
    @SuppressWarnings("unchecked")
    public kniffel_ui(kniffel_player[] player) {
        this.player = player;
        numberOfPlayers = player.length;
        roundNr = 1;

        setTitle("Kniffel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        //setzt eine Border um das mainPanel
        int borderThickness = (int) (Toolkit.getDefaultToolkit().getScreenResolution() * 0.2);
        mainPanel.setBorder(new EmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness));

        //fügt die aktuelle Runde und den aktuellen Spieler zum Fenster hinzu
        JPanel roundAndPlayerPanel = new JPanel(new GridLayout(2, 1));
        roundLabel = new JLabel("Runde " + roundNr);
        currentPlayerLabel = new JLabel("Spieler: " + player[currentPlayerIndex].getName());
        roundAndPlayerPanel.add(roundLabel);
        roundAndPlayerPanel.add(currentPlayerLabel);

        //erstellt Arrays an Label, damit jeder Spieler ein eigenständiges Label hat
        upperHalfLabel = new JLabel[numberOfPlayers];
        bonusLabel = new JLabel[numberOfPlayers];
        totalUpperHalfLabelUp = new JLabel[numberOfPlayers];
        totalUpperHalfLabelDown = new JLabel[numberOfPlayers];
        lowerHalfLabel = new JLabel[numberOfPlayers];
        totalPointsLabel = new JLabel[numberOfPlayers];

        //Initialisiert alle Punkte Label mit 0
        for(int i = 0; i < numberOfPlayers; i++){
          upperHalfLabel[i] = new JLabel("0");
          bonusLabel[i] = new JLabel("0");
          totalUpperHalfLabelUp[i] = new JLabel("0");
          totalUpperHalfLabelDown[i] = new JLabel("0");
          lowerHalfLabel[i] = new JLabel("0");
          totalPointsLabel[i] = new JLabel("0");

          //zentriert den Inhalt mittig
          upperHalfLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
          upperHalfLabel[i].setVerticalAlignment(SwingConstants.CENTER);
          bonusLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
          bonusLabel[i].setVerticalAlignment(SwingConstants.CENTER);
          totalUpperHalfLabelUp[i].setHorizontalAlignment(SwingConstants.CENTER);
          totalUpperHalfLabelUp[i].setVerticalAlignment(SwingConstants.CENTER);
          totalUpperHalfLabelDown[i].setHorizontalAlignment(SwingConstants.CENTER);
          totalUpperHalfLabelDown[i].setVerticalAlignment(SwingConstants.CENTER);
          lowerHalfLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
          lowerHalfLabel[i].setVerticalAlignment(SwingConstants.CENTER);
          totalPointsLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
          totalPointsLabel[i].setVerticalAlignment(SwingConstants.CENTER);
        }

        //Kategorien-Panel, wird an der linken Seite angezeigt, die Zeilenanzahl ist 7 mehr als es Kategorien gibt
        //6 Zeilen für die Summen/Bonus, eine leere Zeile oben damit die Kategorien nicht in der Zeile mit den Namen stehen
        JPanel categoriesPanel = new JPanel(new GridLayout(categories.length + 7, 1));
        categoriesPanel.add(new JLabel());
        for(int i = 0; i < categories.length; i++){
          categoriesPanel.add(new JLabel(categories[i]));

          if(i == 5){
            categoriesPanel.add(new JLabel("Summe oben"));
            categoriesPanel.add(new JLabel("Bonus (63 oder mehr)"));
            categoriesPanel.add(new JLabel("Summe oben gesamt"));
          }
        }
        categoriesPanel.add(new JLabel("Summe oben"));
        categoriesPanel.add(new JLabel("Summe unten"));
        categoriesPanel.add(new JLabel("Summe gesamt"));

        //Spieler-Panel, wird oben horizontal angezeigt
        JPanel playerPanel = new JPanel(new GridLayout(1, numberOfPlayers + 1));
        scoreSelectors = new JComboBox[numberOfPlayers][categories.length];

        //fügt jedem Spieler für jede Kategorie ein passendes Dropdown Menu zum Auswählen der Punkte hinzu, dazwischen werden Felder für die Punkte hinzugefügt
        for (int i = 0; i < numberOfPlayers; i++) {
            JPanel playerColumn = new JPanel(new GridLayout(categories.length + 7, 1));

            JLabel playerLabel = new JLabel(player[i].getName());
            Font font = playerLabel.getFont();
            playerLabel.setFont(new Font(font.getName(), Font.PLAIN, 18));
            playerColumn.add(playerLabel);

            for (int j = 0; j < categories.length; j++) {
                scoreSelectors[i][j] = new JComboBox<>(dropDownOptions(categories[j]));
                scoreSelectors[i][j].setSelectedItem(null);
                playerColumn.add(scoreSelectors[i][j]);

                if(j == 5){
                  playerColumn.add(upperHalfLabel[i]);
                  playerColumn.add(bonusLabel[i]);
                  playerColumn.add(totalUpperHalfLabelUp[i]);
                }
            }
            playerColumn.add(totalUpperHalfLabelDown[i]);
            playerColumn.add(lowerHalfLabel[i]);
            playerColumn.add(totalPointsLabel[i]);

            playerPanel.add(playerColumn);
        }

        //Button zum nächsten Spieler
        nextPlayerButton = new JButton("Nächster Spieler");
        nextPlayerButton.addActionListener(this);

        //füßgt die alle Panels dem mainPanel hinzu
        mainPanel.add(categoriesPanel, BorderLayout.WEST);
        mainPanel.add(playerPanel, BorderLayout.CENTER);
        mainPanel.add(nextPlayerButton, BorderLayout.SOUTH);
        mainPanel.add(roundAndPlayerPanel, BorderLayout.NORTH);

        add(mainPanel);

        setLocationRelativeTo(null); // Zentriere das Fenster
        setVisible(true);
    }

    /**
     * gibt jedem Dropdown Menu die Optionen vor, so dass nur gültige Punkte eingetragen werden können
     * @param categorie die Kategorie des Dropdown Menus
     * @return gibt die möglichen Punkte zu der Kategorie zurück
     */
    private Integer[] dropDownOptions(String categorie){
      ArrayList<Integer> options = new ArrayList<>();
      options.add(null);
      
      switch (categorie) {
        case "1er":
          for(int i = 1; i <= 6; i++){
            options.add(i);
          }
          break;

        case "2er":
          for(int i = 1; i <= 6; i++){
            options.add(i * 2);
          }
          break;

        case "3er":
          for(int i = 1; i <= 6; i++){
            options.add(i * 3);
          }
          break;

        case "4er":
          for(int i = 1; i <= 6; i++){
            options.add(i * 4);
          }
          break;

        case "5er":
          for(int i = 1; i <= 6; i++){
            options.add(i * 5);
          }
          break;

        case "6er":
          for(int i = 1; i <= 6; i++){
            options.add(i * 6);
          }
          break;

        case "3er Pasch":
        case "4er Pasch":
        case "Chance":
          for(int i = 5; i <= 30; i++){
            options.add(i);
          }
          break;
        
        case "Full House":
          options.add(25);
          break;

        case "Kleine Straße":
          options.add(30);
          break;

        case "Große Straße":
          options.add(40);
          break;

        case "Kniffel":
          options.add(50);
          break;
      }

      Integer[] returnOptions = new Integer[options.size()];
      for(int i = 0; i < returnOptions.length; i++){
        returnOptions[i] = options.get(i);
      }

      return returnOptions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextPlayerButton) {
          int chosenCategorie = whichOptionWasChosen(currentPlayerIndex);
          if(chosenCategorie != -1 && onlyOneOptionChosen(currentPlayerIndex)){
            if(onlyCurrentPlayerChose(currentPlayerIndex)){
              logCategorie(chosenCategorie, currentPlayerIndex);
              nextPlayer();
            }else{
              JOptionPane.showMessageDialog(this, "In dieser Runde darf nur " + player[currentPlayerIndex].getName() + " eine Auswahl treffen.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
          }else{
            JOptionPane.showMessageDialog(this, "Es darf nur genau eine Option ausgewählt sein.", "Fehler", JOptionPane.ERROR_MESSAGE);
          }
        }
    }

    /**
     * setzt currentPlayerIndex auf den nächsten Spieler
     */
    public void nextPlayer(){
      if(currentPlayerIndex == numberOfPlayers - 1){
        roundNr++;
        currentPlayerIndex = 0;
      }else{
        currentPlayerIndex++;
      }
      setRoundLabel();
      setPlayerLabel();
    }

    public boolean onlyCurrentPlayerChose(int playerNr){
      for(int i = 0; i < numberOfPlayers; i++){
        for(int j = 0; j < scoreSelectors[i].length; j++){
          if(i != currentPlayerIndex){
            if(scoreSelectors[i][j].getSelectedItem() != null && scoreSelectors[i][j].isEnabled()){
              return false;
            }
          }
        }
      }
      return true;
    }

    /**
     * loggt die Ausgeählte Kategorie für den Spieler ein und gibt dem Spieler die entsprechenden Punkte
     * @param categorieIndex der Index der eingeloggten Kategorie
     * @param currentPlayerIndex der betroffene Spieler
     */
    public void logCategorie(int categorieIndex, int currentPlayerIndex){
      int chosenOption = (Integer) scoreSelectors[currentPlayerIndex][categorieIndex].getSelectedItem();
      scoreSelectors[currentPlayerIndex][categorieIndex].setEnabled(false);

      //prüft ob die Punkte in der oberen Hälfte oder der unteren Hälfte erzielt wurden
      if(categorieIndex < 6){
        player[currentPlayerIndex].changePointsUpperHalf(chosenOption, currentPlayerIndex, this);
      }else{
        player[currentPlayerIndex].changePointsLowerHalf(chosenOption, currentPlayerIndex, this);
      }
    }

    public boolean onlyOneOptionChosen(int playerNr){
      boolean onlyOptionChosen = false;
      for(int i = 0; i < scoreSelectors[playerNr].length; i++){
        if(scoreSelectors[playerNr][i].getSelectedItem() != null && scoreSelectors[playerNr][i].isEnabled()){
          if(onlyOptionChosen){
            return false;
          }else{
            onlyOptionChosen = true;
          }
        }
      }
      return onlyOptionChosen;
    }

    /**
     * gibt aus, welche Option der Spieler ausgewähl hat
     * @param playerNr der Spieler, der an der Reihe ist
     * @return den Index der ausgewählten Option, -1 wenn keine Opion ausgewählt wurde
     */
    public int whichOptionWasChosen(int playerNr){
      for(int i = 0; i < scoreSelectors[playerNr].length; i++){
          if(scoreSelectors[playerNr][i].getSelectedItem() != null && scoreSelectors[playerNr][i].isEnabled()){
            return i;
          }
      }
      return -1;
    }

    /**
     * ändert das Punkte Label welches die oberen Punkte vor Bonus anzeigt
     * @param playerNr der betroffene Spieler
     * @param text der neue Wert für das Label
     */
    public void setUpperHalfLabel(int playerNr, String text){
      upperHalfLabel[playerNr].setText(text);
    }

    /**
     * ändert das Punkte Label welches die gesamten Punkte von oben, unten am Spielrand anzeigt
     * @param playerNr der betroffene Spieler
     * @param text der neue Wert für das Label
     */
    public void setTotalUpperHalfDownLabel(int playerNr, String text){
      totalUpperHalfLabelDown[playerNr].setText(text);
    }

    /**
     * ändert das Punkte Label welches den Bonus anzeigt
     * @param playerNr der betroffene Spieler 
     * @param text der neue Wert für das Label
     */
    public void setBonusLabel(int playerNr, String text){
      bonusLabel[playerNr].setText(text);
    }

    /**
     * ändert das Punkte Label welches die gesamten Punkte von oben, oben nach dem Bonus anzeigt
     * @param playerNr der betroffene Spieler
     * @param text der neue Wert für das Label
     */
    public void setTotalUpperHalfUpLabel(int playerNr, String text){
      totalUpperHalfLabelUp[playerNr].setText(text);
    }

    /**
     * ändert das Label welches die Punkte von unten aneigt
     * @param playerNr der betroffene Spieler
     * @param text der neue Wert für das Label
     */
    public void setLowerHalfLabel(int playerNr, String text){
      lowerHalfLabel[playerNr].setText(text);
    }

    /**
     * ändert das Label welches die Gesamtpunkte anzeigt
     * @param playerNr der betrofene Spieler
     * @param text der neue Wert für das Label
     */
    public void setTotalPointsLabel(int playerNr, String text){
      totalPointsLabel[playerNr].setText(text);
    }

    public void setRoundLabel(){
      roundLabel.setText("Runde " + roundNr);
    }

    public void setPlayerLabel(){
      currentPlayerLabel.setText("Spieler: " + player[currentPlayerIndex].getName());
    }

    public static void main(String[] args) {
        int numberOfPlayers = 4;
        //new kniffel_ui(numberOfPlayers);
    }
}
