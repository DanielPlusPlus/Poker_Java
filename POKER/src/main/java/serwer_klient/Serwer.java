package serwer_klient;
import sql.CreateSQL;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//Tu obowiazuje zasada kto pierwszy ten lepszy, zaczyna ten ktory jako pierwszy sie podlaczy do serwera

public class Serwer {
    public static void main(String[] args){
        new CreateSQL();
        try(ServerSocket serverSocket = new ServerSocket(12345)){
            System.out.println("Serwer nasłuchuje na porcie 12345...");

            while(true) {
                Socket clientSocket1 = serverSocket.accept();
                System.out.println("Połączono z klientem nr. 1 o adresie: " + clientSocket1.getInetAddress());

                Thread KlientWatek1 = new KlientWatek1(clientSocket1);
                KlientWatek1.start();

                Socket clientSocket2 = serverSocket.accept();
                System.out.println("Połączono z klientem nr. 2 o adresie: " + clientSocket2.getInetAddress());

                Thread KlientWatek2 = new KlientWatek2(clientSocket2);
                KlientWatek2.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //new DropSQL();
    }
}
