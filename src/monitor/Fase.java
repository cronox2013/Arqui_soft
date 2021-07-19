package monitor;

import java.io.Serializable;

public class Fase implements Serializable {

    private String nombre;
    private int dia;
    public Fase(String nombre){
        this.nombre = nombre;
        dia = 0;
    }

    public void setPrimeraFase( ) {
        this.nombre = "PrimeraFase";
    }
    public void setSegundaFase( ) {
        this.nombre = "SegundaFase";
    }
    public void setDiaInicio(){
        this.dia = 0;
    }
    public int addDia(){
        return this.dia++;
    }
    public boolean esPrimeraFase(){
        return this.nombre.equals("PrimeraFase");
    }
    public String getNombre() {
        return nombre;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

}