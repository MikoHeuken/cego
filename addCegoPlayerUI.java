import javax.swing.*;

import CEGO.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class addCegoPlayerUI extends JFrame implements ActionListener {
    private JTextField nameField;
    private JTextArea nameListArea;
    private JButton addButton;
    private JButton startGameButton;
    private ArrayList<String> nameList;

    public addCegoPlayerUI() {
        setTitle("Spieler hinzufügen");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Textfeld zum Eingeben von Namen
        nameField = new JTextField(20);

        // Button zum Hinzufügen von Namen
        addButton = new JButton("Hinzufügen");
        addButton.addActionListener(this);

        // Textbereich zur Anzeige der Liste von Namen
        nameListArea = new JTextArea(10, 20);
        nameListArea.setEditable(false); // Damit der Benutzer nicht direkt in die Liste eingeben kann

        // Button zum Spiel starten
        startGameButton = new JButton("Spiel starten");
        startGameButton.addActionListener(this);

        // Layout
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name: "));
        inputPanel.add(nameField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(nameListArea), BorderLayout.CENTER);
        add(startGameButton, BorderLayout.SOUTH);

        // Initialisierung der Namensliste
        nameList = new ArrayList<>();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addPlayer();
        }
        if(e.getSource() == startGameButton){
          if(nameList.size() >= 3){
            startGame();
          }else{
            JOptionPane.showMessageDialog(this, "Zum Starten der Runde werden mindestens drei Spieler benötigt.", "Fehler", JOptionPane.ERROR_MESSAGE);
          }
        }
    }

    /**
     * fragt ab mit welchem Einsatz gespielt werden soll, danach startet eine Runde
     */
    private void startGame(){
      JFrame frame = new JFrame("Einsatz wählen");
      frame.setSize(300, 100);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Schließt nur das Spielstart-Fenster, nicht das Hauptfenster
      frame.setLayout(new FlowLayout());

      JLabel label = new JLabel("Geben Sie den Einsatz ein:");
      JTextField betField = new JTextField(10);
      JButton confirmButton = new JButton("Bestätigen");

      confirmButton.addActionListener(new ActionListener() {
          /**
           * bei Betätigen des Bestätigen Buttons, wird die Angabe zum Starten eines neuen Spieles genommen
           */
          @Override
          public void actionPerformed(ActionEvent e) {
              String betString = betField.getText();
              int bet = Integer.parseInt(betString); 
              frame.dispose(); // Schließe das Einsatzauswahlfenster

              CEGO.cego_player[] player = new cego_player[nameList.size()];
              for(int i = 0; i < nameList.size(); i++){
                player[i] = new cego_player(nameList.get(i), 0);
              }
              CEGO.cego_ui game = new cego_ui(player, bet);
              dispose();
          }
      });

      frame.add(label);
      frame.add(betField);
      frame.add(confirmButton);

      frame.setVisible(true);
    }

    /**
     * fügt den Spieler aus dem Textfeld der Liste hinzu
     */
    private void addPlayer(){
      String newName = nameField.getText().trim(); // Trim, um Leerzeichen am Anfang und Ende zu entfernen
            if (!newName.isEmpty()) {
                nameList.add(newName);
                updateNameListArea();
                nameField.setText(""); // Textfeld leeren für den nächsten Namen
            }
    }

    private void updateNameListArea() {
        StringBuilder sb = new StringBuilder();
        for (String name : nameList) {
            sb.append(name).append("\n");
        }
        nameListArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        new addCegoPlayerUI();
    }
}
