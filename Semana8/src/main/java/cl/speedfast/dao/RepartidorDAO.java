package cl.speedfast.dao;
import cl.speedfast.modelo.Repartidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RepartidorDAO {


    public void crear(Repartidor r) throws SQLException {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getNombre());
            ps.executeUpdate();
        }
    }

    public List<Repartidor> leerTodos() throws SQLException {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM repartidores ORDER BY id";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Repartidor(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return lista;
    }

    public void actualizar(Repartidor r) throws SQLException {
        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getNombre());
            ps.setInt(2, r.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM repartidores WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}