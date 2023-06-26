package konsolatest;
import java.util.HashSet;
import java.util.Set;

import konsola.Karta;
import konsola.Talia;
import org.junit.Test;
import org.junit.Assert;

public class TaliaTest {

    @Test
    public void testNewRozdanie() {
        Talia talia = new Talia();
        talia.newRozdanie();
        Assert.assertEquals(52, talia.talia.size());
    }

    @Test
    public void testInitTalia() {
        Talia talia = new Talia();
        Assert.assertEquals(52, talia.talia.size());
        Set<String> unikalneKarty = new HashSet<>();
        for (Karta karta : talia.talia) {
            String kartaString = karta.toString();
            unikalneKarty.add(kartaString);
        }
        Assert.assertEquals(52, unikalneKarty.size());
    }

    @Test
    public void testShuffleTalia() {
        Talia talia1 = new Talia();
        Talia talia2 = new Talia();
        Assert.assertNotEquals(talia1.toString(), talia2.toString());

        talia1.shuffleTalia();
        talia2.shuffleTalia();

        Assert.assertNotEquals(talia1.toString(), talia2.toString());
    }
}
