package cargarsintomas.ventanaSinto;

import monitor.Sintomas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CargarSIU extends JFrame {

    public CargarSIU(Sintomas sintomas){
        super("Cargar Sintomas");

        setBounds(5, 5, 950, 500);
        setResizable(true);
        PanelSintomas sintomaJPanel = new PanelSintomas(sintomas, this);
        setVisible(true);
        setLayout(new BorderLayout());
        add(sintomaJPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                   cerrarVentana();
            }
        });
        sincronizar();
    }

    private void cerrarVentana(){
                synchronized(this){
                    this.notify();
                }
                setVisible(false);
                dispose();
    }
    private void sincronizar(){
        synchronized(this){
            try{
                this.wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
