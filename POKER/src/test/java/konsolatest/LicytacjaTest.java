package konsolatest;
import konsola.Gracz;
import konsola.Gra;
import konsola.Licytacja;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;

class LicytacjaTest {

    private Gracz g1, g2;
    private Licytacja licytacja;

    @BeforeEach
    void setUp() {
        String name1 = "Gracz 1";
        assertEquals("Gracz 1",name1);
        String name2 = "Gracz 2";
        assertEquals("Gracz 2",name2);
        long zet1 = 1000;
        assertEquals(1000,zet1);
        long zet2 = 2000;
        assertEquals(2000,zet2);
        g1 = new Gracz(name1, zet1);
        g2 = new Gracz(name2, zet2);
        Gra.pula = 100;
    }

    @Test
    void foldLicytacja() {
        String userInput = "1";
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        licytacja = new Licytacja(g1,g2);
        assertEquals("1", userInput);
        licytacja.foldLicytacja(g1); //drugie sprawdzenie
        assertFalse(Gra.running);
        System.setIn(System.in);
    }

    @Test
    void raiseLicytacja() {
        String userInput = String.format("3%s200%s200",System.lineSeparator(),System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        String[] answers = userInput.split(System.lineSeparator());
        licytacja = new Licytacja(g1,g2);
        assertEquals("3", answers[0]);
        g1.zetony -= 200;
        licytacja.raiseLicytacja(g1); // drugie sprawdzenie
        assertEquals(400, g1.zetony);
        System.setIn(System.in);
    }

    @Test
    void callLicytacja() {
        String userInput = "2";
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        licytacja = new Licytacja(g1,g2);
        licytacja.ch = 0;
        assertEquals(0,licytacja.ch);
        assertEquals("2", userInput);
        licytacja.callLicytacja(g1); //drugie sprawdzenie
        assertEquals(200,Gra.pula);
        assertEquals(800,g1.zetony);
        System.setIn(System.in);
    }
}