package cl.speedfast.vistas;

import cl.speedfast.controlador.PedidoController;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private final PedidoController controller;

    public VentanaPrincipal(PedidoController controller) {
        this.controller = controller;

        setTitle("SpeedFast - Menú Principal");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Encabezado
        JLabel lblTitulo = new JLabel("SpeedFast - Gestión de Entregas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel Central con Grid
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton btnRegistrar = new JButton("Registrar Pedido");
        JButton btnListar = new JButton("Listar Pedidos");
        JButton btnAsignar = new JButton("Asignar Repartidor");
        JButton btnIniciar = new JButton("Iniciar Entregas");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnAsignar);
        panelBotones.add(btnIniciar);

        add(panelBotones, BorderLayout.CENTER);

        // Eventos de los botones
        btnRegistrar.addActionListener(e -> {
            VentanaRegistroPedido vRegistro = new VentanaRegistroPedido(controller);
            vRegistro.setVisible(true);
        });

        btnListar.addActionListener(e -> {
            VentanaListaPedidos vLista = new VentanaListaPedidos(controller);
            vLista.setVisible(true);
        });

        btnAsignar.addActionListener(e -> {
            VentanaAsignarRepartidor vAsignar = new VentanaAsignarRepartidor(controller);
            vAsignar.setVisible(true);
        });

        btnIniciar.addActionListener(e -> {
            int activados = controller.iniciarEntregas();
            if (activados > 0) {
                JOptionPane.showMessageDialog(this,
                        "¡Entregas iniciadas exitosamente!\nSe cambiaron " + activados + " pedido(s) a estado EN_REPARTO.",
                        "Simulación de Entregas",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No hay pedidos con repartidor asignado en estado PENDIENTE.",
                        "Información",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}