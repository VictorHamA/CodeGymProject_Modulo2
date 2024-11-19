package Bosque.herbivoros;

import Bosque.Animal;
import Bosque.Movimientos;
import Bosque.Plantas;

public class Bufalo extends Animal implements Herbivoros {

    public Bufalo(String especie, int filaActual, int columnaActual, Movimientos movimiento) {
        super(especie, filaActual, columnaActual, movimiento);
    }

    // abstract class Animal
    @Override
    public String getEmoji() {
        return "🐃";
    }

    // interface Herbivoros
    @Override
    public String comer(Plantas planta) {
        planta.borrar();
        planta.eliminar();
        return " El búfalo 🐃 se comió una planta 🌳";
    }

    // interface Herbivoros
    @Override
    public String comer(Herbivoros herbivoro) {
        return null;
    }

    @Override
    public String reproducir(String especie, int fila, int columna, Movimientos movimiento) {

        Herbivoros herbivoro = new Bufalo(especie,fila, columna,movimiento);
        Thread animalHerbivoro = new Thread((Animal) herbivoro);
        animalHerbivoro.start();

        return "Nació un " + this.getEspecie() + " " +this.getEmoji() + " ";
    }

}
