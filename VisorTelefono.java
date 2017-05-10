import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Stack;
import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * Describe la clase VisorTeléfono, en ella se crean los botones que permitirán próximamente darles
 * un mayor uso, pero en esta versión sólo los creamos.
 * 
 * @author Adrian Arias
 * @version 25/04/2017
 */
//Modificado Unico
public class VisorTelefono
{
    // -------------------------------------- CONSTANTES

    private static final int ESPACIO_BORDE = 10; // Son los píxeles que dejaremos entre la ventana y el botón.
    private static final String MENSAJE_LLAMADA = "¡¡L L A M A N D O!";
    private static final int MIN_DIGITOS = 3;
    private static final int NUM_DIGITOS = 10; //Numero de dígitos del telefono
    // -------------------------------------- CAMPOS O VARIABLES DE INSTANCIA

    private JFrame ventana; // Contiene todos los botones del teléfono.

    private JTextField cajaTexto;
    private JButton botonInformacion;
    private JPanel panel_botones;
    private JPanel contenidoVentana;
    private String numeroMarcado;
    private Stack<String> listaNumeros;
    private int numIntentos; // Es el número de intentos que lleva la persona.
    private String pass2 = "1234";
    private JButton botonN, botonB, botonC, botonL, botonLl, botonR;
    private int vecesCero, vecesUno, vecesDos, vecesTres, vecesCuatro, vecesCinco, vecesSeis, vecesSiete,
    vecesOcho, vecesNueve;

    private int [] cuantas;

    private long tiempoLlamada, tiempoEmpieza;
    // -------------------------------------- CONSTRUCTOR
    /**
     * Constructor for objects of class VisorTelefono
     */
    public VisorTelefono()
    {
        construirVentana();
        listaNumeros = new Stack<String>();
        // Ajustamos la ventana al centro de la pantalla.
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        ventana.setLocation(d.width/2-ventana.getWidth()/2, d.height/2-ventana.getHeight()/2); 
        ventana.setVisible(true);
        cuantas = new int [10];
    }

    // -------------------------------------- MÉTODOS PRIVADOS

    /**
     * Crea la ventana del teléfono y sus componentes (en este caso los botones).
     */
    private void construirVentana()
    {
        if (numIntentos == 3) {
            System.exit(0);
        }
        String pass;
        pass = JOptionPane.showInputDialog("Teclee el codigo PIN");
        if (pass.equals(pass2)) {
            // -- Creamos la ventana y le asignamos un nombre;
            ventana = new JFrame ("Teléfono");

            // -- Creamos el panel contenedor.
            contenidoVentana = (JPanel) ventana.getContentPane();
            contenidoVentana.setBorder(new EmptyBorder(ESPACIO_BORDE, ESPACIO_BORDE, ESPACIO_BORDE, ESPACIO_BORDE)); // Le asignamos un borde.
            contenidoVentana.setLayout (new BorderLayout(ESPACIO_BORDE, ESPACIO_BORDE)); // Para separar todo un poco.

            creaBotones();

            // La construcción está hecha. Se organizan los componentes y se muestran
            ventana.pack();

        }
        else {
            numIntentos++;
            construirVentana();
        }
    }

    /**
     * Creamos la zona de los botones y los botones.
     */
    private void creaBotones()
    {
        // --  Creamos la caja donde se verán los valores que deseamos.

        cajaTexto = new JTextField();
        contenidoVentana.add(cajaTexto);

        panel_botones = new JPanel();
        contenidoVentana.add(panel_botones);

        panel_botones.setLayout(new GridLayout(5, 3, ESPACIO_BORDE, ESPACIO_BORDE));  // Las dos últimas sirven como separación horizontal y vertical entre ellas.

        agregarBotonNumero(panel_botones, "1");
        agregarBotonNumero(panel_botones, "2");
        agregarBotonNumero(panel_botones, "3");
        agregarBotonNumero(panel_botones, "4");
        agregarBotonNumero(panel_botones, "5");
        agregarBotonNumero(panel_botones, "6");
        agregarBotonNumero(panel_botones, "7");
        agregarBotonNumero(panel_botones, "8");
        agregarBotonNumero(panel_botones, "9");

        agregarBotonBorrar(panel_botones, "B");
        agregarBotonNumero(panel_botones, "0");
        agregarBotonLimpiar(panel_botones, "L");

        agregarBotonRepetir(panel_botones, "R");
        agregarBotonLlamada(panel_botones, "LL");
        agregarBotonColgar(panel_botones, "C");

        botonInformacion = new JButton("Información");
        botonInformacion.setToolTipText("Informa de las teclas pulsadas");
        botonInformacion.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){ mostrarInformacion();}
            });
        contenidoVentana.add(botonInformacion);

        contenidoVentana.add(botonInformacion, BorderLayout.SOUTH);
        contenidoVentana.add (cajaTexto, BorderLayout.NORTH);
        contenidoVentana.add (panel_botones, BorderLayout.CENTER);
    }

    /**
     * 
     */
    private void habilitarBotonesEspeciales()
    {
        if (!cajaTexto.equals("")) {
            botonB.setEnabled(true);
            botonL.setEnabled(true);
            botonLl.setEnabled(true);
            botonR.setEnabled(true);
        }
    }

    /**
     * Permite mostrar la información al pulsar el botón
     */
    private void mostrarInformacion()
    {
        String informacion = "Pulsaciones de teclas\n\n";
        for(int i = 0; i <= 9; i++){
            informacion += "Tecla " + i + " - " + cuantas[i];
            if(cuantas[i] == 1){
                informacion += " vez\n";
            }
            else{
                informacion += " veces\n";
            }
        }
        informacion += "\n";
        JOptionPane.showMessageDialog(ventana,informacion,"Informacion",JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Permite agregar los botones con sus acciones
     */
    private void agregarBotonNumero(Container panel, String textoBoton)
    {
        botonN = new JButton(textoBoton);
        panel_botones.add(botonN);
        botonN.setToolTipText("Marca el " + textoBoton);
        botonN.addActionListener (new ActionListener () {
                public void actionPerformed (ActionEvent evento) {
                    String numTelefono = cajaTexto.getText();
                    if(!numTelefono.equals(MENSAJE_LLAMADA)){
                        cajaTexto.setText(numTelefono + textoBoton);
                        int numero = Integer.parseInt(textoBoton);
                        cuantas[numero]++;
                    }
                    habilitarBotonesEspeciales();
                }
            });
    }

    /**
     * Permite agregar al boton borrar sus borrar el ultimo digito
     */
    private void agregarBotonBorrar(Container panel, String textoBoton)
    {
        botonB = new JButton(textoBoton);
        panel_botones.add(botonB);
        botonB.setEnabled(false);
        botonB.setToolTipText("Marca el " + textoBoton);
        botonB.addActionListener (new ActionListener () {
                public void actionPerformed (ActionEvent evento) {
                    String numMarcado = cajaTexto.getText();
                    int numDigitos = numMarcado.length();
                    if(numDigitos > 0 && !numMarcado.equals(MENSAJE_LLAMADA)){
                        cajaTexto.setText(numMarcado.substring(0, numDigitos - 1));
                        int numero = Integer.parseInt(numMarcado.substring(numDigitos -1));
                        cuantas[numero]--;
                    }
                    if (numDigitos == 1) {
                        botonB.setEnabled(false);
                        botonL.setEnabled(false);
                        botonC.setEnabled(false);
                        botonLl.setEnabled(false);
                    }
                }
            });
    }

    /**
     * Permite agregar al boton Limpiar sus borrar el todos los digitos
     */
    private void agregarBotonLimpiar(Container panel, String textoBoton)
    {
        botonL = new JButton(textoBoton);
        panel_botones.add(botonL);
        botonL.setEnabled(false);
        botonL.setToolTipText("Marca el " + textoBoton);
        botonL.addActionListener (new ActionListener () {
                public void actionPerformed (ActionEvent evento) {
                    cajaTexto.setText("");
                    botonLl.setEnabled(false);
                    botonL.setEnabled(false);
                }
            });
    }

    /**
     * Permite agregar al boton repetir escribir la ultima llamada
     */
    private void agregarBotonRepetir(Container panel, String textoBoton)
    {
        botonR = new JButton(textoBoton);
        panel_botones.add(botonR);
        botonR.setEnabled(false);
        botonR.setToolTipText("Marca el " + textoBoton);
        botonR.addActionListener (new ActionListener () {
                public void actionPerformed (ActionEvent evento) {
                    if(!listaNumeros.isEmpty()){
                        String numero = listaNumeros.pop();
                        cajaTexto.setText(numero);
                    }
                }
            });
    }

    /**
     * Permite agregar al boton llamada la accion llamada
     */
    private void agregarBotonLlamada(Container panel, String textoBoton)
    {
        botonLl = new JButton(textoBoton);
        panel_botones.add(botonLl);
        botonLl.setEnabled(false);
        botonLl.setToolTipText("Marca el " + textoBoton);
        botonLl.addActionListener (new ActionListener () {
                public void actionPerformed (ActionEvent evento) {
                    numeroMarcado = cajaTexto.getText();
                    int numDigitos = numeroMarcado.length();
                    if(numDigitos >= MIN_DIGITOS){
                        cajaTexto.setText(MENSAJE_LLAMADA);
                        listaNumeros.push(numeroMarcado);
                        tiempoEmpieza = 0;
                        tiempoEmpieza = System.currentTimeMillis();
                        botonN.setEnabled(false);
                        botonB.setEnabled(false);
                        botonC.setEnabled(true);
                        botonL.setEnabled(false);
                    }
                }
            });
    }

    /**
     * Permite agregar al boton colgar cerrar la aplicacion
     */
    private void agregarBotonColgar(Container panel, String textoBoton)
    {
        botonC = new JButton(textoBoton);
        panel_botones.add(botonC);
        botonC.setEnabled(false);
        botonC.setToolTipText("Marca el " + textoBoton);
        botonC.addActionListener (new ActionListener () {
                public void actionPerformed (ActionEvent evento) {
                    String numTelefono = cajaTexto.getText();
                    if(numTelefono.equals(MENSAJE_LLAMADA)){
                        tiempoLlamada = System.currentTimeMillis();
                        JOptionPane.showMessageDialog(ventana, (tiempoLlamada - tiempoEmpieza)/1000 + " segundo(s)");
                        botonN.setEnabled(true);
                        botonLl.setEnabled(false);
                        botonL.setEnabled(false);
                        cajaTexto.setText("");
                    }
                }
            });
    }
}
