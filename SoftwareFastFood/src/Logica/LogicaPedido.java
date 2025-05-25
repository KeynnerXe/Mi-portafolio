/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author Keinner Ramos
 */
import Modelos.Pedido;
import java.util.ArrayList;

public class LogicaPedido {
    private ArrayList<Pedido> pedidos;

    public LogicaPedido() {
        pedidos = new ArrayList<>();
    }

    public void guardarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public double calcularCambio(Pedido pedido) {
        return pedido.calcularCambio();
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }
}
