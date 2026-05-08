package pasteleriasystem.Modelo.Productos;

// HERENCIA: ProductoIndividual extiende Producto (cupcakes, galletas, etc.)
public class ProductoIndividual extends Producto {

    private String tipo; // ej: "cupcake", "galleta", "muffin"

    public ProductoIndividual(String id, String nombre, double precio,
                              int stock, String descripcion, String tipo) {
        super(id, nombre, precio, stock, descripcion);
        this.tipo = tipo;
    }

    public String getTipo()          { return tipo; }
    public void   setTipo(String t)  { this.tipo = t; }

    // POLIMORFISMO: @Override
    @Override
    public String getCategoria() {
        return "INDIVIDUAL";
    }

    @Override
    public String toString() {
        return super.toString() + "," + tipo;
    }
}
