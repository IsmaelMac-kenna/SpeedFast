package cl.speedfast.model;

import cl.speedfast.interfaces.Rastreable;
import java.util.ArrayList;
import java.util.List;

public class ControladorDeEnvios implements Rastreable {
    private List<Pedido> historial;

    public ControladorDeEnvios() {
        this.historial = new ArrayList<>();
    }

    public void registrarEntrega(Pedido pedido) {
        if (!pedido.isCancelado()) {
            historial.add(pedido);
        }
    }

    @Override
    public void verHistorial() {
        System.out.println("Historial:");
        for (Pedido p : historial) {
            System.out.println("- " + p.getClass().getSimpleName() +
                    " #" + p.getIdPedido() +
                    " - entregado por " +
                    p.getRepartidor());
        }
    }
}