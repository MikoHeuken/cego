package KNIFFEL;

class test{

public static void main(String[] args) {
    kniffel_player miko = new kniffel_player("Miko");
    kniffel_player hugo = new kniffel_player("Hugo");
    kniffel_player louis = new kniffel_player("Louis");
    kniffel_player marcel = new kniffel_player("Marcel");
    kniffel_player[] player = {miko, hugo, louis, marcel};
    new kniffel_ui(player);
}
}
