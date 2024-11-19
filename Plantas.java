package Bosque;

import java.util.ArrayList;
import java.util.List;

public class Plantas implements Runnable{

    private static final List<Plantas> plantas = new ArrayList<>();

    private int fila;
    private int columna;
    private Movimientos movimiento;

    private volatile boolean planta = true;

    public Plantas(int fila, int columna, Movimientos movimiento) {
        this.fila = fila;
        this.columna = columna;
        this.movimiento = movimiento;
        synchronized (plantas) {
            plantas.add(this);
        }
    }
    public static List<Plantas> obtenerPlantas() {
        synchronized (plantas) {
            return new ArrayList<>(plantas);
        }
    }

    public void eliminar() {
        synchronized (plantas) {
            plantas.remove(this);
        }
    }

    @Override
    public void run() {
        while (planta) {
            movimiento.colocarPlantaEnIsla(fila, columna, this);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void borrar(){
        planta = false;
    }

    public String getEmoji() {
        return "🌳";
    }

}
