public class SalidaInvalidaException extends Exception {
    private boolean fatal;

    public SalidaInvalidaException(String mensaje) {
        this(mensaje, false);
    }

    public SalidaInvalidaException(String mensaje, boolean fatal) {
        super(mensaje);
        this.fatal = fatal;
    }

    public boolean isFatal() {
        return fatal;
    }
}
