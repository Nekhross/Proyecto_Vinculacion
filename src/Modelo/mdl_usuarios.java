package Modelo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class mdl_usuarios extends Conexion {

    public boolean registrar(usuarios usu) {

        PreparedStatement ps = null;
        Connection con = conexion();
        String sql = "INSERT INTO usuarios (usuario, clave, nombres, apellidos, cedula, id_rol) VALUES (?,?,?,?,?,?)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, usu.getUsuario());
            ps.setString(2, usu.getClave());
            ps.setString(3, usu.getNombres());
            ps.setString(4, usu.getApellidos());
            ps.setString(5, usu.getCedula());
            ps.setInt(6, usu.getIdrol());
            ps.execute();
            return true;

        } catch (Exception e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }

        }

    }

    public boolean modificar(usuarios usu) {

        PreparedStatement ps = null;
        Connection con = conexion();
        String sql = "UPDATE usuarios SET usuario=?, clave=?, nombres=?, apellidos=?, cedula=? where id= ?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, usu.getUsuario());
            ps.setString(2, usu.getClave());
            ps.setString(3, usu.getNombres());
            ps.setString(4, usu.getApellidos());
            ps.setString(5, usu.getCedula());
            ps.setInt(6, usu.getId());
            ps.execute();
            return true;

        } catch (Exception e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }

        }

    }

    public boolean verificarCuenta(usuarios usu) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = conexion();
        String sql = "SELECT * from usuarios where usuario = ? and clave = ?";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, usu.getUsuario());
            ps.setString(2, usu.getClave());
            rs = ps.executeQuery();

            if (rs.next()) {

                usu.setUsuario(rs.getString("usuario"));
                usu.setClave(rs.getString("clave"));
                usu.setNombres(rs.getString("nombres"));
                usu.setApellidos(rs.getString("apellidos"));
                usu.setCedula(rs.getString("cedula"));
                usu.setIdrol(rs.getInt("id_rol"));
                usu.setId(Integer.parseInt(rs.getString("id")));
                return true;

            } else {

                System.err.println("error");
            }
            return false;

        } catch (Exception e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }

        }

    }

    public boolean buscar(usuarios usu) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = conexion();
        String sql = "SELECT * from usuarios where id=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, usu.getId());
            rs = ps.executeQuery();

            if (rs.next()) {

                usu.setUsuario(rs.getString("usuario"));
                usu.setClave(rs.getString("clave"));
                usu.setNombres(rs.getString("nombres"));
                usu.setApellidos(rs.getString("apellidos"));
                usu.setId(Integer.parseInt(rs.getString("id")));
                return true;

            } 
            return false;

        } catch (Exception e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.err.println(e);
            }

        }

    }
    public int conteoUsuarios(usuarios usu) throws SQLException {
        Statement st = conexion().createStatement();
        String myStatement = "select count(*) as total from usuarios";
        ResultSet rs = st.executeQuery(myStatement);
        int num = 0;
        while (rs.next()) {
            num = (rs.getInt(1));
        }
        conexion().close();
        return num;

    }

}
