package cargarregistros;

import cargarregistros.archivos.RedactorRegistros;
import cargarregistros.ventanaReg.CargarRUI;
import monitor.Registro;
import monitor.Registros;
import monitor.Sintomas;

public class CargarRegistros {

    private final Sintomas sintomas;
    private CargarRUI inter;
    public CargarRegistros(Sintomas sintomas) {
        this.sintomas = sintomas;
    }
    public Registro getRegistro() {
        inter =new CargarRUI(sintomas);
        Registro r;
        r = inter.getUltimo();
        return r;
    }
    public Registros getRegistros(){
        inter =new CargarRUI(sintomas);
        RedactorRegistros redactorRegistros = new RedactorRegistros();
        return redactorRegistros.leerRegistro();
    }
    public void mostrarNotificacion(String mensaje,int diganostico){
         inter.mostrarNotificacion(mensaje,diganostico);
    }

}
