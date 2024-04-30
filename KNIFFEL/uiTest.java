package KNIFFEL;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class uiTest {

  private kniffel_player[] player = {new kniffel_player("miko"), new kniffel_player("marcel")};
  private kniffel_ui testUi = new kniffel_ui(player);
 
  @Test 
  public void dropDownTest(){
    String[] temp = testUi.dropDownOptions("5er");
    for(int i = 1; i < temp.length - 1; i++){
      assertEquals(String.valueOf((i * 5)), temp[i]);
    }
    assertEquals(null, temp[0]);
    assertEquals("Streichen", temp[temp.length - 1]);
  }

  @Test
  public void orderPlayerTest(){
    player[0].setPoints(20);
    player[1].setPoints(30);

    kniffel_ui ui = new kniffel_ui(player);

    ui.orderPlayer();
    assertEquals(ui.getPlayer()[1].getPoints(), 20);
  }

}
