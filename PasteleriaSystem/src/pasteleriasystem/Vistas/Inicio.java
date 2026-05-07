package pasteleriasystem.Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Inicio extends JFrame {

    // ── Componentes ──────────────────────────────────────────
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblError;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnRegistrar;
    private JPanel panelCentro;

    public Inicio() {
        initComponents();
        centrarVentana();
    }

    private void initComponents() {

        // ── Ventana ──────────────────────────────────────────
        setTitle("Pastelería — Iniciar sesión");
        setSize(420, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 243, 240));

        // ── Panel central (tarjeta blanca) ───────────────────
        panelCentro = new JPanel();
        panelCentro.setLayout(null);
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setBorder(BorderFactory.createLineBorder(new Color(220, 218, 212), 1));

        // ── Título ───────────────────────────────────────────
        lblTitulo = new JLabel("Pastelería");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(50, 48, 44));
        lblTitulo.setBounds(0, 40, 380, 36);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblSubtitulo = new JLabel("Inicia sesión para continuar");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(130, 128, 122));
        lblSubtitulo.setBounds(0, 80, 380, 20);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // ── Separador ────────────────────────────────────────
        JSeparator sep = new JSeparator();
        sep.setBounds(40, 114, 300, 1);
        sep.setForeground(new Color(230, 228, 224));

        // ── Campo email ──────────────────────────────────────
        lblEmail = new JLabel("Correo electrónico");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEmail.setForeground(new Color(100, 98, 94));
        lblEmail.setBounds(40, 130, 300, 18);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setBounds(40, 152, 300, 38);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 208, 202), 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));

        // ── Campo contraseña ─────────────────────────────────
        lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPassword.setForeground(new Color(100, 98, 94));
        lblPassword.setBounds(40, 206, 300, 18);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBounds(40, 228, 300, 38);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 208, 202), 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));

        // ── Label de error ───────────────────────────────────
        lblError = new JLabel("");
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblError.setForeground(new Color(180, 40, 40));
        lblError.setBounds(40, 272, 300, 18);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);

        // ── Botón ingresar ───────────────────────────────────
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setBounds(40, 296, 300, 42);
        btnIngresar.setBackground(new Color(29, 158, 117));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover botón ingresar
        btnIngresar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnIngresar.setBackground(new Color(15, 110, 86));
            }
            public void mouseExited(MouseEvent e) {
                btnIngresar.setBackground(new Color(29, 158, 117));
            }
        });

        // ── Separador inferior ───────────────────────────────
        JSeparator sep2 = new JSeparator();
        sep2.setBounds(40, 352, 300, 1);
        sep2.setForeground(new Color(230, 228, 224));

        // ── Botón registrarse ────────────────────────────────
        btnRegistrar = new JButton("¿No tienes cuenta? Regístrate");
        btnRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRegistrar.setBounds(40, 362, 300, 30);
        btnRegistrar.setBackground(Color.WHITE);
        btnRegistrar.setForeground(new Color(100, 98, 94));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRegistrar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnRegistrar.setForeground(new Color(29, 158, 117));
            }
            public void mouseExited(MouseEvent e) {
                btnRegistrar.setForeground(new Color(100, 98, 94));
            }
        });

        // ── Agregar componentes al panel ─────────────────────
        panelCentro.add(lblTitulo);
        panelCentro.add(lblSubtitulo);
        panelCentro.add(sep);
        panelCentro.add(lblEmail);
        panelCentro.add(txtEmail);
        panelCentro.add(lblPassword);
        panelCentro.add(txtPassword);
        panelCentro.add(lblError);
        panelCentro.add(btnIngresar);
        panelCentro.add(sep2);
        panelCentro.add(btnRegistrar);

        // ── Panel exterior con margen ────────────────────────
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(245, 243, 240));
        panelCentro.setPreferredSize(new Dimension(380, 410));
        wrapper.add(panelCentro);

        add(wrapper, BorderLayout.CENTER);

        // ── Acciones ─────────────────────────────────────────
        btnIngresar.addActionListener(e -> accionIngresar());
        btnRegistrar.addActionListener(e -> accionRegistrar());

        // Permitir ingresar con la tecla Enter
        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) accionIngresar();
            }
        });
    }

    // ── Lógica del botón Ingresar ────────────────────────────
    private void accionIngresar() {
        String email    = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Validación básica de campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            mostrarError("Por favor completa todos los campos.");
            return;
        }

        // TODO: reemplaza esto con tu UsuarioDAO cuando lo tengas
        // Por ahora simula un login de prueba para verificar que la vista funciona
        if (email.equals("admin@pasteleria.com") && password.equals("1234")) {
            lblError.setText("");
            JOptionPane.showMessageDialog(this, "Bienvenido Administrador");
            // new AdminFrame().setVisible(true);
            // this.dispose();
        } else if (email.equals("cliente@pasteleria.com") && password.equals("1234")) {
            lblError.setText("");
            JOptionPane.showMessageDialog(this, "Bienvenido Cliente");
            // new CatalogoFrame().setVisible(true);
            // this.dispose();
        } else {
            mostrarError("Correo o contraseña incorrectos.");
        }
    }

    // ── Lógica del botón Registrar ───────────────────────────
    private void accionRegistrar() {
        // TODO: new RegistroFrame().setVisible(true);
        JOptionPane.showMessageDialog(this, "Pantalla de registro — próximamente.");
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
    }

    private void centrarVentana() {
        setLocationRelativeTo(null);
    }

    // ── Main de prueba — borra esto cuando tengas el Main.java ─
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Inicio().setVisible(true));
    }
}
