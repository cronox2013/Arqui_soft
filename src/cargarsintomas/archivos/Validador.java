package cargarsintomas.archivos;

import monitor.Sintoma;
import monitor.Sintomas;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern pat = Pattern.compile("^[A-Z\s]{3,40}$");
        Matcher mat = pat.matcher(sintoma.toString());
        boolean cumpleSintaxis=mat.matches();
        return res && cumpleSintaxis;
    }

    public String tratarNombre(String nombre){
        String res;
        res = nombre.toUpperCase();
        return res;
    }
}
