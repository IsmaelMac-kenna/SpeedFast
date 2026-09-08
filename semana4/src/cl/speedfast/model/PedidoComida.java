package cl.speedfast.model;


public class PedidoComida extends Pedido {
    private boolean requiereMantenerCaliente;

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm, boolean requiereMantenerCaliente) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.requiereMantenerCaliente = requiereMantenerCaliente;
    }

    @Override
    public double calcularTiempoEntrega() {
        return (distanciaKm * 5) + 10;
    }
}