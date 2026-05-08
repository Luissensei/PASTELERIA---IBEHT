package pasteleriasystem.Modelo.Usuarios;

// HERENCIA: Administrador extiende Usuario
public class Administrador extends Usuario {

    public Administrador(String nombre, String email, String password) {
        super(nombre, email, password);
    }

    // POLIMORFISMO: @Override funcional
    @Override
    public String getRol() {
        return "ADMIN";
    }
}
