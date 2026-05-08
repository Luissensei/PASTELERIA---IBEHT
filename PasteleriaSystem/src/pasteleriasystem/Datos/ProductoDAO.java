package pasteleriasystem.Datos;
 
import pasteleriasystem.Modelo.Productos.Producto;
import pasteleriasystem.Modelo.Productos.Torta;
import pasteleriasystem.Modelo.Productos.ProductoIndividual;

import java.io.*;
import java.util.ArrayList;

public class ProductoDAO {

    private static final String ARCHIVO = "datos/productos.txt";

    public ArrayList<Producto> cargarProductos() {
        ArrayList<Producto> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            cargarDatosDemostracion(lista);
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] p = linea.split(",");
                if (p.length < 6) continue;

                String id          = p[0];
                String nombre      = p[1];
                double precio      = Double.parseDouble(p[2]);
                int    stock       = Integer.parseInt(p[3]);
                String descripcion = p[4];
                String categoria   = p[5];

                if ("TORTA".equals(categoria) && p.length >= 8) {
                    lista.add(new Torta(id, nombre, precio, stock, descripcion,
                                        p[6], Integer.parseInt(p[7])));
                } else if ("INDIVIDUAL".equals(categoria) && p.length >= 7) {
                    lista.add(new ProductoIndividual(id, nombre, precio, stock, descripcion, p[6]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer productos: " + e.getMessage());
        }
        return lista;
    }

    public void guardarProductos(ArrayList<Producto> lista) {
        crearDirectorio();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Producto p : lista) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar productos: " + e.getMessage());
        }
    }

    public void actualizarStock(ArrayList<Producto> lista) {
        guardarProductos(lista);
    }

    // Datos de demostración si no existe el archivo aún
    private void cargarDatosDemostracion(ArrayList<Producto> lista) {
        lista.add(new Torta("T001", "Torta de Chocolate", 45000, 10,
                            "Torta húmeda de chocolate con ganache", "Chocolate", 12));
        lista.add(new Torta("T002", "Torta de Vainilla", 38000, 8,
                            "Torta esponjosa de vainilla con crema", "Vainilla", 10));
        lista.add(new Torta("T003", "Torta Red Velvet", 52000, 5,
                            "Torta red velvet con frosting de queso crema", "Vainilla", 14));
        lista.add(new ProductoIndividual("P001", "Cupcake de Chocolate", 5500, 30,
                                        "Cupcake con buttercream de chocolate", "cupcake"));
        lista.add(new ProductoIndividual("P002", "Galleta de Mantequilla", 2800, 50,
                                        "Galleta artesanal de mantequilla", "galleta"));
        lista.add(new ProductoIndividual("P003", "Muffin de Arándanos", 4200, 25,
                                        "Muffin esponjoso con arándanos frescos", "muffin"));
        guardarProductos(lista);
    }

    private void crearDirectorio() {
        new File("datos").mkdirs();
    }
}
