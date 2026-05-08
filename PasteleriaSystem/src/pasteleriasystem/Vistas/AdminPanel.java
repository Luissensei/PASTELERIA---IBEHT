package pasteleriasystem.Vistas;

import pasteleriasystem.Datos.PedidoDAO;
import pasteleriasystem.Datos.ProductoDAO;
import pasteleriasystem.Modelo.Productos.*;
import pasteleriasystem.Modelo.CarritoPedidos.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AdminPanel extends JFrame {

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final PedidoDAO   pedidoDAO   = new PedidoDAO();
    private final ArrayList<Producto> productos;

    private DefaultTableModel   modeloProductos;
    private DefaultTableModel   modeloPedidos;

    public AdminPanel() {
        this.productos = productoDAO.cargarProductos();
        setTitle("Pasteleria — Panel Administrador");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        construirUI();
    }

    private void construirUI() {
        getContentPane().setBackground(new Color(245, 243, 240));
        setLayout(new BorderLayout());

        // ── Encabezado ────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(50, 48, 44));
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel titulo = new JLabel("Panel de Administracion");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        JButton btnSalir = new JButton("Cerrar sesion");
        btnSalir.setBackground(new Color(29, 158, 117));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.addActionListener(e -> { dispose(); new Inicio().setVisible(true); });
        header.add(titulo,  BorderLayout.WEST);
        header.add(btnSalir, BorderLayout.EAST);

        // ── Pestañas ──────────────────────────────────────────
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabs.addTab("Productos", panelProductos());
        tabs.addTab("Pedidos",   panelPedidos());

        add(header, BorderLayout.NORTH);
        add(tabs,   BorderLayout.CENTER);
    }

    // ── Tab Productos ─────────────────────────────────────────
    private JPanel panelProductos() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Nombre", "Categoría", "Precio", "Stock", "Descripción"};
        modeloProductos = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        cargarTablaProductos();

        JTable tabla = new JTable(modeloProductos);
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setSelectionBackground(new Color(200, 235, 220));

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        acciones.setBackground(new Color(245, 243, 240));

        JButton btnAgregar  = btn("Nuevo producto",     new Color(29, 158, 117));
        JButton btnEditar   = btn("Editar stock",       new Color(80, 80, 160));
        JButton btnEliminar = btn("Eliminar",           new Color(180, 60, 60));

        btnAgregar.addActionListener(e -> agregarProducto(tabla));
        btnEditar.addActionListener(e -> editarStock(tabla));
        btnEliminar.addActionListener(e -> eliminarProducto(tabla));

        acciones.add(btnAgregar);
        acciones.add(btnEditar);
        acciones.add(btnEliminar);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(acciones,               BorderLayout.SOUTH);
        return panel;
    }

    private void cargarTablaProductos() {
        modeloProductos.setRowCount(0);
        for (Producto p : productos) {
            modeloProductos.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getCategoria(),
                "$" + String.format("%.0f", p.getPrecio()),
                p.getStock(), p.getDescripcion()
            });
        }
    }

    private void agregarProducto(JTable tabla) {
        JTextField txtNombre  = new JTextField();
        JTextField txtPrecio  = new JTextField();
        JTextField txtStock   = new JTextField();
        JTextField txtDesc    = new JTextField();
        String[]   categorias = {"TORTA", "INDIVIDUAL"};
        JComboBox<String> cbCategoria = new JComboBox<>(categorias);

        Object[] campos = {
            "Nombre:",      txtNombre,
            "Precio:",      txtPrecio,
            "Stock:",       txtStock,
            "Descripción:", txtDesc,
            "Categoría:",   cbCategoria
        };

        int res = JOptionPane.showConfirmDialog(this, campos, "Nuevo Producto",
                                               JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return;

        try {
            String nombre   = txtNombre.getText().trim();
            double precio   = Double.parseDouble(txtPrecio.getText().trim());
            int    stock    = Integer.parseInt(txtStock.getText().trim());
            String desc     = txtDesc.getText().trim();
            String cat      = (String) cbCategoria.getSelectedItem();
            String id       = cat.charAt(0) + String.format("%03d", productos.size() + 1);

            Producto nuevo;
            if ("TORTA".equals(cat)) {
                nuevo = new Torta(id, nombre, precio, stock, desc, "Mixto", 8);
            } else {
                nuevo = new ProductoIndividual(id, nombre, precio, stock, desc, "unidad");
            }
            productos.add(nuevo);
            productoDAO.guardarProductos(productos);
            cargarTablaProductos();
            JOptionPane.showMessageDialog(this, "Producto agregado.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser numeros.", "Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarStock(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona un producto."); return; }

        Producto p = productos.get(fila);
        String input = JOptionPane.showInputDialog(this,
            "Nuevo stock para: " + p.getNombre(), p.getStock());
        if (input == null) return;

        try {
            p.setStock(Integer.parseInt(input.trim()));
            productoDAO.guardarProductos(productos);
            cargarTablaProductos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un numero valido.", "Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona un producto."); return; }

        int conf = JOptionPane.showConfirmDialog(this,
            "¿Eliminar " + productos.get(fila).getNombre() + "?", "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) return;

        productos.remove(fila);
        productoDAO.guardarProductos(productos);
        cargarTablaProductos();
    }

    // ── Tab Pedidos ───────────────────────────────────────────
    private JPanel panelPedidos() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Cliente", "Total", "Método de Pago", "Estado", "Fecha"};
        modeloPedidos = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        for (Pedido p : pedidoDAO.cargarPedidos()) {
            modeloPedidos.addRow(new Object[]{
                p.getId(), p.getEmailCliente(),
                "$" + String.format("%.0f", p.getTotal()),
                p.getMetodoPago(), p.getEstado(), p.getFechaHora()
            });
        }

        JTable tabla = new JTable(modeloPedidos);
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JButton btnRefrescar = btn("Refrescar", new Color(80, 80, 160));
        btnRefrescar.addActionListener(e -> {
            modeloPedidos.setRowCount(0);
            for (Pedido p : pedidoDAO.cargarPedidos()) {
                modeloPedidos.addRow(new Object[]{
                    p.getId(), p.getEmailCliente(),
                    "$" + String.format("%.0f", p.getTotal()),
                    p.getMetodoPago(), p.getEstado(), p.getFechaHora()
                });
            }
        });

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.add(btnRefrescar);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(footer,                 BorderLayout.SOUTH);
        return panel;
    }

    private JButton btn(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}
