# La Mazmorra Medieval — Diseño del juego

Proyecto Integrador Final · Informática I · UPA
Grupo 9: Sebastián Ayala y Esteban Ayala
Categoría: Aventura de texto (exploración) · Ambientación: Mazmorra medieval

## De qué se trata

El jugador despierta en la entrada de una mazmorra medieval y tiene que escapar llegando a la Cámara Final. En el camino se mueve entre 7 salas conectadas (norte/sur/este/oeste), recolecta objetos y tiene que resolver tres obstáculos usando lo que encontró:

1. El **Pasillo Oscuro** es intransitable sin la **Antorcha**.
2. El **Foso de Pinchos** es intransitable sin el **Escudo**.
3. La **Cámara Final** está cerrada y no se puede ganar sin la **Llave Dorada**.

Los tres objetos están en salas distintas a las que hay que ir a buscar antes de poder avanzar, así que el jugador necesariamente explora el mapa yendo y viniendo antes de poder terminar la partida.

## Mapa de salas

| Sala | Objeto | Bloqueada por | Salidas |
|---|---|---|---|
| 0. Entrada | — | — | norte → 1 |
| 1. Sala de las Antorchas | Antorcha | — | sur → 0, norte → 2, este → 3 |
| 2. Pasillo Oscuro (trampa) | — | Antorcha | sur → 1, norte → 5 |
| 3. Sala del Cofre | Llave Dorada | — | oeste → 1 |
| 5. Sala de las Estatuas | Escudo | — | sur → 2, este → 6 |
| 6. Foso de Pinchos (trampa) | — | Escudo | oeste → 5, norte → 4 |
| 4. Cámara Final | — | Llave Dorada (victoria) | sur → 6 |

## Cómo termina la partida

- **Victoria:** el jugador llega a la sala 4 (Cámara Final) con la Llave Dorada en el inventario.
- **Derrota:** el jugador intenta cruzar el Pasillo Oscuro sin Antorcha, el Foso de Pinchos sin Escudo, o entrar a la Cámara Final sin la Llave Dorada — en los tres casos se lanza `SalidaInvalidaException`, se atrapa en el menú principal y termina la partida mostrando el motivo.
- El jugador también puede salir del juego en cualquier momento desde el menú.

## Decisiones de diseño

- **`Sala` usa 4 atributos `int` (norte/sur/este/oeste)** en lugar de una estructura más genérica (como un mapa dirección→sala) porque son solo 4 direcciones fijas y así se mantiene simple y encapsulado, sin usar una estructura de datos que no se vio en el curso (HashMap). `-1` en cualquiera de los cuatro significa que no hay salida en esa dirección.
- **El mapa completo vive en `ArrayList<Sala>` dentro de `Mazmorra`**, y cada sala se referencia por su `id` (posición lógica, no necesariamente el índice del ArrayList) — esto cumple el requisito de estructura de datos estándar de Java.
- **El inventario del jugador es `ArrayList<Objeto>`** dentro de `Jugador`. Se recorre con búsqueda lineal (`buscarObjetoPorNombre`) cuando el jugador escribe "usar antorcha" o "usar llave dorada", y opcionalmente se puede ordenar por valor con Bubble Sort (`ordenarInventarioPorValor`) para mostrarlo ordenado en el menú de inventario.
- **`SalidaInvalidaException` se usa para dos casos inválidos del juego**: elegir una dirección que no existe desde la sala actual, y entrar a una sala bloqueada sin el objeto requerido. Se decidió usar la misma excepción para ambos casos (con mensajes distintos) porque conceptualmente ambos son "no podés ir para ahí ahora", en vez de crear una excepción separada para cada caso. Un atributo `boolean fatal` dentro de la excepción distingue si el error termina la partida (sala bloqueada) o simplemente deja reintentar (dirección inexistente).
- **`Sala` tiene un atributo `itemRequerido` (String, `null` si la sala no pide nada)** en lugar de que `Mazmorra` sepa "a mano" que el Pasillo Oscuro pide Antorcha y la Cámara Final pide Llave Dorada. Así, `procesarMovimiento()` tiene una única regla genérica ("si la sala pide un objeto y no lo tenés, no podés pasar") que sirve para cualquier cantidad de salas bloqueadas — agregar el Foso de Pinchos (pide Escudo) no requirió tocar la lógica de movimiento, solo agregar la sala en `construirMapa()`.
- **`Mazmorra` es la clase con `main()`** y concentra el menú y el bucle de juego; no se consideró parte de las "2-3 clases encapsuladas" del requisito porque su rol es orquestar el juego, no modelar una entidad del dominio (eso lo hacen `Sala`, `Objeto` y `Jugador`).
