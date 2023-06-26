package serwer_klient_test;
import konsola.Gracz;
import konsola.Rozdajacy;
import org.junit.jupiter.api.Test;
import serwer_klient.Multiplayer;
import serwer_klient.KlientWatek2;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KlientWatek2Test {

    @Test
    void runTest() throws IOException{
        Socket clientSocket = mock(Socket.class);
        mock(PrintWriter.class);
        BufferedReader in = mock(BufferedReader.class);
        mock(ObjectOutputStream.class);
        mock(ObjectInputStream.class);
        Gracz gracz1 = mock(Gracz.class);
        Gracz gracz2 = mock(Gracz.class);
        Rozdajacy rozdajacy = mock(Rozdajacy.class);
        Multiplayer.gracz1 = gracz1;
        Multiplayer.gracz2 = gracz2;
        Multiplayer.dealer = rozdajacy;
        Multiplayer.stawka = 0;
        Multiplayer.wybor1 = 0;
        Multiplayer.wybor2 = 0;
        Multiplayer.wynik = 0;
        when(in.readLine()).thenReturn("2");
        when(clientSocket.getInputStream()).thenReturn(mock(InputStream.class));
        when(clientSocket.getOutputStream()).thenReturn(mock(OutputStream.class));
        KlientWatek2 klientWatek2 = new KlientWatek2(clientSocket);
        klientWatek2.start();
        assertEquals(0, Multiplayer.stawka);
        assertEquals(0, Multiplayer.wybor1);
        assertEquals(0, Multiplayer.wybor2);
        assertEquals(0, Multiplayer.wynik);
        klientWatek2.interrupt();
    }
}
