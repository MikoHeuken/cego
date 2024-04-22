import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//TODO überprüfen ob in der Runde 4 Stiche gemacht wurden
public class cego_ui extends JFrame implements ActionListener {
    private JLabel roundLabel;
    private Label[] playerNames;
    private JTextField[] playerScores;
    private JComboBox<String>[] playerStiches;
    private JButton finishRoundButton;
    private int roundNumber = 1;
    private int numberOfPlayers; // Beispielwert
    private cego_player[] player;

    /**
     * Konstruktor für das Cego-Punkte-Fenster
     * Es wird ein fenster erstellt, welches die aktuelle Runde anzeigt. Darunter befinden sich die Spieler mit ihren jeweiligen Punkten, sowie einer Möglichkeit anzugeben, wer wie viele Stiche gemacht hat.
     * @param player Die spieler, welche in der Runde Teilnehmen.
     */
    public cego_ui(cego_player[] player) {
        this.player = player;
        numberOfPlayers = player.length;

        //Titel, Größe, Schließoptionen und Layout werden gesetzt
        setTitle("Score Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Rundenzähler hinzufügen
        roundLabel = new JLabel("Runde: " + roundNumber);
        add(roundLabel, BorderLayout.NORTH);

        //die Fläche ür die Spielerfelder hinzufügen
        JPanel playerPanel = new JPanel(new GridLayout(numberOfPlayers, 3));
        playerNames = new Label[numberOfPlayers];
        playerScores = new JTextField[numberOfPlayers];
        playerStiches = new JComboBox[numberOfPlayers];

        //für jeden Spieler werden sein Name, sein Score und eine ComboBox zum Auswählen der gemachten Stiche hinzugefügt
        for(int i = 0; i < numberOfPlayers; i++){
            playerNames[i] = new Label(player[i].name);
            playerPanel.add(playerNames[i]);

            playerScores[i] = new JTextField("" + player[i].money);
            playerScores[i].setEditable(false); // Punktestand nicht editierbar
            playerPanel.add(playerScores[i]);

            playerStiches[i] = new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "Aussetzen"});
            playerPanel.add(playerStiches[i]);
        }
        add(playerPanel, BorderLayout.CENTER);

        //Abschlussbutton
        finishRoundButton = new JButton("Runde abschließen");
        finishRoundButton.addActionListener(this);
        add(finishRoundButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == finishRoundButton){
            //Runde abschließen
            roundNumber++;
            roundLabel.setText("Runde: " + roundNumber);

            //die Anzahl der Stiche pro Spieler werden ermittelt und die Punkte aktualisiert
            for(int i = 0; i < numberOfPlayers; i++){
                String stich = (String) playerStiches[i].getSelectedItem();

                //TODO aktualisierung der Punkte
                /**
                if (!stich.equals("Aussetzen")) {
                    int score = Integer.parseInt(playerScores[i].getText());
                    score += Integer.parseInt(stich);
                    playerScores[i].setText(Integer.toString(score));
                }
                playerStiches[i].setSelectedItem("0"); // Dropdown-Menü zurücksetzen */
            }
        }
    }

    public static void main(String[] args) {
        new cego_ui(null);
    }
}
