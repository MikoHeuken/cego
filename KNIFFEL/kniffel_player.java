package KNIFFEL;

public class kniffel_player {
  private String name;
  private int points;
  private int bonus = 0;
  private int sumUpperHalf = 0;
  private int totalSumUpperHalf = 0;
  private int sumLowerHalf = 0;
  private int[] pointsInCategories;

  public kniffel_player(String name){
    this.name = name;
    points = 0;
    pointsInCategories = new int[13];
  }

  /**
   * ändert die Punkte eines Spielers in der oberen Hälfte
   * @param value der Wert um den die Punkte erhöht werden
   * @param playerNr die Spieler Nummer, wichtig damit das richtige Label geändert werden kann
   * @param ui die ui-Klasse, welche die Methode aufruft
   */
  public void changePointsUpperHalf(int categoryIndex, int value, int playerNr, kniffel_ui ui){
    pointsInCategories[categoryIndex] += value;
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
  public void changePointsLowerHalf(int categoryIndex, int value, int playerNr, kniffel_ui ui){
    pointsInCategories[categoryIndex] += value;
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

  /**
   * ändert einen Wert im pointsInCategories Array; wird verwendet um von kniffel_ui gestrichene Werte zu setzen
   * @param index die betroffene Kategorie
   * @param value der Wert, welcher an der Stelle eingesetzt wird
   */
  public void setPointsInCategory(int index, int value){
    pointsInCategories[index] = value;
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

  public int[] getPointsInCategories(){
    return pointsInCategories;
  }
}
