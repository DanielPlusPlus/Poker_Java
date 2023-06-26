package serwer_klient_test;
import org.junit.jupiter.api.*;
import serwer_klient.SocketHP;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.net.Socket;
class SocketHPTest {
    private SocketHP socketHP;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    @BeforeEach
    void setUp() {
        socketHP = new SocketHP();
        socket = mock(Socket.class);
        out = mock(PrintWriter.class);
        in = mock(BufferedReader.class);
        objectInputStream = mock(ObjectInputStream.class);
        objectOutputStream = mock(ObjectOutputStream.class);

        socketHP.socket = socket;
        socketHP.out = out;
        socketHP.in = in;
        socketHP.objectInputStream = objectInputStream;
        socketHP.objectOutputStream = objectOutputStream;
    }

    @Test
    void start() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        InputStream inputStream = mock(InputStream.class);
        when(socket.getOutputStream()).thenReturn(outputStream);
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(mock(ObjectOutputStream.class));
        when(socket.getInputStream()).thenReturn(mock(ObjectInputStream.class));

        assertNotNull(socketHP.socket);
        assertNotNull(socketHP.out);
        assertNotNull(socketHP.in);
        assertNotNull(socketHP.objectOutputStream);
        assertNotNull(socketHP.objectInputStream);

        Thread.currentThread().interrupt();
    }

    @Test
    void end() throws IOException {
        socketHP.end();

        verify(in, times(1)).close();
        verify(out, times(1)).close();
        verify(objectInputStream, times(1)).close();
        verify(objectOutputStream, times(1)).close();
        verify(socket, times(1)).close();
    }
}
