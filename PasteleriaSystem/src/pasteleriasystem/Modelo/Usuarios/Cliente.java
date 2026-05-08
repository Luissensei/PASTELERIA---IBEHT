package pasteleriasystem.Modelo.Usuarios;
 
import pasteleriasystem.Modelo.CarritoPedidos.Pedido;
import java.util.ArrayList;

// HERENCIA: Cliente extiende Usuario
public class Cliente extends Usuario {

    private ArrayList<Pedido> historialPedidos;

    public Cliente(String nombre, String email, String password) {
        super(nombre, email, password);
        this.historialPedidos = new ArrayList<>();
    }

    // POLIMORFISMO: @Override funcional
    @Override
    public String getRol() {
        return "CLIENTE";
    }

    public void agregarPedido(Pedido pedido) {
        historialPedidos.add(pedido);
    }

    public ArrayList<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }
}
