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

public class CreateSQLTest {
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        String url = "jdbc:oracle:thin:@//localhost:1521/xe";
        String username = "root";
        String password = "root";

        connection = DriverManager.getConnection(url, username, password);
    }

    @After
    public void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testTableCreation() {
        new CreateSQL();
        new DropSQL();
    }

    @Test
    public void testInsertData() {
        new CreateSQL();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM uzytkownicy");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(2, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new DropSQL();
    }
}