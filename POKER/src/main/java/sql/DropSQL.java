package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropSQL {
    String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    String username = "root";
    String password = "root";
    String drop = "DROP TABLE uzytkownicy CASCADE CONSTRAINTS";
    String sequence = "DROP SEQUENCE Uzytkownik_id";
    public DropSQL(){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(drop);
            statement.executeUpdate(sequence);
            System.out.println("Tabela 'uzytkownicy' została usunieta w bazie danych Oracle.");
        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas usuwania tabeli: " + e.getMessage());
        }
    }
}
