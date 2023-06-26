package grafika;
import javax.swing.*;

public class PokerCard extends JLabel{
    public PokerCard(String str, int x, int y) {
        super(new ImageIcon(str));
        this.setFocusable(false);
        this.setBorder(null);
        this.setBounds(x,y,150,208);
    }
}
