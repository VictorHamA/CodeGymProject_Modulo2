package Bosque;

public class Isla {

    public static final int FILA_MAXIMA = 10;
    public static final int COLUMNA_MAXIMA = 20;
    public static int maximaOcupacion= FILA_MAXIMA * COLUMNA_MAXIMA;
    public static int controlPoblacional=0;

    public static void main(String[] args) {

        PanelIsla frame = new PanelIsla("Isla");
        frame.setBounds(10,20,1500,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

