class test{

    public static void main(String[] args) {
        cego_player[] player = cego_player.getSavedPlayers();
        for(int i = 0; i < player.length; i++){
            System.out.println(player[i].name + ": " + player[i].money);
        }
    }

}