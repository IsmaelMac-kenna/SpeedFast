package cl.speedfast.main;

import cl.speedfast.controlador.PedidoController;
import cl.speedfast.vistas.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PedidoController controller = new PedidoController();
            VentanaPrincipal ventana = new VentanaPrincipal(controller);
            ventana.setVisible(true);
        });
    }
}