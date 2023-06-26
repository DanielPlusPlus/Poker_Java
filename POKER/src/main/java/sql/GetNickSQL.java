package sql;
import java.sql.*;

public class GetNickSQL {
    String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    String username = "root";
    String password = "root";
    public static String name = "";
    PreparedStatement p = null;
    ResultSet rs = null;
    public GetNickSQL(int id) {

        String sql = "SELECT nazwa FROM uzytkownicy WHERE id_uzytkownika = '"+id+"'";
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            p = connection.prepareStatement(sql);
            rs = p.executeQuery();
            name = "";
            if(rs.next()) {
                name += rs.getString("nazwa");
            }
        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas odczytywania danych: " + e.getMessage());
        }
    }
}
