package KNIFFEL;

class test{

public static void main(String[] args) {
    kniffel_player miko = new kniffel_player("Miko");
    kniffel_player yannick = new kniffel_player("Yannick");
    kniffel_player louis = new kniffel_player("Louis");
    kniffel_player marcel = new kniffel_player("Marcel");
    kniffel_player[] player = {miko, yannick, louis, marcel};
    new kniffel_ui(player);
}
}
