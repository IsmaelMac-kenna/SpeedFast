package cl.speedfast.dao;

import cl.speedfast.modelo.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PedidoDAO {

    public void crear(Pedido p) throws SQLException {
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getDireccion());
            ps.setString(2, p.getTipo());
            ps.setString(3, p.getEstado());
            ps.executeUpdate();
        }
    }
    public void actualizarEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public List<Pedido> leerTodos() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos ORDER BY id";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                ));
            }
        }
        return lista;
    }

    public void actualizar(Pedido p) throws SQLException {
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getDireccion());
            ps.setString(2, p.getTipo());
            ps.setString(3, p.getEstado());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        }
    }


    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }


    public List<Pedido> leerPorEstado(String estado) throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos WHERE estado = ? ORDER BY id";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Pedido(
                            rs.getInt("id"),
                            rs.getString("direccion"),
                            rs.getString("tipo"),
                            rs.getString("estado")
                    ));
                }
            }
        }
        return lista;
    }
}