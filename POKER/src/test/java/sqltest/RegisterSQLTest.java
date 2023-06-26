package sqltest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sql.CreateSQL;
import sql.DropSQL;
import sql.RegisterSQL;

import static org.junit.Assert.assertEquals;

public class RegisterSQLTest {
    private Connection connection;

    @Before
    public void setUp() {
        new CreateSQL();
        try {
            String url = "jdbc:oracle:thin:@//localhost:1521/xe";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        new DropSQL();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterSQLSuccessful() {
        new RegisterSQL("Danny", "Zuko");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM uzytkownicy")) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(3, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterSQLFailed() {
        new RegisterSQL("Vicky", "Wiku123");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM uzytkownicy")) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(2, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
