package diagnosticos;

import monitor.FuncionDiagnostico;
import monitor.Registro;
import monitor.Registros;
import monitor.Sintoma;
import monitor.Sintomas;

import java.util.HashMap;
import java.util.Map;

public class DiagnosticoSimple extends FuncionDiagnostico {

    private Map<Sintoma,Integer> pesos;

    public DiagnosticoSimple(Sintomas ls) {
        super(ls);
        pesos = new HashMap<>();
        for (Sintoma s: ls) {
            pesos.put(s,s.peso());
        }
    }

    @Override
    public int diagnostico(Registros registros) {
        int pesoSintomas = 0;

        try {
            Registro registro = registros.peek();
            Sintomas sintomas = registro.getSintomas();
            for (Sintoma s: sintomas) {
                pesoSintomas += pesos.get(s);
            }
        }catch (NullPointerException ex){

        }
        return pesoSintomas;
    }

}
