package pasteleriasystem.Modelo.Pagos;

// ABSTRACCIÓN: clase abstracta para distintos métodos de pago
public abstract class Pago {

    private double monto;

    public Pago(double monto) {
        this.monto = monto;
    }

    public double getMonto() { return monto; }

    // POLIMORFISMO: cada método de pago lo implementa
    public abstract String procesar();

    public abstract String getTipo();
}
