package konsolatest;
import java.util.ArrayList;

import konsola.Karta;
import konsola.Reka;
import org.junit.Assert;
import org.junit.Test;

public class RekaTest {

    @Test
    public void testGiveKarty() {
        Reka reka = new Reka();
        ArrayList<Karta> karty = new ArrayList<>();
        karty.add(new Karta("h", "A"));
        karty.add(new Karta("d", "2"));
        karty.add(new Karta("c", "3"));
        karty.add(new Karta("s", "4"));
        karty.add(new Karta("h", "5"));
        reka.reka = karty;

        boolean[] tab = {true, false, true, false, true};
        reka.giveKarty(tab);

        Assert.assertEquals(2, reka.reka.size());
    }

    @Test
    public void testCalculatePunkty() {
        Reka reka = new Reka();
        ArrayList<Karta> karty = new ArrayList<>();
        karty.add(new Karta("h", "10"));
        karty.add(new Karta("h", "J"));
        karty.add(new Karta("h", "Q"));
        karty.add(new Karta("h", "K"));
        karty.add(new Karta("h", "A"));
        reka.reka = karty;

        int punkty = reka.calculatePunkty();

        Assert.assertEquals(10000000, punkty);
    }

    @Test
    public void testFlipAll() {
        Reka reka = new Reka();
        ArrayList<Karta> karty = new ArrayList<>();
        karty.add(new Karta("h", "A"));
        karty.add(new Karta("d", "2"));
        karty.add(new Karta("c", "3"));
        karty.add(new Karta("s", "4"));
        karty.add(new Karta("h", "5"));
        reka.reka = karty;

        reka.flipAll();

        String rekaString = reka.toString();

        Assert.assertEquals("XX XX XX XX XX", rekaString);
    }

    @Test
    public void testToString() {
        Reka reka = new Reka();
        ArrayList<Karta> karty = new ArrayList<>();
        karty.add(new Karta("h", "A"));
        karty.add(new Karta("d", "2"));
        karty.add(new Karta("c", "3"));
        karty.add(new Karta("s", "4"));
        karty.add(new Karta("h", "5"));
        reka.reka = karty;

        String rekaString = reka.toString();

        Assert.assertEquals("Ah 2d 3c 4s 5h", rekaString);
    }
}
