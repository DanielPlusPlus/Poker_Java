package serwer_klient;
import konsola.Gracz;
import konsola.Rozdajacy;

public class Multiplayer {
    public static Gracz gracz1, gracz2;
    public static int iloscOsob = 0;
    public static long stawka = 0;
    public static Rozdajacy dealer;
    public static int wybor1 = 0, wybor2 = 0, wynik = 0;

    public static void refresh(Gracz g1, Gracz g2, Rozdajacy r) {
        g1.clearHand();
        g2.clearHand();
        stawka = 0;
        r.newRozdanie();
    }

}