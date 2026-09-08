package cl.speedfast.app;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cl.speedfast.model.*;
import cl.speedfast.tareas.Repartidor;
import cl.speedfast.interfaces.*;

public class Main {
    public static void main(String[] args) {
        //Creación de datos de prueba
        List<Pedido> pedidosFrancisca = new ArrayList<>();
        pedidosFrancisca.add(new PedidoComida(101, "Paseo Ahumada 56", 3.2, true));
        pedidosFrancisca.add(new PedidoComida(102, "Pedro de Valdivia 567", 2.0, false));

        List<Pedido> pedidosRoberto = new ArrayList<>();
        pedidosRoberto.add(new PedidoExpress(201, "Antonio Varas 153", 5.5, true));
        pedidosRoberto.add(new PedidoExpress(202, "Vitacura 4321", 4.1, true));

        List<Pedido> pedidosGenaro = new ArrayList<>();
        pedidosGenaro.add(new PedidoEncomienda(301, "Duble Almeida 414", 8.0, 3.5));
        pedidosGenaro.add(new PedidoEncomienda(302, "San Martín 222", 6.2, 1.2));

        //Instanciación de los repartidores (Runnables)
        Repartidor repartidor1 = new Repartidor("Francisca", pedidosFrancisca);
        Repartidor repartidor2 = new Repartidor("Roberto", pedidosRoberto);
        Repartidor repartidor3 = new Repartidor("Genaro", pedidosGenaro);

        //Ejecución concurrente usando ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("=== INICIANDO SIMULACIÓN DE ENTREGAS SPEEDFAST ===");
        System.out.println("--------------------------------------------------");

        executor.submit(repartidor1);
        executor.submit(repartidor2);
        executor.submit(repartidor3);

        executor.shutdown();

        try {
            //Espera a que todos los hilos terminen su trabajo
            if (executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("--------------------------------------------------");
                System.out.println("Todos los repartidores han finalizado sus entregas.");
            } else {
                System.out.println("El tiempo de espera finalizó antes de terminar todas las entregas.");
            }
        } catch (InterruptedException e) {
            System.err.println("Error durante la espera de finalización del pool de hilos.");
            Thread.currentThread().interrupt();
        }
    }
}