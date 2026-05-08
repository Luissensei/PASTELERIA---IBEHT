package pasteleriasystem.Modelo.CarritoPedidos;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Pedido {

    public enum Estado { PENDIENTE, CONFIRMADO, ENTREGADO, CANCELADO }

    private String             id;
    private String             emailCliente;
    private ArrayList<ItemCarrito> items;
    private double             total;
    private Estado             estado;
    private String             fechaHora;
    private String             metodoPago;

    public Pedido(String id, String emailCliente, ArrayList<ItemCarrito> items,
                  double total, String metodoPago) {
        this.id           = id;
        this.emailCliente = emailCliente;
        this.items        = new ArrayList<>(items);
        this.total        = total;
        this.estado       = Estado.CONFIRMADO;
        this.metodoPago   = metodoPago;
        this.fechaHora    = LocalDateTime.now()
                              .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // Constructor para cargar desde archivo
    public Pedido(String id, String emailCliente, double total,
                  Estado estado, String fechaHora, String metodoPago) {
        this.id           = id;
        this.emailCliente = emailCliente;
        this.items        = new ArrayList<>();
        this.total        = total;
        this.estado       = estado;
        this.fechaHora    = fechaHora;
        this.metodoPago   = metodoPago;
    }

    // Getters
    public String             getId()          { return id; }
    public String             getEmailCliente(){ return emailCliente; }
    public ArrayList<ItemCarrito> getItems()   { return items; }
    public double             getTotal()       { return total; }
    public Estado             getEstado()      { return estado; }
    public String             getFechaHora()   { return fechaHora; }
    public String             getMetodoPago()  { return metodoPago; }

    public void setEstado(Estado estado) { this.estado = estado; }

    @Override
    public String toString() {
        return id + "|" + emailCliente + "|" + total + "|" + estado + "|" + fechaHora + "|" + metodoPago;
    }
}
