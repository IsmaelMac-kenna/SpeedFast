package cl.speedfast.model;


public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        double tiempoCalculado = 10 + (getDistanciaKm() * 2);
        return (int) Math.round(tiempoCalculado);
    }

    @Override
    public void asignarRepartidor() {
        setRepartidor("Repartidor Express");
    }
}