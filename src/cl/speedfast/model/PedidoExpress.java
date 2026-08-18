package cl.speedfast.model;

public class PedidoExpress extends Pedido {
    //ATRIBUTOS
    private String tienda;
    private double distancia;
    //CONSTRUCTOR
    public PedidoExpress(String idPedido, String cliente, String direccionEntrega, String tienda, double distancia) {
        super(idPedido, cliente, direccionEntrega);
        this.tienda = tienda;
        this.distancia = distancia;
    }
    //GET & SET
    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    //Sobreescritura
    @Override
    public String asignarRepartidor() {
        return "[Pedido Express] \nAsignando repartidor...\n" +
                "→ Repartidor más cercano a " + tienda + " con disponibilidad inmediata encontrado.";
    }

    //Sobrecarga específica de la subclase
    public String asignarRepartidor(String repartidor, int horaLimiteMinutos) {
        return "[Pedido Express] \nAsignando repartidor...\n" +
                "→ Repartidor más cercano con disponibilidad inmediata encontrado (Garantía " + horaLimiteMinutos + " min).\n" +
                "→ Pedido asignado a " + repartidor;
    }
}