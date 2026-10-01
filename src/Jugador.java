import java.util.ArrayList;

// Representa al aventurero que explora la mazmorra: sabe en que sala esta
// parado y que objetos lleva en su inventario (ArrayList<Objeto>).
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

    // Busqueda lineal: recorre el inventario comparando nombres
    public Objeto buscarObjetoPorNombre(String nombre) {
        for (int i = 0; i < inventario.size(); i++) {
            if (inventario.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return inventario.get(i);
            }
        }
        return null;
    }

    public boolean tieneObjeto(String nombre) {
        return buscarObjetoPorNombre(nombre) != null;
    }

    // Bubble Sort (ver TP16) ordenando de menor a mayor valor
    public void ordenarInventarioPorValor() {
        int n = inventario.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (inventario.get(j).getValor() > inventario.get(j + 1).getValor()) {
                    Objeto temp = inventario.get(j);
                    inventario.set(j, inventario.get(j + 1));
                    inventario.set(j + 1, temp);
                }
            }
        }
    }

    public void mostrarInventario() {
        ordenarInventarioPorValor();
        System.out.println("--- Inventario de " + nombre + " ---");
        if (inventario.isEmpty()) {
            System.out.println("(vacío)");
            return;
        }
        for (Objeto o : inventario) {
            System.out.println("- " + o.getNombre() + " (valor " + o.getValor() + "): " + o.getDescripcion());
        }
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
