package pasteleriasystem.Vistas;
 
import pasteleriasystem.Datos.ProductoDAO;
import pasteleriasystem.Modelo.CarritoPedidos.Carrito;
import pasteleriasystem.Modelo.Productos.Producto;
import pasteleriasystem.Modelo.Usuarios.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Catalogo extends JFrame {

    private final Cliente      cliente;
    private final ProductoDAO  productoDAO = new ProductoDAO();
    private final Carrito      carrito     = new Carrito();
    private ArrayList<Producto> productos;

    private JTable             tablaProductos;
    private DefaultTableModel  modeloTabla;
    private JLabel             lblTotal;
    private JSpinner           spinnerCantidad;

    public Catalogo(Cliente cliente) {
        this.cliente  = cliente;
        this.productos = productoDAO.cargarProductos();
        setTitle("Pasteleria — Catalogo");
        setSize(820, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        JLabel titulo = new JLabel("Catalogo de Productos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);

        JLabel bienvenido = new JLabel("Hola, " + cliente.getNombre());
        bienvenido.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        bienvenido.setForeground(new Color(210, 240, 230));

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setOpaque(false);

        JButton btnHistorial = btnSecundario("Mis pedidos");
        JButton btnCerrar    = btnSecundario("Cerrar sesion");
        btnHistorial.addActionListener(e -> verHistorial());
        btnCerrar.addActionListener(e -> cerrarSesion());

        headerRight.add(btnHistorial);
        headerRight.add(btnCerrar);

        header.add(titulo,      BorderLayout.WEST);
        header.add(bienvenido,  BorderLayout.CENTER);
        header.add(headerRight, BorderLayout.EAST);

        // ── Tabla de productos ────────────────────────────────
        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Stock", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(26);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaProductos.setSelectionBackground(new Color(200, 235, 220));
        cargarTabla();

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // ── Panel inferior — acciones ────────────────────────
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
        panelAcciones.setBackground(new Color(245, 243, 240));

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        spinnerCantidad.setPreferredSize(new Dimension(60, 30));

        JButton btnAgregar  = btnPrincipal("Agregar al carrito");
        JButton btnVerCarrito = btnPrincipal("Ver carrito  →");
        btnVerCarrito.setBackground(new Color(80, 80, 160));

        lblTotal = new JLabel("Total carrito: $0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal.setForeground(new Color(29, 158, 117));

        btnAgregar.addActionListener(e -> agregarAlCarrito());
        btnVerCarrito.addActionListener(e -> abrirCarrito());

        panelAcciones.add(lblCantidad);
        panelAcciones.add(spinnerCantidad);
        panelAcciones.add(btnAgregar);
        panelAcciones.add(btnVerCarrito);
        panelAcciones.add(lblTotal);

        add(header,        BorderLayout.NORTH);
        add(scroll,        BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : productos) {
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getCategoria(),
                "$" + String.format("%.0f", p.getPrecio()),
                p.getStock(), p.getDescripcion()
            });
        }
    }

    private void agregarAlCarrito() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto primero.");
            return;
        }
        Producto producto  = productos.get(fila);
        int      cantidad  = (int) spinnerCantidad.getValue();

        try {
            carrito.agregarProducto(producto, cantidad);
            lblTotal.setText("Total carrito: $" + String.format("%.0f", carrito.calcularTotal()));
            JOptionPane.showMessageDialog(this, "X" + producto.getNombre() + " agregado al carrito.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ":/" + ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirCarrito() {
        if (carrito.estaVacio()) {
            JOptionPane.showMessageDialog(this, "El carrito esta vac0o.");
            return;
        }
        new VistaCarrito(cliente, carrito, productos, this).setVisible(true);
    }

    private void verHistorial() {
        new HistorialPedidos(cliente).setVisible(true);
    }

    private void cerrarSesion() {
        dispose();
        new Inicio().setVisible(true);
    }

    private JButton btnPrincipal(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(new Color(29, 158, 117));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton btnSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBackground(new Color(15, 110, 86));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public void actualizarTotal() {
        lblTotal.setText("Total carrito: $" + String.format("%.0f", carrito.calcularTotal()));
        cargarTabla();
    }
}
