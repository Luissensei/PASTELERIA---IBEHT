package pasteleriasystem.Vistas;
 
import pasteleriasystem.Datos.UsuarioDAO;
import pasteleriasystem.Modelo.Usuarios.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Registro extends JFrame {

    private JTextField     txtNombre, txtEmail;
    private JPasswordField txtPassword, txtConfirmar;
    private JLabel         lblError;
    private final UsuarioDAO dao = new UsuarioDAO();

    public Registro() {
        setTitle("Pasteleria — Registro");
        setSize(420, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 243, 240));
        construirUI();
    }

    private void construirUI() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 218, 212), 1));
        panel.setPreferredSize(new Dimension(380, 400));

        JLabel titulo = new JLabel("Crear cuenta");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(50, 48, 44));
        titulo.setBounds(0, 30, 380, 32);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        txtNombre   = campo(panel, "Nombre completo",     40, 80);
        txtEmail    = campo(panel, "Correo electrónico",  40, 154);
        txtPassword = campoPassword(panel, "Contraseña",  40, 228);
        txtConfirmar= campoPassword(panel, "Confirmar contraseña", 40, 302);

        lblError = new JLabel("");
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblError.setForeground(new Color(180, 40, 40));
        lblError.setBounds(40, 348, 300, 18);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnCrear = new JButton("Crear cuenta");
        btnCrear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCrear.setBounds(40, 368, 300, 42);
        btnCrear.setBackground(new Color(29, 158, 117));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFocusPainted(false);
        btnCrear.setBorderPainted(false);
        btnCrear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCrear.addActionListener(e -> registrar());

        panel.add(titulo);
        panel.add(lblError);
        panel.add(btnCrear);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(245, 243, 240));
        wrapper.add(panel);
        add(wrapper, BorderLayout.CENTER);
    }

    private void registrar() {
        String nombre    = txtNombre.getText().trim();
        String email     = txtEmail.getText().trim();
        String pass      = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        try {
            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                throw new Exception("Todos los campos son obligatorios.");
            }
            if (!pass.equals(confirmar)) {
                throw new Exception("Las contraseñas no coinciden.");
            }
            if (!email.contains("@")) {
                throw new Exception("Correo electrónico invalido.");
            }
            if (dao.existeEmail(email)) {
                throw new Exception("Este correo ya esta registrado.");
            }

            Cliente nuevo = new Cliente(nombre, email, pass);
            dao.agregarUsuario(nuevo);
            JOptionPane.showMessageDialog(this, "¡Cuenta creada exitosamente!\nYa puedes iniciar sesion.");
            dispose();

        } catch (Exception ex) {
            lblError.setText(ex.getMessage());
        }
    }

    private JTextField campo(JPanel panel, String etiqueta, int x, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(100, 98, 94));
        lbl.setBounds(x, y, 300, 18);

        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBounds(x, y + 22, 300, 38);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 208, 202), 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));

        panel.add(lbl);
        panel.add(txt);
        return txt;
    }

    private JPasswordField campoPassword(JPanel panel, String etiqueta, int x, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(100, 98, 94));
        lbl.setBounds(x, y, 300, 18);

        JPasswordField txt = new JPasswordField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBounds(x, y + 22, 300, 38);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 208, 202), 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));

        panel.add(lbl);
        panel.add(txt);
        return txt;
    }
}
