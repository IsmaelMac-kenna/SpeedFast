package cl.speedfast;

import cl.speedfast.model.*;

public class Main {
    public static void main(String[] args) {
        ControladorDeEnvios gestor = new ControladorDeEnvios();

        //Creamos Pedidos
        PedidoComida p1 = new PedidoComida(1,
                "Paseo Ahumada 56",
                5);
        PedidoEncomienda p2 = new PedidoEncomienda(2,
                "Antonio Varas 153",
                10);
        PedidoExpress p3 = new PedidoExpress(3,
                "Duble Almeida 414",
                3);

        //Asignaciones (Sobrecarga y Sobrescritura)
        p1.asignarRepartidor("Ricado Arjona");
        p2.asignarRepartidor("Uber Mamani");
        p3.asignarRepartidor("Ismael Mac-kenna");

        //Simulación de salida para Pedido Encomienda
        System.out.println("\n[Pedido Encomienda]");
        p2.mostrarResumen();
        p2.despachar();
        gestor.registrarEntrega(p2); // Registramos para ejemplo en el historial
        gestor.registrarEntrega(p1);

        System.out.println();

        //Cancelación de Pedido Express
        p3.cancelar();

        System.out.println();

        //Visualización del Historial
        gestor.verHistorial();
    }
}