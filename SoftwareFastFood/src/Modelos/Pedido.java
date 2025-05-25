/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author Keinner Ramos
 */
public class Pedido {
    private String descripcion;
    private String direccion;
    private String metodoPago;
    private double total;
    private double billete;
    private String comentario;

    public Pedido(String descripcion, String direccion, String metodoPago,
                  double total, double billete, String comentario) {
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.metodoPago = metodoPago;
        this.total = total;
        this.billete = billete;
        this.comentario = comentario;
    }

    public double calcularCambio() {
        if (metodoPago.equalsIgnoreCase("Efectivo")) {
            return billete - total;
        }
        return 0;
    }

    // Getters (opcional si los usas)
    public String getDescripcion() { return descripcion; }
    public String getDireccion() { return direccion; }
    public String getMetodoPago() { return metodoPago; }
    public double getTotal() { return total; }
    public double getBillete() { return billete; }
    public String getComentario() { return comentario; }
}