package konsola;
import java.util.ArrayList;
import java.util.Collections;

public class Talia{
    final String[] kolory = {"h", "d", "c", "s"};
    final String[] figury = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    public ArrayList<Karta> talia = new ArrayList<>();

    public Talia() {
        initTalia();
        shuffleTalia();
    }

    public void newRozdanie() {
        talia.clear();
        initTalia();
        shuffleTalia();
    }

    void initTalia() {
        for(int i=0; i<4; i++)
            for(int j=0; j<13; j++){
                talia.add(new Karta(kolory[i], figury[j]));
            }
    }

    public void shuffleTalia() {
        Collections.shuffle(talia);
    }

    @Override
    public String toString() {
        String res;
        res=""+talia.get(0);
        for(int i=1; i<52; i++)
            res=res+" "+talia.get(i);
        return res;
    }
}
