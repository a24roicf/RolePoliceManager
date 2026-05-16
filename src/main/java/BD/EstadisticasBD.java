package BD;

import modelo.Estadistica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roi conles ferro
 */
public class EstadisticasBD {

    //Obtiene el total de usuarios registrados
    public static int getTotalUsuarios() {
        String sql = "SELECT COUNT(*) as total FROM Usuario";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener total de usuarios: " + e.getMessage());
        }
        
        return 0;
    }

    //Obtiene el total de divisiones
    public static int getTotalDivisiones() {
        String sql = "SELECT COUNT(*) as total FROM Division";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener total de divisiones: " + e.getMessage());
        }
        
        return 0;
    }

    //Obtiene el total de licencias asignadas
    public static int getTotalLicenciasAsignadas() {
        String sql = "SELECT COUNT(*) as total FROM Usuario_Licencia";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener total de licencias asignadas: " + e.getMessage());
        }
        
        return 0;
    }

    //Obtiene el total de informes creados
    public static int getTotalInformes() {
        String sql = "SELECT COUNT(*) as total FROM Informe";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener total de informes: " + e.getMessage());
        }
        
        return 0;
    }

    //Obtiene el total de anuncios publicados
    public static int getTotalAnuncios() {
        String sql = "SELECT COUNT(*) as total FROM Anuncio";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener total de anuncios: " + e.getMessage());
        }
        
        return 0;
    }

    //Obtiene el total de acciones registradas en logs
    public static int getTotalLogs() {
        String sql = "SELECT COUNT(*) as total FROM Log_actividad";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener total de logs: " + e.getMessage());
        }
        
        return 0;
    }

    //Obtiene la distribución de usuarios por división
    public static List<Estadistica> getUsuariosPorDivision() {
        List<Estadistica> stats = new ArrayList<>();
        String sql = "SELECT d.nombre, COUNT(ud.id_usuario) AS total " +
                     "FROM Division d " +
                     "LEFT JOIN Usuario_Division ud ON d.id_division = ud.id_division " +
                     "GROUP BY d.nombre " +
                     "ORDER BY total DESC";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int total = rs.getInt("total");
                stats.add(new Estadistica(nombre, total, "division"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios por división: " + e.getMessage());
        }
        
        return stats;
    }

    //Obtiene las licencias más asignadas
    public static List<Estadistica> getLicenciasMasAsignadas() {
        List<Estadistica> stats = new ArrayList<>();
        String sql = "SELECT l.nombre, COUNT(ul.id_usuario) AS total " +
                     "FROM Licencia l " +
                     "LEFT JOIN Usuario_Licencia ul ON l.id_licencia = ul.id_licencia " +
                     "GROUP BY l.nombre " +
                     "ORDER BY total DESC " +
                     "LIMIT 10";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int total = rs.getInt("total");
                stats.add(new Estadistica(nombre, total, "licencia"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener licencias más asignadas: " + e.getMessage());
        }
        
        return stats;
    }

    //Obtiene la actividad por módulo desde los logs
    public static List<Estadistica> getActividadPorModulo() {
        List<Estadistica> stats = new ArrayList<>();
        String sql = "SELECT modulo, COUNT(*) AS total " +
                     "FROM Log_actividad " +
                     "GROUP BY modulo " +
                     "ORDER BY total DESC";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String modulo = rs.getString("modulo");
                int total = rs.getInt("total");
                stats.add(new Estadistica(modulo, total, "log"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener actividad por módulo: " + e.getMessage());
        }
        
        return stats;
    }

    //Obtiene la actividad por tipo de acción desde los logs
    public static List<Estadistica> getActividadPorTipoAccion() {
        List<Estadistica> stats = new ArrayList<>();
        String sql = "SELECT tipo_accion, COUNT(*) AS total " +
                     "FROM Log_actividad " +
                     "GROUP BY tipo_accion " +
                     "ORDER BY total DESC";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String accion = rs.getString("tipo_accion");
                int total = rs.getInt("total");
                stats.add(new Estadistica(accion, total, "accion"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener actividad por tipo de acción: " + e.getMessage());
        }
        
        return stats;
    }

    //Obtiene la distribución de usuarios por rango
    public static List<Estadistica> getUsuariosPorRango() {
        List<Estadistica> stats = new ArrayList<>();
        String sql = "SELECT r.nombre, COUNT(u.id_usuario) AS total " +
                     "FROM Rango r " +
                     "LEFT JOIN Usuario u ON r.id_rango = u.id_rango " +
                     "GROUP BY r.nombre " +
                     "ORDER BY total DESC";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String rango = rs.getString("nombre");
                int total = rs.getInt("total");
                stats.add(new Estadistica(rango, total, "rango"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios por rango: " + e.getMessage());
        }
        
        return stats;
    }
}