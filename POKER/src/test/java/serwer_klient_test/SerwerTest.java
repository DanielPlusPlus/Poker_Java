package serwer_klient_test;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SerwerTest {

    @Mock
    private ServerSocket serverSocket;

    @Mock
    private Socket clientSocket1;

    @Mock
    private Socket clientSocket2;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(serverSocket.accept()).thenReturn(clientSocket1).thenReturn(clientSocket2);
    }

    @Test
    public void testSerwer() throws IOException {

        serverSocket = Mockito.mock(ServerSocket.class);

        clientSocket1 = Mockito.mock(Socket.class);
        clientSocket2 = Mockito.mock(Socket.class);
        clientSocket1.connect(serverSocket.getLocalSocketAddress());
        clientSocket2.connect(serverSocket.getLocalSocketAddress());

        Assert.assertFalse(clientSocket1.isConnected());
        Assert.assertFalse(clientSocket2.isConnected());

        clientSocket1.close();
        clientSocket2.close();
        serverSocket.close();

        Thread.currentThread().interrupt();
    }
}
