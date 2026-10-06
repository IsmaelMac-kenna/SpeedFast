package cl.speedfast.vista;

import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class PanelPedidos extends JPanel {

    private final PedidoDAO dao = new PedidoDAO();
    private JTextField txtDireccion;
    private JComboBox<String> cbTipo;
    private JTable tabla;
    private DefaultTableModel modelo;
    private int idSeleccionado = -1;

    private static final String[] TIPOS = {"COMIDA", "ENCOMIENDA", "EXPRESS"};

    public PanelPedidos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);

        cargarTabla();
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField(20);
        panel.add(txtDireccion);

        panel.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(TIPOS);
        panel.add(cbTipo);

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");

        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());

        panel.add(btnNuevo);
        panel.add(btnGuardar);
        panel.add(btnEliminar);

        return panel;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Solo lectura: el estado no se edita aquí
            }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) seleccionFila();
        });

        return new JScrollPane(tabla);
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        try {
            for (Pedido p : dao.leerTodos()) {
                modelo.addRow(new Object[]{p.getId(), p.getDireccion(), p.getTipo(), p.getEstado()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al leer pedidos:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardar() {
        String direccion = txtDireccion.getText().trim();

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "La dirección es obligatoria.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipo = (String) cbTipo.getSelectedItem();

        try {
            if (idSeleccionado == -1) {
                //nuevo pedido siempre aparece como pendiente
                dao.crear(new Pedido(direccion, tipo, "PENDIENTE"));
                JOptionPane.showMessageDialog(this,
                        " Pedido registrado con estado PENDIENTE.\n"
                                + "Cambiará a EN_REPARTO cuando se asigne una entrega.");
            } else {

                String estadoActual = (String) modelo.getValueAt(tabla.getSelectedRow(), 3);
                dao.actualizar(new Pedido(idSeleccionado, direccion, tipo, estadoActual));
                JOptionPane.showMessageDialog(this, " Pedido actualizado.");
            }
            limpiarFormulario();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    " Error al guardar:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una fila de la tabla.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el pedido ID " + idSeleccionado + "?\n"
                        + "(Si tiene entregas asociadas, la BD lo impedirá)",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(idSeleccionado);
                JOptionPane.showMessageDialog(this, " Pedido eliminado.");
                limpiarFormulario();
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        " No se puede eliminar (tiene entregas asociadas):\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void seleccionFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = (int) modelo.getValueAt(fila, 0);
            txtDireccion.setText((String) modelo.getValueAt(fila, 1));
            cbTipo.setSelectedItem(modelo.getValueAt(fila, 2));
        }
    }

    private void limpiarFormulario() {
        idSeleccionado = -1;
        txtDireccion.setText("");
        cbTipo.setSelectedIndex(0);
        tabla.clearSelection();
        txtDireccion.requestFocus();
    }
}