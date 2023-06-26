package grafikatest;
import javax.swing.ImageIcon;

import grafika.PokerCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class PokerCardTest {

    @Test
    public void testPokerCardCreation() {
        String imagePath = "images/4c";
        int x = 10;
        int y = 20;

        PokerCard pokerCard = new PokerCard(imagePath, x, y);

        assertNotNull(pokerCard.getIcon());
        assertEquals(imagePath, ((ImageIcon) pokerCard.getIcon()).getDescription());
        assertFalse(pokerCard.isFocusable());
        assertNull(pokerCard.getBorder());
        assertEquals(x, pokerCard.getX());
        assertEquals(y, pokerCard.getY());
        assertEquals(150, pokerCard.getWidth());
        assertEquals(208, pokerCard.getHeight());
    }
}
