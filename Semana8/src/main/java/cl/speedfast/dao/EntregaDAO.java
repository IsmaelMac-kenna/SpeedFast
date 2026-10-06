package cl.speedfast.dao;

import cl.speedfast.modelo.Entrega;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EntregaDAO {

    public void crear(Entrega e) throws SQLException {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getIdPedido());
            ps.setInt(2, e.getIdRepartidor());
            ps.setDate(3, Date.valueOf(e.getFecha()));   // LocalDate -> sql.Date
            ps.setTime(4, Time.valueOf(e.getHora()));    // LocalTime -> sql.Time
            ps.executeUpdate();
        }
    }

    public List<Entrega> leerTodos() throws SQLException {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT id, id_pedido, id_repartidor, fecha, hora FROM entregas ORDER BY id";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getDate("fecha").toLocalDate(),   // sql.Date -> LocalDate
                        rs.getTime("hora").toLocalTime()     // sql.Time -> LocalTime
                ));
            }
        }
        return lista;
    }

    public void actualizar(Entrega e) throws SQLException {
        String sql = "UPDATE entregas SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getIdPedido());
            ps.setInt(2, e.getIdRepartidor());
            ps.setDate(3, Date.valueOf(e.getFecha()));
            ps.setTime(4, Time.valueOf(e.getHora()));
            ps.setInt(5, e.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM entregas WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
