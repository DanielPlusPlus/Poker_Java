package sql;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteSQL {
    String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    String username = "root";
    String password = "root";
    public DeleteSQL(String login, String haslo){
        String delete = "DELETE FROM uzytkownicy WHERE nazwa = '"+login+"' AND haslo = '"+haslo+"'";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(delete);
            System.out.println("Poprawne usuniecie uzytkownika z bazy");
            JOptionPane.showMessageDialog(null,"Poprawne usuniecie uzytkownika z bazy");
        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas usuwania uzytkownika: " + e.getMessage());
            JOptionPane.showMessageDialog(null,"Wystąpił błąd podczas usuwania uzytkownika: " + e.getMessage());
        }
    }
}
