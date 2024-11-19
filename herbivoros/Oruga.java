package Bosque.herbivoros;

import Bosque.Animal;
import Bosque.Movimientos;
import Bosque.Plantas;

public class Oruga extends Animal implements Herbivoros {

    public Oruga(String especie, int filaActual, int columnaActual, Movimientos movimiento) {
        super(especie, filaActual, columnaActual, movimiento);
    }

    @Override
    public String getEmoji() {
        return "🐛";
    }

    @Override
    public String comer(Plantas planta) {
        planta.borrar();
        planta.eliminar();
        return "La oruga "+ getEmoji()+ " se comió una planta " + planta.getEmoji();
    }

    @Override
    public String comer(Herbivoros herbivoro) {
        return null;
    }

    @Override
    public String reproducir(String especie, int fila, int columna, Movimientos movimiento) {

        Herbivoros herbivoro = new Oruga(especie,fila, columna,movimiento);
        Thread animalHerbivoro = new Thread((Animal) herbivoro);
        animalHerbivoro.start();

        return "Nació un " + this.getEspecie() + " " +this.getEmoji() + " ";
    }
}
