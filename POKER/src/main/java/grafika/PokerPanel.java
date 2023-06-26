package grafika;
import konsola.Gracz;
import konsola.Rozdajacy;
import serwer_klient.SocketHP;
import sql.UpdateZetonySQL;
import sql.GetInfoSQL;
import sql.GetNickSQL;
import sql.GetZetonySQL;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Random;

public class PokerPanel extends JPanel implements ActionListener, MouseListener {
    static Dimension dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    static final int SCREEN_WIDTH = (int) dim.getWidth();
    static final int SCREEN_HEIGHT = (int) dim.getHeight();
    int id;
    JTextArea rankingArea;
    Image BG;
    Image napis;
    Image table;
    AudioInputStream audioStream;
    File file;
    Clip clip;

    ImageIcon musicOn, musicOff, backArrow;
    JLabel nameLabel;
    JLabel musicLabel, backLabel;
    PokerButton menuB1, menuB2, menuB3, menuB4;
    static int STATE = 0;
    Gracz gracz1, gracz2;
    PokerCard c1, c2, c3, c4, c5;
    Rozdajacy dealer;
    int pom, pom2, hv;
    int ktoZaczyna;
    Random random;
    public long stawka;
    String[] opcjeLicytacji, opcjeTakNie;
    int siChoice;
    int odrzucone;
    boolean newGame;
    boolean enabled, enabled2;
    boolean running, isRunning, refreashed, changeableWhite, changeableGreen, changeableRed, wygraned, next;
    String ktoryJestem;
    private final serwer_klient.SocketHP SocketHP = new SocketHP();
    Image wygranaGif;
    Image wygranaNapis;
    File file2;
    Clip clip2;
    AudioInputStream audioStream2;

    public PokerPanel(int id) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        this.id = id;
        random = new Random();
        hv = 0;
        odrzucone = 0;
        opcjeLicytacji = new String[]{"fold", "raise", "call"};
        opcjeTakNie = new String[]{"Tak","Nie"};
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(false);
        new GetNickSQL(id);
        new GetZetonySQL(id);
        gracz1 = new Gracz(GetNickSQL.name, GetZetonySQL.zetony);
        gracz2 = new Gracz("SI", gracz1.zetony);
        dealer = new Rozdajacy();
        rankingArea = new JTextArea();
        musicLabel = new JLabel();
        backArrow = new ImageIcon("images/arrowLeft.png");
        backLabel = new JLabel(backArrow);
        file = new File("music/BGMusic.wav");
        file2 = new File("music/fireworks.wav");
        audioStream2 = AudioSystem.getAudioInputStream(file2);
        audioStream = AudioSystem.getAudioInputStream(file);
        clip2 = AudioSystem.getClip();
        clip2.open(audioStream2);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        wygranaNapis = new ImageIcon("images/winText.png").getImage();
        wygranaGif = Toolkit.getDefaultToolkit().getImage("images/wildFireworks.gif");
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(wygranaGif,0);
        mt.waitForAll();
        BG = new ImageIcon("images/BG.jpg").getImage();
        BG = BG.getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_DEFAULT);
        table = new ImageIcon("images/Table.jpg").getImage();
        table = table.getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_DEFAULT);
        napis = new ImageIcon("images/title.png").getImage();
        musicOn = new ImageIcon("images/musicOn.png");
        musicOff = new ImageIcon("images/musicOff.png");
        napis = napis.getScaledInstance(1600, 1100, Image.SCALE_DEFAULT);
        nameLabel = new JLabel(GetNickSQL.name);
        menuB1 = new PokerButton("SINGLEPLAYER", Color.WHITE);
        this.setVisible(true);
        menuB1.addActionListener(this);
        menuB2 = new PokerButton("RANKING", Color.WHITE);
        menuB4 = new PokerButton("WYJSCIE", Color.WHITE);
        menuB3 = new PokerButton("MULTIPLAYER",Color.WHITE);
        menuB2.addActionListener(this);
        menuB3.addActionListener(this);
        musicLabel.setIcon(musicOn);
        musicLabel.addMouseListener(this);
        backLabel.addMouseListener(this);
        menuB4.addActionListener(this);
        pom = 1;
        pom2 = 1;
        stawka = 0;
        enabled = false;
        enabled2 = false;
        running = true;
        isRunning = true;
        refreashed = true;
        newGame = true;
        changeableWhite = true;
        changeableGreen = false;
        changeableRed = false;
        wygraned = false;
        next = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (STATE == 0) {
            menu(g);
        } else if (STATE == 1) {
            try {
                graj(g);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else if(STATE == 3){
            multiplayer(g);
        }else if (STATE == 2) {
            try {

                ranking(g);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        repaint();
    }

    public void refresh(Gracz g1, Gracz g2, Rozdajacy r) {
        g1.clearHand();
        g2.clearHand();
        stawka = 0;
        r.newRozdanie();
    }
    public void menu(Graphics g) {
        this.removeAll();
        Graphics2D g2 = (Graphics2D) g;
        repaint();
        g2.drawImage(BG, 0, 0, null);
        g2.drawImage(napis, SCREEN_WIDTH / 2 - 700, -300, null);
        menuB1.setBounds(SCREEN_WIDTH / 2 - menuB1.getText().length() * menuB1.getFont().getSize() / 2, SCREEN_HEIGHT / 4 + 70,
                menuB1.getText().length() * menuB1.getFont().getSize(), 100);
        this.add(menuB1);
        musicLabel.setBounds(0, 0, 50, 50);
        this.add(musicLabel);
        menuB2.setBounds(SCREEN_WIDTH / 2 - menuB2.getText().length() * menuB2.getFont().getSize() / 2, SCREEN_HEIGHT / 4 + 270,
                menuB2.getText().length() * menuB2.getFont().getSize(), 100);
        this.add(menuB2);
        menuB3.setBounds(SCREEN_WIDTH / 2 - menuB3.getText().length() * menuB3.getFont().getSize() / 2, SCREEN_HEIGHT / 4 + 170,
                menuB3.getText().length() * menuB3.getFont().getSize(), 100);
        this.add(menuB3);
        menuB4.setBounds(SCREEN_WIDTH / 2 - menuB4.getText().length() * menuB4.getFont().getSize() / 2, SCREEN_HEIGHT / 4 + 370,
                menuB4.getText().length() * menuB4.getFont().getSize(), 100);
        this.add(menuB4);

    }

    public void graj(Graphics g) throws InterruptedException {
        this.removeAll();
        long playerRaise;
        long sumaPrzyg = 1;
        clip.stop();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(table, 0, 0, null);
        g2.setColor(Color.red);
        g2.fillOval(SCREEN_WIDTH / 2 - 50, 50, 100, 100);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(SCREEN_WIDTH / 2 - 50, 50, 100, 100);
        g2.setFont(new Font("Algerian", Font.PLAIN, 25));
        g2.setColor(Color.white);
        g2.drawString("SI", SCREEN_WIDTH / 2 - 10, 110);

        g2.setFont(new Font("Algerian", Font.PLAIN, 40));
        g2.drawString("Pula: ", 10, 50);
        g2.drawString(Long.toString(stawka), 150, 50);

        g2.setColor(Color.blue);
        g2.fillOval(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 150, 100, 100);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 150, 100, 100);
        g2.setFont(new Font("Algerian", Font.PLAIN, 25));
        g2.setColor(Color.white);
        g2.drawString(gracz1.nick, SCREEN_WIDTH / 2 - 20, SCREEN_HEIGHT - 90);
        g2.drawString(Long.toString(gracz1.zetony), SCREEN_WIDTH / 2 + 110, SCREEN_HEIGHT - 90);

        if(refreashed) {
            repaint();
            pom2 = 1;
            pom = 1;
            enabled = false;
            enabled2 = false;
            refreashed = false;
            running = true;
            isRunning = true;
            gracz2.zetony = gracz1.zetony;
            next = false;
        }

        if(wygraned) {
            wygranaGracza(g2);
        }

        if (pom2 == 1) {
            refresh(gracz1,gracz2,dealer);
            dealer.rozdaj(gracz1, gracz2);
            pom2 = 0;
        }
        if (enabled) {
            drawFlippedKarty(g2);
            drawKarty(gracz1);
        }
        if (enabled2) {
            repaint();
            drawKartyGracza(g2, gracz2);
            drawKartyGracza2(g2, gracz1);
            if (hv == 1) {
                clip2.start();
                wygranaGracza(g2);
            }
        }
        if (pom == 1) {
            pom = 0;
            repaint();
            ktoZaczyna = random.nextInt(3)+1;
            if (ktoZaczyna == 1) {
                try {
                    sumaPrzyg = drawPrzygotowanie();
                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(null,"Podales pusta kwote!");
                }
                if(sumaPrzyg<=0 || sumaPrzyg>gracz1.zetony) {
                    JOptionPane.showMessageDialog(null,"Podano zla kwote!\nKwota zostanie wylosowana automatycznie.");
                    sumaPrzyg = random.nextLong(gracz1.zetony);
                }
                stawka += sumaPrzyg;
                new UpdateZetonySQL(gracz1.nick, -stawka);
                gracz1.zetony -= stawka;
                JOptionPane.showMessageDialog(null, "Przeciwnik podwaja!! \nZaczynamy gre!");
                enabled = true;
                stawka *= 3;
                int playerOption = JOptionPane.showOptionDialog(null, "Zaczynamy LICYTACJE!!!\nWybierz co chcesz zrobić: ", "Licytacja",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcjeLicytacji, opcjeLicytacji[0]);

                switch (playerOption) {
                    case 0 -> {
                        JOptionPane.showMessageDialog(null, "FOLDUJESZ! \nPrzeciwnik wygrywa gre!");
                        isRunning = false;
                    }
                    case 1 -> {
                        JOptionPane.showMessageDialog(null, "RAISUJESZ!\n");
                        String x = JOptionPane.showInputDialog("Podaj stawke do podwyzszenia: ");
                        if(!x.isEmpty()){
                            playerRaise = Long.parseLong(x);
                        }
                        else {
                            playerRaise = random.nextLong(gracz1.zetony);
                        }
                        if(playerRaise<=0 || playerRaise>gracz1.zetony) {
                            JOptionPane.showMessageDialog(null,"Podane zla kwota! \n Kwota zostanie wybrana automatycznie");
                            playerRaise = random.nextLong(gracz1.zetony);
                            stawka += playerRaise + stawka;
                            gracz1.zetony -= playerRaise + stawka;
                            new UpdateZetonySQL(gracz1.nick, -playerRaise - stawka);
                        }
                        else {
                            stawka += playerRaise + stawka;
                            gracz1.zetony -= playerRaise + stawka;
                            new UpdateZetonySQL(gracz1.nick, -playerRaise - stawka);
                        }

                    }
                    case 2 -> {
                        JOptionPane.showMessageDialog(null, "CALLUJESZ!!");
                        stawka *= 2;
                        new UpdateZetonySQL(gracz1.nick, -stawka);
                        JOptionPane.showMessageDialog(null, "Podwyzszaszl!\n W puli jest: " + stawka);
                    }
                }
                if(isRunning) {
                    int opcjaOdrzucenia = JOptionPane.showOptionDialog(null,
                            "Czy chcesz odrzucic karty?\nMozesz odrzucic do 4 kart",
                            "Odrzucanie kart", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, opcjeTakNie, opcjeTakNie[0]);
                    if (opcjaOdrzucenia == 0) {
                        boolean[] tab = new boolean[5];
                        int liczbaWymian = 0;
                        JOptionPane.showMessageDialog(null, "Twoje karty po kolei: ");
                        for (int i = 0; i < 5; i++) {
                            int odp = JOptionPane.showOptionDialog(null, "Czy chcesz wymienic ta karte: " + gracz1.reka.get(i),
                                    "Wymiana", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, opcjeTakNie, opcjeTakNie[0]);
                            if (odp == 0) {
                                tab[i] = true;
                                liczbaWymian++;
                            }
                            if (liczbaWymian == 4) {
                                break;
                            }
                        }
                        gracz1.giveKarty(tab);
                        dealer.rozdajDodatkowe(gracz1);
                    }
                    JOptionPane.showMessageDialog(null, "Przeciwnik prowadzi licytacje!");
                    siChoice = random.nextInt(3) + 1;
                    switch (siChoice) {
                        case 1 -> {
                            enabled = false;
                            enabled2 = false;
                            clip2.start();
                            wygraned = true;
                            JOptionPane.showMessageDialog(null, "Przeciwnik FOLDUJE!\n Wygrywasz gre!");
                            wygraned = false;
                            clip2.stop();
                            gracz1.zetony += stawka;
                            new UpdateZetonySQL(gracz1.nick, stawka);
                            running = false;
                        }
                        case 2 -> {
                            JOptionPane.showMessageDialog(null, "Przeciwnik CALLUJE!");
                            stawka *= 2;
                        }
                        case 3 -> {
                            JOptionPane.showMessageDialog(null, "Przeciwnik RAISUJE!");
                            long raiseSi = random.nextLong(100);
                            stawka += raiseSi + stawka;
                            JOptionPane.showMessageDialog(null, "Pula wynosi: " + stawka);
                        }
                    }
                    if(running) {
                        removeDrawnKarty();
                        int wynik = dealer.ktoWygrywa(gracz1, gracz2);
                        if (wynik == 1) {
                            hv = 1;
                            enabled = false;
                            enabled2 = true;
                            if(clip2.isRunning() && wygraned) {
                                Thread.sleep(5000);
                            }
                            JOptionPane.showMessageDialog(null, "WYGRYWASZ!!!");
                            hv = 0;
                            clip2.stop();
                            gracz1.zetony += stawka;
                            new UpdateZetonySQL(gracz1.nick, stawka);

                        } else if (wynik == 2) {
                            enabled = false;
                            enabled2 = true;
                            JOptionPane.showMessageDialog(null, "WYGRYWA PRZECIWNIK!!!");
                        } else if (wynik == 0) {
                            enabled = false;
                            enabled2 = true;
                            JOptionPane.showMessageDialog(null, "REMIS!!!");
                            gracz1.zetony += stawka / 2;
                            new UpdateZetonySQL(gracz1.nick, stawka / 2);
                        }
                    }
                }
                int result2 = JOptionPane.showOptionDialog(null,"Czy chcesz zagrac ponownie?","Jeszcze raz?",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, opcjeTakNie, opcjeTakNie[0]);
                if(result2==0) {
                    refreashed = true;
                }
                else {
                    int result = JOptionPane.showConfirmDialog(null,"Powrot do menu","Koniec",
                            JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);
                    if(result == 0) {
                        STATE=0;
                        repaint();
                    }
                }
            } else {
                long suma = random.nextLong(gracz1.zetony / 6) + 1;
                stawka += suma;
                JOptionPane.showMessageDialog(null, "ZACZYNA PRZECIWNIK!!!\nW puli jest: " + stawka);
                int choice = JOptionPane.showOptionDialog(null, "Czy podwajasz stawke??", "Czy gramy?",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, opcjeTakNie, opcjeTakNie[0]);
                if (choice == 0) {
                    stawka += suma * 2;
                    enabled = true;
                    new UpdateZetonySQL(gracz1.nick, -suma * 2);
                    gracz1.zetony -= suma * 2;
                    JOptionPane.showMessageDialog(null, "Podwajasz stawke!!!\n Zaczynamy gre!");
                    JOptionPane.showMessageDialog(null, "Przeciwnik prowadzi LICYTACJE!!!");
                    siChoice = random.nextInt(3) + 1;
                    switch (siChoice) {
                        case 1 -> {
                            enabled = false;
                            enabled2 = false;
                            wygraned = true;
                            clip2.start();
                            if(clip2.isRunning() && wygraned) {
                                Thread.sleep(5000);
                            }
                            JOptionPane.showMessageDialog(null, "Przeciwnik FOLDUJE!\n Wygrywasz gre!");
                            wygraned = false;
                            clip2.stop();
                            gracz1.zetony += stawka;
                            new UpdateZetonySQL(gracz1.nick, stawka);
                            isRunning = false;
                        }
                        case 2 -> {
                            JOptionPane.showMessageDialog(null, "Przeciwnik CALLUJE!");
                            stawka *= 2;
                        }
                        case 3 -> {
                            JOptionPane.showMessageDialog(null, "Przeciwnik RAISUJE!");
                            long raiseSi = random.nextLong(100);
                            stawka += raiseSi+stawka;
                            JOptionPane.showMessageDialog(null, "Pula wynosi: " + stawka);
                        }
                    }
                    if(isRunning) {
                        int opcjaOdrzucenia = JOptionPane.showOptionDialog(null,
                                "Czy chcesz odrzucic karty?\nMozesz odrzucic do 4 kart",
                                "Odrzucanie kart", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, opcjeTakNie, opcjeTakNie[0]);
                        if (opcjaOdrzucenia == 0) {
                            boolean[] tab = new boolean[5];
                            int liczbaWymian = 0;
                            JOptionPane.showMessageDialog(null, "Twoje karty po kolei: ");
                            for (int i = 0; i < 5; i++) {
                                int odp = JOptionPane.showOptionDialog(null, "Czy chcesz wymienic ta karte: " + gracz1.reka.get(i),
                                        "Pozbycie", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                        null, opcjeTakNie, opcjeTakNie[0]);
                                if (odp == 0) {
                                    tab[i] = true;
                                    liczbaWymian++;
                                }
                                if (liczbaWymian == 4) {
                                    break;
                                }
                            }
                            gracz1.giveKarty(tab);
                            dealer.rozdajDodatkowe(gracz1);
                        }
                        JOptionPane.showMessageDialog(null, "Prowadzisz LICYTACJE!!!");
                        int playerOption = JOptionPane.showOptionDialog(null, "Zaczynamy LICYTACJE!!!\nWybierz co chcesz zrobić: ", "Licytacja",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcjeLicytacji, opcjeLicytacji[0]);

                        switch (playerOption) {
                            case 0 -> {
                                JOptionPane.showMessageDialog(null, "FOLDUJESZ! \nPrzeciwnik wygrywa gre!");
                                isRunning = false;
                                running = false;
                            }
                            case 1 -> {
                                JOptionPane.showMessageDialog(null, "RAISUJESZ!\n");
                                String x = JOptionPane.showInputDialog("Podaj stawke do podwyzszenia: ");
                                if(!x.isEmpty()){
                                    playerRaise = Long.parseLong(x);
                                }
                                else {
                                    playerRaise = random.nextLong(gracz1.zetony);
                                }
                                if(playerRaise<= 0 ||playerRaise>gracz1.zetony) {
                                    JOptionPane.showMessageDialog(null,"Podano zla kwote!\nKwota zostanie wybrana automatycznie");
                                    playerRaise = random.nextLong(gracz1.zetony);
                                    stawka += playerRaise;
                                    gracz1.zetony -= playerRaise;
                                    new UpdateZetonySQL(gracz1.nick, -playerRaise);
                                }
                                else {
                                    stawka += playerRaise;
                                    gracz1.zetony -= playerRaise;
                                    new UpdateZetonySQL(gracz1.nick, -playerRaise);
                                }
                            }
                            case 2 -> {
                                JOptionPane.showMessageDialog(null, "CALLUJESZ!!!");
                                stawka *= 2;
                                JOptionPane.showMessageDialog(null, "Podwyzszasz!\n W puli jest: " + stawka);
                            }
                        }
                        if(running) {
                            removeDrawnKarty();
                            int wynik = dealer.ktoWygrywa(gracz1, gracz2);
                            if (wynik == 1) {
                                hv = 1;
                                enabled = false;
                                enabled2 = true;
                                if(clip2.isRunning() && wygraned) {
                                    Thread.sleep(5000);
                                }
                                JOptionPane.showMessageDialog(null, "WYGRYWASZ!!!");
                                hv = 0;
                                clip2.stop();
                                gracz1.zetony += stawka;
                                new UpdateZetonySQL(gracz1.nick, stawka);
                            } else if (wynik == 2) {
                                enabled = false;
                                enabled2 = true;
                                JOptionPane.showMessageDialog(null, "WYGRYWA PRZECIWNIK!!!");
                            } else if (wynik == 0) {
                                enabled = false;
                                enabled2 = true;
                                JOptionPane.showMessageDialog(null, "REMIS!!!");
                                gracz1.zetony += stawka / 2;
                                new UpdateZetonySQL(gracz1.nick, stawka / 2);
                            }
                        }
                    }
                    int result2 = JOptionPane.showOptionDialog(null,"Czy chcesz zagrac ponownie?","Jeszcze raz?",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, opcjeTakNie, opcjeTakNie[0]);
                    if(result2==0) {
                        refreashed = true;
                    }
                    else {
                        int result = JOptionPane.showConfirmDialog(null,"Powrot do menu","Koniec",
                                JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);
                        if(result == 0) {
                            STATE=0;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "KONIEC GRY!!!\nWygrywa przeciwnik!");
                    int result2 = JOptionPane.showOptionDialog(null,"Czy chcesz zagrac ponownie?","Jeszcze raz?",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, opcjeTakNie, opcjeTakNie[0]);
                    if(result2==0) {
                        refreashed = true;
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Powrot do menu!");
                        STATE=0;
                    }
                }
            }
        }
    }

    public void wygranaGracza(Graphics g) {
        g.drawImage(wygranaNapis,-170, 0,this);
        g.drawImage(wygranaGif,SCREEN_WIDTH/4-250,
                50,this);
        g.drawImage(wygranaGif,SCREEN_WIDTH/2-120,
                50,this);
        g.drawImage(wygranaGif,SCREEN_WIDTH-370,
                50,this);
    }
    public void multiplayer(Graphics g) {

        try {

            if(newGame) {
                JOptionPane.showMessageDialog(null, "Zamknij okno i oczekuj cierpliwie na drugiego gracza!");
                SocketHP.stawka = 0;
                SocketHP.start();
                ktoryJestem = SocketHP.in.readLine();
                SocketHP.gracz = new Gracz(GetNickSQL.name, GetZetonySQL.zetony);
                SocketHP.objectOutputStream.writeObject(SocketHP.gracz);
                SocketHP.przeciwnik = (Gracz) SocketHP.objectInputStream.readObject();
                newGame = false;
            }

            this.removeAll();
            long playerRaise;
            long sumaPrzyg = 1;
            clip.stop();
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(table, 0, 0, null);
            g2.setColor(Color.magenta);
            g2.fillOval(SCREEN_WIDTH / 2 - 50, 50, 100, 100);
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(SCREEN_WIDTH / 2 - 50, 50, 100, 100);
            g2.setFont(new Font("Algerian", Font.PLAIN, 25));
            g2.setColor(Color.white);
            g2.drawString(SocketHP.przeciwnik.nick, SCREEN_WIDTH / 2 - 25, 110);

            g2.setFont(new Font("Algerian", Font.PLAIN, 40));
            g2.drawString("Pula: ", 10, 50);
            g2.drawString(Long.toString(SocketHP.stawka), 150, 50);

            g2.setColor(Color.blue);
            g2.fillOval(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 150, 100, 100);
            if(changeableWhite) {
                g2.setColor(Color.white);
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 150, 100, 100);
            }
            if(changeableGreen) {
                g2.setColor(Color.green);
                g2.setStroke(new BasicStroke(5));
                g2.drawOval(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 150, 100, 100);
            }
            if(changeableRed) {
                g2.setColor(Color.red);
                g2.setStroke(new BasicStroke(5));
                g2.drawOval(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 150, 100, 100);
            }
            g2.setFont(new Font("Algerian", Font.PLAIN, 25));
            g2.setColor(Color.white);
            g2.drawString(SocketHP.gracz.nick, SCREEN_WIDTH / 2 - 20, SCREEN_HEIGHT - 90);
            g2.drawString(Long.toString(SocketHP.gracz.zetony), SCREEN_WIDTH / 2 + 110, SCREEN_HEIGHT - 90);


            if(refreashed) {
                repaint();
                pom = 1;
                enabled = false;
                enabled2 = false;
                refreashed = false;
                running = true;
                isRunning = true;
                changeableWhite = true;
                changeableRed = false;
                changeableGreen = false;
            }
            if (enabled) {
                drawFlippedKarty(g2);
                drawKarty(SocketHP.gracz);
            }
            if (enabled2) {
                repaint();
                drawKartyGracza(g2, SocketHP.przeciwnik);
                drawKartyGracza2(g2, SocketHP.gracz);
            }

            if (pom == 1) {
                pom = 0;
                repaint();
                JOptionPane.showMessageDialog(null, "Gracz " + SocketHP.przeciwnik.nick + "\ndolaczyl do gry przeciwko tobie");
                if (ktoryJestem.equals("1")) {
                    changeableWhite = false;
                    changeableGreen = true;
                    try {
                        sumaPrzyg = drawPrzygotowanie();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Podales pusta kwote!");
                    }
                    if (sumaPrzyg <= 0 || sumaPrzyg > SocketHP.gracz.zetony) {
                        JOptionPane.showMessageDialog(null, "Podano zla kwote!\nKwota zostanie wylosowana automatycznie.");
                        sumaPrzyg = random.nextLong(SocketHP.gracz.zetony);
                    }

                    changeableGreen = false;
                    changeableRed = true;
                    
                    SocketHP.stawka += sumaPrzyg;
                    new UpdateZetonySQL(SocketHP.gracz.nick, -SocketHP.stawka);
                    SocketHP.gracz.zetony -= SocketHP.stawka;

                    //przeslanie stawki na serwer
                    SocketHP.out.println(""+SocketHP.stawka);

                    //podwojenie stawki
                    SocketHP.stawka = Long.parseLong(SocketHP.in.readLine());
                    if(SocketHP.stawka == 0){
                        JOptionPane.showMessageDialog(null, "Przeciwnik rezygnuje z gry!!");
                    }

                    if(SocketHP.stawka != 0) {

                        JOptionPane.showMessageDialog(null, "Przeciwnik podwaja!! \nZaczynamy gre!");

                        //rozdanie kart
                        SocketHP.gracz = (Gracz) SocketHP.objectInputStream.readObject();
                        enabled = true;

                        changeableRed = false;
                        changeableGreen = true;

                        int playerOption = JOptionPane.showOptionDialog(null, "Zaczynamy LICYTACJE!!!\nWybierz co chcesz zrobić: ", "Licytacja",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcjeLicytacji, opcjeLicytacji[0]);

                        //przeslanie wyboru na serwer
                        SocketHP.out.println("" + playerOption);

                        switch (playerOption) {
                            case 0 -> {
                                JOptionPane.showMessageDialog(null, "FOLDUJESZ! \nPrzeciwnik wygrywa gre!");
                                //zmiana wzgledem singla
                                isRunning = false;
                            }
                            case 1 -> {
                                JOptionPane.showMessageDialog(null, "RAISUJESZ!\n");
                                String x = JOptionPane.showInputDialog("Podaj stawke do podwyzszenia: ");
                                if (!x.isEmpty()) {
                                    playerRaise = Long.parseLong(x);
                                } else {
                                    playerRaise = random.nextLong(SocketHP.gracz.zetony);
                                }
                                if (playerRaise <= 0 || playerRaise > SocketHP.gracz.zetony) {
                                    JOptionPane.showMessageDialog(null, "Podane zla kwota! \n Kwota zostanie wybrana automatycznie");
                                    playerRaise = random.nextLong(SocketHP.gracz.zetony);
                                    SocketHP.stawka += playerRaise + SocketHP.stawka;
                                    SocketHP.gracz.zetony -= playerRaise + SocketHP.stawka;
                                    new UpdateZetonySQL(SocketHP.gracz.nick, -playerRaise - SocketHP.stawka);
                                } else {
                                    SocketHP.stawka += playerRaise + SocketHP.stawka;
                                    SocketHP.gracz.zetony -= playerRaise + SocketHP.stawka;
                                    new UpdateZetonySQL(SocketHP.gracz.nick, -playerRaise - SocketHP.stawka);
                                }
                                SocketHP.out.println("" + SocketHP.stawka);
                            }
                            case 2 -> {
                                JOptionPane.showMessageDialog(null, "CALLUJESZ!!");
                                SocketHP.stawka *= 2;
                                new UpdateZetonySQL(SocketHP.gracz.nick, -SocketHP.stawka);
                                JOptionPane.showMessageDialog(null, "Podwyzszaszl!\n W puli jest: " + SocketHP.stawka);
                            }
                        }
                        if (isRunning) {
                            int opcjaOdrzucenia = JOptionPane.showOptionDialog(null,
                                    "Czy chcesz odrzucic karty?\nMozesz odrzucic do 4 kart",
                                    "Odrzucanie kart", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, opcjeTakNie, opcjeTakNie[0]);
                            if (opcjaOdrzucenia == 0) {
                                boolean[] tab = new boolean[5];
                                int liczbaWymian = 0;
                                JOptionPane.showMessageDialog(null, "Twoje karty po kolei: ");
                                for (int i = 0; i < 5; i++) {
                                    int odp = JOptionPane.showOptionDialog(null, "Czy chcesz wymienic ta karte: " + SocketHP.gracz.reka.get(i),
                                            "Wymiana", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                            null, opcjeTakNie, opcjeTakNie[0]);
                                    if (odp == 0) {
                                        tab[i] = true;
                                        liczbaWymian++;
                                    }
                                    if (liczbaWymian == 4) {
                                        break;
                                    }
                                }

                                //oddanie kart
                                SocketHP.gracz.giveKarty(tab);

                            }

                            SocketHP.objectOutputStream.writeObject(SocketHP.gracz);
                            SocketHP.gracz = (Gracz) SocketHP.objectInputStream.readObject();

                            changeableGreen = false;
                            changeableRed = true;


                            JOptionPane.showMessageDialog(null, "Przeciwnik prowadzi licytacje!");

                            //odbior wyboru
                            int playerOption2 = Integer.parseInt(SocketHP.in.readLine());

                            switch (playerOption2) {
                                case 0 -> {
                                    JOptionPane.showMessageDialog(null, "Przeciwnik FOLDUJE!\n Wygrywasz gre!");
                                    SocketHP.gracz.zetony += SocketHP.stawka;
                                    new UpdateZetonySQL(SocketHP.gracz.nick, SocketHP.stawka);
                                    running = false;
                                }
                                case 1 -> {
                                    JOptionPane.showMessageDialog(null, "Przeciwnik RAISUJE!");
                                    SocketHP.stawka = Long.parseLong(SocketHP.in.readLine());
                                    JOptionPane.showMessageDialog(null, "Pula wynosi: " + SocketHP.stawka);
                                }
                                case 2 -> {
                                    JOptionPane.showMessageDialog(null, "Przeciwnik CALLUJE!");
                                    stawka *= 2;
                                }
                            }
                            if (running) {

                                //przeslanie wyniku na serwer
                                SocketHP.objectOutputStream.writeObject(SocketHP.gracz);

                                //odebranie wyniku
                                int wynik = Integer.parseInt(SocketHP.in.readLine());

                                //odebranie przeciwnika
                                SocketHP.przeciwnik = (Gracz) SocketHP.objectInputStream.readObject();

                                removeDrawnKarty();
                                enabled = false;
                                enabled2 = true;

                                changeableRed = false;
                                changeableGreen = false;
                                changeableWhite = true;

                                if (wynik == 1) {
                                    JOptionPane.showMessageDialog(null, "WYGRYWASZ!!!");
                                    SocketHP.gracz.zetony += SocketHP.stawka;
                                    new UpdateZetonySQL(SocketHP.gracz.nick, SocketHP.stawka);
                                } else if (wynik == 2) {
                                    JOptionPane.showMessageDialog(null, "WYGRYWA PRZECIWNIK!!!");
                                } else if (wynik == 0) {
                                    JOptionPane.showMessageDialog(null, "REMIS!!!");
                                    SocketHP.gracz.zetony += stawka / 2;
                                    new UpdateZetonySQL(SocketHP.gracz.nick, SocketHP.stawka / 2);
                                }
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Powrot do menu!");
                    STATE = 0;
                    SocketHP.end();
                } else {
                    SocketHP.stawka = Long.parseLong(SocketHP.in.readLine());
                    long suma=SocketHP.stawka;

                    changeableWhite = false;
                    changeableRed = true;


                    JOptionPane.showMessageDialog(null, "ZACZYNA PRZECIWNIK!!!\nW puli jest: " + SocketHP.stawka);

                    changeableRed = false;
                    changeableGreen = true;

                    int choice = JOptionPane.showOptionDialog(null, "Czy podwajasz stawke??", "Czy gramy?",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, opcjeTakNie, opcjeTakNie[0]);

                    if(choice == 1)
                        SocketHP.out.println("0");

                    if (choice == 0) {
                        SocketHP.stawka += SocketHP.stawka * 2;

                        new UpdateZetonySQL(SocketHP.gracz.nick, -suma * 2);
                        SocketHP.gracz.zetony -= suma * 2;


                        //podwojona stawka
                        JOptionPane.showMessageDialog(null, "Podwajasz stawke!!!\n Zaczynamy gre!");
                        SocketHP.out.println(""+SocketHP.stawka);

                        //rozdanie kart
                        SocketHP.gracz = (Gracz) SocketHP.objectInputStream.readObject();
                        enabled = true;

                        changeableGreen = false;
                        changeableRed = true;

                        JOptionPane.showMessageDialog(null, "Przeciwnik prowadzi LICYTACJE!!!");

                        //odbior wyboru
                        int playerOption = Integer.parseInt(SocketHP.in.readLine());

                        switch (playerOption) {
                            case 0 -> {
                                JOptionPane.showMessageDialog(null, "Przeciwnik FOLDUJE!\n Wygrywasz gre!");
                                SocketHP.gracz.zetony += SocketHP.stawka;
                                new UpdateZetonySQL(SocketHP.gracz.nick, SocketHP.stawka);
                                isRunning = false;
                            }
                            case 1 -> {
                                JOptionPane.showMessageDialog(null, "Przeciwnik RAISUJE!");
                                SocketHP.stawka = Long.parseLong(SocketHP.in.readLine());
                                JOptionPane.showMessageDialog(null, "Pula wynosi: " + SocketHP.stawka);
                            }
                            case 2 -> {
                                JOptionPane.showMessageDialog(null, "Przeciwnik CALLUJE!");
                                SocketHP.stawka *= 2;
                            }
                        }

                        changeableRed = false;
                        changeableGreen = true;


                        if (isRunning) {
                            int opcjaOdrzucenia = JOptionPane.showOptionDialog(null,
                                    "Czy chcesz odrzucic karty?\nMozesz odrzucic do 4 kart",
                                    "Odrzucanie kart", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, opcjeTakNie, opcjeTakNie[0]);
                            if (opcjaOdrzucenia == 0) {
                                boolean[] tab = new boolean[5];
                                int liczbaWymian = 0;
                                JOptionPane.showMessageDialog(null, "Twoje karty po kolei: ");
                                for (int i = 0; i < 5; i++) {
                                    int odp = JOptionPane.showOptionDialog(null, "Czy chcesz wymienic ta karte: " + SocketHP.gracz.reka.get(i),
                                            "Pozbycie", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                            null, opcjeTakNie, opcjeTakNie[0]);
                                    if (odp == 0) {
                                        tab[i] = true;
                                        liczbaWymian++;
                                    }
                                    if (liczbaWymian == 4) {
                                        break;
                                    }
                                }

                                //oddanie kart
                                SocketHP.gracz.giveKarty(tab);

                            }

                            SocketHP.objectOutputStream.writeObject(SocketHP.gracz);
                            SocketHP.gracz = (Gracz) SocketHP.objectInputStream.readObject();

                            changeableRed = false;
                            changeableGreen = true;


                            JOptionPane.showMessageDialog(null, "Prowadzisz LICYTACJE!!!");
                            int playerOption2 = JOptionPane.showOptionDialog(null, "Zaczynamy LICYTACJE!!!\nWybierz co chcesz zrobić: ", "Licytacja",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcjeLicytacji, opcjeLicytacji[0]);

                            //przeslanie wyboru na serwer
                            SocketHP.out.println(""+playerOption2);

                            switch (playerOption2) {
                                case 0 -> {
                                    JOptionPane.showMessageDialog(null, "FOLDUJESZ! \nPrzeciwnik wygrywa gre!");
                                    isRunning = false;
                                    running = false;
                                }
                                case 1 -> {
                                    JOptionPane.showMessageDialog(null, "RAISUJESZ!\n");
                                    String x = JOptionPane.showInputDialog("Podaj stawke do podwyzszenia: ");
                                    if (!x.isEmpty()) {
                                        playerRaise = Long.parseLong(x);
                                    } else {
                                        playerRaise = random.nextLong(SocketHP.gracz.zetony);
                                    }
                                    if (playerRaise <= 0 || playerRaise > SocketHP.gracz.zetony) {
                                        JOptionPane.showMessageDialog(null, "Podano zla kwote!\nKwota zostanie wybrana automatycznie");
                                        playerRaise = random.nextLong(SocketHP.gracz.zetony);
                                        SocketHP.stawka += playerRaise;
                                        SocketHP.gracz.zetony -= playerRaise;
                                        new UpdateZetonySQL(SocketHP.gracz.nick, -playerRaise);
                                    } else {
                                        SocketHP.stawka += playerRaise;
                                        SocketHP.gracz.zetony -= playerRaise;
                                        new UpdateZetonySQL(SocketHP.gracz.nick, -playerRaise);
                                    }
                                    SocketHP.out.println(""+SocketHP.stawka);
                                }
                                case 2 -> {
                                    JOptionPane.showMessageDialog(null, "CALLUJESZ!!!");
                                    SocketHP.stawka *= 2;
                                    JOptionPane.showMessageDialog(null, "Podwyzszasz!\n W puli jest: " + SocketHP.stawka);
                                }
                            }
                            if (running) {

                                //przeslanie wyniku na serwer
                                SocketHP.objectOutputStream.writeObject(SocketHP.gracz);

                                //odebranie wyniku
                                int wynik = Integer.parseInt(SocketHP.in.readLine());

                                //odebranie przeciwnika
                                SocketHP.przeciwnik = (Gracz) SocketHP.objectInputStream.readObject();


                                removeDrawnKarty();
                                enabled = false;
                                enabled2 = true;

                                changeableGreen = false;
                                changeableRed = false;
                                changeableWhite = true;


                                if (wynik == 2) {
                                    JOptionPane.showMessageDialog(null, "WYGRYWASZ!!!");
                                    SocketHP.gracz.zetony += SocketHP.stawka;
                                    new UpdateZetonySQL(SocketHP.gracz.nick, SocketHP.stawka);
                                } else if (wynik == 1) {
                                    JOptionPane.showMessageDialog(null, "WYGRYWA PRZECIWNIK!!!");
                                } else if (wynik == 0) {
                                    JOptionPane.showMessageDialog(null, "REMIS!!!");
                                    SocketHP.gracz.zetony += SocketHP.stawka / 2;
                                    new UpdateZetonySQL(SocketHP.gracz.nick, SocketHP.stawka / 2);
                                }
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Powrot do menu!");
                        STATE = 0;
                        SocketHP.end();

                    } else {
                        JOptionPane.showMessageDialog(null, "KONIEC GRY!!!\nWygrywa przeciwnik!");
                        JOptionPane.showMessageDialog(null, "Powrot do menu!");
                        STATE = 0;
                        SocketHP.end();

                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public long drawPrzygotowanie() {
        return Long.parseLong(JOptionPane.showInputDialog("ZACZYNASZ TY!!!\nPodaj stawke do puli: "));
    }

    public void drawFlippedKarty(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for(int i=0;i<5;i++) {
            g2.drawImage(new ImageIcon("images/CardsBack.png").getImage(),
                    400+i*150,200,null);

        }
    }

    public void drawKartyGracza(Graphics g,Gracz gracz) {
        Graphics2D g2 = (Graphics2D) g;
        for(int i=0;i<5;i++) {
            g2.drawImage(new ImageIcon("images/"+gracz.reka.get(i)+".png").getImage(),
                    400+i*150,200,null);

        }
    }

    public void drawKartyGracza2(Graphics g,Gracz gracz) {
        Graphics2D g2 = (Graphics2D) g;
        for(int i=0;i<5;i++) {
            g2.drawImage(new ImageIcon("images/"+gracz.reka.get(i)+".png").getImage(),
                    400+i*150,450,null);

        }
    }

    public void removeDrawnKarty() {
        this.remove(c1);
        this.remove(c2);
        this.remove(c3);
        this.remove(c4);
        this.remove(c5);
    }
    public void drawKarty(Gracz gracz) {
        c1 = new PokerCard("images/"+gracz.reka.get(0)+".png",400,450);
        c2 = new PokerCard("images/"+gracz.reka.get(1)+".png",550,450);
        c3 = new PokerCard("images/"+gracz.reka.get(2)+".png",700,450);
        c4 = new PokerCard("images/"+gracz.reka.get(3)+".png",850,450);
        c5 = new PokerCard("images/"+gracz.reka.get(4)+".png",1000,450);
        this.add(c1);
        this.add(c2);
        this.add(c3);
        this.add(c4);
        this.add(c5);

    }

    public void ranking(Graphics g) throws SQLException {
        this.removeAll();
        new GetInfoSQL();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(BG,0,0,null);
        g2.setFont(new Font("Algerian",Font.PLAIN,140));
        g2.setColor(Color.ORANGE);
        g2.drawString("RANKING",SCREEN_WIDTH/2-240,120);
        rankingArea.setBounds(150,200,SCREEN_WIDTH-300,600);
        rankingArea.setFocusable(false);
        rankingArea.setForeground(Color.white);
        rankingArea.setFont(new Font("Algerian",Font.BOLD,70));
        rankingArea.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        rankingArea.setBackground(new Color(1f,1f,1f,0.2f));
        rankingArea.setText("");
        rankingArea.append(GetInfoSQL.result);
        this.add(rankingArea);
        backLabel.setBounds(0,0,100,100);
        this.add(backLabel);
    }



    public void wyjscie() {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==menuB1) {
            STATE=1;
            refreashed = true;
        }
        if(e.getSource()==menuB2) {
            STATE=2;
        }

        if(e.getSource()==menuB3) {
            STATE=3;
            refreashed = true;
            newGame = true;
        }
        if(e.getSource()==menuB4) {
            wyjscie();
        }

        repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getSource()==musicLabel) {
            if (clip.isRunning()) {
                musicLabel.setIcon(musicOff);
                clip.stop();
            } else {
                musicLabel.setIcon(musicOn);
                clip.start();
            }
        }
        if(e.getSource()==backLabel) {
            if(STATE==2) {
                STATE=0;
            }
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