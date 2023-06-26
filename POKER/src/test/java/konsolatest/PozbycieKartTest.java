package konsolatest;
import konsola.Gracz;
import konsola.PozbycieKart;
import konsola.Rozdajacy;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PozbycieKartTest {

    @Test
    void testPozbycieKartZWymiana() {
        String userInput = String.format("t%sn%st%st%sn%st",System.lineSeparator(),System.lineSeparator(),System.lineSeparator(),
                System.lineSeparator(),System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        Rozdajacy rozdajacy = new Rozdajacy();
        String name = "Gracz";
        assertEquals("Gracz",name);
        long zet = 1000;
        assertEquals(1000,zet);
        Gracz gracz = new Gracz(name,zet);
        Gracz gracz2 = new Gracz("Gracz 2",2000);
        rozdajacy.rozdaj(gracz,gracz2);
        new PozbycieKart(gracz);
        System.setIn(System.in);
    }

    @Test
    void testPozbycieKartBezWymiany() {
        String userInput = "n";
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        String expected = "Pozostawiono reka w stanie poczatkowym.";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);
        String name = "Gracz";
        assertEquals("Gracz",name);
        long zet = 1000;
        assertEquals(1000,zet);
        Gracz gracz = new Gracz(name,zet);
        new PozbycieKart(gracz);
        String[] answers = baos.toString().split(System.lineSeparator());
        String actual = answers[1];
        assertEquals(expected,actual);
        System.setIn(System.in);

    }

}