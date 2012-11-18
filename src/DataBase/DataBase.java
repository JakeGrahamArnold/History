package DataBase;

import java.sql.*;

public class DataBase {

    Connection con;
    Statement st;
    ResultSet rs;

    /*
     * Constructor to connect to the Database
     */
    public DataBase() {

        connect();

    }

    /*
     * Connect to the Database
     */
    public void connect() {

        try {

            String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
            Class.forName(driver);

            String db = "jdbc:odbc:history";

            con = DriverManager.getConnection(db);

            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select * from History";
            rs = st.executeQuery(sql);

            System.out.println("connected");


        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public static void main(String[] args) {

        new DataBase();
        new EditDataBase();


        //start on year 1 / month 1 / day 1
        new World(1, 1, 1);

    }
}