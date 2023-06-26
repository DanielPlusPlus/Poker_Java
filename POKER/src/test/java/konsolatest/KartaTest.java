package konsolatest;
import konsola.Karta;
import org.junit.Test;
import org.junit.Assert;

public class KartaTest {

    @Test
    public void testSetKolor() {
        Karta karta = new Karta("h", "A");
        karta.setKolor("c");
        Assert.assertEquals("c", karta.kolor);
    }

    @Test
    public void testSetFigura() {
        Karta karta = new Karta("h", "A");
        karta.setFigura("A");
        Assert.assertEquals("A", karta.figura);
    }

    @Test
    public void testVisibleCard() {
        Karta karta = new Karta("h", "A");
        String result = karta.toString();
        Assert.assertEquals("Ah", result);
    }

    @Test
    public void testHiddenCard() {
        Karta karta = new Karta("h", "A");
        karta.obrocKarte();
        String result = karta.toString();
        Assert.assertEquals("XX", result);
    }

    @Test
    public void test2xFlipCard() {
        Karta karta = new Karta("h", "A");
        karta.obrocKarte();
        Assert.assertFalse(karta.czy_widac);
        karta.obrocKarte();
        Assert.assertTrue(karta.czy_widac);
    }
}
