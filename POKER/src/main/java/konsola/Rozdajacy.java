package konsola;

public class Rozdajacy extends Talia {
    public int liczbaKart=51;
    public Rozdajacy() {
        super();
    }

    @Override
    public void newRozdanie() {
        super.newRozdanie();
        liczbaKart=51;
    }

    public void rozdaj(Gracz g1, Gracz g2) {
        for(int i=0; i<5; i++){
            g1.reka.add(talia.get(liczbaKart));
            liczbaKart--;
            g2.reka.add(talia.get(liczbaKart));
            liczbaKart--;
        }
        // funkcja rozdajaca graczom po 5 kart z talii
    }

    public void rozdajDodatkowe(Gracz g) {
        int pom=g.reka.size();
        for(int i=pom; i<5; i++) {
            g.reka.add(talia.get(liczbaKart));
            liczbaKart--;
        }
        // zmiana
        //funkcja rozdajaca dodatkowe karty od odrzuconych wczesniej od gracza
    }
    public int ktoWygrywa(Gracz g1, Gracz g2) {
        int r1 = g1.calculatePunkty();
        int r2 = g2.calculatePunkty();
        if(r1>r2)
            return 1;
        else if(r1<r2)
            return 2;
        else
            return 0;
    }
    //funkcja zwracajaca wygranego gracza
}