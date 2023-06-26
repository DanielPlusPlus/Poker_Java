package konsola;
import java.util.Random;
import java.util.Scanner;

public class Gra {
    boolean playing = true;
    Rozdajacy dealer;
    public static Gracz player1;
    public static Gracz player2;
    public static boolean running = true;
    int ktoZaczyna;
    static Random random;
    static Scanner scanner;
    public static long pula = 0;
    static int siChoice;
    public Gra() {
        random = new Random();
        ktoZaczyna = random.nextInt(3)+1;
        dealer = new Rozdajacy();
        scanner = new Scanner(System.in);
        System.out.println("Podaj swoj nick graczu! \nNick: ");
        String name = scanner.nextLine();
        player1 = new Gracz(name, 1000);
        player2 = new Gracz("SI",10000);
        siChoice = random.nextInt(3)+1;
        while(playing) {
            new Przygotowanie(player1, player2, ktoZaczyna);
            new Play(dealer, player1, player2, ktoZaczyna);
            System.out.println("Czy chcesz zagrac jeszcze raz? (t/n)");
            String res = scanner.next().toLowerCase();
            if(res.equals("n")) {
                playing = false;
            }
            player1.clearHand();
            player2.clearHand();
            pula = 0;
            ktoZaczyna = random.nextInt(2);
        }
    }

    public static void showKarty() {
        System.out.println("Karty przeciwnika: "+player2);
        System.out.println("Karty gracza "+player1.nick+": "+player1);
    }

    public static void licytacjaSI(int w) {
        if(w == 1) {
            System.out.println("Przeciwnik folduje! Wygrywasz ty!");
            running = player2.fold();
        }
        else if(w == 2) {
            System.out.println("Przeciwnik calluje! Postaw stawke: ");
            int plus = scanner.nextInt();
            pula += plus;
            System.out.println("Pula wynosi: "+pula);
        }
        else {
            System.out.println("Przeciwnik podwyzsza! ");
            long stawka = random.nextLong(100);
            pula += stawka;
            System.out.println("Pula wynosi: "+pula);
        }
    }

    public static void main(String[] args) {
        new Gra();
    }
}

