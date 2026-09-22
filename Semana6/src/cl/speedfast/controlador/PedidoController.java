package cl.speedfast.controlador;

import cl.speedfast.model.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoController {
    private final List<Pedido> listaPedidos;
    private final List<Repartidor> listaRepartidores;

    public PedidoController() {
        this.listaPedidos = new ArrayList<>();
        this.listaRepartidores = new ArrayList<>();

        // Repartidores de prueba
        listaRepartidores.add(new Repartidor(01, "Alberto"));
        listaRepartidores.add(new Repartidor(02, "Bruno"));
        listaRepartidores.add(new Repartidor(03, "Carlos"));

        // Pedidos de prueba iniciales
        listaPedidos.add(new Pedido(1, "Callejon Diagon 934", "Comida"));
        listaPedidos.add(new Pedido(2, "Vivaceta 541", "Express"));
        listaPedidos.add(new Pedido(3, "San Isidro 45", "Encomienda"));
    }

    public void agregarPedido(Pedido pedido) {
        listaPedidos.add(pedido);
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public List<Repartidor> getListaRepartidores() {
        return listaRepartidores;
    }

    public boolean existeId(int id) {
        return listaPedidos.stream().anyMatch(p -> p.getId() == id);
    }

    // Método para asignar repartidor a un pedido
    public boolean asignarRepartidor(int idPedido, Repartidor repartidor) {
        for (Pedido p : listaPedidos) {
            if (p.getId() == idPedido) {
                p.setRepartidor(repartidor.getNombre());
                return true;
            }
        }
        return false;
    }

    // Método para simular el inicio de las entregas
    public int iniciarEntregas() {
        int pedidosIniciados = 0;
        for (Pedido p : listaPedidos) {
            // Solo inician los pedidos que tienen un repartidor asignado y están pendientes
            if (!p.getRepartidor().equals("-") && p.getEstado().equals("PENDIENTE")) {
                p.setEstado("EN_REPARTO");
                pedidosIniciados++;
            }
        }
        return pedidosIniciados;
    }
}