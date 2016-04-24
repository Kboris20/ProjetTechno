/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfactory;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author christop.francill
 */
public class OracleConnections {
    private static OracleDataSource ods = null;
    
    private static final String USER = "boris_klett";
    private static final String PASSWORD = "boris_klett";
    private static final String HOST = "db.ig.he-arc.ch";
    private static final String PORT = "1521";
    private static final String SID = "ens2";
    
    public static Connection getConnection() throws SQLException{
        if(ods == null){
            String url = "jdbc:oracle:thin:@" + HOST + ":" + PORT + ":" + SID;
            
            ods = new OracleDataSource();
            ods.setUser(USER);
            ods.setPassword(PASSWORD);
            ods.setURL(url);
        }
        
        Connection connection = ods.getConnection();
        connection.setAutoCommit(false);
        
        return connection;
    }
}
