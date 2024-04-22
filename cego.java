class cego{

    //TODO private
    private cego_player[] player;
    private float checkpot;
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
        ui.changeCheckpot();
        for(int i = 0; i < player.length; i++){
            player[i].setPoints(-bet);
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
    public void bonusRound(cego_player[] player){
        for(int i = 0; i < player.length; i++){
            player[i].setPoints(-checkpot);
        }

        checkpot = player.length * checkpot;

        ui.changeCheckpot();
        ui.changePoints();
    }

}