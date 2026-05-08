package pasteleriasystem.Modelo.Usuarios;

// ABSTRACCIÓN + HERENCIA: clase padre abstracta
public abstract class Usuario {

    // ENCAPSULACIÓN: atributos privados
    private String nombre;
    private String email;
    private String password;

    public Usuario(String nombre, String email, String password) {
        this.nombre   = nombre;
        this.email    = email;
        this.password = password;
    }

    // Getters y Setters
    public String getNombre()   { return nombre; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }

    public void setNombre(String nombre)     { this.nombre   = nombre; }
    public void setEmail(String email)       { this.email    = email; }
    public void setPassword(String password) { this.password = password; }

    // POLIMORFISMO: método que cada subclase sobreescribe
    public abstract String getRol();

    @Override
    public String toString() {
        return nombre + "," + email + "," + password + "," + getRol();
    }
}
