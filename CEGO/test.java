package CEGO;
class test{
    public static void main(String[] args) {
        cego_player[] player = cego_player.getSavedPlayers();
        new cego_ui(player, 10);
    }

}