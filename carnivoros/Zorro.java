package Bosque.carnivoros;

import Bosque.Animal;
import Bosque.Movimientos;
import Bosque.herbivoros.Herbivoros;

public class Zorro extends Animal implements Carnivoros {

    public Zorro(String especie, int filaActual, int columnaActual, Movimientos movimiento) {
        super(especie, filaActual, columnaActual, movimiento);
    }

    @Override
    public String getEmoji() {
        return "🦊";
    }

    @Override
    public String comer(Herbivoros herbivoro) {
        ((Animal) herbivoro).detener();
        ((Animal) herbivoro).eliminar();
        return "El Zorro "+ getEmoji() +" ha atrapado a un herbívoro " + ((Animal) herbivoro).getEmoji();
    }

    @Override
    public String reproducir(String especie, int fila, int columna, Movimientos movimiento) {

        Carnivoros carnivoro = new Zorro(especie,fila, columna,movimiento);
        Thread animalCarnivoro = new Thread((Animal)carnivoro);

        animalCarnivoro.start();

        return "Nació un " + this.getEspecie() + " " +this.getEmoji() + " ";
    }

}
