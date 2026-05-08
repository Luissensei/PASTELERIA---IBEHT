package pasteleriasystem.Modelo.Productos;

// ABSTRACCIÓN: clase abstracta para todos los productos
public abstract class Producto {

    private String id;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;

    public Producto(String id, String nombre, double precio, int stock, String descripcion) {
        this.id          = id;
        this.nombre      = nombre;
        this.precio      = precio;
        this.stock       = stock;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getId()          { return id; }
    public String getNombre()      { return nombre; }
    public double getPrecio()      { return precio; }
    public int    getStock()       { return stock; }
    public String getDescripcion() { return descripcion; }

    public void setNombre(String nombre)          { this.nombre      = nombre; }
    public void setPrecio(double precio)          { this.precio      = precio; }
    public void setStock(int stock)               { this.stock       = stock; }
    public void setDescripcion(String descripcion){ this.descripcion = descripcion; }

    // Reduce stock; lanza excepción si no hay suficiente
    public void reducirStock(int cantidad) throws Exception {
        if (cantidad > stock) {
            throw new Exception("Stock insuficiente para: " + nombre);
        }
        this.stock -= cantidad;
    }

    // POLIMORFISMO: cada subclase indica su categoría
    public abstract String getCategoria();

    @Override
    public String toString() {
        return id + "," + nombre + "," + precio + "," + stock + "," + descripcion + "," + getCategoria();
    }
}
