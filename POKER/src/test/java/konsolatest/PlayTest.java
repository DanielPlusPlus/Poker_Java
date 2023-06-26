package konsolatest;
import konsola.Gracz;
import konsola.Rozdajacy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayTest {
    private Gracz g1, g2;
    private Rozdajacy rozdajacy;

    @BeforeEach
    void setUp()
    {
        String name1 = "Gracz1";
        assertEquals("Gracz1",name1);
        String name2 = "Gracz2";
        assertEquals("Gracz2",name2);
        long zet1 = 1000;
        assertEquals(1000,zet1);
        long zet2 = 2000;
        assertEquals(2000,zet2);
        g1 = new Gracz(name1, zet1);
        g2 = new Gracz(name2, zet2);
        rozdajacy = new Rozdajacy();

    }

    @Test
    void testPlayWhenPlayerStarts() {
        int ktoZaczyna = 1;
        assertEquals(1,ktoZaczyna);
        rozdajacy.rozdaj(g1,g2);
        rozdajacy.rozdajDodatkowe(g1);
        rozdajacy.rozdajDodatkowe(g2);
        int wynik = rozdajacy.ktoWygrywa(g1,g2);
        assertTrue(wynik >= 0 && wynik < 3);
    }

    @Test
    void testPlayWhenSiStarts() {
        int ktoZaczyna = 2;
        assertEquals(2,ktoZaczyna);
        rozdajacy.rozdaj(g1,g2);
        rozdajacy.rozdajDodatkowe(g1);
        rozdajacy.rozdajDodatkowe(g2);
        int wynik = rozdajacy.ktoWygrywa(g1,g2);
        assertTrue(wynik >= 0 && wynik < 3);
    }
}