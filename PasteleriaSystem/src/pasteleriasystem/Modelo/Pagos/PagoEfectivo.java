package pasteleriasystem.Modelo.Pagos;

public class PagoEfectivo extends Pago {

    public PagoEfectivo(double monto) {
        super(monto);
    }

    @Override
    public String procesar() {
        return "Pago en efectivo por $" + getMonto() + " procesado.";
    }

    @Override
    public String getTipo() { return "EFECTIVO"; }
}
