package konsola;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Reka implements Serializable {
    public ArrayList<Karta> reka = new ArrayList<>();
    int ilePunktow=0;
    final String[][] ukladyKart = {{"poker królewski","10000000"},{ "poker","9000000"},{ "kareta","8000000"},{ "full","7000000"},
            {"kolor","6000000"}, {"strit","5000000"}, {"trójka","4000000"}, {"dwie pary","3000000"},
            {"para","2000000"},{"wysoka karta","1000000"}};
    //10 milonow, bo dziesiatki - to najwyzsza karta, max 13
    //setki - najwyzsza karta w drugiej parze - max 1300
    //tysiace - najwyzsza karta z pierwszej pary - max 130000
    public Reka() {
        //karty daje rozdajacy
    }

    public void giveKarty(boolean[] tab) {
        ArrayList<Karta> pom = new ArrayList<>();
        for(int i=0; i<5; i++){
            if(tab[i]) {
                pom.add(reka.get(i));
            }
        }
        reka.removeAll(pom);
        //poprawka
    }

    public int calculatePunkty() {
        int[] tabFigura=new int[5];
        String[] tabKolor=new String[5];
        for(int i=0; i<5; i++){
            if(reka.get(i).figura.equals("J"))
                tabFigura[i]=11;
            else if(reka.get(i).figura.equals("Q"))
                tabFigura[i]=12;
            else if(reka.get(i).figura.equals("K"))
                tabFigura[i]=13;
            else if(reka.get(i).figura.equals("A"))
                tabFigura[i]=14;
            else
                tabFigura[i]=Integer.parseInt(reka.get(i).figura);
            tabKolor[i]=reka.get(i).kolor;
        }
        Arrays.sort(tabFigura);
        if((tabKolor[0].equals(tabKolor[1]))&&(tabKolor[0].equals(tabKolor[2]))&&(tabKolor[0].equals(tabKolor[3]))&&(tabKolor[0].equals(tabKolor[4]))){
            //Karty sa jednakowego koloru
            if((tabFigura[0]==10)&&(tabFigura[1]==11)&&(tabFigura[2]==12)&&(tabFigura[3]==13)&&(tabFigura[4]==14)){
                System.out.println("Uklad to: "+ukladyKart[0][0]);
                ilePunktow=Integer.parseInt(ukladyKart[0][1]);
            }//poker krolewski
            else if((tabFigura[4]==(tabFigura[3]+1))&&(tabFigura[4]==(tabFigura[2]+2))&&(tabFigura[4]==(tabFigura[1]+3))&&(tabFigura[4]==(tabFigura[0]+4))){
                System.out.println("Uklad to: "+ukladyKart[1][0]);
                ilePunktow=Integer.parseInt(ukladyKart[1][1]);
                ilePunktow+=tabFigura[4];
            }//poker
            else{
                System.out.println("Uklad to: "+ukladyKart[4][0]);
                ilePunktow=Integer.parseInt(ukladyKart[4][1]);
                ilePunktow+=tabFigura[4];
            }//kolor
        }
        else{
            if((tabFigura[1]==tabFigura[0])&&(tabFigura[2]==tabFigura[0])&&(tabFigura[3]==tabFigura[0])){
                System.out.println("Uklad to: "+ukladyKart[2][0]);
                ilePunktow=Integer.parseInt(ukladyKart[2][1]);
                ilePunktow+=tabFigura[3]*10000;
            }//kareta
            else if((tabFigura[2]==tabFigura[1])&&(tabFigura[3]==tabFigura[1])&&(tabFigura[4]==tabFigura[1])){
                System.out.println("Uklad to: "+ukladyKart[2][0]);
                ilePunktow=Integer.parseInt(ukladyKart[2][1]);
                ilePunktow+=tabFigura[4]*10000;
            }//kareta
            else if((tabFigura[1]==tabFigura[0])&&(tabFigura[2]==tabFigura[0])&&(tabFigura[3]==tabFigura[4])){
                System.out.println("Uklad to: "+ukladyKart[4][0]);
                ilePunktow=Integer.parseInt(ukladyKart[4][1]);
                ilePunktow+=tabFigura[2]*10000;
            }//full
            else if((tabFigura[3]==tabFigura[2])&&(tabFigura[4]==tabFigura[2])&&(tabFigura[0]==tabFigura[1])){
                System.out.println("Uklad to: "+ukladyKart[4][0]);
                ilePunktow=Integer.parseInt(ukladyKart[4][1]);
                ilePunktow+=tabFigura[4]*10000;
            }//full
            else if((tabFigura[4]==(tabFigura[3]+1))&&(tabFigura[4]==(tabFigura[2]+2))&&(tabFigura[4]==(tabFigura[1]+3))&&(tabFigura[4]==(tabFigura[0]+4))){
                System.out.println("Uklad to: "+ukladyKart[5][0]);
                ilePunktow=Integer.parseInt(ukladyKart[5][1]); //jeszcze brakuje tu rozwazenia przypadku w ktorym As jest najnizsza karta w stricie, ale mozna to chyba powinac
                ilePunktow+=tabFigura[4];
            }//strit
            else if((tabFigura[1]==tabFigura[0])&&(tabFigura[2]==tabFigura[0])){
                System.out.println("Uklad to: "+ukladyKart[6][0]);
                ilePunktow=Integer.parseInt(ukladyKart[6][1]);
                ilePunktow+=tabFigura[0]*10000;
            }//trojka
            else if((tabFigura[2]==tabFigura[1])&&(tabFigura[3]==tabFigura[1])){
                System.out.println("Uklad to: "+ukladyKart[6][0]);
                ilePunktow=Integer.parseInt(ukladyKart[6][1]);
                ilePunktow+=tabFigura[1]*10000;
            }//trojka
            else if((tabFigura[3]==tabFigura[2])&&(tabFigura[4]==tabFigura[2])){
                System.out.println("Uklad to: "+ukladyKart[6][0]);
                ilePunktow=Integer.parseInt(ukladyKart[6][1]);
                ilePunktow+=tabFigura[2]*10000;
            }//trojka
            else if((tabFigura[0]==tabFigura[1])&&(tabFigura[2]==tabFigura[3])){
                System.out.println("Uklad to: " +ukladyKart[7][0]);
                ilePunktow=Integer.parseInt(ukladyKart[7][1]);
                ilePunktow+=tabFigura[3]*10000;
                ilePunktow+=tabFigura[0]*100;
                ilePunktow+=tabFigura[4];
            }//dwie pary
            else if((tabFigura[0]==tabFigura[1])&&(tabFigura[3]==tabFigura[4])){
                System.out.println("Uklad to: " +ukladyKart[7][0]);
                ilePunktow=Integer.parseInt(ukladyKart[7][1]);
                ilePunktow+=tabFigura[4]*10000;
                ilePunktow+=tabFigura[0]*100;
                ilePunktow+=tabFigura[2];
            }//dwie pary
            else if((tabFigura[1]==tabFigura[2])&&(tabFigura[3]==tabFigura[4])){
                System.out.println("Uklad to: " +ukladyKart[7][0]);
                ilePunktow=Integer.parseInt(ukladyKart[7][1]);
                ilePunktow+=tabFigura[3]*10000;
                ilePunktow+=tabFigura[1]*100;
                ilePunktow+=tabFigura[0];
            }//dwie pary
            else if(tabFigura[0]==tabFigura[1]){
                System.out.println("Uklad to: " +ukladyKart[8][0]);
                ilePunktow=Integer.parseInt(ukladyKart[8][1]);
                ilePunktow+=tabFigura[0]*10000;
                ilePunktow+=tabFigura[4];
            }//para
            else if(tabFigura[1]==tabFigura[2]){
                System.out.println("Uklad to: " +ukladyKart[8][0]);
                ilePunktow=Integer.parseInt(ukladyKart[8][1]);
                ilePunktow+=tabFigura[1]*10000;
                ilePunktow+=tabFigura[4];
            }//para
            else if(tabFigura[2]==tabFigura[3]){
                System.out.println("Uklad to: " +ukladyKart[8][0]);
                ilePunktow=Integer.parseInt(ukladyKart[8][1]);
                ilePunktow+=tabFigura[2]*10000;
                ilePunktow+=tabFigura[4];
            }//para
            else if(tabFigura[3]==tabFigura[4]){
                System.out.println("Uklad to: " +ukladyKart[8][0]);
                ilePunktow=Integer.parseInt(ukladyKart[8][1]);
                ilePunktow+=tabFigura[3]*10000;
                ilePunktow+=tabFigura[2];
            }//para
            else {
                System.out.println("Uklad to: " + ukladyKart[9][0]);
                ilePunktow = Integer.parseInt(ukladyKart[9][1]);
                ilePunktow += tabFigura[4];
            }//wysoka karta
        }
        return ilePunktow;
    }

    public void flipAll() {
        for(Karta k : reka) {
            k.obrocKarte();
        }
    }

    public String toString() {
        String res = "";
        res+=reka.get(0);
        for(int i=1; i<reka.size(); i++)
            res+=" "+reka.get(i);
        return res;
    }
}