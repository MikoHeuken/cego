package KNIFFEL;

public class kniffel_player {
  //TODO variable ob Bonus erreicht wurde
  private String name;
  private int points;
  private int bonus = 35;

  public kniffel_player(String name){
    this.name = name;
    points = 0;
  }

  public String getName(){
    return name;
  }

  public void changePoints(int value){
    points += value;
  }

  public int getPoints(){
    return points;
  }
}
