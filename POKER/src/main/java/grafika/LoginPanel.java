package grafika;
import sql.LoginSQL;
import sql.RegisterSQL;
import sql.DeleteSQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class LoginPanel extends JPanel implements ActionListener, MouseListener {
    public final JButton loginButton;
    public final JButton registerButton;
    public final JButton deleteButton;
    public final JButton exitButton;
    public final JButton acceptLoginButton;
    private final JButton acceptRegisterButton;
    private final JButton acceptDeleteButton;
    public final JTextField login1;
    public final JTextField haslo1;
    private final JTextField login2;
    private final JTextField haslo2;
    private final JTextField login3;
    private final JTextField haslo3;
    public final JLabel backLabel;
    ImageIcon backArrow;
    Image BG, napis;
    public int STATE = 0;
    private final int SCREEN_WIDTH;

    public LoginPanel(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.SCREEN_WIDTH=SCREEN_WIDTH;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setPreferredSize(new Dimension(SCREEN_WIDTH/2,SCREEN_HEIGHT/2));
        this.setBackground(Color.BLACK);
        this.setFocusable(false);

        BG = new ImageIcon("images/BG.jpg").getImage();
        BG = BG.getScaledInstance(SCREEN_WIDTH/2,SCREEN_HEIGHT/2,Image.SCALE_DEFAULT);

        backArrow = new ImageIcon("images/arrowLeft.png");
        Image image = backArrow.getImage();
        Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        backArrow = new ImageIcon(scaledImage);
        backLabel = new JLabel(backArrow);
        backLabel.addMouseListener(this);

        napis = new ImageIcon("images/title.png").getImage();
        napis = napis.getScaledInstance(800,550,Image.SCALE_DEFAULT);

        loginButton = new JButton("Logowanie");
        loginButton.addActionListener(this);

        registerButton = new JButton("Rejestracja");
        registerButton.addActionListener(this);

        deleteButton = new JButton("Usuwanie konta");
        deleteButton.addActionListener(this);

        exitButton = new JButton("Wyjście");
        exitButton.addActionListener(this);

        login1 = new JTextField(30);

        haslo1 = new JPasswordField(30);

        login2 = new JTextField(30);

        haslo2 = new JPasswordField(30);

        login3 = new JTextField(30);

        haslo3 = new JPasswordField(30);

        acceptLoginButton = new JButton("Zaloguj się");
        acceptLoginButton.addActionListener(this);

        acceptRegisterButton = new JButton("Zarejestruj się");
        acceptRegisterButton.addActionListener(this);

        acceptDeleteButton = new JButton("Usuń konto");
        acceptDeleteButton.addActionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(STATE==0) {
            repaint();
            menu(g);

        }
        else if(STATE==1) {
            logowanie(g);
        }
        else if(STATE==2) {
            rejestrowanie(g);
        }
        else if(STATE==3) {
            usuwanie(g);
        }
    }

    public void menu(Graphics g) {
        this.removeAll();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(BG,0,0,null);
        g2.drawImage(napis,SCREEN_WIDTH/2-750, -150, null);
        loginButton.setBounds(SCREEN_WIDTH/4-deleteButton.getText().length()*registerButton.getFont().getSize()/2, 115, deleteButton.getText().length()*deleteButton.getFont().getSize(), 30);
        this.add(loginButton);
        registerButton.setBounds(SCREEN_WIDTH/4-deleteButton.getText().length()*registerButton.getFont().getSize()/2, 165, deleteButton.getText().length()*deleteButton.getFont().getSize(), 30);
        this.add(registerButton);
        deleteButton.setBounds(SCREEN_WIDTH/4-deleteButton.getText().length()*registerButton.getFont().getSize()/2, 215, deleteButton.getText().length()*deleteButton.getFont().getSize(), 30);
        this.add(deleteButton);
        exitButton.setBounds(SCREEN_WIDTH/4-deleteButton.getText().length()*registerButton.getFont().getSize()/2, 265, deleteButton.getText().length()*deleteButton.getFont().getSize(), 30);
        this.add(exitButton);
    }
    public void logowanie(Graphics g) {
        this.removeAll();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(BG,0,0,null);

        g2.setFont(new Font("Algerian",Font.PLAIN,30));
        g2.setColor(Color.ORANGE);
        String s1 = "Podaj login:";
        int r1 = g2.getFontMetrics().stringWidth(s1);
        g2.drawString(s1,(getWidth() - r1)/2,115);

        login1.setBounds(SCREEN_WIDTH/4-30*login1.getFont().getSize()/2, 135, 30*login1.getFont().getSize(), 30);
        this.add(login1);

        g2.setFont(new Font("Algerian",Font.PLAIN,30));
        g2.setColor(Color.ORANGE);
        String s2 = "Podaj hasło:";
        int r2 = g2.getFontMetrics().stringWidth(s2);
        g2.drawString(s2,(getWidth() - r2)/2,215);

        haslo1.setBounds(SCREEN_WIDTH/4-30*haslo1.getFont().getSize()/2, 235, 30*haslo1.getFont().getSize(), 30);
        this.add(haslo1);

        acceptLoginButton.setBounds(SCREEN_WIDTH/4-acceptLoginButton.getText().length()*acceptLoginButton.getFont().getSize()/2, 285, acceptLoginButton.getText().length()*acceptLoginButton.getFont().getSize(), 30);
        this.add(acceptLoginButton);

        backLabel.setBounds(0,0, 50,50);
        this.add(backLabel);
    }
    public void rejestrowanie(Graphics g) {
        this.removeAll();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(BG,0,0,null);

        g2.setFont(new Font("Algerian",Font.PLAIN,30));
        g2.setColor(Color.ORANGE);
        String s1 = "Podaj login:";
        int r1 = g2.getFontMetrics().stringWidth(s1);
        g2.drawString(s1,(getWidth() - r1)/2,115);

        login2.setBounds(SCREEN_WIDTH/4-30*login2.getFont().getSize()/2, 135, 30*login2.getFont().getSize(), 30);
        this.add(login2);

        g2.setFont(new Font("Algerian",Font.PLAIN,30));
        g2.setColor(Color.ORANGE);
        String s2 = "Podaj hasło:";
        int r2 = g2.getFontMetrics().stringWidth(s2);
        g2.drawString(s2,(getWidth() - r2)/2,215);

        haslo2.setBounds(SCREEN_WIDTH/4-30*haslo2.getFont().getSize()/2, 235, 30*haslo2.getFont().getSize(), 30);
        this.add(haslo2);

        acceptRegisterButton.setBounds(SCREEN_WIDTH/4-acceptRegisterButton.getText().length()*acceptRegisterButton.getFont().getSize()/2, 285, acceptRegisterButton.getText().length()*acceptRegisterButton.getFont().getSize(), 30);
        this.add(acceptRegisterButton);

        backLabel.setBounds(0,0, 50,50);
        this.add(backLabel);
    }
    public void usuwanie(Graphics g) {
        this.removeAll();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(BG,0,0,null);

        g2.setFont(new Font("Algerian",Font.PLAIN,30));
        g2.setColor(Color.ORANGE);
        String s1 = "Podaj login:";
        int r1 = g2.getFontMetrics().stringWidth(s1);
        g2.drawString(s1,(getWidth() - r1)/2,115);

        login3.setBounds(SCREEN_WIDTH/4-30*login3.getFont().getSize()/2, 135, 30*login3.getFont().getSize(), 30);
        this.add(login3);

        g2.setFont(new Font("Algerian",Font.PLAIN,30));
        g2.setColor(Color.ORANGE);
        String s2 = "Podaj hasło:";
        int r2 = g2.getFontMetrics().stringWidth(s2);
        g2.drawString(s2,(getWidth() - r2)/2,215);

        haslo3.setBounds(SCREEN_WIDTH/4-30*haslo3.getFont().getSize()/2, 235, 30*haslo3.getFont().getSize(), 30);
        this.add(haslo3);

        acceptDeleteButton.setBounds(SCREEN_WIDTH/4-acceptDeleteButton.getText().length()*acceptDeleteButton.getFont().getSize()/2, 285, acceptDeleteButton.getText().length()*acceptDeleteButton.getFont().getSize(), 30);
        this.add(acceptDeleteButton);

        backLabel.setBounds(0,0, 50,50);
        this.add(backLabel);
    }
    public void wyjscie() {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == loginButton) {
                STATE = 1;
            }

            if (e.getSource() == registerButton) {
                STATE = 2;
            }

            if (e.getSource() == deleteButton) {
                STATE = 3;
            }

            if (e.getSource() == exitButton) {
                wyjscie();
            }

            if (e.getSource() == acceptLoginButton) {
                String login = login1.getText();
                String haslo = haslo1.getText();
                LoginSQL LoginSQL = new LoginSQL(login, haslo);
                if (LoginSQL.id != -1) {
                    JComponent comp = (JComponent)e.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                    new PokerFrame(LoginSQL.id);
                }
            }

            if (e.getSource() == acceptRegisterButton) {
                String login = login2.getText();
                String haslo = haslo2.getText();
                new RegisterSQL(login, haslo);
            }

            if (e.getSource() == acceptDeleteButton) {
                String login = login3.getText();
                String haslo = haslo3.getText();
                new DeleteSQL(login, haslo);
            }
            repaint();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex){
            wyjscie();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==backLabel) {
                STATE=0;
                repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
