package cargarregistros.ventanaReg;

import cargarsintomas.ventanaSinto.CargarSIU;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CerrarVentanaListener extends WindowAdapter {
    CargarRUI interfazReg;
    CerrarVentanaListener(CargarRUI interfazReg){
        this.interfazReg= interfazReg;
    }
    @Override
    public void windowClosing(WindowEvent we){
        interfazReg.cerrarVentana();
    }
}
