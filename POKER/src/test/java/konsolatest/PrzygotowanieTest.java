package konsolatest;
import konsola.Gra;
import konsola.Gracz;
import konsola.Przygotowanie;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class PrzygotowanieTest {

    @Test
    void testPrzygotowanieGraczZaczyna() {
        String userInput = "200";
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        int ktoZaczyna = 1;
        assertEquals(1,ktoZaczyna);
        String name = "Gracz";
        assertEquals("Gracz",name);
        long zet = 1000;
        assertEquals(1000,zet);
        String name2 = "Gracz 2";
        assertEquals("Gracz 2",name2);
        long zet2 = 2000;
        assertEquals(2000,zet2);
        Gracz gracz1 = new Gracz(name,zet);
        Gracz gracz2 = new Gracz(name2,zet2);
        new Przygotowanie(gracz1,gracz2,ktoZaczyna);
        long suma = 200;
        Gra.pula = 200;
        assertTrue(suma < gracz1.zetony);
        assertEquals(400,suma*2);
        assertEquals(800,gracz1.zetony);
        System.setIn(System.in);
    }
    @Test
    void testPrzygotowaniePrzeciwnikZaczyna() {
        String userInput = "t";
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        int ktoZaczyna = 2;
        assertEquals(2,ktoZaczyna);
        String name = "Gracz";
        assertEquals("Gracz",name);
        long zet = 1000;
        assertEquals(1000,zet);
        String name2 = "Gracz 2";
        assertEquals("Gracz 2",name2);
        long zet2 = 2000;
        assertEquals(2000,zet2);
        Gracz gracz1 = new Gracz(name,zet);
        Gracz gracz2 = new Gracz(name2,zet2);
        new Przygotowanie(gracz1,gracz2,ktoZaczyna);
        long suma = 2000;
        assertTrue(suma > gracz1.zetony);
        assertEquals(4000,suma*2);
        System.setIn(System.in);
    }
}