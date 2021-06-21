package cargarsintomas.archivos;

import monitor.Sintoma;
import monitor.Sintomas;

import java.util.Set;

public class Validador {

    public boolean Validar(Sintoma sintoma){
        boolean res = true;
        Redactor red = new Redactor();
        Sintomas sintomas = red.leerSintoma();
        for(Sintoma sin : sintomas){
            if(sin.toString().equals(sintoma.toString())){
                res = false;
            }
        }
        return res;
    }

    public String tratarNombre(String nombre){
        String res;
        res = nombre.toUpperCase();
        return res;
    }
}
