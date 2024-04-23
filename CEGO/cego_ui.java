package CEGO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class cego_ui extends JFrame implements ActionListener {
    private JLabel roundLabel;
    private JLabel checkpotLabel;
    private Label[] playerNames;
    private JTextField[] playerScores;
    private JComboBox<String>[] playerStiches;
    private JButton finishRoundButton;
    private JButton finishGameButon;
    private JButton backButton;
    private int roundNumber;
    private int numberOfPlayers;
    private cego_player[] player;
    private cego game;
    private ArrayList<Float>[] cache;

    /**
     * Konstruktor für das Cego-Punkte-Fenster
     * Es wird ein Fenster erstellt, welches die aktuelle Runde anzeigt. Darunter befinden sich die Spieler mit ihren jeweiligen Punkten, sowie einer Möglichkeit anzugeben, wer wie viele Stiche gemacht hat.
     * @param player Die spieler, welche in der Runde Teilnehmen.
     */
    @SuppressWarnings("unchecked")
    public cego_ui(cego_player[] player, int bet) {
        this.player = player; 
        numberOfPlayers = player.length;
        game = new cego(player, bet, this);
        roundNumber = 1;
        cache = new ArrayList[numberOfPlayers + 1];

        for(int i = 0; i < cache.length; i++){
            cache[i] = new ArrayList<>();
        }

        //Titel, Größe, Schließoptionen und Layout werden gesetzt
        setTitle("Cego");
        setSize(800, 300);
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

        //Rundenendebutton, Zurückbutton und Spielendebutton
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        finishRoundButton = new JButton("Runde abschließen");
        finishGameButon = new JButton("Spiel abschließen und speichern");
        backButton = new JButton("Zurück");
        buttonPanel.add(backButton);
        buttonPanel.add(finishRoundButton);
        buttonPanel.add(finishGameButon);
        backButton.addActionListener(this);
        finishRoundButton.addActionListener(this);
        finishGameButon.addActionListener(this);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        game.startRound();
    }

    /**
     * setzt fügt dem Cache für den Spieler an der Stelle i den Wert n hinzu
     * @param i die Stelle des Spielers im Cache
     * @param n der Wert, welcher hinzugefügt werden soll
     */
    public void setCache(int i, float n){
        cache[i].add(n);
    }

    public int getCacheLength(){
        return cache.length;
    }

    /**
     * ActionListener für die Buttons "Zurück", "Runde abschließen" und "Spiel abschließen und speichern"
     */
    @Override
    public void actionPerformed(ActionEvent e){
        //überprüft ob eine Runde abgeschlossen werden kann, wenn ja schkießt die Runde ab
        if(e.getSource() == finishRoundButton){
            if (!isRoundValid()) {
                JOptionPane.showMessageDialog(this, "Die Runde ist nicht gültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }else{
                finishRound();
            }
        }
        //überprüft ob ein Spiel abgeschlossen werden kann, wenn ja schließt das Spiel und speichert
        if(e.getSource() == finishGameButon){
            if(!isRoundValid()){
                JOptionPane.showMessageDialog(this, "Die Runde ist nicht gültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }else{
                finishGame();
            }
        }
        //überprüft ob zurück gegangen werden kann, wenn ja geht das Spiel eine Runde zurück
        if(e.getSource() == backButton){
            if(roundNumber == 1){
                JOptionPane.showMessageDialog(this, "In der ersten Runde kann nicht zurück gegangen werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }else{
                back();
            }
        }
    }

    /**
     * geht eine Runde zurück, dazu werden die Punkte und der Checkpot aus dem Cache geladen und anschließend aus dem Cache entfernt
     */
    public void back(){
        roundNumber--;

        for(int i = 0; i < numberOfPlayers; i++){
            float pointsNow = cache[i].get(cache[i].size() - 1);
            float oldPoints = cache[i].get(cache[i].size() - 2);

            player[i].setPoints(oldPoints - pointsNow);
            cache[i].remove(cache[i].size() - 1);
        }

        game.setCheckpot(cache[cache.length - 1].get(cache[cache.length - 1].size() - 2));
        cache[cache.length - 1].remove(cache[cache.length - 1].size() - 1);

        changeCheckpot();
        changePoints();
        roundLabel.setText("Runde: " + roundNumber);
    }

    /**
     * wird auferufen wenn der Spielabschlussbutton betätigt wird
     */
    public void finishGame(){
        ArrayList<cego_player> playersWithoutStitch = new ArrayList<cego_player>();
        for(int i = 0; i < numberOfPlayers; i++){
            String stitch = (String) playerStiches[i].getSelectedItem();

            if(stitch.equals("0")){
                playersWithoutStitch.add(player[i]);
            }

            if (!stitch.equals("Aussetzen")) {
                game.splitPot(Integer.parseInt(stitch), player[i]);
            }
        }

        game.safeRound(roundNumber, cache);
        dispose();
    }

    /**
     * wird aufgerufen wenn der Rundenabschlussbutton betätigt wird
     */
    public void finishRound(){
        ArrayList<cego_player> playersWithoutStitch = new ArrayList<cego_player>();

            //die Anzahl der Stiche pro Spieler werden ermittelt und die Punkte aktualisiert
            for(int i = 0; i < numberOfPlayers; i++){

                String stitch = (String) playerStiches[i].getSelectedItem();

                if(stitch.equals("0")){
                    playersWithoutStitch.add(player[i]);
                }

                if (!stitch.equals("Aussetzen")) {
                    game.splitPot(Integer.parseInt(stitch), player[i]);
                }
                playerStiches[i].setSelectedItem("0"); // Dropdown-Menü zurücksetzen */
            }

            //Runde abschließen
            roundNumber++;
            roundLabel.setText("Runde: " + roundNumber);

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
        new cego_ui(null, 10);
    }
}
