package konsola;
import java.io.Serializable;

public class Karta implements Serializable{
    public String kolor;
    public String figura;
    public boolean czy_widac = true;

    public Karta(String kolor, String figura) {
        setKolor(kolor);
        setFigura(figura);
    }

    public void setKolor(String kolor) {
        this.kolor = kolor;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }

    @Override
    public String toString() {
        String res;
        if(this.czy_widac) {
            res = this.figura+this.kolor;
        }
        else {
            res = "XX";
        }
        return res;
    }

    public void obrocKarte() {
        this.czy_widac = !this.czy_widac;
    }

}
