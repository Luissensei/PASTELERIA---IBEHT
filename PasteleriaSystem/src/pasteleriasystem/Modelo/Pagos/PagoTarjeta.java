package pasteleriasystem.Modelo.Pagos;

public class PagoTarjeta extends Pago {

    private String ultimosDigitos;

    public PagoTarjeta(double monto, String ultimosDigitos) {
        super(monto);
        this.ultimosDigitos = ultimosDigitos;
    }

    public String getUltimosDigitos() { return ultimosDigitos; }

    @Override
    public String procesar() {
        return "Pago con tarjeta **** " + ultimosDigitos + " por $" + getMonto() + " procesado.";
    }

    @Override
    public String getTipo() { return "TARJETA"; }
}
