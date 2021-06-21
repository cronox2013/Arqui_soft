package cargarregistros.archivos;

import monitor.Registro;
import monitor.Registros;
import monitor.Sintoma;
import monitor.Sintomas;

import java.io.*;
import java.util.Date;

public class Redactor {
    private String path = getPath();

    public String getPath(){
        String path1 = "cargarregistros/registros.txt";
        String path2 = "src/cargarregistros/registros.txt";
        String res="";
        if(new File (path2).exists()){
            res += path2;
        }else{
            res+= path1;
        }

        System.out.println(res);
        return res;
    }

    public boolean escribirArchivo(Registro r) {
        Registros registros = leerRegistro();
        if(registros!=null){
            registros.push(r);
        }else{
            registros= new Registros();
            registros.push(r);
        }
        try (ObjectOutputStream objectOutput = new ObjectOutputStream(
                new FileOutputStream(path))) {
            objectOutput.writeObject(registros);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Registros leerRegistro() {
        FileInputStream ficheroIn = null;
        Registros registros = null;
        try {
            ficheroIn = new FileInputStream(path);
            ObjectInputStream tuberiaEntrada = new ObjectInputStream(ficheroIn);
            registros = (Registros) tuberiaEntrada.readObject();
        } catch (IOException e) {
            System.out.println("Lectura terminada");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(registros==null){
            registros = new Registros();
        }
        return registros;
    }
}
