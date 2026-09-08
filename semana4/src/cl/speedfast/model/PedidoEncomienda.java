package cl.speedfast.model;


public class PedidoEncomienda extends Pedido {
    private double pesoKg;

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm, double pesoKg) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.pesoKg = pesoKg;
    }

    @Override
    public double calcularTiempoEntrega() {
        return (distanciaKm * 4) + (pesoKg * 2);
    }
}