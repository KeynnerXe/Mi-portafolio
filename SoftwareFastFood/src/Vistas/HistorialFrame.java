package Vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HistorialFrame extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JButton btnExportarTxt, btnCalcularTotal, btnCalcularGanancia, btnEliminarPedido;
    private JLabel lblGanancia;
    private JTextField txtInversion, txtNombreUsuario;

    public HistorialFrame() {
        setTitle("Historial de Pedidos");
        setSize(1000, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        modeloTabla = new DefaultTableModel(new Object[]{"Fecha", "Descripción", "Dirección", "Método de Pago", "Total", "Comentario", "Billete", "Cambio", "Entregado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 8 ? Boolean.class : String.class;
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setRowHeight(50);
        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        scrollPane.setBounds(10, 10, 960, 300);
        add(scrollPane);

        JLabel lblNombre = new JLabel("Usuario actual:");
        lblNombre.setBounds(10, 320, 100, 25);
        add(lblNombre);

        txtNombreUsuario = new JTextField();
        txtNombreUsuario.setBounds(110, 320, 150, 25);
        add(txtNombreUsuario);

        btnExportarTxt = new JButton("Exportar a .txt");
        btnExportarTxt.setBounds(270, 320, 150, 30);
        btnExportarTxt.addActionListener(e -> exportarATxt());
        add(btnExportarTxt);

        btnCalcularTotal = new JButton("Calcular Ventas");
        btnCalcularTotal.setBounds(430, 320, 150, 30);
        btnCalcularTotal.addActionListener(e -> calcularVentas());
        add(btnCalcularTotal);

        JLabel lblInversion = new JLabel("Inversión:");
        lblInversion.setBounds(10, 360, 80, 25);
        add(lblInversion);

        txtInversion = new JTextField();
        txtInversion.setBounds(90, 360, 100, 25);
        add(txtInversion);

        btnCalcularGanancia = new JButton("Calcular Ganancia");
        btnCalcularGanancia.setBounds(200, 360, 170, 30);
        btnCalcularGanancia.addActionListener(e -> calcularGanancia());
        add(btnCalcularGanancia);

        btnEliminarPedido = new JButton("Eliminar Pedido");
        btnEliminarPedido.setBounds(380, 360, 150, 30);
        btnEliminarPedido.addActionListener(e -> eliminarPedido());
        add(btnEliminarPedido);

        lblGanancia = new JLabel("Ganancia: $0.00");
        lblGanancia.setBounds(10, 400, 960, 25);
        add(lblGanancia);

        setLayout(null);
    }

    public void agregarPedido(Object[] fila) {
        modeloTabla.addRow(fila);
    }

    private void exportarATxt() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historial_pedidos.txt"))) {
            writer.write("Usuario: " + txtNombreUsuario.getText().trim());
            writer.newLine();
            writer.newLine();
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                if ((Boolean) modeloTabla.getValueAt(i, 8)) {
                    for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                        String valor = modeloTabla.getValueAt(i, j) != null ? modeloTabla.getValueAt(i, j).toString() : "";
                        writer.write(valor);
                        writer.write("\t");
                    }
                    writer.newLine();
                }
            }
            JOptionPane.showMessageDialog(this, "Exportación completada: historial_pedidos.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage());
        }
    }

    private void calcularVentas() {
        double total = 0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if ((Boolean) modeloTabla.getValueAt(i, 8)) {
                total += Double.parseDouble(modeloTabla.getValueAt(i, 4).toString());
            }
        }
        JOptionPane.showMessageDialog(this, "Total de ventas entregadas: $" + String.format("%.2f", total));
    }

    private void calcularGanancia() {
        try {
            double totalTransferencia = 0;
            double totalEfectivo = 0;

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                if ((Boolean) modeloTabla.getValueAt(i, 8)) {
                    String metodo = modeloTabla.getValueAt(i, 3).toString();
                    double valor = Double.parseDouble(modeloTabla.getValueAt(i, 4).toString());
                    if (metodo.equalsIgnoreCase("Transferencia")) {
                        totalTransferencia += valor;
                    } else if (metodo.equalsIgnoreCase("Efectivo")) {
                        totalEfectivo += valor;
                    }
                }
            }

            double total = totalTransferencia + totalEfectivo;
            double inversion = Double.parseDouble(txtInversion.getText().trim());
            double ganancia = total - inversion;

            lblGanancia.setText("Ganancia: $" + String.format("%.2f", ganancia) +
                    " (Transferencia: $" + String.format("%.2f", totalTransferencia) +
                    ", Efectivo: $" + String.format("%.2f", totalEfectivo) + ")");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa una inversión válida.");
        }
    }

    private void eliminarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este pedido?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                modeloTabla.removeRow(filaSeleccionada);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un pedido para eliminar.");
        }
    }
}
