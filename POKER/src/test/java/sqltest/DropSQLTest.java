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

import static org.junit.Assert.assertEquals;

public class DropSQLTest {
    private Connection connection;

    @Before
    public void setUp() {
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
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDropTable() {
        new CreateSQL();
        new DropSQL();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM user_tables WHERE table_name = 'UZYTKOWNICY'");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(0, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDropSequence() {
        new CreateSQL();
        new DropSQL();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM user_sequences WHERE sequence_name = 'UZYTKOWNIK_ID'");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(0, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}