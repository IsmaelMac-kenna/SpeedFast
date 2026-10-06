package cl.speedfast.vista;


import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.modelo.Entrega;
import cl.speedfast.modelo.Pedido;
import cl.speedfast.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;


public class PanelEntregas extends JPanel {

    private final EntregaDAO dao = new EntregaDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();

    private JComboBox<Pedido> cbPedido;
    private JComboBox<Repartidor> cbRepartidor;
    private JTextField txtFecha;   // formato AAAA-MM-DD
    private JTextField txtHora;    // formato HH:mm:ss
    private JTable tabla;
    private DefaultTableModel modelo;
    private int idSeleccionado = -1;

    public PanelEntregas() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearBotonesAcciones(), BorderLayout.SOUTH);
        add(crearTabla(), BorderLayout.CENTER);

        cargarCombos();
        cargarTabla();
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Pedido:"));
        cbPedido = new JComboBox<>(); // se llena desde la BD
        panel.add(cbPedido);

        panel.add(new JLabel("Repartidor:"));
        cbRepartidor = new JComboBox<>(); // se llena desde la BD
        panel.add(cbRepartidor);

        panel.add(new JLabel("Fecha (AAAA-MM-DD):"));
        txtFecha = new JTextField(10);
        txtFecha.setText(LocalDate.now().toString()); // valor por defecto
        panel.add(txtFecha);

        panel.add(new JLabel("Hora (HH:mm:ss):"));
        txtHora = new JTextField(8);
        txtHora.setText(LocalTime.now().withNano(0).toString());
        panel.add(txtHora);

        return panel;
    }

    private JPanel crearBotonesAcciones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Registrar Entrega");
        JButton btnEntregado = new JButton("Marcar ENTREGADO");
        JButton btnEliminar = new JButton("Eliminar");

        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardar());
        btnEntregado.addActionListener(e -> marcarEntregado());
        btnEliminar.addActionListener(e -> eliminar());

        panel.add(btnNuevo);
        panel.add(btnGuardar);
        panel.add(btnEntregado);
        panel.add(btnEliminar);

        return panel;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"ID", "Pedido", "Repartidor", "Fecha", "Hora"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) seleccionFila();
        });

        return new JScrollPane(tabla);
    }

    /** Carga los combos consultando la BD (requisito de la pauta) */
    public void cargarCombos() {
        try {
            cbPedido.removeAllItems();
            for (Pedido p : pedidoDAO.leerTodos()) {
                cbPedido.addItem(p); // toString() muestra "1 - Av. ..."
            }

            cbRepartidor.removeAllItems();
            for (Repartidor r : repartidorDAO.leerTodos()) {
                cbRepartidor.addItem(r); // toString() muestra "1 - Pedro"
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar combos:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        try {
            // Mapas para mostrar nombres legibles en la tabla
            java.util.Map<Integer, String> nombresRep = new java.util.HashMap<>();
            for (Repartidor r : repartidorDAO.leerTodos()) nombresRep.put(r.getId(), r.getNombre());
            java.util.Map<Integer, String> dirsPed = new java.util.HashMap<>();
            for (Pedido p : pedidoDAO.leerTodos()) dirsPed.put(p.getId(), p.getDireccion());

            for (Entrega e : dao.leerTodos()) {
                modelo.addRow(new Object[]{
                        e.getId(),
                        e.getIdPedido() + " - " + dirsPed.getOrDefault(e.getIdPedido(), "?"),
                        e.getIdRepartidor() + " - " + nombresRep.getOrDefault(e.getIdRepartidor(), "?"),
                        e.getFecha(),
                        e.getHora()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al leer entregas:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardar() {
        Pedido pedSel = (Pedido) cbPedido.getSelectedItem();
        Repartidor repSel = (Repartidor) cbRepartidor.getSelectedItem();

        // Validaciones (criterio 3 de la pauta)
        if (pedSel == null || repSel == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe existir al menos un pedido y un repartidor registrados.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate fecha;
        LocalTime hora;
        try {
            fecha = LocalDate.parse(txtFecha.getText().trim());
            hora = LocalTime.parse(txtHora.getText().trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Formato inválido.\nFecha: AAAA-MM-DD  |  Hora: HH:mm:ss\n" + ex.getMessage(),
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (idSeleccionado == -1) {
                dao.crear(new Entrega(pedSel.getId(), repSel.getId(), fecha, hora));
                // Coherencia de negocio: el pedido pasa a EN_REPARTO
                pedidoDAO.actualizarEstado(pedSel.getId(), "EN_REPARTO");

                JOptionPane.showMessageDialog(this,
                        " Entrega registrada.\nEl pedido #" + pedSel.getId()
                                + " ahora está EN_REPARTO.");
            } else {
                dao.actualizar(new Entrega(idSeleccionado, pedSel.getId(), repSel.getId(), fecha, hora));
                JOptionPane.showMessageDialog(this, " Entrega actualizada.");
            }
            limpiarFormulario();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    " Error al registrar la entrega:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        private void marcarEntregado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una entrega de la tabla.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idEntrega = (int) modelo.getValueAt(fila, 0);
            Entrega e = dao.leerTodos().stream()
                    .filter(x -> x.getId() == idEntrega)
                    .findFirst().orElseThrow();

            pedidoDAO.actualizarEstado(e.getIdPedido(), "ENTREGADO");
            JOptionPane.showMessageDialog(this,
                    " Pedido #" + e.getIdPedido() + " marcado como ENTREGADO.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    " Error:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una fila de la tabla.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Eliminar la entrega ID " + idSeleccionado + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(idSeleccionado);
                JOptionPane.showMessageDialog(this, " Entrega eliminada.");
                limpiarFormulario();
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        " Error al eliminar:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void seleccionFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = (int) modelo.getValueAt(fila, 0);
            // Reflejar selección en combos (buscando por id)
            try {
                Entrega sel = dao.leerTodos().stream()
                        .filter(x -> x.getId() == idSeleccionado)
                        .findFirst().orElse(null);
                if (sel != null) {
                    seleccionarPorId(cbPedido, sel.getIdPedido());
                    seleccionarPorId(cbRepartidor, sel.getIdRepartidor());
                    txtFecha.setText(sel.getFecha().toString());
                    txtHora.setText(sel.getHora().toString());
                }
            } catch (Exception ignored) { }
        }
    }
    /** Helper: selecciona en el combo el item cuyo id coincida */
    private <T> void seleccionarPorId(JComboBox<T> combo, int idBuscado) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            T item = combo.getItemAt(i);
            int idItem = -1;

            if (item instanceof Pedido p) idItem = p.getId();
            else if (item instanceof Repartidor r) idItem = r.getId();

            if (idItem == idBuscado) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    //Limpia el formulario y vuelve al modo creación
    private void limpiarFormulario() {
        idSeleccionado = -1;
        txtFecha.setText(LocalDate.now().toString());
        txtHora.setText(LocalTime.now().withNano(0).toString());
        tabla.clearSelection();
    }
}