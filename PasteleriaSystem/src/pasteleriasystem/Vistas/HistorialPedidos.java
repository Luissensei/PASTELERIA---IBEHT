package pasteleriasystem.Vistas;

import pasteleriasystem.Datos.PedidoDAO;
import pasteleriasystem.Modelo.CarritoPedidos.*;
import pasteleriasystem.Modelo.Usuarios.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HistorialPedidos extends JFrame {

    public HistorialPedidos(Cliente cliente) {
        setTitle("Mis Pedidos — " + cliente.getNombre());
        setSize(640, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        construirUI(cliente);
    }

    private void construirUI(Cliente cliente) {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 243, 240));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(29, 158, 117));
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel titulo = new JLabel("Historial de Pedidos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);

        String[] cols = {"ID Pedido", "Fecha", "Total", "Método de Pago", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        PedidoDAO dao = new PedidoDAO();
        ArrayList<Pedido> pedidos = dao.pedidosDeCliente(cliente.getEmail());

        if (pedidos.isEmpty()) {
            JLabel vacio = new JLabel("No tienes pedidos aún.", SwingConstants.CENTER);
            vacio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            vacio.setForeground(new Color(130, 128, 122));
            add(header,  BorderLayout.NORTH);
            add(vacio,   BorderLayout.CENTER);
            return;
        }

        for (Pedido p : pedidos) {
            modelo.addRow(new Object[]{
                p.getId(), p.getFechaHora(),
                "$" + String.format("%.0f", p.getTotal()),
                p.getMetodoPago(), p.getEstado()
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        add(header,                    BorderLayout.NORTH);
        add(new JScrollPane(tabla),    BorderLayout.CENTER);
    }
}
