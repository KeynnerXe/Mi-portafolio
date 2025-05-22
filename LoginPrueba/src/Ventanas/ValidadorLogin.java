/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventanas;

import javax.swing.JOptionPane;

public class ValidadorLogin {

    public static void validar(String usuario, String contraseña) {
        if (usuario.equals("Admin") && contraseña.equals("12345")) {
            JOptionPane.showMessageDialog(null, "Bienvenido, usuario :)");
        } else {
            JOptionPane.showMessageDialog(null, "Datos incorrectos");
        }
    }
}