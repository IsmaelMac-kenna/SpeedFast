package cl.speedfast.model;

public class Pedido {
    private int id;
    private String direccion;
    private String tipo;
    private String estado;
    private String repartidor;

    public Pedido(int id, String direccion, String tipo) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = "PENDIENTE";
        this.repartidor = "-";
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getDireccion() { return direccion; }
    public String getTipo() { return tipo; }
    public String getEstado() { return estado; }
    public String getRepartidor() { return repartidor; }

    public void setEstado(String estado) { this.estado = estado; }
    public void setRepartidor(String repartidor) { this.repartidor = repartidor; }
}
