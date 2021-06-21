package cargarsintomas;

import cargarsintomas.archivos.Redactor;
import monitor.Sintomas;

import java.util.Scanner;

public class CargarSintomas {

    private Sintomas sintomas;
    private Redactor red;

    public CargarSintomas() {
        red = new Redactor();
        sintomas = red.leerSintoma();
    }

    public Sintomas getSintomas() {
        CargarSIU ui = new CargarSIU();
        Scanner si = new Scanner(System.in);
        //si.nextLine();
        sintomas = red.leerSintoma();
        return sintomas;
    }

}
