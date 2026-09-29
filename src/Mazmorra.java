import java.util.ArrayList;
import java.util.Scanner;

public class Mazmorra {
    private ArrayList<Sala> mapa;
    private Jugador jugador;

    public Mazmorra() {
        this.mapa = new ArrayList<>();
        construirMapa();
        this.jugador = new Jugador("Aventurero", 0);
    }

    // TODO: crear las 6 salas del mapa (ver DISENO.md) y agregarlas a mapa
    private void construirMapa() {
    }

    // TODO: buscar la Sala cuyo id == this.jugador.getSalaActualId() y devolverla
    private Sala getSalaActual() {
        return null;
    }

    public void mostrarSalaActual() {
        // TODO: imprimir nombre y descripcion de la sala actual
    }

    // TODO: usar Sala.tieneSalida()/getSalidaHacia() para mover al jugador,
    // y lanzar SalidaInvalidaException si la direccion no existe o la sala
    // destino es una trampa/final que requiere un objeto que el jugador no tiene
    public void procesarMovimiento(String direccion) throws SalidaInvalidaException {
    }

    public void iniciarPartida() {
        Scanner sc = new Scanner(System.in);
        boolean jugando = true;

        while (jugando) {
            mostrarSalaActual();
            System.out.println("1) Moverse  2) Ver inventario  3) Usar objeto  4) Salir");
            System.out.print("Opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: {
                    System.out.print("Dirección (norte/sur/este/oeste): ");
                    String direccion = sc.nextLine();
                    try {
                        procesarMovimiento(direccion);
                    } catch (SalidaInvalidaException e) {
                        System.out.println(e.getMessage());
                        jugador.setVivo(false);
                        jugando = false;
                    }
                    break;
                }
                case 2:
                    jugador.mostrarInventario();
                    break;
                case 3:
                    // TODO: pedir nombre de objeto y usarlo (buscarObjetoPorNombre)
                    break;
                case 4:
                    jugando = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        sc.close();
    }

    public static void main(String[] args) {
        Mazmorra juego = new Mazmorra();
        juego.iniciarPartida();
    }
}
