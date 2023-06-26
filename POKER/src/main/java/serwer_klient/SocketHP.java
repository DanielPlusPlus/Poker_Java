package serwer_klient;
import konsola.Gracz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SocketHP {
    public java.net.Socket socket;
    public PrintWriter out;
    public BufferedReader in;
    public ObjectInputStream objectInputStream;
    public ObjectOutputStream objectOutputStream;
    public Gracz gracz;
    public Gracz przeciwnik;
    public long stawka = 0;

    public void start(){
        try {
            socket = new java.net.Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void end(){
        try {
            in.close();
            out.close();
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
