package serwer_klient_test;
import org.junit.jupiter.api.Test;
import serwer_klient.KlientWatek1;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

class KlientWatek1Test {

    @Test
    void run() throws IOException {
        Socket clientSocket = mock(Socket.class);
        mock(PrintWriter.class);
        BufferedReader in = mock(BufferedReader.class);
        mock(ObjectOutputStream.class);
        mock(ObjectInputStream.class);
        when(in.readLine()).thenReturn("1");
        when(clientSocket.getInputStream()).thenReturn(mock(InputStream.class));
        when(clientSocket.getOutputStream()).thenReturn(mock(OutputStream.class));
        KlientWatek1 klientWatek1 = new KlientWatek1(clientSocket);
        klientWatek1.start();
        klientWatek1.interrupt();
    }
}