package cl.speedfast.vistas;

import cl.speedfast.controlador.PedidoController;
import cl.speedfast.model.*;
import javax.swing.*;
import java.awt.*;

public class VentanaAsignarRepartidor extends JFrame {
    private final PedidoController controller;
    private JComboBox<String> comboPedidos;
    private JComboBox<Repartidor> comboRepartidores;

    public VentanaAsignarRepartidor(PedidoController controller) {
        this.controller = controller;

        setTitle("Asignar Repartidor");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("  Seleccionar Pedido:"));
        comboPedidos = new JComboBox<>();
        cargarPedidosPendientes();
        add(comboPedidos);

        add(new JLabel("  Seleccionar Repartidor:"));
        comboRepartidores = new JComboBox<>();
        for (Repartidor r : controller.getListaRepartidores()) {
            comboRepartidores.addItem(r);
        }
        add(comboRepartidores);

        JButton btnAsignar = new JButton("Asignar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnAsignar);
        add(btnCancelar);

        btnAsignar.addActionListener(e -> realizarAsignacion());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarPedidosPendientes() {
        comboPedidos.removeAllItems();
        for (Pedido p : controller.getListaPedidos()) {
            if (p.getEstado().equals("PENDIENTE")) {
                comboPedidos.addItem("ID: " + p.getId() + " - " + p.getTipo());
            }
        }
    }

    private void realizarAsignacion() {
        if (comboPedidos.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay pedidos pendientes para asignar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String seleccion = (String) comboPedidos.getSelectedItem();
        int idPedido = Integer.parseInt(seleccion.split(" - ")[0].replace("ID: ", ""));
        Repartidor repartidor = (Repartidor) comboRepartidores.getSelectedItem();

        if (controller.asignarRepartidor(idPedido, repartidor)) {
            JOptionPane.showMessageDialog(this, "Repartidor " + repartidor.getNombre() + " asignado al Pedido #" + idPedido, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al asignar repartidor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
