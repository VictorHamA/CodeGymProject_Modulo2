package Bosque;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Bosque.carnivoros.*;
import Bosque.herbivoros.*;
import static Bosque.Isla.*;


public abstract class Animal implements Runnable{

    private String especie;
    private static final List<Animal> listaAnimales = new ArrayList<>();
    private int filaActual;
    private int columnaActual;

    private Movimientos movimiento;
    private volatile boolean caminar = true;

    public Animal(String especie, int filaActual, int columnaActual, Movimientos movimiento) {
        this.especie = especie;
        this.filaActual = filaActual;
        this.columnaActual = columnaActual;
        this.movimiento = movimiento;

        synchronized (listaAnimales) {
            listaAnimales.add(this);
        }
    }

    public static List<Animal> getListaAnimales() {
        synchronized (listaAnimales) {
            return new ArrayList<>(listaAnimales);
        }
    }

    public void eliminar() {
        synchronized (listaAnimales) {
            listaAnimales.remove(this);
        }
    }

    public void detener() {
        this.caminar = false;
    }

    public void run() {
        while (caminar) {
            int filaAnterior = this.filaActual;
            int columnaAnterior = this.columnaActual;

            int[] nuevaPosicion = avanzar();
            int nuevaFila = nuevaPosicion[0];
            int nuevaColumna = nuevaPosicion[1];
            synchronized (Animal.class) {
                for (Animal thatAnimal : getListaAnimales()) {
                    if (thatAnimal != this && thatAnimal.getFilaActual() == nuevaFila && thatAnimal.getColumnaActual() == nuevaColumna) {
                        if (this instanceof Carnivoros && thatAnimal instanceof Herbivoros) {
                            String mensaje=((Carnivoros) this).comer((Herbivoros) thatAnimal);
                            PanelIsla.mostrarMensaje(mensaje);

                        } else if (this instanceof Herbivoros && thatAnimal instanceof Carnivoros) {
                            String mensaje=((Carnivoros) thatAnimal).comer((Herbivoros) this);
                            PanelIsla.mostrarMensaje(mensaje);

                        } else if (this instanceof Herbivoros && thatAnimal instanceof Herbivoros) {
                            // Solo el Pato, Raton y Jabali comen a la Oruga, en sus clases se implemento el metodo
                            // hervibivoro come herbivoro, los demas animales hervivoros devuelven null
                            // thatAnimal = oruga
                            if(thatAnimal.especie=="Oruga") {
                                String mensaje = ((Herbivoros) this).comer((Herbivoros) thatAnimal);
                                if(mensaje!=null){
                                    PanelIsla.mostrarMensaje(mensaje);
                                }

                            } else if(this.especie=="Oruga"){

                                boolean comeHerbivoro = switch (thatAnimal.especie) {
                                    case "Raton" -> true;
                                    case "Jabali" -> true;
                                    case "Pato"-> true;
                                    default -> false;
                                };

                                if(comeHerbivoro){
                                    String mensaje = ((Herbivoros) thatAnimal).comer((Herbivoros) this);
                                    if (mensaje!=null){
                                        PanelIsla.mostrarMensaje(mensaje);
                                    }
                                }

                            }

                        }

                        //Reproducir
                        if (this.especie.equals(thatAnimal.especie)){
                            boolean sexoThis = ThreadLocalRandom.current().nextBoolean();
                            boolean sexoThat = ThreadLocalRandom.current().nextBoolean();
                            if(sexoThis!=sexoThat){

                                   // PanelIsla.mostrarMensaje( "Maxima ocupacion " +(int) maximaOcupacion* 0.10 + ", Población Actual:" +controlPoblacional);

                                    int bornFila = ThreadLocalRandom.current().nextInt(FILA_MAXIMA);
                                    int bornColumna = ThreadLocalRandom.current().nextInt(COLUMNA_MAXIMA);

                                    if (this instanceof Carnivoros) {

                                        if( controlPoblacional  <= (int) maximaOcupacion * 0.10) {
                                            String mensaje = ((Carnivoros) this).reproducir(especie, bornFila, bornColumna, movimiento);
                                            PanelIsla.mostrarMensaje(mensaje);
                                            controlPoblacional++;
                                            try {
                                                PanelIsla.mostrarNacimiento(this.filaActual, this.columnaActual, getEmoji());
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        } else {

                                            String mensaje = "Sobrepoblamiento animal, el \uD83D\uDE4D guardabosques inhabilito la reproducción de carnivoros";
                                            PanelIsla.mostrarMensaje(mensaje);
                                        }

                                    } else if (this instanceof Herbivoros) {
                                        String mensaje = ((Herbivoros) this).reproducir(especie, bornFila, bornColumna, movimiento);
                                        PanelIsla.mostrarMensaje(mensaje);

                                        try {
                                            PanelIsla.mostrarNacimiento(this.filaActual, this.columnaActual, getEmoji());
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                            }
                        }
                    }
                }

                for (Plantas planta : Plantas.obtenerPlantas()) {
                    if (planta.getFila() == nuevaFila && planta.getColumna() == nuevaColumna) {
                        if (this instanceof Herbivoros) {
                            String mensaje = ((Herbivoros) this).comer(planta);
                            PanelIsla.mostrarMensaje(mensaje);
                        }
                    }
                }
                this.movimiento.moverAnimal(filaAnterior, columnaAnterior, nuevaFila, nuevaColumna, this);
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int[] avanzar() {
        int[][] movimientos = {
                {-1, 0}, // arriba
                {1, 0},  // abajo
                {0, -1}, // izquierda
                {0, 1}   // derecha
        };
        int direccion;
        int newRow, newCol;

        do {
            direccion = ThreadLocalRandom.current().nextInt(movimientos.length);
            newRow = this.filaActual + movimientos[direccion][0];
            newCol = this.columnaActual + movimientos[direccion][1];
        } while (newRow < 0 || newRow >= FILA_MAXIMA || newCol < 0 || newCol >= COLUMNA_MAXIMA);

        this.filaActual=newRow;
        this.columnaActual=newCol;
        return new int[]{newRow, newCol};
    }

    public abstract String getEmoji();

    public int getFilaActual() {
        return filaActual;
    }

    public void setFilaActual(int filaActual) {
        this.filaActual = filaActual;
    }

    public int getColumnaActual() {
        return columnaActual;
    }

    public void setColumnaActual(int columnaActual) {
        this.columnaActual = columnaActual;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }



}
