package Bosque.herbivoros;

import Bosque.Movimientos;
import Bosque.Plantas;

public interface Herbivoros {
    String comer(Plantas planta);
    String comer(Herbivoros herbivoro);
    String reproducir(String especie, int fila, int columna, Movimientos movimiento);
}
