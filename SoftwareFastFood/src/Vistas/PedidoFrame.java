package Vistas;

import Logica.LogicaPedido;
import Modelos.Pedido;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PedidoFrame extends JFrame {

    private LogicaPedido logica;
    private HistorialFrame historial;

    private JTextArea txtDescripcion, txtComentario;
    private JTextField txtDireccion, txtBillete, txtTotal;
    private JComboBox<String> cmbMetodoPago;
    private JButton btnConfirmar, btnReabrirHistorial;

    public PedidoFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        logica = new LogicaPedido();
        historial = new HistorialFrame();
        historial.setVisible(true);

        cmbMetodoPago.addActionListener(e -> {
            boolean efectivo = cmbMetodoPago.getSelectedItem().toString().equalsIgnoreCase("Efectivo");
            txtBillete.setEnabled(efectivo);
            if (!efectivo) {
                txtBillete.setText("");
            }
        });
        txtBillete.setEnabled(false);

    }

    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Registro de Pedido");
        setSize(400, 600);
        setLayout(null);

        JLabel lbl1 = new JLabel("Descripción del pedido:");
        lbl1.setBounds(20, 20, 200, 20);
        add(lbl1);

        txtDescripcion = new JTextArea();
        JScrollPane sp1 = new JScrollPane(txtDescripcion);
        sp1.setBounds(20, 40, 340, 60);
        add(sp1);

        JLabel lbl2 = new JLabel("Dirección de entrega:");
        lbl2.setBounds(20, 110, 200, 20);
        add(lbl2);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(20, 130, 340, 25);
        add(txtDireccion);

        JLabel lbl3 = new JLabel("Método de pago:");
        lbl3.setBounds(20, 160, 200, 20);
        add(lbl3);

        cmbMetodoPago = new JComboBox<>(new String[]{"Transferencia", "Efectivo"});
        cmbMetodoPago.setBounds(20, 180, 150, 25);
        add(cmbMetodoPago);

        JLabel lbl4 = new JLabel("Billete entregado:");
        lbl4.setBounds(20, 210, 200, 20);
        add(lbl4);

        txtBillete = new JTextField();
        txtBillete.setBounds(20, 230, 150, 25);
        add(txtBillete);

        JLabel lbl5 = new JLabel("Comentario adicional:");
        lbl5.setBounds(20, 260, 200, 20);
        add(lbl5);

        txtComentario = new JTextArea();
        JScrollPane sp2 = new JScrollPane(txtComentario);
        sp2.setBounds(20, 280, 340, 60);
        add(sp2);

        JLabel lbl6 = new JLabel("Total a pagar:");
        lbl6.setBounds(20, 350, 100, 20);
        add(lbl6);

        txtTotal = new JTextField();
        txtTotal.setBounds(20, 370, 100, 25);
        add(txtTotal);

        btnConfirmar = new JButton("Confirmar Pedido");
        btnConfirmar.setBounds(100, 420, 180, 30);
        btnConfirmar.addActionListener(e -> confirmarPedido());
        add(btnConfirmar);

        btnReabrirHistorial = new JButton("Mostrar Historial");
        btnReabrirHistorial.setBounds(100, 470, 180, 30);
        btnReabrirHistorial.addActionListener(e -> {
            if (historial == null || !historial.isDisplayable()) {
                historial = new HistorialFrame();
            }
            historial.setVisible(true);
        });
        add(btnReabrirHistorial);
    }

    private void confirmarPedido() {
        try {
            String descripcion = txtDescripcion.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String metodoPago = cmbMetodoPago.getSelectedItem().toString();
            String comentario = txtComentario.getText().trim();

            if (descripcion.isEmpty() || direccion.isEmpty() || txtTotal.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor completa todos los campos obligatorios.");
                return;
            }

            double total = Double.parseDouble(txtTotal.getText().trim());
            double billete = 0;
            double cambio = 0;

            Pedido pedido = new Pedido(descripcion, direccion, metodoPago, total, billete, comentario);
            logica.guardarPedido(pedido);

            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            Object[] fila = new Object[]{fecha, descripcion, direccion, metodoPago, total, comentario, 
                                        metodoPago.equals("Efectivo") ? billete : "-", 
                                        metodoPago.equals("Efectivo") ? cambio : "-", false};

            historial.agregarPedido(fila);

            JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa valores numéricos válidos en Total o Billete.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el pedido: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtDescripcion.setText("");
        txtDireccion.setText("");
        cmbMetodoPago.setSelectedIndex(0);
        txtBillete.setText("");
        txtComentario.setText("");
        txtTotal.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PedidoFrame().setVisible(true));
    }
}
