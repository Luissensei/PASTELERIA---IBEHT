package pasteleriasystem.Datos;
 
import pasteleriasystem.Modelo.Usuarios.Usuario;
import pasteleriasystem.Modelo.Usuarios.Cliente;
import pasteleriasystem.Modelo.Usuarios.Administrador;
 
import java.io.*;
import java.util.ArrayList;
public class UsuarioDAO {

    private static final String ARCHIVO = "datos/usuarios.txt";

    // Carga todos los usuarios desde el archivo
    public ArrayList<Usuario> cargarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] partes = linea.split(",");
                // formato: nombre,email,password,rol
                if (partes.length < 4) continue;
                String nombre   = partes[0];
                String email    = partes[1];
                String password = partes[2];
                String rol      = partes[3];

                if ("ADMIN".equals(rol)) {
                    lista.add(new Administrador(nombre, email, password));
                } else {
                    lista.add(new Cliente(nombre, email, password));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer usuarios: " + e.getMessage());
        }
        return lista;
    }

    // Guarda la lista completa (sobreescribe)
    public void guardarUsuarios(ArrayList<Usuario> lista) {
        crearDirectorio();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Usuario u : lista) {
                bw.write(u.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    // Agrega un usuario al final del archivo
    public void agregarUsuario(Usuario u) {
        crearDirectorio();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(u.toString());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
        }
    }

    // Busca por email y password — retorna null si no existe
    public Usuario buscarPorCredenciales(String email, String password) {
        for (Usuario u : cargarUsuarios()) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // Verifica si un email ya está registrado
    public boolean existeEmail(String email) {
        for (Usuario u : cargarUsuarios()) {
            if (u.getEmail().equals(email)) return true;
        }
        return false;
    }

    private void crearDirectorio() {
        new File("datos").mkdirs();
    }
}
