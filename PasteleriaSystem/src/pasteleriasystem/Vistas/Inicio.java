package pasteleriasystem.Vistas;
 
import pasteleriasystem.Datos.UsuarioDAO;
import pasteleriasystem.Modelo.Usuarios.Usuario;
import pasteleriasystem.Modelo.Usuarios.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Inicio extends JFrame {

    private JLabel         lblTitulo, lblSubtitulo, lblEmail, lblPassword, lblError;
    private JTextField     txtEmail;
    private JPasswordField txtPassword;
    private JButton        btnIngresar, btnRegistrar;
    private JPanel         panelCentro;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Inicio() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Pasteleria — Iniciar sesion");
        setSize(420, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 243, 240));

        panelCentro = new JPanel(null);
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setBorder(BorderFactory.createLineBorder(new Color(220, 218, 212), 1));

        lblTitulo = new JLabel("Pasteleria");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(50, 48, 44));
        lblTitulo.setBounds(0, 40, 380, 36);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblSubtitulo = new JLabel("Inicia sesion para continuar");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(130, 128, 122));
        lblSubtitulo.setBounds(0, 80, 380, 20);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JSeparator sep = new JSeparator();
        sep.setBounds(40, 114, 300, 1);
        sep.setForeground(new Color(230, 228, 224));

        lblEmail = new JLabel("Correo electronico");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEmail.setForeground(new Color(100, 98, 94));
        lblEmail.setBounds(40, 130, 300, 18);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setBounds(40, 152, 300, 38);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 208, 202), 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));

        lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPassword.setForeground(new Color(100, 98, 94));
        lblPassword.setBounds(40, 206, 300, 18);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBounds(40, 228, 300, 38);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 208, 202), 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));

        lblError = new JLabel("");
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblError.setForeground(new Color(180, 40, 40));
        lblError.setBounds(40, 272, 300, 18);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);

        btnIngresar = crearBoton("Ingresar", new Color(29, 158, 117), new Color(15, 110, 86));
        btnIngresar.setBounds(40, 296, 300, 42);

        JSeparator sep2 = new JSeparator();
        sep2.setBounds(40, 352, 300, 1);
        sep2.setForeground(new Color(230, 228, 224));

        btnRegistrar = new JButton("¿No tienes cuenta? Regístrate");
        btnRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRegistrar.setBounds(40, 362, 300, 30);
        btnRegistrar.setBackground(Color.WHITE);
        btnRegistrar.setForeground(new Color(100, 98, 94));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnRegistrar.setForeground(new Color(29, 158, 117)); }
            public void mouseExited (MouseEvent e) { btnRegistrar.setForeground(new Color(100, 98, 94)); }
        });

        for (JComponent c : new JComponent[]{lblTitulo, lblSubtitulo, sep, lblEmail, txtEmail,
                lblPassword, txtPassword, lblError, btnIngresar, sep2, btnRegistrar}) {
            panelCentro.add(c);
        }

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(245, 243, 240));
        panelCentro.setPreferredSize(new Dimension(380, 410));
        wrapper.add(panelCentro);
        add(wrapper, BorderLayout.CENTER);

        btnIngresar.addActionListener(e -> accionIngresar());
        btnRegistrar.addActionListener(e -> abrirRegistro());
        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) accionIngresar();
            }
        });
    }

    private void accionIngresar() {
        String email    = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            lblError.setText("Por favor completa todos los campos.");
            return;
        }

        try {
            Usuario usuario = usuarioDAO.buscarPorCredenciales(email, password);
            if (usuario == null) {
                lblError.setText("Correo o contraseña incorrectos.");
                return;
            }
            lblError.setText("");
            dispose();

            // POLIMORFISMO en acción: getRol() determina qué vista abrir
            if ("ADMIN".equals(usuario.getRol())) {
                new AdminPanel().setVisible(true);
            } else {
                new Catalogo((Cliente) usuario).setVisible(true);
            }
        } catch (Exception ex) {
            lblError.setText("Error al iniciar sesion.");
        }
    }

    private void abrirRegistro() {
        new Registro().setVisible(true);
    }

    private JButton crearBoton(String texto, Color normal, Color hover) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(normal);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited (MouseEvent e) { btn.setBackground(normal); }
        });
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Inicio().setVisible(true));
    }
}
