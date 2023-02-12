/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public Connection conexion() {
        String cadena = "jdbc:postgresql://localhost:5432/vinculacionchoferes";
        String user = "postgres";
        String pass = "root";
        Connection cnn = null;
        try {
            cnn = DriverManager.getConnection(cadena, user, pass);
            return cnn;
        } catch (Exception e) {
            System.out.println("");
            return null;
        }

    }

}
