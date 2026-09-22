package cl.speedfast.vistas;

import cl.speedfast.controlador.PedidoController;
import cl.speedfast.model.Pedido;
import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {
    private final PedidoController controller;
    private JTextField txtId;
    private JTextField txtDireccion;
    private JComboBox<String> comboTipo;

    public VentanaRegistroPedido(PedidoController controller) {
        this.controller = controller;

        setTitle("Registrar Pedido");
        setSize(380, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Componentes
        add(new JLabel("  ID del Pedido:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("  Dirección:"));
        txtDireccion = new JTextField();
        add(txtDireccion);

        add(new JLabel("  Tipo de Pedido:"));
        String[] tipos = {"Comida", "Encomienda", "Express"};
        comboTipo = new JComboBox<>(tipos);
        add(comboTipo);

        JButton btnGuardar = new JButton("Guardar");
        //JButton btnLimpiar = new JButton("Limpiar"); esto lo dejaremos como unna opcion para mas adelante

        add(btnGuardar);
        //add(btnLimpiar);

        // Acciones
        btnGuardar.addActionListener(e -> guardarPedido());
        //btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void guardarPedido() {
        try {
            String strId = txtId.getText().trim();
            String direccion = txtDireccion.getText().trim();

            if (strId.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = Integer.parseInt(strId);

            if (controller.existeId(id)) {
                JOptionPane.showMessageDialog(this, "El ID del pedido ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tipo = comboTipo.getSelectedItem().toString();

            Pedido nuevoPedido = new Pedido(id, direccion, tipo);
            controller.agregarPedido(nuevoPedido);

            JOptionPane.showMessageDialog(this, "Pedido registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            dispose(); // Cierra la ventana tras guardar

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtDireccion.setText("");
        comboTipo.setSelectedIndex(0);
    }
}