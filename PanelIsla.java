package Bosque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;

import Bosque.carnivoros.*;
import Bosque.herbivoros.*;
import static Bosque.Isla.*;

public class PanelIsla extends JFrame{

    private JButton btnCarnivoro;
    private JButton btnHerbivoro;
    private JButton btnPlanta;
    private JButton btnTerminar;
    private static JTextField[][] txtFields;
    private static JTextArea areaMensajes;
    private Movimientos movimiento;

    private int maxCarnivoros = 5;
    private int maxHerbivoros = 10;


    PanelIsla(String titulo){
        super(titulo);
        iniciarComponentes();
        pack();
    }
    private void iniciarComponentes(){

        btnCarnivoro = new JButton("Crear un Animal Carnivoro");
        btnCarnivoro.setMnemonic(KeyEvent.VK_C);
        btnCarnivoro.setToolTipText("Añadir Carnivoro a la isla");

        btnHerbivoro = new JButton("Crear un Animal Herbivoro");
        btnHerbivoro.setMnemonic(KeyEvent.VK_H);
        btnHerbivoro.setToolTipText("Añadir Herbivoro a la isla");

        btnPlanta = new JButton("Crear una Planta");
        btnPlanta.setMnemonic(KeyEvent.VK_P);
        btnPlanta.setToolTipText("Añadir una Planta a la isla");

        btnTerminar = new JButton("Finalizar Programa");
        btnTerminar.setMnemonic(KeyEvent.VK_F);
        btnTerminar.setToolTipText("Terminar procesos en ejecución");

        txtFields = new JTextField[FILA_MAXIMA][COLUMNA_MAXIMA];

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(FILA_MAXIMA,COLUMNA_MAXIMA));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 24);

        for (int i = 0; i < FILA_MAXIMA; i++) {
            for (int j = 0; j < COLUMNA_MAXIMA; j++) {

                txtFields[i][j] = new JTextField(2);
                txtFields[i][j].setFont(font);
                txtFields[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                txtFields[i][j].setEditable(false);

                panel.add(txtFields[i][j]);
                panel.setVisible(true);

            }
        }

        JPanel panelBotones = new JPanel();

        panelBotones.add(btnCarnivoro);
        panelBotones.add(btnHerbivoro);
        panelBotones.add(btnPlanta);
        panelBotones.add(btnTerminar);
        panelBotones.setLayout(new BorderLayout());
        panelBotones.setLayout(new GridLayout(1,4));
        panelBotones.setVisible(true);

        areaMensajes = new JTextArea();
        areaMensajes.setFont(new Font("Segoe UI emoji",Font.PLAIN,18));
        areaMensajes.setRows(7);
        areaMensajes.setAutoscrolls(true);

        JScrollPane scrollPanel = new JScrollPane(areaMensajes);
        scrollPanel.setVisible(true);

        // Añadir los componentes al contenedor de la aplicación
        getContentPane().add(panel,BorderLayout.CENTER);
        getContentPane().add(panelBotones,BorderLayout.NORTH);
        getContentPane().add(scrollPanel,BorderLayout.SOUTH);

        movimiento = new PosicionIsla(txtFields,areaMensajes);

        // Eventos botones
        ActionListener eventoBotonCarnivoro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int newFila = ThreadLocalRandom.current().nextInt(FILA_MAXIMA);
                int newColumna = ThreadLocalRandom.current().nextInt(COLUMNA_MAXIMA);

                int carnivoroAleatorio = ThreadLocalRandom.current().nextInt(maxCarnivoros);
                Carnivoros carnivoro = switch (carnivoroAleatorio) {
                    case 0 -> new Lobo("Lobo", newFila,newColumna, movimiento);
                    case 1 -> new Boa("Boa", newFila,newColumna,  movimiento);
                    case 2 -> new Zorro("Zorro", newFila,newColumna, movimiento);
                    case 3 -> new Oso("Oso", newFila,newColumna, movimiento);
                    case 4 -> new Aguila("Aguila", newFila,newColumna, movimiento);
                    default ->  throw new IllegalStateException("Valor inesperado: " + carnivoroAleatorio);
                };

                txtFields[newFila][newColumna].setText( ((Animal) carnivoro).getEmoji() );
                mostrarMensaje("Carnivoro" + ((Animal) carnivoro).getEmoji() + "añadido a la isla");

                Thread animalCarnivoro = new Thread((Animal)carnivoro);
                animalCarnivoro.start();
                controlPoblacional ++;

            }
        };
        btnCarnivoro.addActionListener(eventoBotonCarnivoro);

        ActionListener eventoBotonHerbivoro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {

                int newFila = ThreadLocalRandom.current().nextInt(FILA_MAXIMA);
                int newColumna = ThreadLocalRandom.current().nextInt(COLUMNA_MAXIMA);

                int herbivoroAleatorio = ThreadLocalRandom.current().nextInt(maxHerbivoros);
                Herbivoros herbivoro = switch (herbivoroAleatorio) {
                    case 0 -> new Caballo("Caballo", newFila,newColumna, movimiento);
                    case 1 -> new Ciervo("Ciervo", newFila,newColumna,  movimiento) ;
                    case 2 -> new Conejo("Conejo", newFila,newColumna,  movimiento) ;
                    case 3 -> new Raton("Raton", newFila,newColumna, movimiento) ;
                    case 4 -> new Cabra("Cabra", newFila,newColumna, movimiento) ;
                    case 5 -> new Oveja("Oveja", newFila,newColumna, movimiento) ;
                    case 6 -> new Jabali("Jabali", newFila,newColumna, movimiento) ;
                    case 7 -> new Bufalo("Bufalo", newFila,newColumna, movimiento) ;
                    case 8 -> new Pato("Pato", newFila,newColumna, movimiento) ;
                    case 9 -> new Oruga("Oruga", newFila,newColumna, movimiento) ;
                    default ->  throw new IllegalStateException("Valor inexperado: " + herbivoroAleatorio);
                };

                txtFields[newFila][newColumna].setText( ((Animal)herbivoro).getEmoji() ) ;
                mostrarMensaje("Herbivoro" +  ((Animal)herbivoro).getEmoji() + "añadido a la isla");
                Thread animalHerbivoro= new Thread((Animal)herbivoro);
                animalHerbivoro.start();

            }
        };
        btnHerbivoro.addActionListener(eventoBotonHerbivoro);

        ActionListener eventoBotonPlanta = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {

                int newFila = ThreadLocalRandom.current().nextInt(FILA_MAXIMA);
                int newColumna = ThreadLocalRandom.current().nextInt(COLUMNA_MAXIMA);

                Plantas planta = new Plantas(newFila,newColumna,movimiento);
                mostrarMensaje("Se añadió una planta "+ planta.getEmoji() + " a la isla");

                txtFields[newFila][newColumna].setText(planta.getEmoji());
                Thread threadPlanta = new Thread(planta);

                threadPlanta.start();

            }
        };
        btnPlanta.addActionListener(eventoBotonPlanta);

        ActionListener eventoBotonTerminar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                System.exit(0);
            }
        };
        btnTerminar.addActionListener(eventoBotonTerminar);

        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent evento) {
                        super.windowClosing(evento);
                        System.exit(0);
                    }
                }
        );
    }

    public static void mostrarMensaje(String mensaje) {
        String msgActual = areaMensajes.getText();
        msgActual += mensaje + "\n";
        areaMensajes.setText(msgActual);
    }

    public static JTextArea getAreaMensajes() {
        return areaMensajes;
    }

    public static void setAreaMensajes(JTextArea areaMensajes) {
        PanelIsla.areaMensajes = areaMensajes;
    }

    public static JTextField[][] getTxtFields() {
        return txtFields;
    }

    public void setTxtFields(JTextField[][] txtFields) {
        this.txtFields = txtFields;
    }

    public static void mostrarNacimiento(int fila, int columna,String emoji) throws InterruptedException {
        txtFields[fila][columna].setText(emoji);
    }
}

