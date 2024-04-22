import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cego_ui extends JFrame implements ActionListener {
    private JLabel roundLabel;
    private JTextField[] playerNames;
    private JTextField[] playerScores;
    private JComboBox<String>[] playerStiches;
    private JButton finishRoundButton;
    private int roundNumber = 1;
    private int numberOfPlayers; // Beispielwert
    private cego_player[] player;

    public cego_ui(cego_player[] player) {
        this.player = player;
        numberOfPlayers = player.length;

        setTitle("Score Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        setLayout(new BorderLayout());

        // Rundenzähler
        roundLabel = new JLabel("Runde: " + roundNumber);
        add(roundLabel, BorderLayout.NORTH);

        // Spielerfelder
        JPanel playerPanel = new JPanel(new GridLayout(numberOfPlayers, 3));
        playerNames = new JTextField[numberOfPlayers];
        playerScores = new JTextField[numberOfPlayers];
        playerStiches = new JComboBox[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            playerNames[i] = new JTextField(player[i].name);
            playerPanel.add(playerNames[i]);

            playerScores[i] = new JTextField("" + player[i].money);
            playerScores[i].setEditable(false); // Punktestand nicht editierbar
            playerPanel.add(playerScores[i]);

            playerStiches[i] = new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "Aussetzen"});
            playerPanel.add(playerStiches[i]);
        }
        add(playerPanel, BorderLayout.CENTER);

        // Abschlussbutton
        finishRoundButton = new JButton("Runde abschließen");
        finishRoundButton.addActionListener(this);
        add(finishRoundButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == finishRoundButton) {
            // Runde abschließen
            roundNumber++;
            roundLabel.setText("Runde: " + roundNumber);

            for (int i = 0; i < numberOfPlayers; i++) {
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
        //new cego_ui();
    }
}
