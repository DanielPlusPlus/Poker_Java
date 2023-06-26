package serwer_klient_test;
import konsola.Gracz;
import konsola.Rozdajacy;
import org.junit.Before;
import org.junit.Test;
import serwer_klient.Multiplayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MultiplayerTest {

    private Gracz gracz1;
    private Gracz gracz2;
    private Rozdajacy dealer;

    @Before
    public void setUp() {
        gracz1 = new Gracz("Vicky",1000);
        gracz2 = new Gracz("Danny",1000);
        dealer = new Rozdajacy();
        Multiplayer.gracz1 = gracz1;
        Multiplayer.gracz2 = gracz2;
        Multiplayer.dealer = dealer;
    }

    @Test
    public void testRefresh() {
        dealer.rozdaj(gracz1, gracz2);
        Multiplayer.stawka = 1000;
        Multiplayer.refresh(gracz1, gracz2, dealer);

        assertNotNull(gracz1.reka);
        assertEquals(0, gracz1.reka.size());
        assertNotNull(gracz2.reka);
        assertEquals(0, gracz2.reka.size());
        assertEquals(0, Multiplayer.stawka);
        dealer.newRozdanie();
    }
}
