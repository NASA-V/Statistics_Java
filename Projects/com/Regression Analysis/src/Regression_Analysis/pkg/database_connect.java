package Regression_Analysis.pkg;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class database_connect {
    public static final String Connection_String = "jdbc:sqlite:C:/Users/Diego Angulo/Documents/Java Projects/Personal Projects/Database.db";

    private Connection connect() {
        Connection conn = null;
        try {
            DriverManager.registerDriver(new JDBC());
            conn = DriverManager.getConnection(Connection_String);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void insert(double _X, double _Y) {
        String sql = "INSERT INTO All_Series(_X,_Y) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String x, y;

            pstmt.setDouble(1, _X);
            pstmt.setDouble(2, _Y);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        database_connect app = new database_connect();
        app.insert(12, 34);
        app.insert(12, 34);
        app.insert(12, 34);
        app.insert(12, 34);
        app.insert(12, 34);


//            JOptionPane.showMessageDialog(null, "CONNECTION HAS BEEN ESTABLISHED");
//            statement.execute("Create table   All_Series(   _id INT default 1,  _X  DOUBLE,   _Y  DOUBLE) ");
//            statement.execute("Create table   Datalist ( Dataset_Name text not null, 'Group Name' INTEGER primary key autoincrement)");
//            statement.execute("Create table  Series_Information ( _id   INTEGER default 1   primary key autoincrement, Column1_Name INTEGER, Column2_Name INTEGER, Series_Name  text,'Group Name' INTEGER)");

//            DatabaseMetaData dmd = conn.getMetaData();
           /* System.out.println(dmd.getDatabaseProductName());
            System.out.println(dmd.getDatabaseProductVersion());
            System.out.println(dmd.getDriverName());
            System.out.println(dmd.getDriverVersion());
*/
           /* ResultSet resultSet = dmd.getTables(null, null, null, new String[]{"TABLE"});
            while(resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                String remarks = resultSet.getString("REMARKS");

                System.out.println(tableName);
                System.out.println(remarks);
            }
*/


//            statement.execute("INSERT INTO main.All_Series values ()")
//            String query = "insert into (X) values(?)";

//            PreparedStatement statement1 = conn.prepareStatement(query);

//            statement1.setString(1, x);
//            statement1.execute();

    }
}
