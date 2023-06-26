package serwer_klient;
import konsola.Gracz;

import java.io.*;
import java.net.Socket;

public class KlientWatek1 extends Thread {
    private final Socket clientSocket;
    public KlientWatek1(Socket clientSocket) {
        this.clientSocket = clientSocket;

    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            out.println("1");

            Multiplayer.gracz1 = (Gracz) objectInputStream.readObject();

            synchronized (Multiplayer.class) {
                Multiplayer.iloscOsob++;
                if (Multiplayer.iloscOsob == 1) {
                    try {
                        Multiplayer.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            objectOutputStream.writeObject(Multiplayer.gracz2);

            //odbior stawki
            Multiplayer.stawka=Long.parseLong(in.readLine());
            synchronized (Multiplayer.class) {
                Multiplayer.class.notify();
            }

            //odbior podwojonej stawki
            synchronized (Multiplayer.class) {
                try {
                    Multiplayer.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            out.println(""+Multiplayer.stawka);

            if(Multiplayer.stawka != 0) {

                //rozdanie kart
                objectOutputStream.writeObject(Multiplayer.gracz1);

                //odbior wyboru
                Multiplayer.wybor1 = Integer.parseInt(in.readLine());
                synchronized (Multiplayer.class) {
                    Multiplayer.class.notify();
                }

                if (Multiplayer.wybor1 != 0) {

                    if (Multiplayer.wybor1 == 1) {
                        //raise
                        Multiplayer.stawka = Long.parseLong(in.readLine());
                        synchronized (Multiplayer.class) {
                            Multiplayer.class.notify();
                        }
                    }

                    //oddanie kart
                    Multiplayer.gracz1 = (Gracz) objectInputStream.readObject();
                    Multiplayer.dealer.rozdajDodatkowe(Multiplayer.gracz1);
                    objectOutputStream.writeObject(Multiplayer.gracz1);

                    //odbior wyboru
                    synchronized (Multiplayer.class) {
                        try {
                            Multiplayer.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    out.println("" + Multiplayer.wybor2);

                    if (Multiplayer.wybor2 != 0) {

                        if (Multiplayer.wybor2 == 1) {
                            //raise
                            synchronized (Multiplayer.class) {
                                try {
                                    Multiplayer.class.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            out.println("" + Multiplayer.stawka);
                        }

                        //odbior wyniku
                        Multiplayer.gracz1 = (Gracz) objectInputStream.readObject();
                        Multiplayer.iloscOsob++;

                        synchronized (Multiplayer.class) {
                            Multiplayer.class.notify();
                        }

                        //obliczenie wyniku
                        synchronized (Multiplayer.class) {
                            try {
                                Multiplayer.class.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        out.println("" + Multiplayer.wynik);

                        //odebranie przeciwnika
                        objectOutputStream.writeObject(Multiplayer.gracz2);
                    }
                }
            }
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
