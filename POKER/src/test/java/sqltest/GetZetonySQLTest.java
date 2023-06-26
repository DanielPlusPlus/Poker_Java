package sqltest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sql.CreateSQL;
import sql.DropSQL;
import sql.GetZetonySQL;

import static org.junit.Assert.assertEquals;

public class GetZetonySQLTest {
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
    public void testGetZetonySQL() {
        new GetZetonySQL(1);
        assertEquals(1000, GetZetonySQL.zetony);
    }
}
