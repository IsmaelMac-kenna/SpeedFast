package cl.speedfast.model;

public class PedidoEncomienda extends Pedido {
    //ATRIBUTOS
    private double peso;
    private double volumen;
    //CONSTRUCTOR
    public PedidoEncomienda(String idPedido, String cliente, String direccionEntrega, double peso, double volumen) {
        super(idPedido, cliente, direccionEntrega);
        this.peso = peso;
        this.volumen = volumen;
    }
    //GET & SET
    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    //Sobreescritura
    @Override
    public String asignarRepartidor() {
        return "[Pedido Encomienda] \nAsignando repartidor...\n" +
                "→ Validando peso (" + peso + " kg) y embalaje... OK\n" +
                "→ Buscando vehículo adecuado...";
    }

    //Sobrecarga específica de la subclase
    public String asignarRepartidor(String repartidor, boolean pesoValidado) {
        if (pesoValidado) {
            return "[Pedido Encomienda] \nAsignando repartidor...\n" +
                    "→ Validando peso y embalaje... OK\n" +
                    "→ Pedido asignado a " + repartidor;
        } else {
            return "[Pedido Encomienda] Error: El paquete supera las especificaciones para " + repartidor;
        }
    }
}