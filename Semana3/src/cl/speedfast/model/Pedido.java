package cl.speedfast.model;

import cl.speedfast.interfaces.Cancelable;
import cl.speedfast.interfaces.Despachable;


public abstract class Pedido implements Despachable, Cancelable {
    // Encapsulamiento con private
    private int idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private String repartidor;
    private boolean cancelado;

    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.repartidor = "Sin asignar";
        this.cancelado = false;
    }

    // Getters y Setters para permitir acceso controlado
    public int getIdPedido() {
        return idPedido; }
    public String getDireccionEntrega() {
        return direccionEntrega; }
    public double getDistanciaKm() {
        return distanciaKm; }
    public String getRepartidor() {
        return repartidor; }
    protected void setRepartidor(String repartidor) {
        this.repartidor = repartidor; }
    public boolean isCancelado() {
        return cancelado; }

    public abstract int calcularTiempoEntrega();

    public void asignarRepartidor() {
        this.repartidor = "Asignación Automática";
    }

    public void asignarRepartidor(String nombre) {
        this.repartidor = nombre;
    }

    public void mostrarResumen() {
        System.out.println("Pedido #" + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + (int)distanciaKm + " km");
        System.out.println("Repartidor asignado: " + repartidor);
        System.out.println("Tiempo estimado: " + calcularTiempoEntrega() + " minutos");
    }

    @Override
    public void despachar() {
        if (!cancelado) {
            System.out.println("Pedido despachado correctamente.");
        }
    }

    @Override
    public void cancelar() {
        this.cancelado = true;
        System.out.println("Cancelando " + this.getClass().getSimpleName() + " #" + idPedido + "...");
        System.out.println("-> Pedido cancelado exitosamente.");
    }


}