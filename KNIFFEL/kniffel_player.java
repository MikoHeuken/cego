package KNIFFEL;

public class kniffel_player {
  private String name;
  private int points;
  private int bonus = 0;
  private int sumUpperHalf = 0;
  private int totalSumUpperHalf = 0;
  private int sumLowerHalf = 0;

  public kniffel_player(String name){
    this.name = name;
    points = 0;
  }

  /**
   * ändert die Punkte eines Spielers in der oberen Hälfte
   * @param value der Wert um den die Punkte erhöht werden
   * @param playerNr die Spieler Nummer, wichtig damit das richtige Label geändert werden kann
   * @param ui die ui-Klasse, welche die Methode aufruft
   */
  public void changePointsUpperHalf(int value, int playerNr, kniffel_ui ui){
    sumUpperHalf += value;
    totalSumUpperHalf += value;

    ui.setUpperHalfLabel(playerNr, String.valueOf(sumUpperHalf));

    //wenn noch kein Bonus gesetzt ist, wird dieser gesetzt und zu totalSumUpperHalf addiert
    if(bonus != 35 && sumUpperHalf >= 63){
      bonus = 35;
      totalSumUpperHalf += 35;

      ui.setBonusLabel(playerNr, "35");
      changePoints(bonus, playerNr, ui);
    }

    //wenn ein Wert abgezogen wird und der Bonus damit nichtmehr gegben ist, entferne diesen
    if(value < 0 && bonus == 35 && sumUpperHalf < 63){
      bonus = 0;
      totalSumUpperHalf -= 35;

      ui.setBonusLabel(playerNr, "0");
      changePoints(-35, playerNr, ui);
    }

    ui.setTotalUpperHalfUpLabel(playerNr, String.valueOf(totalSumUpperHalf));
    ui.setTotalUpperHalfDownLabel(playerNr, String.valueOf(totalSumUpperHalf));

    changePoints(value, playerNr, ui);
  }

  /**
   * ändert die Punkte eines Spielers in der unteren Hälfte
   * @param value der Wert um den die Punkte erhöht werden
   * @param playerNr die Spielernummer, wichtig damit das richtige Label geändert werden kann
   * @param ui die UI-Klasse, welche die Methode aufruft
   */
  public void changePointsLowerHalf(int value, int playerNr, kniffel_ui ui){
    sumLowerHalf += value;
    ui.setLowerHalfLabel(playerNr, String.valueOf(sumLowerHalf));
    changePoints(value, playerNr, ui);
  }

  /**
   * ändert die gesamten Punkte eines Spielers
   * @param value der Wert um den die Punkte erhöht werden
   * @param playernr die Spielernummer, wichtig damit das richtige Label geändert werden kann
   * @param ui die UI-Klasse, welche die Methode aufruft
   */
  private void changePoints(int value, int playernr, kniffel_ui ui){
    points += value;
    ui.setTotalPointsLabel(playernr, String.valueOf(points));
  }

  public int getPoints(){
    return points;
  }

  public String getName(){
    return name;
  }

  public int getUpperHalf(){
    return sumUpperHalf;
  }

  public int getLowerHalf(){
    return sumLowerHalf;
  }

  public int getBonus(){
    return bonus;
  }
}
