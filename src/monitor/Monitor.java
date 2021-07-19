package monitor;

import cargarregistros.CargarRegistros;

import cargarsintomas.CargarSintomas;

import diagnosticos.DiagnosticoFase;

public class Monitor {

    private Fase fase;
    private Sintomas sintomas;
    private Registros registros;
    private FuncionDiagnostico funcion;
    private int resultadoDiagnostico;
    private CargarRegistros cargarRegistros;

    public Monitor() {
        CargarSintomas cargarSintomas = new CargarSintomas();
        sintomas = cargarSintomas.getSintomas();
        fase = (new DatosFase()).leerDatosFase();
        funcion = new DiagnosticoFase(sintomas,fase);
        registros = new Registros();
        cargarRegistros = new CargarRegistros(sintomas.getSintomasFase(fase));
    }

    public void monitorear() {
        registros = cargarRegistros.getRegistros();
        resultadoDiagnostico = funcion.diagnostico(registros.getRegistrosHoy());
        cargarRegistros.mostrarNotificacion(funcion.getMessage(),resultadoDiagnostico);
        mostrarDiaFase(funcion.getMessage());
    }

    private void mostrarDiaFase(String mensaje){
        System.out.println(mensaje);
    }

    public int getResultado() {
        return resultadoDiagnostico;
    }

}
