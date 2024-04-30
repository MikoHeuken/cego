import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class gameChooserUI extends JFrame implements ActionListener{
  
  JPanel mainPanel;
  JButton kniffelButton;
  JButton cegoButton;

  public gameChooserUI(){
    //Titel, Größe, Schließoptionen und Layout werden gesetzt
    setTitle("Spielauswahl");
    setSize(800, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    mainPanel = new JPanel(new GridLayout(2,1));
    
    kniffelButton = new JButton("Kniffel");
    kniffelButton.addActionListener(this);
    Font font = kniffelButton.getFont();
    kniffelButton.setFont(new Font(font.getName(), Font.PLAIN, 18));

    cegoButton = new JButton("Cego");
    cegoButton.addActionListener(this);
    font = cegoButton.getFont();
    cegoButton.setFont(new Font(font.getName(), Font.PLAIN, 18));

    mainPanel.add(kniffelButton);
    mainPanel.add(cegoButton);

    add(mainPanel);
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
      if(e.getSource() == cegoButton){
        dispose();
        new addCegoPlayerUI();
      }

      if(e.getSource() == kniffelButton){
        dispose();
        new addKniffelPlayerUI();
      }  
  }

  public static void main(String[] args){
    new gameChooserUI();
  }
}
