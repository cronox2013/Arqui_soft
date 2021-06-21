package cargarregistros;


import cargarregistros.archivos.Redactor;
import monitor.Registro;
import monitor.Registros;
import monitor.Sintomas;

import java.util.Scanner;


public class CargarRegistros {

    private Registros registros;
    private Redactor red;
    private Sintomas sintomas;
    private Registro ultimo;

    public CargarRegistros(Sintomas sintomas) {
        this.sintomas= sintomas;
        red = new Redactor();
        registros = red.leerRegistro();
    }

    public Registros getRegistros() {
        return registros;
    }

    public Registro getRegistro(){
        CargarRIU ui= new CargarRIU(sintomas);
        Scanner si = new Scanner(System.in);
        //si.nextLine();
        ultimo = ui.getUltimo();
        return ultimo;
    }
}
