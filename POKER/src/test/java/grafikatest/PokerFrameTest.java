package grafikatest;
import javax.swing.*;

import grafika.PokerFrame;
import org.junit.Test;
import sql.CreateSQL;
import sql.DropSQL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PokerFrameTest {

    @Test
    public void testPokerFrameCreation() {
        try {
            new CreateSQL();
            PokerFrame pokerFrame = new PokerFrame(1);
            assertEquals("POKER", pokerFrame.getTitle());
            assertFalse(pokerFrame.isResizable());
            assertTrue(pokerFrame.isVisible());
            assertEquals(JFrame.EXIT_ON_CLOSE, pokerFrame.getDefaultCloseOperation());
            new DropSQL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
