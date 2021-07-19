package monitor;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Registros implements Iterable<Registro>, Serializable {

    private Stack<Registro> registros;

    public Registros() {
        registros = new Stack<>();
    }

    public void push(Registro r){
        registros.push(r);
    }

    public Registro peek() {
        return registros.peek();
    }

    public boolean isEmpty(){
        return registros.isEmpty();
    }

    public Registros getRegistrosHoy(){
        Registros res = new Registros();
        res.registros = this.registros.stream()
                    .filter(registro -> registro.esHoy()).collect( Collectors.toCollection(Stack::new));
        return res;
    }
    public Registro unionRegistros(){
        Sintomas sintomasUnidos = new Sintomas();
        for(Registro registro : registros){
            for(Sintoma sintoma : registro.getSintomas()){
                sintomasUnidos.add(sintoma);
            }
        }
        return new Registro(new Date(),sintomasUnidos);
    }
    @Override
    public Iterator<Registro> iterator() {
        return registros.iterator();
    }
}
