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

    private void construirMapa() {
        Sala entrada = new Sala(0, "Entrada de la Mazmorra",
                "Una escalera de piedra húmeda baja hacia la oscuridad. Solo hay salida hacia el norte.",
                1, -1, -1, -1);

        Sala antorchas = new Sala(1, "Sala de las Antorchas",
                "Antorchas apagadas cuelgan de las paredes. Hay una que todavía se puede usar.",
                2, 0, 3, -1);
        antorchas.setObjeto(new Objeto("Antorcha", "Ilumina el camino en la oscuridad.", 10, false));

        Sala pasilloOscuro = new Sala(2, "Pasillo Oscuro",
                "No se ve absolutamente nada. Sin una fuente de luz es imposible seguir.",
                4, 1, -1, -1);
        pasilloOscuro.setEsTrampa(true);

        Sala salaCofre = new Sala(3, "Sala del Cofre",
                "Un cofre de madera vieja está entreabierto en el centro de la sala.",
                -1, -1, -1, 1);
        salaCofre.setObjeto(new Objeto("Llave Dorada", "Una llave ornamentada, parece abrir algo importante.", 50, true));

        Sala camaraFinal = new Sala(4, "Cámara Final",
                "Una puerta enorme con una cerradura dorada bloquea la salida de la mazmorra.",
                -1, 2, -1, -1);
        camaraFinal.setEsFinal(true);

        mapa.add(entrada);
        mapa.add(antorchas);
        mapa.add(pasilloOscuro);
        mapa.add(salaCofre);
        mapa.add(camaraFinal);
    }

    private Sala buscarSalaPorId(int id) {
        for (int i = 0; i < mapa.size(); i++) {
            if (mapa.get(i).getId() == id) {
                return mapa.get(i);
            }
        }
        return null;
    }

    private Sala getSalaActual() {
        return buscarSalaPorId(jugador.getSalaActualId());
    }

    public void mostrarSalaActual() {
        Sala actual = getSalaActual();
        System.out.println();
        System.out.println("=== " + actual.getNombre() + " ===");
        System.out.println(actual.getDescripcion());
        if (actual.getObjeto() != null) {
            System.out.println("Hay un objeto acá: " + actual.getObjeto().getNombre());
        }
    }

    public void procesarMovimiento(String direccion) throws SalidaInvalidaException {
        Sala actual = getSalaActual();

        if (!actual.tieneSalida(direccion)) {
            throw new SalidaInvalidaException("No hay salida hacia el " + direccion + " desde acá.", false);
        }

        int destinoId = actual.getSalidaHacia(direccion);
        Sala destino = buscarSalaPorId(destinoId);

        if (destino.isEsTrampa() && !jugador.tieneObjeto("Antorcha")) {
            throw new SalidaInvalidaException("Está muy oscuro para seguir sin una antorcha. Te perdés en la oscuridad...", true);
        }
        if (destino.isEsFinal() && !jugador.tieneObjeto("Llave Dorada")) {
            throw new SalidaInvalidaException("La puerta está cerrada con llave. No podés forzarla...", true);
        }

        jugador.moverA(destinoId);
        destino.setVisitada(true);
    }

    public void iniciarPartida() {
        Scanner sc = new Scanner(System.in);
        boolean jugando = true;

        while (jugando) {
            mostrarSalaActual();
            System.out.println("1) Moverse  2) Tomar objeto  3) Ver inventario  4) Usar objeto  5) Salir");
            System.out.print("Opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: {
                    System.out.print("Dirección (norte/sur/este/oeste): ");
                    String direccion = sc.nextLine();
                    try {
                        procesarMovimiento(direccion);
                        if (getSalaActual().isEsFinal()) {
                            System.out.println();
                            System.out.println("¡La puerta se abre con la Llave Dorada! Escapaste de la mazmorra.");
                            System.out.println("--- VICTORIA ---");
                            jugando = false;
                        }
                    } catch (SalidaInvalidaException e) {
                        System.out.println(e.getMessage());
                        if (e.isFatal()) {
                            jugador.setVivo(false);
                            System.out.println("--- GAME OVER ---");
                            jugando = false;
                        }
                    }
                    break;
                }
                case 2: {
                    Sala actual = getSalaActual();
                    if (actual.getObjeto() == null) {
                        System.out.println("No hay nada para tomar en esta sala.");
                    } else {
                        Objeto tomado = actual.getObjeto();
                        jugador.agregarObjeto(tomado);
                        actual.setObjeto(null);
                        System.out.println("Tomaste: " + tomado.getNombre());
                    }
                    break;
                }
                case 3:
                    jugador.mostrarInventario();
                    break;
                case 4: {
                    System.out.print("Nombre del objeto a usar: ");
                    String nombreObjeto = sc.nextLine();
                    Objeto encontrado = jugador.buscarObjetoPorNombre(nombreObjeto);
                    if (encontrado == null) {
                        System.out.println("No tenés ese objeto en el inventario.");
                    } else {
                        System.out.println("Usás " + encontrado.getNombre() + ": " + encontrado.getDescripcion());
                    }
                    break;
                }
                case 5:
                    System.out.println("Saliste de la mazmorra sin terminar la partida.");
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
