package sql;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class LoginSQL {
    String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    String username = "root";
    String password = "root";
    public int id;

    public LoginSQL(String login, String haslo) {
        String select = "SELECT id_uzytkownika FROM uzytkownicy WHERE nazwa = '" + login + "' AND haslo = '" + haslo + "'";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(select)) {
            if (resultSet.next()) {
                id = resultSet.getInt("id_uzytkownika");
                System.out.println("Znaleziono użytkownika");
                JOptionPane.showMessageDialog(null,"Znaleziono użytkownika");
            } else {
                id = -1;
                System.out.println("Nie znaleziono użytkownika o podanych danych.");
                JOptionPane.showMessageDialog(null,"Nie znaleziono użytkownika o podanych danych");
            }
        } catch (SQLException e) {
            id = -1;
            System.err.println("Wystąpił błąd podczas wyszukiwania użytkownika: " + e.getMessage());
            JOptionPane.showMessageDialog(null,"Wystąpił błąd podczas wyszukiwania użytkownika: " + e.getMessage());
        }
    }
}
