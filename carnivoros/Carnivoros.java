package Bosque.carnivoros;

import Bosque.Movimientos;
import Bosque.herbivoros.Herbivoros;

public interface Carnivoros {
    String comer(Herbivoros herbivoro);
    String reproducir(String especie, int fila, int columna, Movimientos movimiento);
}
