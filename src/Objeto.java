public class Objeto {
    private String nombre;
    private String descripcion;
    private int valor;
    private boolean esLlave;

    public Objeto(String nombre, String descripcion, int valor, boolean esLlave) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.esLlave = esLlave;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getValor() {
        return valor;
    }

    public boolean isEsLlave() {
        return esLlave;
    }
}
