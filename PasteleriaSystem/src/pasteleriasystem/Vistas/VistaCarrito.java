package pasteleriasystem.Vistas;
 
import pasteleriasystem.Datos.PedidoDAO;
import pasteleriasystem.Datos.ProductoDAO;
import pasteleriasystem.Modelo.CarritoPedidos.Carrito;
import pasteleriasystem.Modelo.CarritoPedidos.ItemCarrito;
import pasteleriasystem.Modelo.CarritoPedidos.Pedido;
import pasteleriasystem.Modelo.Pagos.Pago;
import pasteleriasystem.Modelo.Pagos.PagoEfectivo;
import pasteleriasystem.Modelo.Pagos.PagoTarjeta;
import pasteleriasystem.Modelo.Productos.Producto;
import pasteleriasystem.Modelo.Usuarios.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VistaCarrito extends JFrame {

    private final Cliente       cliente;
    private final Carrito       carrito;
    private final ArrayList<Producto> productos;
    private final Catalogo      catalogo;

    private DefaultTableModel   modeloTabla;
    private JLabel              lblTotal;

    public VistaCarrito(Cliente cliente, Carrito carrito,
                        ArrayList<Producto> productos, Catalogo catalogo) {
        this.cliente   = cliente;
        this.carrito   = carrito;
        this.productos = productos;
        this.catalogo  = catalogo;

        setTitle("Pasteleria — Mi carrito");
        setSize(680, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        construirUI();
    }

    private void construirUI() {
        getContentPane().setBackground(new Color(245, 243, 240));
        setLayout(new BorderLayout(10, 10));

        // ── Encabezado ────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(29, 158, 117));
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel titulo = new JLabel("Mi Carrito");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);

        // ── Tabla ─────────────────────────────────────────────
        String[] cols = {"Producto", "Categoria", "Precio unit.", "Cantidad", "Subtotal"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setSelectionBackground(new Color(200, 235, 220));
        cargarTabla(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // ── Panel inferior ────────────────────────────────────
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
        footer.setBackground(new Color(245, 243, 240));

        lblTotal = new JLabel("Total: $" + String.format("%.0f", carrito.calcularTotal()));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setForeground(new Color(29, 158, 117));

        JButton btnEliminar  = btn("Eliminar seleccionado", new Color(180, 60, 60));
        JButton btnConfirmar = btn("Confirmar pedido",      new Color(29, 158, 117));

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona un item."); return; }
            String nombre = (String) modeloTabla.getValueAt(fila, 0);
            // buscar id por nombre
            for (ItemCarrito item : carrito.getItems()) {
                if (item.getProducto().getNombre().equals(nombre)) {
                    carrito.eliminarProducto(item.getProducto().getId());
                    break;
                }
            }
            cargarTabla(tabla);
            actualizarTotal();
            if (carrito.estaVacio()) dispose();
        });

        btnConfirmar.addActionListener(e -> confirmarPedido());

        footer.add(lblTotal);
        footer.add(btnEliminar);
        footer.add(btnConfirmar);

        add(header, BorderLayout.NORTH);
        add(scroll,  BorderLayout.CENTER);
        add(footer,  BorderLayout.SOUTH);
    }

    private void cargarTabla(JTable tabla) {
        modeloTabla.setRowCount(0);
        for (ItemCarrito item : carrito.getItems()) {
            Producto p = item.getProducto();
            modeloTabla.addRow(new Object[]{
                p.getNombre(), p.getCategoria(),
                "$" + String.format("%.0f", p.getPrecio()),
                item.getCantidad(),
                "$" + String.format("%.0f", item.getSubtotal())
            });
        }
    }

    private void actualizarTotal() {
        lblTotal.setText("Total: $" + String.format("%.0f", carrito.calcularTotal()));
        catalogo.actualizarTotal();
    }

    private void confirmarPedido() {
        // Selección de método de pago
        String[] metodos = {"Efectivo", "Tarjeta"};
        String metodo = (String) JOptionPane.showInputDialog(
            this, "Selecciona método de pago:", "Método de pago",
            JOptionPane.QUESTION_MESSAGE, null, metodos, metodos[0]);

        if (metodo == null) return;

        // ABSTRACCIÓN + POLIMORFISMO: usar clase abstracta Pago
        Pago pago;
        if ("Tarjeta".equals(metodo)) {
            String digitos = JOptionPane.showInputDialog(this, "Últimos 4 dígitos de tu tarjeta:");
            if (digitos == null || digitos.length() != 4) {
                JOptionPane.showMessageDialog(this, "Dígitos inválidos."); return;
            }
            pago = new PagoTarjeta(carrito.calcularTotal(), digitos);
        } else {
            pago = new PagoEfectivo(carrito.calcularTotal());
        }

        // Reducir stock de cada producto
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            for (ItemCarrito item : carrito.getItems()) {
                item.getProducto().reducirStock(item.getCantidad());
            }
            productoDAO.guardarProductos(productos);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardar pedido
        PedidoDAO pedidoDAO = new PedidoDAO();
        String id = pedidoDAO.generarId();
        Pedido pedido = new Pedido(id, cliente.getEmail(),
                                  carrito.getItems(), carrito.calcularTotal(), pago.getTipo());
        pedidoDAO.guardarPedido(pedido);
        cliente.agregarPedido(pedido);

        JOptionPane.showMessageDialog(this,
            pago.procesar() + "\n\nPedido " + id + " confirmado.\nGracias por tu compra 🎂");

        carrito.vaciar();
        catalogo.actualizarTotal();
        dispose();
    }

    private JButton btn(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}
