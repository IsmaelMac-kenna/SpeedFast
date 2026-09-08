package cl.speedfast.model;


public class PedidoExpress extends Pedido {
    private boolean prioridadAlta;

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm, boolean prioridadAlta) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.prioridadAlta = prioridadAlta;
    }

    @Override
    public double calcularTiempoEntrega() {
        return distanciaKm * 3;
    }
}