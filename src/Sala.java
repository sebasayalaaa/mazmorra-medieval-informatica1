// Representa una habitacion de la mazmorra: tiene descripcion, hasta 4
// salidas (norte/sur/este/oeste, -1 si no existe esa salida), puede tener
// un objeto para recoger, y puede estar bloqueada por itemRequerido.
public class Sala {
    private int id;
    private String nombre;
    private String descripcion;
    private int norte;
    private int sur;
    private int este;
    private int oeste;
    private Objeto objeto;
    private boolean esTrampa;
    private boolean esFinal;
    private boolean visitada;
    private String itemRequerido;

    public Sala(int id, String nombre, String descripcion, int norte, int sur, int este, int oeste) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.norte = norte;
        this.sur = sur;
        this.este = este;
        this.oeste = oeste;
        this.objeto = null;
        this.esTrampa = false;
        this.esFinal = false;
        this.visitada = false;
        this.itemRequerido = null;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean tieneSalida(String direccion) {
        return getSalidaHacia(direccion) != -1;
    }

    public int getSalidaHacia(String direccion) {
        String d = direccion.trim().toLowerCase();
        if (d.equals("norte")) {
            return norte;
        } else if (d.equals("sur")) {
            return sur;
        } else if (d.equals("este")) {
            return este;
        } else if (d.equals("oeste")) {
            return oeste;
        }
        return -1;
    }

    // Arma un texto con las direcciones que realmente tienen salida desde
    // esta sala (ej: "norte, este"), para guiar al jugador antes de que
    // elija moverse en vez de que tenga que adivinar.
    public String getSalidasDisponibles() {
        String salidas = "";
        if (norte != -1) {
            salidas += "norte, ";
        }
        if (sur != -1) {
            salidas += "sur, ";
        }
        if (este != -1) {
            salidas += "este, ";
        }
        if (oeste != -1) {
            salidas += "oeste, ";
        }
        if (salidas.isEmpty()) {
            return "(ninguna)";
        }
        return salidas.substring(0, salidas.length() - 2);
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public boolean isEsTrampa() {
        return esTrampa;
    }

    public void setEsTrampa(boolean esTrampa) {
        this.esTrampa = esTrampa;
    }

    public boolean isEsFinal() {
        return esFinal;
    }

    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    public String getItemRequerido() {
        return itemRequerido;
    }

    // El nombre del objeto que hace falta tener para poder entrar a esta sala.
    // null significa que la sala no pide nada para entrar.
    public void setItemRequerido(String itemRequerido) {
        this.itemRequerido = itemRequerido;
    }
}
