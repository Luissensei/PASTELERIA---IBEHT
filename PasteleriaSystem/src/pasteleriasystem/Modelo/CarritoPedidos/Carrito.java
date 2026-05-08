package pasteleriasystem.Modelo.CarritoPedidos;
 
import pasteleriasystem.Modelo.Productos.Producto;
import java.util.ArrayList;

public class Carrito {

    // COLECCIONES: ArrayList de items
    private ArrayList<ItemCarrito> items;

    public Carrito() {
        this.items = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) throws Exception {
        // Verificar stock antes de agregar
        if (cantidad > producto.getStock()) {
            throw new Exception("No hay suficiente stock de: " + producto.getNombre());
        }

        // Si ya existe el producto, sumar cantidad
        for (ItemCarrito item : items) {
            if (item.getProducto().getId().equals(producto.getId())) {
                int nuevaCantidad = item.getCantidad() + cantidad;
                if (nuevaCantidad > producto.getStock()) {
                    throw new Exception("Stock insuficiente para la cantidad solicitada.");
                }
                item.setCantidad(nuevaCantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(String productoId) {
        items.removeIf(item -> item.getProducto().getId().equals(productoId));
    }

    public void actualizarCantidad(String productoId, int nuevaCantidad) throws Exception {
        for (ItemCarrito item : items) {
            if (item.getProducto().getId().equals(productoId)) {
                if (nuevaCantidad > item.getProducto().getStock()) {
                    throw new Exception("Stock insuficiente.");
                }
                if (nuevaCantidad <= 0) {
                    eliminarProducto(productoId);
                } else {
                    item.setCantidad(nuevaCantidad);
                }
                return;
            }
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void vaciar() {
        items.clear();
    }

    public ArrayList<ItemCarrito> getItems() { return items; }

    public boolean estaVacio() { return items.isEmpty(); }
}
