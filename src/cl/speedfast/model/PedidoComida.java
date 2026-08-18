package cl.speedfast.model;

public class PedidoComida extends Pedido {
    //ATRIBUTOS
    private String restaurante;
    private int tiempoPreparacion;
    //CONSTRUCTOR
    public PedidoComida(String idPedido, String cliente, String direccionEntrega, String restaurante, int tiempoPreparacion) {
        super(idPedido, cliente, direccionEntrega);
        this.restaurante = restaurante;
        this.tiempoPreparacion = tiempoPreparacion;
    }
    //GET & SET
    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    //Sobreescritura
    @Override
    public String asignarRepartidor() {
        return "[Pedido Comida] \nAsignando repartidor...\n" +
                "→ Verificando mochila térmica... OK\n" +
                "→ Buscando repartidor cercano a " + restaurante + "...";
    }

    //Sobrecarga específica de la subclase
    public String asignarRepartidor(String repartidor, boolean requiereMochilaTermica) {
        if (requiereMochilaTermica) {
            return "[Pedido Comida] \nAsignando repartidor...\n" +
                    "→ Verificando mochila térmica... OK\n" +
                    "→ Pedido asignado a " + repartidor;
        } else {
            return "[Pedido Comida] Error: El repartidor " + repartidor + " no cuenta con mochila térmica.";
        }
    }
}