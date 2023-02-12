package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mdl_alumnos extends Conexion {

    Conexion con = new Conexion();

    public boolean verificarAlumno(Alumnos Alum) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = conexion();
        String sql = "SELECT * from public.alumnos WHERE cedula=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, Alum.getCedula());
            rs = ps.executeQuery();

            if (rs.next()) {
                Alum.setId(Integer.parseInt(rs.getString("id")));
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

    public boolean registrar(Alumnos Alum) {

        PreparedStatement ps = null;
        Connection con = conexion();
        String sql = "INSERT INTO public.alumnos(cedula, nombres, apellidos) VALUES (?, ?, ?)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, Alum.getCedula());
            ps.setString(2, Alum.getNombres());
            ps.setString(3, Alum.getApellidos());

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

    public ResultSet consultarAlumnosTabla() throws SQLException {

        Statement st = con.conexion().createStatement();
        String sql = "SELECT cedula, nombres, apellidos, id FROM public.alumnos";
        ResultSet rs = st.executeQuery(sql);
        conexion().close();
        return rs;
    }

    public ResultSet consultarAlumnos(Alumnos Alum) throws SQLException {

        PreparedStatement ps = null;
        Connection con = conexion();
        String sql = "SELECT id, nombres, apellidos, cedula FROM public.alumnos WHERE id=?";
        ps.setInt(1, Alum.getId());
        ResultSet rs = ps.executeQuery(sql);
        conexion().close();
        return rs;
    }

    public boolean modificar(Alumnos Alum) {

        PreparedStatement ps = null;
        Connection con = conexion();
        String sql = "UPDATE public.alumnos SET nombres=?, apellidos=?, cedula=? WHERE id= ?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, Alum.getNombres());
            ps.setString(2, Alum.getApellidos());
            ps.setString(3, Alum.getCedula());
            ps.setInt(4, Alum.getId());

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

    public int conteoAlumnos(Alumnos Alum) throws SQLException {
        Statement st = con.conexion().createStatement();
        String myStatement = "select count(*) as total from alumnos";
        ResultSet rs = st.executeQuery(myStatement);
        int num = 0;
        while (rs.next()) {
            num = (rs.getInt(1));
        }
        conexion().close();
        return num;

    }

}
