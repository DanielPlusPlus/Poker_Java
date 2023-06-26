package grafikatest;
import javax.swing.*;

import grafika.LoginFrame;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginFrameTest {

    @Test
    public void testLoginFrameCreation() {
        LoginFrame loginFrame = new LoginFrame();

        assertEquals("ZALOGUJ SIĘ...", loginFrame.getTitle());
        assertFalse(loginFrame.isResizable());
        assertTrue(loginFrame.isVisible());
        assertEquals(WindowConstants.EXIT_ON_CLOSE, loginFrame.getDefaultCloseOperation());

        int expectedX = LoginFrame.SCREEN_WIDTH / 4;
        int expectedY = LoginFrame.SCREEN_HEIGHT / 4;
        assertEquals(expectedX, loginFrame.getX());
        assertEquals(expectedY, loginFrame.getY());
    }
}
