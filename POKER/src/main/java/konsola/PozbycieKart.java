package konsola;
import java.util.Scanner;

public class PozbycieKart {
    Scanner scanner;
    public PozbycieKart(Gracz g) {
        scanner = new Scanner(System.in);
        boolean[] tab=new boolean[5];
        int liczbaWymian=0;
        System.out.println("Czy chcesz pozbyc sie kart? (t/n)");
        String odp = scanner.next();
        if(odp.equals("t")) {
            System.out.println("Twoje karty po kolei: ");
            for(int i=0; i<5; i++) {
                System.out.println("Czy chcesz wymienic ta karte: "+g.reka.get(i)+"? (t/n)");
                odp=scanner.next().toLowerCase();
                if(odp.equals("t")){
                    tab[i]=true;
                    liczbaWymian++;
                }
                if(liczbaWymian==4)
                    break;
            }
            g.giveKarty(tab);
        }
        else if(odp.equals("n")) {
            System.out.println("Pozostawiono reka w stanie poczatkowym.");
        }
        else {
            System.out.println("Podano zla odpowiedz");
            Gra.running = false;
        }
    }
}

