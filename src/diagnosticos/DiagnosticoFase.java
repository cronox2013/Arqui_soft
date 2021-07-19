package diagnosticos;

import monitor.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DiagnosticoFase extends FuncionDiagnostico {
    private Fase fase;
    private int diaActual;
    private int numSintomas;
    private final Map<Sintoma, Integer> pesos;
    private int pesoPrimeraFase;
    private int pesoSegundaFase;
    private String mensaje;

    public DiagnosticoFase(Sintomas ls,Fase fase) {
        super(ls);
        pesos = new HashMap<>();
        for (Sintoma s : ls) {
            pesos.put(s, s.peso());
        }
        this.fase=fase;
        diaActual=this.fase.getDia()+1;
        mensaje="";
        cargarFaseSintomasMonitor(ls);
        inicializarPesos();
    }

    @Override
    public int diagnostico(Registros registros) {
        Registro registro ;
        int peso=0;
        if(!registros.isEmpty()){
            registro = registros.unionRegistros();
            peso = evaluarRegistro(registro);
        }
        return peso;
    }

    private int evaluarRegistro(Registro registro){
        int pesoRegistro;
        if(fase.esPrimeraFase()){
            mensaje+=("Se encuentra en la Primera Fase, Dia " + diaActual) + "\n";
            pesoRegistro= evaluarPorFase(registro);
            if(pesoRegistro>numSintomas*pesoPrimeraFase/2){
                mensaje+=("Usted presenta mas de la mitad de los sintomas de primera Fase, Se recomienda ir al Medico, y  hacerse control diario\n");
                if(diaActual==3){
                    mensaje+=("Usted Pasa a Segunda fase, debe visitar al medico urgentemente y hacerte una prueba\n");
                    fase.setSegundaFase();
                    fase.setDiaInicio();
                }
                fase.addDia();
            }else{
                mensaje+=("Usted presenta menos de la mitad de los sintomas de primera fase, continue registrandose Diariamente\n");
                fase.setDiaInicio();
            }
        }else{
            mensaje+=("Se encuentra en la Segunda Fase, Dia " + diaActual) + "\n";
            pesoRegistro= evaluarPorFase(registro);
            if(pesoRegistro>numSintomas*pesoSegundaFase/2){
                mensaje+=("Usted presenta mas de la mitad de los sintomas de Segunda Fase, Debes visitar al medico urgentemente y hacerte una prueba\n");
                if(diaActual>=2){
                    if(fueMedico()){
                        fase.setDiaInicio();
                        fase.setPrimeraFase();
                    }else{
                        fase.addDia();
                    }
                }
            }else{
                mensaje+=("Usted presenta menos de la mitad de los sintomas de Segunda Fase, Debes visitar al medico urgentemente y hacerte una prueba\n");
                fase.setDiaInicio();
            }
        }

        (new DatosFase()).guardarDatosFase(fase);
        return pesoRegistro;
    }

    private int evaluarPorFase(Registro registro){
        int res=0;
        Sintomas sintomas = registro.getSintomas();
        for (Sintoma s : sintomas) {
            if(s.getClass().getSimpleName().equals(fase.getNombre())){
                res += pesos.get(s);
            }
        }
        return res;
    }

    private void cargarFaseSintomasMonitor(Sintomas ls) {
        numSintomas=0;
        for (Sintoma s: ls) {
            String nombre = s.getClass().getSimpleName();
            if(nombre.equals(fase.getNombre())){
                numSintomas++;
            }
        }
    }

    public String getMessage(){
        return mensaje;
    }

    public boolean fueMedico(){
        boolean res = false;
        System.out.println("Se realizo usted una pruebade covid? \n1. Si me la hice \n2. No me la hice aun");
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        if(a==1){
            res = true;
            System.out.println(" Porfavor Indique su resultado \n1. Resultado de prueba Positivo \n2. Resultado de prueba Negativo");
            a = sc.nextInt();
            if(a==1){
                mensaje+=("Ok, Siga las indicaciones de su medico,Esperamos su pronta recumeracion, se reiniciara el registro");
            }else{
                mensaje+=("Usted presenta sintomas, puede ser un falso Negativo, se regresara a primera fase, porfavor continue registrando sus sintomas");
            }
        }
        return res;
    }
    private void inicializarPesos(){
        pesoPrimeraFase=10;
        pesoSegundaFase=100;
    }

}
