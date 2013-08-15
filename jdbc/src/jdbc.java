import java.io.*;
import java.sql.*;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class jdbc {
    static Connection conn=null;
    static Statement stmt = null;
    static ResultSet rs = null;
    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        } //end of try~catch

        try {
            //String strConn = "jdbc:mysql://112.175.39.80/mysql?user=root&password=kmu20!@";
            //conn = DriverManager.getConnection(strConn);
            conn=DriverManager.getConnection("jdbc:mysql://dalpha.org:33306","root","kmu20!@");
            // Do something with the Connection
            System.out.println("Connected!!");
            
            // Get a statement from the connection
            Statement stmt = conn.createStatement() ;
            
            String regId = "APA91bF6Y_b6-PyoBekgpxNP3CD24mEYX3H2evhZBZbaqwA0ycyEp9yFoEZT-TnSLacFB-1iqNXbxmESbgNfxOuT1FAOzxeyh1gSRCRO0omVrSwhhLcN_WYg7jjZ7dyuvnGGBWiWzu7bwFz_ENtX_W8M-PDk8l039Q";
            String sql= "insert into reg_id values('"+regId+"')"; 
            System.out.println(sql);
            stmt.executeUpdate(sql); 
            // Execute the query                    
            //String sql= "insert into reg_id values(123458)"; 
            //stmt.executeUpdate(sql); 

          ResultSet rs = stmt.executeQuery( "SELECT * FROM reg_id" ) ;
            
            // Loop through the result set
            while( rs.next() )
               System.out.println( rs.getString(1) ) ;
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } //end of try~catch

    } //end of main
}