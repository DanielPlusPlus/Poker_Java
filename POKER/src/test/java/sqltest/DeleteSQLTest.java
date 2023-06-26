package sqltest;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeleteSQLTest {
    private Connection connection;

    @Before
    public void setUp() {
        String url = "jdbc:oracle:thin:@//localhost:1521/xe";
        String username = "root";
        String password = "root";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE uzytkownicy_test (" +
                    "id_uzytkownika NUMBER CONSTRAINT id_uzytkownika_test_pk PRIMARY KEY, " +
                    "nazwa VARCHAR2(30) CONSTRAINT nazwa_uq UNIQUE, " +
                    "haslo VARCHAR2(30), " +
                    "zetony NUMBER)");

            statement.executeUpdate("INSERT INTO uzytkownicy_test VALUES (1, 'Vicky' , 'Wiku123', 1000)");
            statement.executeUpdate("INSERT INTO uzytkownicy_test VALUES (2, 'Ala' , 'Kot', 2000)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String delete = "DELETE FROM uzytkownicy_test WHERE nazwa = 'Vicky' AND haslo = 'Wiku123'";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(delete);
            System.out.println("Poprawne usuniecie uzytkownika z bazy");
            JOptionPane.showMessageDialog(null, "Poprawne usuniecie uzytkownika z bazy");
        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas usuwania uzytkownika: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas usuwania uzytkownika: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE uzytkownicy_test");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteUser() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM uzytkownicy_test WHERE nazwa = 'Vicky'");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(0, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}