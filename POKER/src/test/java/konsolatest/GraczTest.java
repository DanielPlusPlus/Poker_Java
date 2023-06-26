package konsolatest;
import konsola.Gracz;
import konsola.Karta;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class GraczTest {

    private Gracz gracz;

    @Before
    public void setUp() {
        gracz = new Gracz("Gracz", 1000);
    }

    @Test
    public void testClearHand() {
        gracz.reka.add(new Karta("h", "A"));
        gracz.reka.add(new Karta("d", "K"));
        gracz.clearHand();
        Assert.assertTrue(gracz.reka.isEmpty());
    }

    @Test
    public void testFold() {
        gracz.reka.add(new Karta("h", "A"));
        gracz.reka.add(new Karta("d", "K"));
        boolean result = gracz.fold();
        Assert.assertTrue(gracz.reka.isEmpty());
        Assert.assertFalse(result);
    }

    @Test
    public void testCall() {
        long stawka = 100;
        long result = gracz.call(stawka);
        Assert.assertEquals(stawka * 2, result);
    }

    @Test
    public void testRaise() {
        long stawka = 100;
        long ilePodbic = 50;
        long result = gracz.raise(stawka, ilePodbic);
        Assert.assertEquals(stawka + ilePodbic, result);
    }
}
