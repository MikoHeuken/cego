import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class cego_player {
    
    private String name;
    private float points;
    //int roundsPlayed;

    public cego_player(String name, float points){
        this.name = name;
        this.points = points;
    }

    public String getName(){
        return name;
    }

    public float getPoints(){
        return points;
    }

    public void setPoints(float valueChange){
        points += valueChange;
    }

    public static void safePlayer(cego_player[] cegoPlayer){
        String location = "./stuff/cego/text.txt";
        try (PrintWriter pWriter = new PrintWriter(new FileWriter(location, true));){
            //pWriter.println("Hallo Welt!");
            for(int i = 0; i < cegoPlayer.length; i++){
                pWriter.print("\n" + cegoPlayer[i].name + "," + cegoPlayer[i].points);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static cego_player[] getSavedPlayers(){
        String fileName = "./text.txt";
        cego_player[] returnPlayer = null;
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Path.of(fileName));                             //liest alle Zeilen ein
        } catch (IOException e) {
            e.printStackTrace();
        }

        returnPlayer = new cego_player[lines.size()];                                  //erstellt ein Array mit der Zeilengröße
        for(int i = 0; i < lines.size(); i++){
            String[] temp = lines.get(i).split(",");                             //jeder Wert pro Zeile wird einzeln gespecihert
            returnPlayer[i] = new cego_player(temp[0], Float.parseFloat(temp[1]));     //ein neuer Spieler wird erstellt
        }

        return returnPlayer;
    }

}
