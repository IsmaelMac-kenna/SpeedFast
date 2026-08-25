package cl.speedfast.model;

public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        int tiempoBase = 10;
        if (distanciaKm > 5) {
            tiempoBase += 5;
        }
        return tiempoBase;
    }
}