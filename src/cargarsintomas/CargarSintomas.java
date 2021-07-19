package cargarsintomas;

import cargarsintomas.ventanaSinto.CargarSIU;
import cargarsintomas.archivos.RedactorSintomas;
import monitor.Sintomas;

public class CargarSintomas {

    private Sintomas sintomas;
    private RedactorSintomas red;
    private CargarSIU ui;

    public CargarSintomas() {
        red = new RedactorSintomas();
        sintomas = red.leerSintoma();
    }

    public Sintomas getSintomas() {
        sintomas = red.leerSintoma();
        ui = new CargarSIU(sintomas);
        return sintomas;
    }


}
