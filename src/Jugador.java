import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private int salaActualId;
    private ArrayList<Objeto> inventario;
    private boolean vivo;

    public Jugador(String nombre, int salaInicialId) {
        this.nombre = nombre;
        this.salaActualId = salaInicialId;
        this.inventario = new ArrayList<>();
        this.vivo = true;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSalaActualId() {
        return salaActualId;
    }

    public void moverA(int idSala) {
        this.salaActualId = idSala;
    }

    public void agregarObjeto(Objeto objeto) {
        inventario.add(objeto);
    }

    // TODO: busqueda lineal en el inventario por nombre, devolver null si no esta
    public Objeto buscarObjetoPorNombre(String nombre) {
        return null;
    }

    public boolean tieneObjeto(String nombre) {
        return buscarObjetoPorNombre(nombre) != null;
    }

    // TODO: ordenar el inventario por valor (Bubble Sort, ver TP16)
    public void ordenarInventarioPorValor() {
    }

    public void mostrarInventario() {
        System.out.println("--- Inventario de " + nombre + " ---");
        for (Objeto o : inventario) {
            System.out.println("- " + o.getNombre() + ": " + o.getDescripcion());
        }
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
