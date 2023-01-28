/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alexander
 */
public class ConexionBD {
    public static Connection conectarMySQL(){
        String urlConnection = "jdbc:mysql://localhost:3306/librarysystem_v2?useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "123456";
        
        try {
            Connection conn = DriverManager.getConnection(urlConnection, user, password);
            return conn;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
            return null;
        }
    }
}
