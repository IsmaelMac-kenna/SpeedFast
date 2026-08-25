package cl.speedfast;

import cl.speedfast.model.*;

public class Main {
    public static void main(String[] args) {


        // Arreglo para iterar y mostrar el comportamiento polimórfico
        Pedido[] pedidos = {

        // Instanciación utilizando polimorfismo
        new PedidoComida(   1,
                            "Paseo Ahumada 56",
                            5.0),
        new PedidoEncomienda(  2,
                               "Antonio Varas 153",
                               10.0),
        new PedidoExpress(  3,
                            "Duble Almeida 414",
                            3.0),
        };
        for (Pedido p : pedidos) {
            p.mostrarResumen();
        }
    }
}