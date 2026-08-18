package cl.speedfast;
/**
        * Clase base abstracta o genérica que representa un pedido dentro del sistema SpeedFast.
 * Contiene los atributos comunes de todos los pedidos y la definición básica
 * de los métodos de asignación de repartidores.
 * * @author Ismael
 * @version 1.0
        */

import cl.speedfast.model.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Polimorfismo mediante referencias de la clase base
        List<Pedido> listaPedidos = new ArrayList<>();
        //Creamos 3 instancias para ejecutar en la consola
        PedidoComida comida = new PedidoComida("PeCo-001", "Ana Gómez", "Av. Providencia 1234", "Sushi House", 20);
        PedidoEncomienda encomienda = new PedidoEncomienda("PeEn-002", "Carlos Ruiz", "Calle Las Condes 567", 3.5, 0.2);
        PedidoExpress express = new PedidoExpress("PeEx-003", "María López", "Av. Italia 890", "Farmacia Cruz Verde", 1.8);

        listaPedidos.add(comida);
        listaPedidos.add(encomienda);
        listaPedidos.add(express);
        //Aqui usamos el polimorfismo para mostrar en consola el resultado de la sobre escritura
        System.out.println("=== SPEEDFAST SYSTEM ===\n");
        for (Pedido p : listaPedidos) {
            System.out.println(p.asignarRepartidor());
            System.out.println("---------------------------------------------------");
        }
        //Ejemplo de la sobrecargar usando los datos del ejemplo
        System.out.println("\n=== DEMOSTRACION SOBRECARGA ===\n");
        System.out.println(comida.asignarRepartidor("Juan Pérez", true));
        System.out.println();
        System.out.println(encomienda.asignarRepartidor("Camila Soto", true));
        System.out.println();
        System.out.println(express.asignarRepartidor("Luis Díaz", 15));
    }
}