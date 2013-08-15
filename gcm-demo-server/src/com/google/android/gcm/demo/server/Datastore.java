/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gcm.demo.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.sql.*;

/**
 * Simple implementation of a data store using standard Java collections.
 * <p>
 * This class is thread-safe but not persistent (it will lost the data when the
 * app is restarted) - it is meant just as an example.
 */
public final class Datastore {

  private static final List<String> regIds = new ArrayList<String>();
  private static final Logger logger =
      Logger.getLogger(Datastore.class.getName());
  private static Connection conn=null;
  private static Statement stmt = null;
  private static ResultSet rs = null;
  
  private Datastore() {
    throw new UnsupportedOperationException();
  }

  /**
   * Registers a device.
   */
  public static void update_dept(String regId, String dept1, String dept2, String dept3, String dept4, String dept5, String dept6, String dept7, String dept8, String dept9, String dept10, String dept11, String dept12, String dept13, String dept14, String dept15, String dept16 )
  {

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
          conn=DriverManager.getConnection("jdbc:mysql://app.dalpha.org:3306/gcm_client","root","kmu20!@");
          // Do something with the Connection
          System.out.println("Connected!!");

          // Get a statement from the connection
          Statement stmt = conn.createStatement() ;

          // Execute the query                    
          //String sql= "UPDATE `reg_id` SET `dept1`=['" + dept[0] + "'] WHERE id='"+regId+"'"; 
          //String sql= "UPDATE `reg_id` SET `dept1`=['" + dept[0] + "'],`dept2`=['" + dept[1] + "']," +
          //		"`dept3`=['" + dept[2] + "'],`dept4`=['" + dept[3] + "'],`dept5`=['" + dept[4] + "']," +
         // 		"`dept6`=['" + dept[5] + "'],`dept7`=['" + dept[6] + "'],`dept8`=['" + dept[7] + "']," +
          //	    "`dept9`=['" + dept[8] + "'],`dept10`=['" + dept[9] + "'],`dept11`=['" + dept[10] + "']," +
          //	    "`dept12`=['" + dept[11] + "'],`dept13`=['" + dept[12] + "'],`dept14`=['" + dept[13] + "']," +
          //	    "`dept15`=['" + dept[14] + "'],`dept16`=['" + dept[15] + "'] WHERE id='"+regId+"'"; 
          //String sql= "UPDATE `reg_id` SET `dept1`='T' WHERE `id`='"+ regId +"'";
          
          String sql = "UPDATE `reg_id` SET `dept1`='"+ dept1 +"', `dept2`='"+ dept2 +"',`dept3`='"+ dept3 +"',`dept4`='"+ dept4 +"',`dept5`='"+ dept5 +"',`dept6`='"+ dept6 +"',`dept7`='"+ dept7 +"',`dept8`='"+ dept8 +"',`dept9`='"+ dept9 +"',`dept10`='"+ dept10 +"',`dept11`='"+ dept11 +"',`dept12`='"+ dept12 +"',`dept13`='"+ dept13 +"',`dept14`='"+ dept14 +"',`dept15`='"+ dept15 +"',`dept16`='"+ dept16 +"' WHERE `id`='"+ regId +"'";
          
          stmt.executeUpdate(sql); 
          
          synchronized (regIds) {
            //regIds.remove(oldId);
            //regIds.add(newId);
          }
          
      } catch (SQLException ex) {
          // handle any errors
    	  logger.info("SQLException: " + ex.getMessage());
    	  logger.info("SQLState: " + ex.getSQLState());
    	  logger.info("VendorError: " + ex.getErrorCode());
          System.out.println("SQLException: " + ex.getMessage());
          System.out.println("SQLState: " + ex.getSQLState());
          System.out.println("VendorError: " + ex.getErrorCode());
      } //end of try~catch
	  
	  
	  
  }
  
  public static void register(String regId) {
	  
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
          conn=DriverManager.getConnection("jdbc:mysql://app.dalpha.org:3306/gcm_client","root","kmu20!@");
          // Do something with the Connection
          System.out.println("Connected!!");
          
          // Get a statement from the connection
          Statement stmt = conn.createStatement() ;

          // Execute the query                    
          String sql= "insert into reg_id (id) values('"+regId+"')"; 
          stmt.executeUpdate(sql); 

        ResultSet rs = stmt.executeQuery( "SELECT id FROM reg_id" ) ;
          
          // Loop through the result set
          while( rs.next() )
          {
             System.out.println( rs.getString(1) ) ;
             synchronized (rs.getString(1)) {
                 regIds.add(rs.getString(1));
               }
          }
          
      } catch (SQLException ex) {
          // handle any errors
    	  logger.info("SQLException: " + ex.getMessage());
    	  logger.info("SQLState: " + ex.getSQLState());
    	  logger.info("VendorError: " + ex.getErrorCode());
          System.out.println("SQLException: " + ex.getMessage());
          System.out.println("SQLState: " + ex.getSQLState());
          System.out.println("VendorError: " + ex.getErrorCode());
      } //end of try~catch
  //  logger.info("Registering " + regId);
    
  }

  /**
   * Unregisters a device.
   */
  public static void unregister(String regId) {
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
          conn=DriverManager.getConnection("jdbc:mysql://app.dalpha.org:3306/gcm_client","root","kmu20!@");
          // Do something with the Connection
          System.out.println("Connected!!");
          
          // Get a statement from the connection
          Statement stmt = conn.createStatement() ;

          // Execute the query                    
          String sql= "DELETE FROM `reg_id` WHERE id='" +regId+ "'"; 
          stmt.executeUpdate(sql); 

          logger.info("Unregistering " + regId);
          synchronized (regIds) {
              regIds.remove(regId);
          }
          
      } catch (SQLException ex) {
          // handle any errors
    	  logger.info("SQLException: " + ex.getMessage());
    	  logger.info("SQLState: " + ex.getSQLState());
    	  logger.info("VendorError: " + ex.getErrorCode());
          System.out.println("SQLException: " + ex.getMessage());
          System.out.println("SQLState: " + ex.getSQLState());
          System.out.println("VendorError: " + ex.getErrorCode());
      } //end of try~catch
    
  }
  
  /**
   * Updates the registration id of a device.
   */
  public static void updateRegistration(String oldId, String newId) {

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
          conn=DriverManager.getConnection("jdbc:mysql://app.dalpha.org:3306/gcm_client","root","kmu20!@");
          // Do something with the Connection
          System.out.println("Connected!!");
          
          // Get a statement from the connection
          Statement stmt = conn.createStatement() ;

          // Execute the query                    
          String sql= "UPDATE `reg_id` SET `id`=['" + newId + "'] WHERE id='"+oldId+"'"; 
          stmt.executeUpdate(sql); 
          
          logger.info("Updating " + oldId + " to " + newId);
          synchronized (regIds) {
            regIds.remove(oldId);
            regIds.add(newId);
          }
          
      } catch (SQLException ex) {
          // handle any errors
    	  logger.info("SQLException: " + ex.getMessage());
    	  logger.info("SQLState: " + ex.getSQLState());
    	  logger.info("VendorError: " + ex.getErrorCode());
          System.out.println("SQLException: " + ex.getMessage());
          System.out.println("SQLState: " + ex.getSQLState());
          System.out.println("VendorError: " + ex.getErrorCode());
      } //end of try~catch
	  
    
  }

  /**
   * Gets all registered devices.
   */
  public static List<String> getDevices() {
	  
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
          conn=DriverManager.getConnection("jdbc:mysql://app.dalpha.org:3306/gcm_client","root","kmu20!@");
          // Do something with the Connection
          System.out.println("Connected!!");
          
          // Get a statement from the connection
          Statement stmt = conn.createStatement() ;

          
        ResultSet rs = stmt.executeQuery( "SELECT id FROM reg_id " ) ;
        
        regIds.clear();
        
          // Loop through the result set
          while( rs.next() )
          {
             System.out.println( rs.getString(1) ) ;
             synchronized (rs.getString(1)) {
                 regIds.add(rs.getString(1));
               }
          }
          
          
      } catch (SQLException ex) {
          // handle any errors
    	  logger.info("SQLException: " + ex.getMessage());
    	  logger.info("SQLState: " + ex.getSQLState());
    	  logger.info("VendorError: " + ex.getErrorCode());
          System.out.println("SQLException: " + ex.getMessage());
          System.out.println("SQLState: " + ex.getSQLState());
          System.out.println("VendorError: " + ex.getErrorCode());
      } //end of try~catch
      
    synchronized (regIds) {
      return new ArrayList<String>(regIds);
    }
  }
  
public static List<String> getDevices( String dept1, String dept2, String dept3, String dept4, String dept5, String dept6, String dept7, String dept8, String dept9, String dept10, String dept11, String dept12, String dept13, String dept14, String dept15, String dept16 ) {
	  
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
          conn=DriverManager.getConnection("jdbc:mysql://app.dalpha.org:3306/gcm_client","root","kmu20!@");
          // Do something with the Connection
          System.out.println("Connected!!");
          
          // Get a statement from the connection
          Statement stmt = conn.createStatement() ;

          regIds.clear();

          //ResultSet rs = stmt.executeQuery( "SELECT id FROM reg_id WHERE dept='"+dept[x]+"'" ) ;
          
          String sql="SELECT id FROM reg_id WHERE ";
          String temp = dept1+dept2+dept3+dept4+dept5+dept6+dept7+dept8+dept9+dept10+dept11+dept12+dept13+dept14+dept15+dept16;
       
          String [] list = new String[16];
          
          int idx = 0;
          
          
          for( int x=0 ; x<16 ; x++)
          {
        	  if(temp.charAt(x) == 'T')
        		  list[idx++] = "dept" +Integer.toString(x+1);
          }
          
          
          if(idx > 0)
          {
          for( int x=0 ; x<idx ;x++)
          {
        	  if(x==0)
        		  sql +="`"+list[x]+"`='T'";
        	  else
        		  sql += " OR `"+list[x]+"`='T'";
          }
          sql += ";";
          }
          
          else
        	  sql = "SELECT id FROM reg_id WHERE 0";
          
          System.out.println(sql);
          ResultSet rs = stmt.executeQuery(sql);
          
          //ResultSet rs = stmt.executeQuery( "SELECT id FROM reg_id WHERE `dept1`='"+ dept1 +"' OR  `dept2`='"+ dept2 +"' OR `dept3`='"+ dept3 +"' OR `dept4`='"+ dept4 +"' OR `dept5`='"+ dept5 +"' OR `dept6`='"+ dept6 +"' OR `dept7`='"+ dept7 +"' OR `dept8`='"+ dept8 +"' OR `dept9`='"+ dept9 +"' OR `dept10`='"+ dept10 +"' OR `dept11`='"+ dept11 +"' OR `dept12`='"+ dept12 +"' OR `dept13`='"+ dept13 +"' OR `dept14`='"+ dept14 +"' OR `dept15`='"+ dept15 +"' OR `dept16`='"+ dept16 +"'");
          // Loop through the result set
          while( rs.next() )
          {
        	  System.out.println( rs.getString(1) ) ;
        	  synchronized (rs.getString(1)) {
        		  regIds.add(rs.getString(1));
        	  }
          }
          
      } catch (SQLException ex) {
          // handle any errors
    	  logger.info("SQLException: " + ex.getMessage());
    	  logger.info("SQLState: " + ex.getSQLState());
    	  logger.info("VendorError: " + ex.getErrorCode());
          System.out.println("SQLException: " + ex.getMessage());
          System.out.println("SQLState: " + ex.getSQLState());
          System.out.println("VendorError: " + ex.getErrorCode());
      } //end of try~catch
      
    synchronized (regIds) {
      return new ArrayList<String>(regIds);
    }
  }

}
