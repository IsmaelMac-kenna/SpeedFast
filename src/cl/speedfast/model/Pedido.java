package cl.speedfast.model;

public abstract class Pedido {
    protected int idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;

    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    //metodo abstracto para cadaSubClase
    public abstract int calcularTiempoEntrega();

    //metodo para mostrar en consola
    public void mostrarResumen() {
        System.out.println(getClass().getSimpleName() + " #" + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + (int) distanciaKm + " km");
        System.out.println("Tiempo estimado de entrega: " + calcularTiempoEntrega() + " minutos");
        System.out.println();
    }

}