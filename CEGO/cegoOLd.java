package CEGO;
class cegoOLd{

    static String[] player;
    static float[] money;
    static float bet;
    static float checkpot = 0;
    static int roundNr = 0;
    //TODO prüfen ob Anzahl Stiche richtig und ob alle aussetzten durften

    public static void main(String[] args) {
        System.out.println("Bitte die Anzahl der Spieler eingeben.");
        int playerCount = In.readInt();
        player = new String[playerCount];
        money = new float[playerCount];
        System.out.println();

        for(int i = 0; i < playerCount; i++){
            System.out.println("Wie heißt Spieler Nummer " + (i+1) + "?");
            player[i] = In.readLine();
        }
        System.out.println();

        System.out.println("Mit was für einem Einsatz soll gespielt werden?");
        bet = In.readFloat();
        System.out.println();

        String start;
        do{
            System.out.println("Nächste Runde starten? (ja/nein)");
            start = In.readLine();
            if(start.equals("ja")){
                startRound();
            }
        }while(start.equals("ja"));

        getMoney();
    }


    public static void startRound(){
        //Runde startet
        //zieht jedem Spieler den Einsatz ab und setzt den Checkpot
        System.out.println();
        checkpot = 0;
        System.out.println("Starte Runde " + ++roundNr);
        System.out.println("Jeder zahlt " + bet + " in den Checkpot.");

        checkpot += player.length * bet;
        for(int i = 0; i < player.length; i++){
            money[i] -= bet;
        }

        getMoney();

        //frägt ab wie viele Stiche ein Spieler gemacht hat
        int[] stitches = new int[player.length];
        for(int i = 0; i < player.length; i++){
            System.out.println("Wie viele Stiche hat " + player[i] + " gemacht? (-1 für aussetzen)");
            stitches[i] = In.readInt();
        }

        //ruft bonusRound auf falls jemand keinen Stich gemacht hat
        if(splitPot(stitches) != 0){
            bonusRound(stitches);
        }
    }

    /**
     * Verteilt den Pot auf jeden Stich und setzt ihn danach auf 0.
     * @param stitches die Anzahl der Stiche, die pro Spieler gemacht wurden
     * @return die Anzahl der Spieler, welche in den Pot einzahlen müssen
     */
    public static int splitPot(int[] stitches){
        //gibt jedem seinen Anteil und speichert ob jemand nicht gewonnen hat
        boolean[] didWin = new boolean[player.length];
        for(int i = 0; i < player.length; i++){
            switch (stitches[i]) {
                case -1:
                    didWin[i] = true;
                    break;
                case 0:
                    didWin[i] = false;
                    break;
                default:
                    didWin[i] = true;
                    money[i] += (checkpot/4) * stitches[i];
            }
        }

        //zählt wie viele Spieler keinen Stich haben
        int nrOfNoStitch = 0;
        for(int i = 0; i < player.length; i++){
            if(!didWin[i]){
                nrOfNoStitch++;
            }
        }

        return nrOfNoStitch;
    }

    /**
     * startet eine Runde, in der nicht jeder Einzahlt. Merkt sich dazu wie viel im Pot war.
     * @param stitches gibt mit wer keinen Stich gemacht hat. Diese Person(en) zahlen alleine in den Pot
     */
    public static void bonusRound(int[] stitches){
        int counter = 0;
        float tempCheckpot = checkpot;
        checkpot = 0;
        
        //alle die keinen Stich gemacht haben, zahlen ein
        for(int i = 0; i < player.length; i++){
            if(stitches[i] == 0){
                System.out.println(player[i] + " zahlt " + tempCheckpot + " Punkte in den Pot.");
                money[i] -= tempCheckpot;
                counter ++;
            }
        }

        getMoney();

        checkpot += counter * tempCheckpot;
        System.out.println("Es befinden sich nun " + checkpot + " Punkte im Checkpot.");

        //frägt ab wie viele Stiche ein Spieler gemacht hat
        stitches = new int[player.length];
        for(int i = 0; i < player.length; i++){
            System.out.println("Wie viele Stiche hat " + player[i] + " gemacht? (-1 für aussetzen)");
            stitches[i] = In.readInt();
        }
        
        //ruft bonusRound auf falls jemand keinen Stich gemacht hat
        if(splitPot(stitches) != 0){
            bonusRound(stitches);
        }
    }

    public static void getMoney(){
        System.out.println();
        for(int i = 0; i < player.length; i++){
            System.out.println(player[i] + " hat " + money[i] + " Punkte.");
        }
        System.out.println();
    }
}