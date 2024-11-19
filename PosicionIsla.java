package Bosque;

import javax.swing.*;

public class PosicionIsla implements Movimientos {
    private JTextField[][] textField;
    private static JTextArea areaMensajes;

    public PosicionIsla(JTextField[][] txtField, JTextArea areaMensajes) {
        this.textField = txtField;
        this.areaMensajes= areaMensajes;
    }

    public synchronized void colocarPlantaEnIsla(int fila, int columna, Plantas planta){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textField[fila][columna].setText(planta.getEmoji());
            }
        });
    }

    @Override
    public synchronized void moverAnimal(int filaAnterior, int columnaAnterior, int newFila, int newColumna, Animal animal){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    String contenidoActual = textField[newFila][newColumna].getText();
                    String emojiNuevo = animal.getEmoji();
                    String nuevoContenido = contenidoActual + emojiNuevo;
                    textField[newFila][newColumna].setText(nuevoContenido);
                    textField[filaAnterior][columnaAnterior].setText("");
               }
            });
    }

}




