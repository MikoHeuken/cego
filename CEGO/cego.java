package CEGO;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

class cego{

    private cego_player[] player;
    private float checkpot = 0;
    private cego_ui ui;
    private int bet;

    public cego(cego_player[] player, int bet, cego_ui ui) {
        this.player = player;
        this.ui = ui;
        this.bet = bet;
    }

    public int getBet() {
      return bet;
    }

    public void setBet(int bet) {
      this.bet = bet;
    }

    public float getCheckpot() {
      return checkpot;
    }

    public void startRound(){
        //jedem Spieler wird der Punkte-Einsatz abgezogen und der Checkpot wird gesetzt
        checkpot = player.length * bet;
        ui.setCache(ui.getCacheLength() - 1, checkpot);
        ui.changeCheckpot();
        for(int i = 0; i < player.length; i++){
            player[i].setPoints(-bet);
            ui.setCache(i, player[i].getPoints());
        }

        //aktualisiert die Punktekonten
        ui.changePoints();
    }


    /**
     * Verteilt den Pot
     * @param stitchesMade die Anzahl der Stiche, die pro Spieler gemacht wurden
     * @param player der Spieler, der einen Teil des Pots bekommt
     */
    public void splitPot(int stitchesMade, cego_player player){
        player.setPoints(checkpot/4 * stitchesMade);
    }

    /**
     * startet eine runde, in der nur die Spieler Punkte einzahlen, welche in der vorherigen keine Stiche gemacht haben
     * @param player die Spieler, die letzte Runde keinen Stich gemacht haben
     */
    public void bonusRound(cego_player[] playerWithNoStitch){
        for(int i = 0; i < playerWithNoStitch.length; i++){
            playerWithNoStitch[i].setPoints(-checkpot);
        }

        for(int i = 0; i < player.length; i++){
            ui.setCache(i, player[i].getPoints());
        }

        checkpot = playerWithNoStitch.length * checkpot;
        ui.setCache(ui.getCacheLength() - 1, checkpot);

        ui.changeCheckpot();
        ui.changePoints();
    }

    /**
     * speichert eine Runde in einer neuen Textdatei
     * @param playedRounds die Anzahl der gespielten Runden im Spiel
     */
    public void safeRound(int playedRounds, ArrayList<Float>[] cache){
        LocalDateTime now = LocalDateTime.now();
        String location = "./CEGO/savedRounds/" + now.toString().replace(":", ".") + ".txt";

        try(PrintWriter pWriter = new PrintWriter(new FileWriter(location, false));){
            pWriter.println(playedRounds);
            for(int i = 0; i < player.length; i++){
                pWriter.print(player[i].getName() + ",");
                for(int j = 0; j < cache[i].size(); j++){
                    pWriter.print(cache[i].get(j) + ",");
                }
                pWriter.println(player[i].getPoints());
            }
            pWriter.print("Checkpot");
            for(int i = 0; i < cache[ui.getCacheLength() - 1].size(); i++){
                pWriter.print("," + cache[ui.getCacheLength() - 1].get(i));
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}