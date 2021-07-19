package monitor;

import monitor.Sintomas;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registro implements Serializable {

    private Date fecha;
    private Sintomas sintomas;

    public Registro(Date f, Sintomas s) {
        fecha = f;
        sintomas = s;
    }

    public Sintomas getSintomas() {
        return sintomas;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean esHoy(){
        boolean res=false;
        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        Date date2 = new Date();
        if (sdformat.format(fecha).equals(sdformat.format(date2))) {
            res=true;
        }
        return res;
    }

    @Override
    public String toString(){
        return fecha.toString();
    }

}
