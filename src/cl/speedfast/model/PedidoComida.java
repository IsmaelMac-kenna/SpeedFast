package cl.speedfast.model;


public class PedidoComida extends Pedido {

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        double tiempoCalculado = 15 + (getDistanciaKm() * 3);
        return (int) Math.round(tiempoCalculado);
    }

    @Override
    public void asignarRepartidor() {
        setRepartidor("Repartidor Comida Express");
    }
}