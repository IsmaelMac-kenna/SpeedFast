package cl.speedfast.vista;


import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Panel para gestionar Repartidores
public class PanelRepartidores extends JPanel {

    private final RepartidorDAO dao = new RepartidorDAO();
    private JTextField txtNombre;
    private JTable tabla;
    private DefaultTableModel modelo;
    private int idSeleccionado = -1; // -1 = sin selección (modo creación)

    public PanelRepartidores() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);

        cargarTabla(); // Cargar datos iniciales
    }


    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(20);
        panel.add(txtNombre);

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        panel.add(btnNuevo);
        panel.add(btnGuardar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);

        return panel;
    }


    private JScrollPane crearTabla() {
        String[] columnas = {"ID", "Nombre"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla de solo lectura
            }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);

        // Al seleccionar una fila, llenar el formulario para editar
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionFila();
            }
        });

        return new JScrollPane(tabla);
    }

    // Rellena la tabla leyendo desde la BD
    private void cargarTabla() {
        modelo.setRowCount(0); // Vaciar
        try {
            List<Repartidor> lista = dao.leerTodos();
            for (Repartidor r : lista) {
                modelo.addRow(new Object[]{r.getId(), r.getNombre()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al leer repartidores:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Crea o actualiza según haya selección
    private void guardar() {
        String nombre = txtNombre.getText().trim();

        // Validación de entrada (criterio de la pauta)
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (idSeleccionado == -1) {
                dao.crear(new Repartidor(nombre));
                JOptionPane.showMessageDialog(this, " Repartidor registrado.");
            } else {
                dao.actualizar(new Repartidor(idSeleccionado, nombre));
                JOptionPane.showMessageDialog(this, " Repartidor actualizado.");
            }
            limpiarFormulario();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    " Error al guardar:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Elimina el repartidor seleccionado
    private void eliminar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una fila de la tabla.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el repartidor ID " + idSeleccionado + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(idSeleccionado);
                JOptionPane.showMessageDialog(this, " Repartidor eliminado.");
                limpiarFormulario();
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        " No se puede eliminar (posiblemente tiene entregas asociadas):\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void seleccionFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = (int) modelo.getValueAt(fila, 0);
            txtNombre.setText((String) modelo.getValueAt(fila, 1));
        }
    }


    private void limpiarFormulario() {
        idSeleccionado = -1;
        txtNombre.setText("");
        tabla.clearSelection();
        txtNombre.requestFocus();
    }
}