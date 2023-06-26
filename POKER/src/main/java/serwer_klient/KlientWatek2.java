package serwer_klient;
import konsola.Gracz;
import konsola.Rozdajacy;

import java.io.*;
import java.net.Socket;

public class KlientWatek2 extends Thread {
    private final Socket clientSocket;
    public KlientWatek2(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            out.println("2");

            Multiplayer.gracz2 = (Gracz) objectInputStream.readObject();
            synchronized (Multiplayer.class) {
                Multiplayer.iloscOsob++;
                if (Multiplayer.iloscOsob == 2) {
                    Multiplayer.class.notify();
                }
            }

            objectOutputStream.writeObject(Multiplayer.gracz1);

            Multiplayer.dealer = new Rozdajacy();
            Multiplayer.refresh(Multiplayer.gracz1, Multiplayer.gracz2, Multiplayer.dealer);
            Multiplayer.dealer.rozdaj(Multiplayer.gracz1, Multiplayer.gracz2);

            //odbior stawki
            synchronized (Multiplayer.class) {
                    try {
                        Multiplayer.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
            out.println(""+Multiplayer.stawka);

            //odbior podwojonej stawki
            Multiplayer.stawka = Long.parseLong(in.readLine());
            synchronized (Multiplayer.class) {
                Multiplayer.class.notify();
            }
            if(Multiplayer.stawka != 0) {

                //rozdanie kart
                objectOutputStream.writeObject(Multiplayer.gracz2);

                //odbior wyboru
                synchronized (Multiplayer.class) {
                    try {
                        Multiplayer.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                out.println("" + Multiplayer.wybor1);

                if (Multiplayer.wybor1 != 0) {

                    if (Multiplayer.wybor1 == 1) {
                        synchronized (Multiplayer.class) {
                            try {
                                Multiplayer.class.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        out.println("" + Multiplayer.stawka);
                    }

                    //oddanie kart
                    Multiplayer.gracz2 = (Gracz) objectInputStream.readObject();
                    Multiplayer.dealer.rozdajDodatkowe(Multiplayer.gracz2);
                    objectOutputStream.writeObject(Multiplayer.gracz2);

                    //odbior wyboru
                    Multiplayer.wybor2 = Integer.parseInt(in.readLine());
                    synchronized (Multiplayer.class) {
                        Multiplayer.class.notify();
                    }

                    if (Multiplayer.wybor2 != 0) {

                        if (Multiplayer.wybor2 == 1) {
                            //raise
                            Multiplayer.stawka = Long.parseLong(in.readLine());
                            synchronized (Multiplayer.class) {
                                Multiplayer.class.notify();
                            }
                        }

                        //odbior wyniku
                        Multiplayer.gracz2 = (Gracz) objectInputStream.readObject();
                        if (Multiplayer.iloscOsob == 2)
                            synchronized (Multiplayer.class) {
                                try {
                                    Multiplayer.class.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        //obliczenie wyniku
                        Multiplayer.wynik = Multiplayer.dealer.ktoWygrywa(Multiplayer.gracz1, Multiplayer.gracz2);
                        synchronized (Multiplayer.class) {
                            Multiplayer.class.notify();
                        }
                        out.println("" + Multiplayer.wynik);

                        //odebranie przeciwnika
                        objectOutputStream.writeObject(Multiplayer.gracz1);
                    }
                }
            }
            Multiplayer.iloscOsob = 0;
            Multiplayer.stawka = 0;
            Multiplayer.wybor1 = 0;
            Multiplayer.wybor2 = 0;
            Multiplayer.wynik = 0;
            in.close();
            out.close();
            objectInputStream.close();
            objectOutputStream.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
