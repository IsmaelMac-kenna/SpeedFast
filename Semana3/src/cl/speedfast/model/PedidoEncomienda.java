package cl.speedfast.model;


public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        double tiempoCalculado = 10 + (getDistanciaKm() * 3);
        return (int) Math.round(tiempoCalculado); // usamos lo que me recomendo en el feedback para no truncar decimales
    }

    @Override
    public void asignarRepartidor() {
        setRepartidor("Repartidor Encomiendas");
    }
}