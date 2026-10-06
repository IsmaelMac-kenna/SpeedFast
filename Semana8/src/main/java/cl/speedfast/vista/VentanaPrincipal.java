package cl.speedfast.vista;

import javax.swing.*;
import java.awt.*;


public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("SpeedFast - Sistema de Gestión de Entregas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla

        JTabbedPane pestanas = new JTabbedPane();

        // Agregar cada panel como una pestaña
        pestanas.addTab("Repartidores", new PanelRepartidores());
        pestanas.addTab("Pedidos", new PanelPedidos());
        pestanas.addTab("Entregas", new PanelEntregas());

        add(pestanas);
        //refresca al abrir esta pestaña
        pestanas.addChangeListener(e -> {
            Component sel = pestanas.getSelectedComponent();
            if (sel instanceof PanelEntregas pe) {
                pe.cargarCombos();
            }
        });
    }

    public static void main(String[] args) {
        // Ejecutar la interfaz en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}