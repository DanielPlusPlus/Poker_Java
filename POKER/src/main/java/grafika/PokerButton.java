package grafika;
import javax.swing.*;
import java.awt.*;

public class PokerButton extends JButton {
    public PokerButton(String str, Color c) {
        super(str);
        this.setFocusable(false);
        this.setBorder(null);
        this.setBorderPainted(false);
        this.setFont(new Font("Algerian", Font.ITALIC,90));
        this.setForeground(c);
        this.setVerticalTextPosition(JButton.CENTER);
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setContentAreaFilled(false);

    }
}
