package sql;

import java.sql.*;

public class GetInfoSQL {
    String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    String username = "root";
    String password = "root";
    String getAll = "SELECT * FROM uzytkownicy ORDER BY zetony DESC";
    PreparedStatement p = null;
    ResultSet rs = null;
    public static String result = "";
    public GetInfoSQL(){
        try (Connection connection = DriverManager.getConnection(url, username, password)){
             p = connection.prepareStatement(getAll);
             rs = p.executeQuery();
             result = "";
             while(rs.next()) {
                 result += "         "+rs.getString("nazwa")+" \t\t      "+rs.getLong("zetony")+"\n";
             }
        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas odczytywania danych: " + e.getMessage());
        }
    }
}
