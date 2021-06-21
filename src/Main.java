import cargarsintomas.archivos.Redactor;
import monitor.Monitor;
import monitor.Sintoma;
import monitor.Sintomas;
import sintomas.PrimeraFase;

public class Main {

    public static void main(String[] args){
        System.out.println("diagnostico");
        Monitor monitor = new Monitor();
        monitor.monitorear();
        System.out.println("resultado: " + monitor.getResultado());

    }

}
