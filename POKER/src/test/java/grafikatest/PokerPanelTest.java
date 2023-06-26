package grafikatest;
import org.junit.jupiter.api.AfterEach;
import serwer_klient.SocketHP;
import grafika.PokerPanel;
import konsola.Gracz;
import konsola.Rozdajacy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sql.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PokerPanelTest {

    private PokerPanel pokerPanel;
    private Gracz g1, g2;
    private Rozdajacy r;
    @BeforeEach
    void setUp() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        new CreateSQL();
        g1 = new Gracz("A",2000);
        g2 = new Gracz("B",2000);
        r = new Rozdajacy();
        pokerPanel = new PokerPanel(1);
        pokerPanel.stawka = 0;
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new DropSQL();
    }

    @Test
    void testRefresh(){
        r.rozdaj(g1,g2);
        pokerPanel.refresh(g1,g2,r);
        g1.clearHand();
        g2.clearHand();
        assertTrue(g1.reka.isEmpty());
        assertTrue(g2.reka.isEmpty());
        r.newRozdanie();
        assertEquals(51,r.liczbaKart);
        assertEquals(0,pokerPanel.stawka);
    }

    @Test
    void testGrajTyZaczynasz() {
        pokerPanel.refresh(g1,g2,r);
        r.rozdaj(g1,g2);
        long sumaPrzyg = 100;
        pokerPanel.stawka += sumaPrzyg;
        assertEquals(100,pokerPanel.stawka);
        new UpdateZetonySQL("A",-pokerPanel.stawka);
        g1.zetony -= pokerPanel.stawka;
        assertEquals(1900,g1.zetony);
        pokerPanel.stawka *= 3;
        assertEquals(300,pokerPanel.stawka);
        pokerPanel.stawka *= 2;
        assertEquals(600,pokerPanel.stawka);
        new UpdateZetonySQL(g1.nick, -pokerPanel.stawka);
        g1.zetony -= pokerPanel.stawka;
        assertEquals(1300,g1.zetony);
        boolean[] tab = new boolean[]{true,false,false,true,true};
        g1.giveKarty(tab);
        r.rozdajDodatkowe(g1);
        pokerPanel.stawka *= 2;
        assertEquals(1200,pokerPanel.stawka);
        int wynik = r.ktoWygrywa(g1,g2);
        assertTrue(wynik >= 0 && wynik < 3);
        if(wynik == 1){
            g1.zetony += pokerPanel.stawka;
            new UpdateZetonySQL(g1.nick,pokerPanel.stawka);
            assertEquals(2500,g1.zetony);
        }
        if(wynik == 0){
            g1.zetony += pokerPanel.stawka/2;
            new UpdateZetonySQL(g1.nick,pokerPanel.stawka/2);
            assertEquals(1900,g1.zetony);
        }
    }

    @Test
    void testGrajPrzeciwnikZaczyna() {
        pokerPanel.refresh(g2,g1,r);
        r.rozdaj(g2,g1);
        long sumaPrzyg = g2.zetony/5;
        pokerPanel.stawka += sumaPrzyg;
        assertEquals(400,pokerPanel.stawka);
        new UpdateZetonySQL("B",-sumaPrzyg*2);
        g2.zetony -= sumaPrzyg*2;
        assertEquals(1200,g2.zetony);
        pokerPanel.stawka *= 3;
        assertEquals(1200,pokerPanel.stawka);
        pokerPanel.stawka *= 2;
        assertEquals(2400,pokerPanel.stawka);
        new UpdateZetonySQL(g2.nick, -pokerPanel.stawka);
        g2.zetony -= pokerPanel.stawka;
        assertTrue(g2.zetony < 0);
        int wynik = r.ktoWygrywa(g1,g2);
        assertTrue(wynik >= 0 && wynik < 3);
        if(wynik == 1){
            g2.zetony += pokerPanel.stawka;
            new UpdateZetonySQL(g2.nick,pokerPanel.stawka);
            assertEquals(1200,g2.zetony);
        }
        if(wynik == 0){
            g2.zetony += pokerPanel.stawka/2;
            new UpdateZetonySQL(g2.nick,pokerPanel.stawka/2);
            assertEquals(0,g2.zetony);
        }
    }

    @Test
    void Multiplayer(){
        SocketHP SocketG = new SocketHP();
        SocketHP SocketP = mock(SocketHP.class);

        Gracz gracz = new Gracz("A",2000);
        Gracz przeciwnik = new Gracz("B",2000);
        Rozdajacy dealer = new Rozdajacy();

        SocketG.gracz = gracz;
        SocketG.przeciwnik = przeciwnik;

        SocketP.gracz = przeciwnik;
        SocketP.przeciwnik = gracz;

        pokerPanel.refresh(SocketG.gracz,SocketG.przeciwnik,dealer);
        SocketG.gracz.clearHand();
        SocketG.przeciwnik.clearHand();
        assertTrue(SocketG.gracz.reka.isEmpty());
        assertTrue(SocketG.przeciwnik.reka.isEmpty());
        dealer.newRozdanie();
        assertEquals(51,dealer.liczbaKart);
        assertEquals(0,SocketG.stawka);
        pokerPanel.refresh(SocketG.gracz,SocketG.przeciwnik,dealer);
        dealer.rozdaj(SocketG.gracz,SocketG.przeciwnik);

        assertEquals(SocketG.gracz, SocketP.przeciwnik);
        assertEquals(SocketG.przeciwnik, SocketP.gracz);

        assertEquals("A", SocketG.gracz.nick);
        assertEquals("B", SocketP.gracz.nick);

        assertEquals("2000", ""+SocketG.gracz.zetony);
        assertEquals("2000", ""+SocketP.gracz.zetony);

        pokerPanel.refresh(SocketG.gracz, SocketG.przeciwnik, dealer);
        dealer.rozdaj(SocketG.gracz, SocketG.przeciwnik);

        SocketP.przeciwnik = SocketG.gracz;
        SocketP.gracz = SocketG.przeciwnik;

        long sumaPrzyg = 100;
        SocketG.stawka += sumaPrzyg;
        SocketP.stawka += sumaPrzyg;
        assertEquals(100,SocketG.stawka);
        assertEquals(100,SocketP.stawka);

        new UpdateZetonySQL("A",-SocketG.stawka);
        SocketG.gracz.zetony -= SocketG.stawka;

        assertEquals(1900,SocketG.gracz.zetony);

        SocketP.gracz.zetony-=(2*SocketP.stawka);
        new UpdateZetonySQL("B",-(2*SocketP.stawka));

        SocketG.stawka *= 3;
        SocketP.stawka *= 3;

        assertEquals(1800,SocketP.gracz.zetony);

        assertEquals(300,SocketG.stawka);

        new UpdateZetonySQL(SocketG.gracz.nick, -SocketG.stawka);
        SocketG.gracz.zetony -= SocketG.stawka;

        SocketG.stawka *= 2;
        SocketP.stawka *= 2;

        assertEquals(600,SocketG.stawka);
        assertEquals(1600,SocketG.gracz.zetony);

        boolean[] tab = new boolean[]{true,false,false,true,true};
        SocketG.gracz.giveKarty(tab);
        dealer.rozdajDodatkowe(SocketG.gracz);

        new UpdateZetonySQL(SocketP.gracz.nick, -SocketP.stawka);
        SocketP.gracz.zetony -= SocketP.stawka;

        SocketG.stawka *= 2;
        SocketP.stawka *= 2;

        assertEquals(1200,SocketP.stawka);
        assertEquals(1200,SocketP.gracz.zetony);

        assertEquals(SocketG.stawka,SocketP.stawka);
        assertEquals(1200,SocketG.stawka);
        assertEquals(1200,SocketP.stawka);

        SocketP.przeciwnik = SocketG.gracz;
        SocketG.przeciwnik = SocketP.gracz;

        int wynik = dealer.ktoWygrywa(SocketG.gracz, SocketG.przeciwnik);
        int wynik2 = dealer.ktoWygrywa(SocketP.gracz, SocketP.przeciwnik);

        if(wynik > 0)
            assertNotEquals(wynik,wynik2);
        if(wynik == 0)
            assertEquals(wynik,wynik2);

        assertTrue(wynik >= 0 && wynik < 3);
        if(wynik == 0){
            SocketG.gracz.zetony += SocketG.stawka/2;
            SocketP.gracz.zetony += SocketP.stawka/2;
            new UpdateZetonySQL(SocketG.gracz.nick,SocketG.stawka/2);
            new UpdateZetonySQL(SocketP.gracz.nick,SocketP.stawka/2);
            assertEquals(2200,SocketG.gracz.zetony);
            assertEquals(1800,SocketP.gracz.zetony);
        }
        if(wynik == 1){
            SocketG.gracz.zetony += SocketG.stawka;
            new UpdateZetonySQL(SocketG.gracz.nick,SocketG.stawka);
            assertEquals(2800,SocketG.gracz.zetony);
            assertEquals(1200,SocketP.gracz.zetony);
        }
        if(wynik == 2){
            SocketP.gracz.zetony += SocketP.stawka;
            new UpdateZetonySQL(SocketP.gracz.nick,SocketP.stawka);
            assertEquals(2400,SocketP.gracz.zetony);
            assertEquals(1600,SocketG.gracz.zetony);
        }
    }

    @Test
    void testRanking() {
        new GetInfoSQL();
        assertNotNull(GetInfoSQL.result);
    }
}