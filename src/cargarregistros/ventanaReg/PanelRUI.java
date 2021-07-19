package cargarregistros.ventanaReg;
import cargarregistros.archivos.RedactorRegistros;
import monitor.Registro;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import java.awt.*;
public class PanelRUI extends JPanel {
    private JButton cargarButton;
    private JButton terminarButton;

    private ListaRUI listaRUI;
    private TableRUI tableRUI;
    private final JFrame ventanaP;
    private Sintomas sintomas;
    private JLabel notificacion;

    public PanelRUI(Sintomas sintomas,CargarRUI ventanaP){
        this.sintomas=sintomas;
        this.ventanaP=ventanaP;
        setLayout(new GridBagLayout());
        inicializarComponentes();

        aniadirComponentes();
    }
    private void inicializarComponentes(){
        listaRUI=new ListaRUI(sintomas);
        tableRUI=new TableRUI(sintomas);
        cargarButton = new JButton("Cargar");
        terminarButton = new JButton("Terminar");
        notificacion = new JLabel("Usted se encuentra en la : "+obtenerFase());
        notificacion.setBackground(Color.RED);

    }

    private void aniadirComponentes(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 3;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        add(listaRUI,constraints);
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        add(tableRUI,constraints);
        constraints.gridx = 4;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weighty = 0.0;
        constraints.weightx = 0.0;
        add(cargarButton,constraints);
        constraints.gridx = 5;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(terminarButton,constraints);
        constraints.weighty = 0.1;
        constraints.weightx = 0.1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        add(notificacion,constraints);

       terminarButton.addActionListener(new ButtonsListener(this,terminarButton) );
       cargarButton.addActionListener(new ButtonsListener(this,cargarButton) );
    }

    public void cargarRegistro(){
        Registro r=listaRUI.cargarRegistro();
        (new RedactorRegistros()).escribirArchivo(r);
        tableRUI.actualizarTable(r);
        tableRUI.llenarListaRegistro();
    }
    private String obtenerFase(){
        String res="Primera Fase";
        for(Sintoma sinto : sintomas){
            if (sinto.getClass().getSimpleName().equals("SegundaFase")) {
                res = "Segunda Fase";
                break;
            }
        }
        return res;
    }
    public void mostrarNotificacion(String mensaje, int resDiagnostico){
        mensaje=hacerFormatoMensaje(mensaje);
        notificacion.setText(mensaje);
        notificacion.setOpaque(true);
        if(resDiagnostico==0) {
            notificacion.setBackground(new Color(122, 231, 125, 196));
        }else if(resDiagnostico<150){
            notificacion.setBackground(new Color(255, 253, 126, 196));
        }else{
            notificacion.setBackground(new Color(248, 129, 129, 196));
        }
    }

    private String hacerFormatoMensaje(String mensaje){
        String mensajeMod="";
        for(int i = 0;i<mensaje.length();i++){
            if(mensaje.charAt(i)=='\n'){
                mensajeMod+="<br>";
            }else{
                mensajeMod+=mensaje.charAt(i);
            }
        }
        mensajeMod = "<html><body>"+mensajeMod+"</body></html>";
        return mensajeMod;
    }
    public void terminar(){
        continuar();
        ventanaP.setVisible(false);
        ventanaP.dispose();
    }
    public void continuar(){
        synchronized(ventanaP){
            ventanaP.notify();
        }
    }
}
