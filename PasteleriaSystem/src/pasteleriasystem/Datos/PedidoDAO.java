package pasteleriasystem.Datos;
 
import pasteleriasystem.Modelo.CarritoPedidos.Pedido;

import java.io.*;
import java.util.ArrayList;

public class PedidoDAO {

    private static final String ARCHIVO = "datos/pedidos.txt";

    public ArrayList<Pedido> cargarPedidos() {
        ArrayList<Pedido> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                // formato: id|emailCliente|total|estado|fechaHora|metodoPago
                String[] p = linea.split("\\|");
                if (p.length < 6) continue;
                Pedido pedido = new Pedido(
                    p[0], p[1],
                    Double.parseDouble(p[2]),
                    Pedido.Estado.valueOf(p[3]),
                    p[4], p[5]
                );
                lista.add(pedido);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al leer pedidos: " + e.getMessage());
        }
        return lista;
    }

    public void guardarPedido(Pedido pedido) {
        crearDirectorio();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(pedido.toString());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar pedido: " + e.getMessage());
        }
    }

    public ArrayList<Pedido> pedidosDeCliente(String emailCliente) {
        ArrayList<Pedido> resultado = new ArrayList<>();
        for (Pedido p : cargarPedidos()) {
            if (p.getEmailCliente().equals(emailCliente)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // Genera un ID único basado en cantidad de pedidos existentes
    public String generarId() {
        int n = cargarPedidos().size() + 1;
        return String.format("PED%04d", n);
    }

    private void crearDirectorio() {
        new File("datos").mkdirs();
    }
}
