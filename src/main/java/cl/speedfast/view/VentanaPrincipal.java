package cl.speedfast.view;

import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private JTextField txtDireccion, txtRepartidorNombre;
    private JComboBox<String> cbTipo, cbEstado;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private RepartidorDAO repartidorDAO = new RepartidorDAO();

    public VentanaPrincipal() {
        setTitle("SpeedFast - Gestión de Entregas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Superior: Formularios
        JPanel panelFormularios = new JPanel(new GridLayout(2, 1));

        // Formulario Registrar Pedido
        JPanel panelPedido = new JPanel(new FlowLayout());
        panelPedido.setBorder(BorderFactory.createTitledBorder("Registrar Pedido"));
        txtDireccion = new JTextField(15);
        cbTipo = new JComboBox<>(new String[]{"COMIDA", "ENCOMIENDA", "EXPRESS"});
        cbEstado = new JComboBox<>(new String[]{"PENDIENTE", "EN_REPARTO", "ENTREGADO"});
        JButton btnGuardarPedido = new JButton("Guardar Pedido");

        panelPedido.add(new JLabel("Dirección:"));
        panelPedido.add(txtDireccion);
        panelPedido.add(new JLabel("Tipo:"));
        panelPedido.add(cbTipo);
        panelPedido.add(new JLabel("Estado:"));
        panelPedido.add(cbEstado);
        panelPedido.add(btnGuardarPedido);

        // Formulario Registrar Repartidor
        JPanel panelRepartidor = new JPanel(new FlowLayout());
        panelRepartidor.setBorder(BorderFactory.createTitledBorder("Registrar Repartidor"));
        txtRepartidorNombre = new JTextField(15);
        JButton btnGuardarRepartidor = new JButton("Guardar Repartidor");

        panelRepartidor.add(new JLabel("Nombre:"));
        panelRepartidor.add(txtRepartidorNombre);
        panelRepartidor.add(btnGuardarRepartidor);

        panelFormularios.add(panelPedido);
        panelFormularios.add(panelRepartidor);
        add(panelFormularios, BorderLayout.NORTH);

        // Panel Central: Tabla JTable
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Dirección", "Tipo", "Estado"}, 0);
        tablaPedidos = new JTable(modeloTabla);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        // Panel Inferior: Botón Actualizar
        JPanel panelBotones = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Tabla");
        panelBotones.add(btnActualizar);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnGuardarPedido.addActionListener(e -> registrarPedido());
        btnGuardarRepartidor.addActionListener(e -> registrarRepartidor());
        btnActualizar.addActionListener(e -> cargarDatosTabla());

        // Carga inicial
        cargarDatosTabla();
    }

    private void registrarPedido() {
        String dir = txtDireccion.getText().trim();
        if (dir.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una dirección.");
            return;
        }
        Pedido p = new Pedido(dir, cbTipo.getSelectedItem().toString(), cbEstado.getSelectedItem().toString());
        if (pedidoDAO.guardar(p)) {
            JOptionPane.showMessageDialog(this, "Pedido registrado con éxito.");
            txtDireccion.setText("");
            cargarDatosTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el pedido.");
        }
    }

    private void registrarRepartidor() {
        String nom = txtRepartidorNombre.getText().trim();
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del repartidor.");
            return;
        }
        if (repartidorDAO.guardar(new Repartidor(nom))) {
            JOptionPane.showMessageDialog(this, "Repartidor registrado.");
            txtRepartidorNombre.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar repartidor.");
        }
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        List<Pedido> lista = pedidoDAO.listarTodos();
        for (Pedido p : lista) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getDireccion(), p.getTipo(), p.getEstado()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}