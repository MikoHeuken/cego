package KNIFFEL;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class playerTest {

  private kniffel_player player = new kniffel_player("Miko");
  kniffel_player[] playerArray = {player};
  kniffel_ui ui = new kniffel_ui(playerArray);

  @Test
  public void testConstructorName(){
    assertEquals("Miko", player.getName());
  }

  @Test
  public void testConstructorPoints(){
    assertEquals(0, player.getPoints());
  }

  @Test
  public void testChangePointsUpperHalf(){
    player.changePointsUpperHalf(0, 6, 0, ui);
    assertEquals(6, player.getUpperHalf());
  }

  @Test
  public void testChangePointsLowerHalf(){
    player.changePointsLowerHalf(7, 5, 0, ui);
    assertEquals(5, player.getLowerHalf());
  }

  @Test 
  public void testBonus(){
    assertEquals(0, player.getBonus());
    player.changePointsUpperHalf(0, 63, 0, ui);
    assertEquals(35, player.getBonus());
  }

}
