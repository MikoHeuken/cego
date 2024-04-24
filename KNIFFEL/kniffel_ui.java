package KNIFFEL;

import javax.swing.*;
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
    private int numberOfPlayers;
    private int currentPlayerIndex = 0;
    private kniffel_player[] player;

    @SuppressWarnings("unchecked")
    public kniffel_ui(kniffel_player[] player) {
        this.player = player;
        numberOfPlayers = player.length;
        setTitle("Kniffel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Kategorien-Panel
        JPanel categoriesPanel = new JPanel(new GridLayout(categories.length + 1, 1));
            categoriesPanel.add(new JLabel());
        for (String category : categories) {
            categoriesPanel.add(new JLabel(category));
        }

        // Spieler-Panel
        JPanel playerPanel = new JPanel(new GridLayout(1, numberOfPlayers + 1));
        scoreSelectors = new JComboBox[categories.length][numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            JPanel playerColumn = new JPanel(new GridLayout(categories.length + 1, 1));
                playerColumn.add(new JLabel(player[i].getName()));
            for (int j = 0; j < categories.length; j++) {
                scoreSelectors[j][i] = new JComboBox<>(dropDownOptions(categories[j]));
                playerColumn.add(scoreSelectors[j][i]);
            }
            playerPanel.add(playerColumn);
        }

        // Button zum nächsten Spieler
        nextPlayerButton = new JButton("Nächster Spieler");
        nextPlayerButton.addActionListener(this);

        // Layout
        mainPanel.add(categoriesPanel, BorderLayout.WEST);
        mainPanel.add(playerPanel, BorderLayout.CENTER);
        mainPanel.add(nextPlayerButton, BorderLayout.SOUTH);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null); // Zentriere das Fenster
        setVisible(true);
    }

    private Integer[] dropDownOptions(String categorie){
      ArrayList<Integer> options = new ArrayList<>();
      options.add(0);
      
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
            currentPlayerIndex++;
            JOptionPane.showMessageDialog(this, "Spieler " + (currentPlayerIndex % numberOfPlayers + 1) + " ist jetzt dran.");
        }
    }

    public static void main(String[] args) {
        int numberOfPlayers = 4;
        //new kniffel_ui(numberOfPlayers);
    }
}
