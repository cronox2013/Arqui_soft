package cargarregistros.archivos;

import monitor.Registro;
import monitor.Registros;
import java.io.*;

public class RedactorRegistros {

    private String path ;
    public RedactorRegistros(){
        path = "AndyGarciaRegistros.dat";
    }


    public void escribirArchivo(Registro r) {
        Registros registros = leerRegistro();
        if (registros == null) {
            registros = new Registros();
        }
        registros.push(r);
        try (ObjectOutputStream objectOutput = new ObjectOutputStream(
                new FileOutputStream(path))) {
            objectOutput.writeObject(registros);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Registros leerRegistro() {
        FileInputStream ficheroIn;
        Registros registros = new Registros();
        try {
            ficheroIn = new FileInputStream(path);
            ObjectInputStream tuberiaEntrada = new ObjectInputStream(ficheroIn);
            registros = (Registros) tuberiaEntrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(registros==null){
            registros = new Registros();
        }
        return registros;
    }
}


