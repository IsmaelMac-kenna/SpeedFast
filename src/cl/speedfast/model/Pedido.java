package cl.speedfast.model;

public class Pedido {
    //ATRIBUTOS
    private String idPedido;
    private String cliente;
    private String direccionEntrega;
    //CONSTRUCTOR
    public Pedido(String idPedido, String cliente, String direccionEntrega) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.direccionEntrega = direccionEntrega;
    }
    //GET & SET
    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    //Metodo para hacer la base del Override
    public String asignarRepartidor() {
        return "Asignando repartidor genérico para el pedido " + idPedido + "...";
    }

    //Metodo Base para la sobrecarga
    public String asignarRepartidor(String repartidor) {
        return "Pedido " + idPedido + " asignado manualmente a " + repartidor + ".";
    }
}