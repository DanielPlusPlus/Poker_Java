package konsolatest;
import konsola.Gra;
import konsola.Gracz;
import konsola.Rozdajacy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GraTest {
    private Gracz g1, g2;
    Rozdajacy rozdajacy;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
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
        assertNotNull(rozdajacy);
        int ktoZaczyna = 1;
        assertEquals(1,ktoZaczyna);
        Gra.player1 = g1;
        Gra.player2 = g2;
        Gra.pula = 100;
        g1.clearHand();
        g2.clearHand();
        assertEquals(0,g1.reka.size());
        assertEquals(0,g2.reka.size());

    }

    @org.junit.jupiter.api.Test
    public void testShowKarty() {
        rozdajacy.rozdaj(g1,g2);
        assertNotNull(g1);
        assertNotNull(g2);
        String expected1 = "Karty przeciwnika: "+g2;
        String expected2 = "Karty gracza "+g1.nick+": "+g1;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);
        Gra.showKarty();
        String[] answers = baos.toString().split(System.lineSeparator());
        String actual1 = answers[0];
        String actual2 = answers[1];
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        System.setOut(System.out);
    }

    @org.junit.jupiter.api.Test
    public void testLicytacjaSIchoice1() {
        int wybor = 1;
        assertEquals(1,wybor);
        Gra.licytacjaSI(wybor);
        assertFalse(Gra.running);
    }

    @org.junit.jupiter.api.Test
    public void testLicytacjaSIchoice2() {
        int wybor = 2;
        assertEquals(2,wybor);
        int plus = 100;
        Gra.pula += plus;
        assertEquals(200,Gra.pula);
    }

    @org.junit.jupiter.api.Test
    public void testLicytacjaSIchoice3() {
        int wybor = 3;
        assertEquals(3,wybor);
        long stawka = 200;
        Gra.pula += stawka;
        assertEquals(300,Gra.pula);
    }
}