package cl.speedfast.model;


public class Pedido {
    private final String idPedido;
    private final String cliente;
    private final String direccion;
    private EstadoPedido estado;

    public Pedido(String idPedido, String cliente, String direccion) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.direccion = direccion;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public String getIdPedido() {
        return idPedido;
    }
    public String getCliente() {
        return cliente;
    }
    public String getDireccion() {
        return direccion;
    }



    public synchronized EstadoPedido getEstado() {
        return estado;
    }
    public synchronized void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format("Pedido %s (Destino: %s)", idPedido, direccion);
    }
}