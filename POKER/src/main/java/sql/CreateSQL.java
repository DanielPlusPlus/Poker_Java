package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateSQL {
    String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    String username = "root";
    String password = "root";
    String create = "CREATE TABLE uzytkownicy (" +
            "id_uzytkownika NUMBER CONSTRAINT id_uzytkownika_pk PRIMARY KEY, " +
            "nazwa VARCHAR2(30) CONSTRAINT nazwa_uq UNIQUE, " +
            "haslo VARCHAR2(30), " +
            "zetony NUMBER)";
    String sequence = "CREATE SEQUENCE Uzytkownik_id " +
            "MINVALUE 0 " +
            "MAXVALUE 99999 " +
            "START WITH 1 " +
            "INCREMENT BY 1";
    String insert1 = "INSERT INTO uzytkownicy VALUES (Uzytkownik_id.nextval, 'Vicky' , 'Wiku123', 1000)";
    String insert2 = "INSERT INTO uzytkownicy VALUES (Uzytkownik_id.nextval, 'Ala' , 'Kot', 2000)";
    public CreateSQL(){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
                statement.executeUpdate(create);
                statement.executeUpdate(sequence);
                statement.executeUpdate(insert1);
                statement.executeUpdate(insert2);
                System.out.println("Tabela 'uzytkownicy' została utworzona w bazie danych Oracle.");
        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas tworzenia tabeli: " + e.getMessage());
        }
    }
}
