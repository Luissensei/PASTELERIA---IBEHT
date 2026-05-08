package pasteleriasystem.Modelo.Productos;

// HERENCIA: Torta extiende Producto
public class Torta extends Producto {

    private String sabor;
    private int    porciones;

    public Torta(String id, String nombre, double precio, int stock,
                 String descripcion, String sabor, int porciones) {
        super(id, nombre, precio, stock, descripcion);
        this.sabor     = sabor;
        this.porciones = porciones;
    }

    public String getSabor()    { return sabor; }
    public int    getPorciones(){ return porciones; }

    public void setSabor(String sabor)       { this.sabor     = sabor; }
    public void setPorciones(int porciones)  { this.porciones = porciones; }

    // POLIMORFISMO: @Override
    @Override
    public String getCategoria() {
        return "TORTA";
    }

    @Override
    public String toString() {
        return super.toString() + "," + sabor + "," + porciones;
    }
}
