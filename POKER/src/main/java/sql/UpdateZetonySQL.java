package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateZetonySQL {
    String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    String username = "root";
    String password = "root";
    public UpdateZetonySQL(String login, long ile) {
        String update = "UPDATE uzytkownicy SET zetony = zetony + '"+ile+"' WHERE nazwa = '"+login+"'";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(update);
            System.out.println("Poprawna aktualizacja danych.");
        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas aktualizacji uzytkownika: " + e.getMessage());
        }
    }
}
