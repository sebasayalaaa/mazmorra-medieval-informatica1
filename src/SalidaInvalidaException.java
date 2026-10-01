// Excepcion personalizada del juego. Se lanza cuando el jugador intenta
// moverse a donde no puede: una direccion que no existe desde la sala
// actual, o una sala bloqueada para la que le falta un objeto.
public class SalidaInvalidaException extends Exception {
    private boolean fatal;

    // Constructor corto: por defecto NO es fatal (el jugador puede reintentar).
    public SalidaInvalidaException(String mensaje) {
        this(mensaje, false);
    }

    // fatal = true termina la partida (ej: entrar a una trampa sin el objeto
    // necesario). fatal = false solo avisa y deja que el jugador reintente
    // (ej: elegir una direccion que no existe).
    public SalidaInvalidaException(String mensaje, boolean fatal) {
        super(mensaje);
        this.fatal = fatal;
    }

    public boolean isFatal() {
        return fatal;
    }
}
