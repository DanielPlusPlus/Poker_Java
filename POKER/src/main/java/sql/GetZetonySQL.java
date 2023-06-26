package sql;
import java.sql.*;

public class GetZetonySQL {
    String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    String username = "root";
    String password = "root";
    public static long zetony = 0;
    PreparedStatement p = null;
    ResultSet rs = null;
    public GetZetonySQL(int id) {

        String sql = "SELECT zetony FROM uzytkownicy WHERE id_uzytkownika = '"+id+"'";
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            p = connection.prepareStatement(sql);
            rs = p.executeQuery();
            zetony = 0;
            if(rs.next()) {
                zetony += rs.getLong("zetony");
            }
        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas odczytywania danych: " + e.getMessage());
        }
    }
}
