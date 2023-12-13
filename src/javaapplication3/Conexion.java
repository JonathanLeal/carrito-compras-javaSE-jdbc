package javaapplication3;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Conexion {
    Connection con;
    public Conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://depositodetelas.com:3306/itma_tamara","itma_tamara","tami2134!65");
        } catch (Exception e) {
            System.err.println("Error:" +e);
        }
    }
}
