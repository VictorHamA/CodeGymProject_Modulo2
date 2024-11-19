package Bosque.herbivoros;

import Bosque.Animal;
import Bosque.Movimientos;
import Bosque.Plantas;

public class Cabra extends Animal implements Herbivoros {

    public Cabra(String especie, int filaActual, int columnaActual, Movimientos movimiento) {
        super(especie, filaActual, columnaActual, movimiento);
    }

    @Override
    public String getEmoji() {
        return "🐐";
    }

    @Override
    public String comer(Plantas planta) {
        planta.borrar();
        planta.eliminar();
        return "La cabra " + getEmoji() + " se comio una planta " + planta.getEmoji() ;
    }

    @Override
    public String comer(Herbivoros herbivoro) {
        return null;
    }

    @Override
    public String reproducir(String especie, int fila, int columna, Movimientos movimiento) {

        Herbivoros herbivoro = new Cabra(especie,fila, columna,movimiento);
        Thread animalHerbivoro = new Thread((Animal) herbivoro);
        animalHerbivoro.start();

        return "Nació un " + this.getEspecie() + " " +this.getEmoji() + " ";
    }

}
