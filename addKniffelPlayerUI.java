import javax.swing.*;

import KNIFFEL.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class addKniffelPlayerUI extends JFrame implements ActionListener {
    private JTextField nameField;
    private JTextArea nameListArea;
    private JButton addButton;
    private JButton startGameButton;
    private JButton loadGameButton;
    private ArrayList<String> nameList;

    public addKniffelPlayerUI() {
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

        // Button zum Spiel starten oder Spiel laden
        startGameButton = new JButton("Spiel starten");
        startGameButton.addActionListener(this);

        loadGameButton = new JButton("Spiel laden");
        loadGameButton.addActionListener(this);

        //Panel in dem die Buttons angezeigt werden
        JPanel buttonPanel = new JPanel(new GridLayout(2,1));
        buttonPanel.add(startGameButton);
        buttonPanel.add(loadGameButton);

        // Layout
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name: "));
        inputPanel.add(nameField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(nameListArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

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
          if(nameList.size() >= 1){
            startGame();
          }else{
            JOptionPane.showMessageDialog(this, "Zum Starten der Runde wird mindestens ein Spieler benötigt.", "Fehler", JOptionPane.ERROR_MESSAGE);
          }
        }

        if(e.getSource() == loadGameButton){
          
        }
    }

    /**
     * startet eine Runde
     */
    private void startGame(){
        kniffel_player[] player = new kniffel_player[nameList.size()];
        for(int i = 0; i < nameList.size(); i++){
          player[i] = new kniffel_player(nameList.get(i));
        }
        new kniffel_ui(player);
        dispose();
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
        new addKniffelPlayerUI();
    }
}
