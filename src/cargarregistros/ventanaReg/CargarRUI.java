package cargarregistros.ventanaReg;
import cargarregistros.archivos.RedactorRegistros;
import monitor.Registro;
import monitor.Sintomas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CargarRUI extends JFrame {
    private RedactorRegistros red ;
    private PanelRUI panelRUI;
    final CargarRUI frameRegistro = this;
    private Registro ultimo = new Registro(new Date(),new Sintomas());
    private Sintomas sintomas;

    public CargarRUI(Sintomas sintomas) {
        super("Cargar Registro");
        this.sintomas = sintomas;
        red = new RedactorRegistros();
        panelRUI = new PanelRUI(sintomas, this);
        setSize(600, 480);
        setLayout(new GridLayout(1, 1));
        setVisible(true);
        add(panelRUI);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CerrarVentanaListener(this));
        sincronizar();
    }

    private void sincronizar(){
        synchronized(frameRegistro){
            try{
                frameRegistro.wait();
            }
            catch(InterruptedException ignored){
            }
        }
    }

    public void cerrarVentana(){
        synchronized(this){
            this.notify();
        }
        setVisible(false);
        dispose();
    }

    public Registro getUltimo() {
        return ultimo;}
    public void mostrarNotificacion(String mensaje,int diganostico){
        panelRUI.mostrarNotificacion(mensaje,diganostico);
    }
}
