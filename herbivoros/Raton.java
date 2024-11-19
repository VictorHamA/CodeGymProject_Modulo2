package Bosque.herbivoros;

import Bosque.Animal;
import Bosque.Movimientos;
import Bosque.Plantas;

public class Raton extends Animal implements Herbivoros {

    public Raton(String especie, int filaActual, int columnaActual, Movimientos movimiento) {
        super(especie, filaActual, columnaActual, movimiento);
    }

    @Override
    public String getEmoji() {
        return "🐁";
    }

    @Override
    public String comer(Plantas planta) {
        planta.borrar();
        planta.eliminar();
        return "El ratón " +getEmoji() + " comió una planta " + planta.getEmoji();
    }

    @Override
    public String comer(Herbivoros herbivoro) {
        ((Animal) herbivoro).detener();
        ((Animal) herbivoro).eliminar();

        //return "El ratón " +getEmoji() + " comió un herbivoro " + ((Animal) herbivoro).getEmoji();
        return "El " + this.getEspecie() + " " + getEmoji() + " se comió una " +
                    ((Animal) herbivoro).getEspecie() + " " + ((Animal) herbivoro).getEmoji();
    }

    @Override
    public String reproducir(String especie, int fila, int columna, Movimientos movimiento) {

        Herbivoros herbivoro = new Raton(especie,fila, columna,movimiento);
        Thread animalHerbivoro = new Thread((Animal) herbivoro);
        animalHerbivoro.start();

        return "Nació un " + this.getEspecie() + " " +this.getEmoji() + " ";
    }

}
