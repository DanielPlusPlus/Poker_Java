package konsola;
import java.util.Random;
import java.util.Scanner;

public class Licytacja {
    Scanner scanner;
    Random random;
    public int ch;
    public Licytacja(Gracz g, Gracz g2) {
        scanner = new Scanner(System.in);
        random = new Random();
        ch = random.nextInt(2);

        System.out.println("Nastepuje LICYTACJA gracza: "+g.nick+" ! \nWybierz co chcesz zrobic: ");
        System.out.println("1 - fold");
        System.out.println("2 - call");
        System.out.println("3 - raise");
        int wybor = scanner.nextInt();
        switch (wybor) {
            case 1 -> foldLicytacja(g);
            case 2 -> callLicytacja(g2);
            case 3 -> raiseLicytacja(g);
        }

    }

    public void foldLicytacja(Gracz g) {
        Gra.running = g.fold();
    }

    public void raiseLicytacja(Gracz g) {
        long ileWbil = Gra.pula / 3;
        ileWbil *= 2;
        System.out.print("Podaj stawke do puli wieksza niz " + ileWbil + " :");
        long raiseStawka = scanner.nextLong();
        System.out.println();
        g.zetony -= raiseStawka;
        if (raiseStawka < ileWbil) {
            System.out.println("Stawka jest za mala! Automatyczna przegrana!");
            Gra.running = g.fold();
        } else {
            Gra.pula = g.raise(Gra.pula, raiseStawka);
            System.out.println("Pula wynosi: " + Gra.pula);
        }
    }

    public void callLicytacja(Gracz g) {
        System.out.println("Przeciwnik musi postawić stawkę dwa razy większą do podbicia.");
        System.out.println("Obecna pula: "+Gra.pula);
        if(ch == 0) {
            System.out.println("Przeciwnik podwaja!");
            Gra.pula = g.call(Gra.pula);
            g.zetony -= Gra.pula;
        }
        else {
            System.out.println("Przeciwnik pasuje!");
            Gra.running = g.fold();
        }
    }


}
