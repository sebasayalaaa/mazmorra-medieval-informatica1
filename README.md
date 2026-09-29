# La Mazmorra Medieval

Proyecto Integrador Final — Informática I (UPA)
Grupo 9: Sebastián Ayala y Esteban Ayala
Categoría: Aventura de texto (exploración) · Ambientación: Mazmorra medieval

Juego de consola en Java: el jugador explora una mazmorra medieval, recolecta objetos y tiene que llegar a la Cámara Final para ganar.

## Documentación

- [`DIAGRAMA_CLASES.md`](DIAGRAMA_CLASES.md) — diagrama UML de clases.
- [`DISENO.md`](DISENO.md) — diseño del juego y decisiones tomadas.

## Cómo correr el juego

```
cd src
javac -encoding UTF-8 *.java
java Mazmorra
```

## Requisitos técnicos cubiertos

- Clases encapsuladas: `Sala`, `Objeto`, `Jugador`
- Estructura de datos estándar: `ArrayList<Sala>` (mapa) y `ArrayList<Objeto>` (inventario)
- Excepción personalizada: `SalidaInvalidaException`
- Algoritmo: búsqueda lineal en el inventario, ordenamiento (Bubble Sort) opcional
- Menú funcional por consola de principio a fin
