import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


//TODO überprüfen ob in der Runde 4 Stiche gemacht wurden
public class cego_ui extends JFrame implements ActionListener {
    private JLabel roundLabel;
    private JLabel checkpotLabel;
    private Label[] playerNames;
    private JTextField[] playerScores;
    private JComboBox<String>[] playerStiches;
    private JButton finishRoundButton;
    private int roundNumber;
    private int numberOfPlayers;
    private cego_player[] player;
    private cego game;
    private int bet = 10; //TODO noch Hardcoded

    /**
     * Konstruktor für das Cego-Punkte-Fenster
     * Es wird ein Fenster erstellt, welches die aktuelle Runde anzeigt. Darunter befinden sich die Spieler mit ihren jeweiligen Punkten, sowie einer Möglichkeit anzugeben, wer wie viele Stiche gemacht hat.
     * @param player Die spieler, welche in der Runde Teilnehmen.
     */
    @SuppressWarnings("unchecked")
    public cego_ui(cego_player[] player) {
        this.player = player;
        numberOfPlayers = player.length;
        game = new cego(player, bet, this);
        roundNumber = 1;

        //Titel, Größe, Schließoptionen und Layout werden gesetzt
        setTitle("Score Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Rundenzähler und Checkpot Feld erstellen
        roundLabel = new JLabel("Runde: " + roundNumber);
        checkpotLabel = new JLabel("Checkpot: 0");

        //Rundenzähler und Checkpot dem Fenster hinzufügen
        JPanel roundAndCheckpotPanel = new JPanel(new GridLayout(2, 1));
        roundAndCheckpotPanel.add(roundLabel);
        roundAndCheckpotPanel.add(checkpotLabel);
        add(roundAndCheckpotPanel, BorderLayout.NORTH);

        //die Fläche ür die Spielerfelder hinzufügen
        JPanel playerPanel = new JPanel(new GridLayout(numberOfPlayers, 3));
        playerNames = new Label[numberOfPlayers];
        playerScores = new JTextField[numberOfPlayers];
        playerStiches = new JComboBox[numberOfPlayers];

        //für jeden Spieler werden sein Name, sein Score und eine ComboBox zum Auswählen der gemachten Stiche hinzugefügt
        for(int i = 0; i < numberOfPlayers; i++){
            playerNames[i] = new Label(player[i].getName());
            playerPanel.add(playerNames[i]);

            playerScores[i] = new JTextField("" + player[i].getPoints());
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

        startGame();
    }

    /**
     * Startet das Spiel
     */
    private void startGame(){
        game.startRound();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == finishRoundButton){
            if (!isRoundValid()) {
                JOptionPane.showMessageDialog(this, "Die Runde ist nicht gültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return; // Stoppe die Ausführung, wenn die Runde ungültig ist
            }

            ArrayList<cego_player> playersWithoutStitch = new ArrayList<cego_player>();

            //Runde abschließen
            roundNumber++;
            roundLabel.setText("Runde: " + roundNumber);

            //die Anzahl der Stiche pro Spieler werden ermittelt und die Punkte aktualisiert
            for(int i = 0; i < numberOfPlayers; i++){
                String stitch = (String) playerStiches[i].getSelectedItem();

                if(stitch.equals("0")){
                    playersWithoutStitch.add(player[i]);
                }

                if (!stitch.equals("Aussetzen")) {
                    game.splitPot(Integer.parseInt(stitch), player[i]);
                    //int score = Integer.parseInt(playerScores[i].getText());
                    //score += Integer.parseInt(stich);
                    //playerScores[i].setText(Integer.toString(score));
                }
                playerStiches[i].setSelectedItem("0"); // Dropdown-Menü zurücksetzen */
            }

            changePoints();

            //falls es Spieler mit keinem Stich gibt, wird die Bonusrunde gestertet. Dabei werden nur den Spielern ohne Stiche Punkte abgezogen
            if(playersWithoutStitch.size() != 0){
                cego_player[] player = new cego_player[playersWithoutStitch.size()];
                for(int i = 0; i < player.length; i++){
                    player[i] = playersWithoutStitch.get(i);
                }
                game.bonusRound(player);
            }else{
                game.startRound();
            }
        }
    }

    /**
     * ändert die Punkte der Anzeige
     */
    public void changePoints(){
        for(int i = 0; i < numberOfPlayers; i++){
            playerScores[i].setText(Float.toString(player[i].getPoints()));
        }
    }

    /**
     * ändert den Text des Checkpot-Labels
     */
    public void changeCheckpot(){
        checkpotLabel.setText("Checkpot: " + game.getCheckpot());
    }

    /**
     * prüft ob eine Runde abgeschlossen werden kann
     * @return gibt zurück ob die runde gültig ist. Ungültig wird sie wenn weniger als 3 Spieler mitmachen oder nicht genau 4 Stiche erreicht wurden
     */
    public boolean isRoundValid(){
        int nrOfStitches = 0;
        int nrOfAussetzen = 0;

        for(int i = 0; i < numberOfPlayers; i++){
            String stitch = (String) playerStiches[i].getSelectedItem();
            switch (stitch) {
                case "Aussetzen":
                    nrOfAussetzen++;
                    break;
                case "1":
                    nrOfStitches += 1;
                    break;
                case "2":
                    nrOfStitches += 2;
                    break;
                case "3":
                    nrOfStitches += 3;
                    break;
                case "4":
                    nrOfStitches += 4;
                    break;
            }
        }

        if(nrOfStitches != 4 || numberOfPlayers - nrOfAussetzen < 3){
            return false;
        }else{
            return true;
        }
    }

    public static void main(String[] args) {
        new cego_ui(null);
    }
}
