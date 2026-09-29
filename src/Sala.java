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

    // TODO: usar esta direccion para decidir si hay salida (norte/sur/este/oeste != -1)
    public boolean tieneSalida(String direccion) {
        return getSalidaHacia(direccion) != -1;
    }

    // TODO: devolver el id de la sala vecina segun la direccion, o -1 si no hay salida
    public int getSalidaHacia(String direccion) {
        return -1;
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
}
