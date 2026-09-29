# Diagrama de Clases — La Mazmorra Medieval

Proyecto Integrador Final · Informática I · UPA
Grupo 9: Sebastián Ayala y Esteban Ayala
Categoría: Aventura de texto (exploración) · Ambientación: Mazmorra medieval

Este diagrama se entrega ANTES de empezar a programar, según lo pedido en la consigna. GitHub renderiza el bloque `mermaid` de abajo automáticamente al ver este archivo en el repositorio.

```mermaid
classDiagram
    class Sala {
        -int id
        -String nombre
        -String descripcion
        -int norte
        -int sur
        -int este
        -int oeste
        -Objeto objeto
        -boolean esTrampa
        -boolean esFinal
        -boolean visitada
        +Sala(id, nombre, descripcion, norte, sur, este, oeste)
        +getId() int
        +getNombre() String
        +getDescripcion() String
        +tieneSalida(direccion) boolean
        +getSalidaHacia(direccion) int
        +getObjeto() Objeto
        +setObjeto(Objeto o)
        +isEsTrampa() boolean
        +isEsFinal() boolean
        +isVisitada() boolean
        +setVisitada(boolean v)
    }

    class Objeto {
        -String nombre
        -String descripcion
        -int valor
        -boolean esLlave
        +Objeto(nombre, descripcion, valor, esLlave)
        +getNombre() String
        +getDescripcion() String
        +getValor() int
        +isEsLlave() boolean
    }

    class Jugador {
        -String nombre
        -int salaActualId
        -ArrayList~Objeto~ inventario
        -boolean vivo
        +Jugador(nombre, salaInicialId)
        +getNombre() String
        +getSalaActualId() int
        +moverA(int idSala)
        +agregarObjeto(Objeto o)
        +buscarObjetoPorNombre(String nombre) Objeto
        +ordenarInventarioPorValor()
        +tieneObjeto(String nombre) boolean
        +mostrarInventario()
        +isVivo() boolean
        +setVivo(boolean v)
    }

    class SalidaInvalidaException {
        +SalidaInvalidaException(String mensaje)
    }

    class Mazmorra {
        -ArrayList~Sala~ mapa
        -Jugador jugador
        +Mazmorra()
        +construirMapa()
        +iniciarPartida()
        +procesarMovimiento(String direccion)
        +mostrarSalaActual()
        +main(String[] args)
    }

    Mazmorra "1" *-- "5" Sala : contiene
    Mazmorra "1" o-- "1" Jugador : controla
    Sala "1" o-- "0..1" Objeto : puede tener
    Jugador "1" o-- "*" Objeto : inventario
    Mazmorra ..> SalidaInvalidaException : lanza / captura
```

## Cómo se cumplen los requisitos técnicos mínimos

| Requisito | Dónde |
|---|---|
| 2-3 clases encapsuladas | `Sala`, `Objeto`, `Jugador` (atributos privados, constructor, getters/setters) |
| Estructura de datos estándar | `ArrayList<Sala>` en `Mazmorra` (el mapa) y `ArrayList<Objeto>` en `Jugador` (inventario) |
| Excepción personalizada | `SalidaInvalidaException extends Exception` |
| Algoritmo de búsqueda/ordenamiento | Búsqueda lineal en `Jugador.buscarObjetoPorNombre()`; ordenamiento (Bubble Sort) en `Jugador.ordenarInventarioPorValor()` |
| Menú funcional de principio a fin | `Mazmorra.main()` con menú por consola (moverse, ver inventario, usar objeto, salir) |
