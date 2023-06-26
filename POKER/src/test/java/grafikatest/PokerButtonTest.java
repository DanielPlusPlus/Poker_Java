package grafikatest;
import grafika.PokerButton;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PokerButtonTest {

    @Test
    public void testPokerButton() {
        String str = "MENU";
        assertEquals("MENU",str);
        Color c = new Color(10,10,10);
        assertEquals(new Color(10,10,10),c);
        PokerButton pokerButton = new PokerButton(str,c);

        assertNull(pokerButton.getBorder());
        assertFalse(pokerButton.isBorderPainted());
        assertFalse(pokerButton.isFocusable());
        assertEquals(new Font("Algerian", Font.ITALIC,90),pokerButton.getFont());
        assertEquals(c,pokerButton.getForeground());
        assertFalse(pokerButton.isContentAreaFilled());

    }
}