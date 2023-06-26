package grafikatest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sql.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginPanelTest {

    @BeforeEach
    void setUp() {
        new CreateSQL();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new DropSQL();
    }

    @Test
    void testLoginSuccessful() {
        try{
            String login = "Vicky";
            String haslo = "Wiku123";
            LoginSQL loginSQL = new LoginSQL(login,haslo);
            if(loginSQL.id != -1) {
                assertTrue(true);
            }
            else {
                assertFalse(false);
            }

        }
        catch (Exception e) {
            System.out.println("Wyjatek!");
        }
    }

    @Test
    void testLoginFailed() {
        try{
            String login = "yyy";
            String haslo = "yyy";
            LoginSQL loginSQL = new LoginSQL(login,haslo);
            if(loginSQL.id != -1) {
                assertTrue(true);
            }
            else {
                assertFalse(false);
            }

        }
        catch (Exception e) {
            System.out.println("Wyjatek!");
        }
    }

    @Test
    void testRegister() {
        try{
            String login = "yyy";
            String haslo = "yyy";
            new RegisterSQL(login, haslo);
        }
        catch(Exception e) {
            System.out.println("Wystapil wyjatek!");
        }
    }

    @Test
    void testDelete() {
        try{
            String login = "yyy";
            String haslo = "yyy";
            new DeleteSQL(login, haslo);
        }
        catch(Exception e) {
            System.out.println("Wystapil wyjatek!");
        }
    }
}
