import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Clase principal del juego: arma el mapa, contiene el menu y el bucle de
// partida (main() esta aca). No modela una entidad del dominio como Sala,
// Objeto o Jugador, sino que orquesta el juego usando esas tres clases.
public class Mazmorra {
    private ArrayList<Sala> mapa;
    private Jugador jugador;

    public Mazmorra() {
        this.mapa = new ArrayList<>();
        construirMapa();
        this.jugador = new Jugador("Aventurero", 0);
    }

    // Crea las 7 salas de la mazmorra y las conecta entre si por id
    // (ver DISENO.md para el mapa completo con el dibujo de las conexiones).
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
                5, 1, -1, -1);
        pasilloOscuro.setEsTrampa(true);
        pasilloOscuro.setItemRequerido("Antorcha");

        Sala salaCofre = new Sala(3, "Sala del Cofre",
                "Un cofre de madera vieja está entreabierto en el centro de la sala.",
                -1, -1, -1, 1);
        salaCofre.setObjeto(new Objeto("Llave Dorada", "Una llave ornamentada, parece abrir algo importante.", 50, true));

        Sala estatuas = new Sala(5, "Sala de las Estatuas",
                "Estatuas de piedra de antiguos guerreros custodian la sala. Una todavía sostiene un escudo intacto.",
                -1, 2, 6, -1);
        estatuas.setObjeto(new Objeto("Escudo", "Un escudo de guerrero, pesado pero firme.", 30, false));

        Sala fosoPinchos = new Sala(6, "Foso de Pinchos",
                "El piso está cubierto de pinchos oxidados. Cruzar sin protección es una sentencia de muerte.",
                4, -1, -1, 5);
        fosoPinchos.setEsTrampa(true);
        fosoPinchos.setItemRequerido("Escudo");

        Sala camaraFinal = new Sala(4, "Cámara Final",
                "Una puerta enorme con una cerradura dorada bloquea la salida de la mazmorra.",
                -1, 6, -1, -1);
        camaraFinal.setEsFinal(true);
        camaraFinal.setItemRequerido("Llave Dorada");

        mapa.add(entrada);
        mapa.add(antorchas);
        mapa.add(pasilloOscuro);
        mapa.add(salaCofre);
        mapa.add(estatuas);
        mapa.add(fosoPinchos);
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
        System.out.println("Salidas: " + actual.getSalidasDisponibles());
    }

    public void procesarMovimiento(String direccion) throws SalidaInvalidaException {
        Sala actual = getSalaActual();

        if (!actual.tieneSalida(direccion)) {
            throw new SalidaInvalidaException("No hay salida hacia el " + direccion + " desde acá.", false);
        }

        int destinoId = actual.getSalidaHacia(direccion);
        Sala destino = buscarSalaPorId(destinoId);

        // Cualquier sala con itemRequerido bloquea el paso si el jugador no lo tiene.
        // No importa si es una trampa o la puerta final: la regla es la misma,
        // así que agregar una sala bloqueada nueva no requiere tocar este método.
        if (destino.getItemRequerido() != null && !jugador.tieneObjeto(destino.getItemRequerido())) {
            String mensaje = "Te falta " + destino.getItemRequerido() + " para poder seguir. No es seguro continuar así...";
            throw new SalidaInvalidaException(mensaje, true);
        }

        jugador.moverA(destinoId);
        destino.setVisitada(true);
    }

    // Bucle principal del juego: muestra la sala actual, el menu, lee la
    // opcion elegida y la ejecuta. Sigue repitiendo hasta que el jugador
    // gana (llega a la Camara Final con la llave), pierde (excepcion
    // fatal) o elige salir.
    public void iniciarPartida() {
        Scanner sc = new Scanner(System.in);
        boolean jugando = true;

        System.out.println("=================================");
        System.out.println("      LA MAZMORRA MEDIEVAL");
        System.out.println("=================================");
        System.out.println("Quedaste atrapado en una mazmorra. Para escapar tenés que");
        System.out.println("llegar a la Cámara Final, pero el camino tiene dos pasos");
        System.out.println("bloqueados que vas a necesitar resolver explorando primero.");

        while (jugando) {
            mostrarSalaActual();
            System.out.println("1) Moverse  2) Tomar objeto  3) Ver inventario  4) Usar objeto  5) Salir");
            System.out.print("Opción: ");
            int opcion;
            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Eso no es un número. Elegí una opción del 1 al 5.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            switch (opcion) {
                case 1: {
                    System.out.print("Dirección (norte/sur/este/oeste): ");
                    String direccion = sc.nextLine();
                    if (direccion.trim().isEmpty()) {
                        System.out.println("Tenés que escribir una dirección: norte, sur, este u oeste.");
                        break;
                    }
                    try {
                        procesarMovimiento(direccion);
                        System.out.println("Caminás hacia el " + direccion.trim().toLowerCase() + "...");
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
