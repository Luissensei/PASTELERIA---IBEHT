package pasteleriasystem;

import pasteleriasystem.Vistas.Inicio;

public class PasteleriaSystem {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Inicio ventana = new Inicio();
            ventana.setVisible(true);
        });
    }
}
