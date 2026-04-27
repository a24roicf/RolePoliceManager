package BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Rango;

/**
 *
 * @author alumno
 */
public class RangoBD {

    public List<Rango> obtenerRangos() {
        List<Rango> lista = new ArrayList<>();

        String sql = "SELECT id_rango, nombre, nivel, salario_base FROM rango";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rango r = new Rango();
                r.setIdRango(rs.getInt("id_rango"));
                r.setNombre(rs.getString("nombre"));
                r.setNivel(rs.getInt("nivel"));
                r.setSalario(rs.getDouble("salario_base"));

                lista.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
