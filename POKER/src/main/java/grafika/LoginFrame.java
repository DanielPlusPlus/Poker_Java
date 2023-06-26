package grafika;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    static Dimension dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    public static final int SCREEN_WIDTH = (int) dim.getWidth();
    public static final int SCREEN_HEIGHT = (int) dim.getHeight();

    public LoginFrame() {
        this.setLocation(SCREEN_WIDTH/4, SCREEN_HEIGHT/4);
        this.add(new LoginPanel(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("ZALOGUJ SIĘ...");
        this.setResizable(false);
        this.setVisible(true);
    }
}
