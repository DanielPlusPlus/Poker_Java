package grafika;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class PokerFrame extends JFrame {
    public PokerFrame(int id) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        this.add(new PokerPanel(id));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("POKER");
        this.setResizable(false);
        this.setVisible(true);

    }


}

