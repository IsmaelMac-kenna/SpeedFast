package cl.speedfast.vistas;

import cl.speedfast.controlador.PedidoController;
import cl.speedfast.model.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {
    private final PedidoController controller;
    private JTable tablaPedidos;
    private DefaultTableModel model;

    public VentanaListaPedidos(PedidoController controller) {
        this.controller = controller;

        setTitle("Listado de Pedidos");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Configuración del modelo y JTable
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado", "Repartidor"};
        model = new DefaultTableModel(columnas, 0);
        tablaPedidos = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        add(scrollPane, BorderLayout.CENTER);

        // Botón Actualizar
        JButton btnActualizar = new JButton("Actualizar Tabla");
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.add(btnActualizar);
        add(panelInferior, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargarDatos());

        // Cargar datos al abrir
        cargarDatos();
    }

    public void cargarDatos() {
        model.setRowCount(0); // Limpiar filas anteriores
        for (Pedido p : controller.getListaPedidos()) {
            Object[] fila = {
                    p.getId(),
                    p.getDireccion(),
                    p.getTipo(),
                    p.getEstado(),
                    p.getRepartidor()
            };
            model.addRow(fila);
        }
    }
}