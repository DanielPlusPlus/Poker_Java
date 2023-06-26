package konsolatest;
import konsola.Gracz;
import konsola.Karta;
import konsola.Rozdajacy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RozdajacyTest {
    private Rozdajacy dealer;
    private Gracz g1;
    private Gracz g2;

    @Before
    public void setUp() {
        dealer = new Rozdajacy();
        g1 = new Gracz("Vicky",1000);
        g2 = new Gracz("Ala", 2000);
    }

    @Test
    public void testRozdaj() {
        dealer.rozdaj(g1, g2);

        assertEquals(5, g1.reka.size());
        assertEquals(5, g2.reka.size());
    }

    @Test
    public void testRozdajDodatkowe() {
        g1.reka.add(new Karta("c", "A"));
        g1.reka.add(new Karta("c", "K"));

        dealer.rozdajDodatkowe(g1);

        assertEquals(5, g1.reka.size());
    }

    @Test
    public void testKtoWygrywaG1() {
        g1.reka.add(new Karta("h", "A"));
        g1.reka.add(new Karta("h", "2"));
        g1.reka.add(new Karta("h", "3"));
        g1.reka.add(new Karta("h", "4"));
        g1.reka.add(new Karta("h", "5"));

        g2.reka.add(new Karta("h", "A"));
        g2.reka.add(new Karta("d", "2"));
        g2.reka.add(new Karta("c", "3"));
        g2.reka.add(new Karta("s", "4"));
        g2.reka.add(new Karta("h", "5"));

        int result = dealer.ktoWygrywa(g1, g2);

        assertEquals(1, result);
    }

    @Test
    public void testKtoWygrywaG2() {
        g1.reka.add(new Karta("h", "A"));
        g1.reka.add(new Karta("d", "2"));
        g1.reka.add(new Karta("c", "3"));
        g1.reka.add(new Karta("s", "4"));
        g1.reka.add(new Karta("h", "5"));

        g2.reka.add(new Karta("h", "A"));
        g2.reka.add(new Karta("h", "2"));
        g2.reka.add(new Karta("h", "3"));
        g2.reka.add(new Karta("h", "4"));
        g2.reka.add(new Karta("h", "5"));

        int result = dealer.ktoWygrywa(g1, g2);

        assertEquals(2, result);
    }

    @Test
    public void testKtoWygrywaRemis() {
        g1.reka.add(new Karta("h", "A"));
        g1.reka.add(new Karta("d", "2"));
        g1.reka.add(new Karta("c", "3"));
        g1.reka.add(new Karta("s", "4"));
        g1.reka.add(new Karta("h", "5"));

        g2.reka.add(new Karta("h", "A"));
        g2.reka.add(new Karta("d", "2"));
        g2.reka.add(new Karta("c", "3"));
        g2.reka.add(new Karta("s", "4"));
        g2.reka.add(new Karta("h", "5"));

        int result = dealer.ktoWygrywa(g1, g2);

        assertEquals(0, result);
    }
}
