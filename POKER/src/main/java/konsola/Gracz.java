package konsola;
import java.io.Serializable;

public class Gracz extends Reka implements Serializable{
    public long zetony;
    public String nick;

    public Gracz(String nick, long zetony) {
        this.nick = nick;
        this.zetony = zetony;
    }

    public void clearHand() {
        reka.clear();
    }

    //na razie te funkcje nizej byle jak - mozesz zmienic Wika jak chcesz wszystko :)
    public boolean fold() {
        reka.clear();
        return false;
    }

    public long call(long stawka) {
        //System.out.println("Przeciwnik wnosi do puli pewna sume!");
        stawka*=2;
        return stawka;
        //funkcja sprawdzajaca karty w rundzie
    }

    public long raise(long stawka, long ilePodbic) {
        stawka+=ilePodbic;
        return stawka;
        //funkcja podbijajaca stawke
    }
    //brakuje klasy Gra(?) w ktorej bylaby przechowywana aktualna stawka - trzeba napisac
}