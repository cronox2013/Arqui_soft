package cargarregistros.ventanaReg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsListener implements ActionListener {
    PanelRUI panel;
    JButton boton;
    public ButtonsListener(PanelRUI panel,JButton button){
        this.boton = button;
        this.panel = panel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(boton.getText().equals("Cargar")){
            panel.cargarRegistro();
            panel.continuar();
        }
        else{
            if(boton.getText().equals("Terminar")){
                panel.terminar();
            }
        }
    }
}
