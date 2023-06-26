package konsola;
import java.util.Random;
import java.util.Scanner;

public class Przygotowanie {

    Scanner scanner;
    Random random = new Random();
    public Przygotowanie(Gracz g1, Gracz g2, int ktoZaczyna) {
        scanner = new Scanner(System.in);
        long suma;
        if(ktoZaczyna==1) {
            System.out.println("Zaczynasz ty! \nTwoje zetony: "+g1.zetony);
            System.out.println("Podaj stawke do puli: ");
            suma = scanner.nextLong();
            if(suma>g1.zetony || suma<0) {
                System.out.println("Podano zla sume! Zostanie ona wybrana automatycznie.");
                suma = g1.zetony/2;
                Gra.pula += suma;
                g1.zetony -= suma;
                System.out.println("Pula wynosi: "+Gra.pula);
            }
            else{
                Gra.pula += suma;
                g1.zetony -= suma;
                System.out.println("Dodano stawke do puli! Gra.pula wynosi: "+Gra.pula);
            }
            System.out.println("Przeciwnik podwaja stawke!");
            suma *= 2;
            Gra.pula += suma;
            g2.zetony -= suma;
            System.out.println("Pula wynosi: "+Gra.pula);
        }
        else {
            System.out.println("Zaczyna przeciwnik!");
            suma = random.nextLong(g1.zetony/6)+1;
            Gra.pula += suma;
            g2.zetony -= suma;
            System.out.println("Pula wynosi: "+Gra.pula);
            System.out.println("Podwajasz stawke? (t/n)");
            String choice = scanner.next().toLowerCase();
            switch (choice) {
                case "t" -> {
                    suma *= 2;
                    Gra.pula += suma;
                    g1.zetony -= suma;
                    System.out.println("Pula wynosi: "+Gra.pula);
                }
                case "n" -> {
                    System.out.println("Wychodzenie z gry.");
                    Gra.running = false;
                }
                default -> {
                    System.out.println("Podano zla odpowiedz! Automatyczne wyjscie z gry!");
                    Gra.running = false;
                }
            }
        }
    }
}
