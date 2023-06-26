package sql;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterSQL {
    String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    String username = "root";
    String password = "root";
    public RegisterSQL(String login, String haslo){
        String insert = "INSERT INTO uzytkownicy VALUES (Uzytkownik_id.nextval, '"+login+"', '"+haslo+"',1000)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(insert);
            System.out.println("Poprawne dodanie uzytkownika do bazy");
            JOptionPane.showMessageDialog(null,"Poprawne dodanie uzytkownika do bazy");
        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas dodawania uzytkownika: " + e.getMessage());
            JOptionPane.showMessageDialog(null,"Wystąpił błąd podczas dodawania uzytkownika: " + e.getMessage());
        }
    }
}
