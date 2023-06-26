package konsola;
import java.util.Scanner;

public class Play {
    Scanner scanner;
    public Play(Rozdajacy dealer, Gracz player1, Gracz player2, int ktoZaczyna) {
        scanner = new Scanner(System.in);
        dealer.rozdaj(player1,player2);
        player2.flipAll();
        Gra.showKarty();

        if(Gra.running) {
            if(ktoZaczyna == 1){
                new Licytacja(player1, player2);
                new PozbycieKart(player1);
                Gra.licytacjaSI(Gra.siChoice);
                dealer.rozdajDodatkowe(player1);
                dealer.rozdajDodatkowe(player2);
                player2.flipAll();
                Gra.showKarty();
                int wynik = dealer.ktoWygrywa(player1,player2);
                if(wynik == 1){
                    System.out.println("Wygrywasz!");
                    player1.zetony += Gra.pula;
                }
                else if(wynik == 2) {
                    System.out.println("Wygrywa przeciwnik!");
                    player2.zetony += Gra.pula;
                }
                else if(wynik == 0) {
                    System.out.println("Gra zakonczona remisem! Kazdy gracz otrzymuje polowe puli!");
                    player2.zetony += Gra.pula/2;
                    player1.zetony += Gra.pula/2;
                }
            }
            else {
                Gra.licytacjaSI(Gra.siChoice);
                new PozbycieKart(player1);
                new Licytacja(player1,player2);
                dealer.rozdajDodatkowe(player1);
                dealer.rozdajDodatkowe(player2);
                player2.flipAll();
                Gra.showKarty();
                int wynik = dealer.ktoWygrywa(player1,player2);
                if(wynik == 1){
                    System.out.println("Wygrywasz!");
                    player1.zetony += Gra.pula;
                }
                else if(wynik == 2) {
                    System.out.println("Wygrywa przeciwnik!");
                    player2.zetony += Gra.pula;
                }
                else if(wynik == 0) {
                    System.out.println("Gra zakonczona remisem! Kazdy gracz otrzymuje polowe puli!");
                    player2.zetony += Gra.pula/2;
                    player1.zetony += Gra.pula/2;
                }
            }
            System.out.println("Gra zakonczona!!!");

        }
    }
}
