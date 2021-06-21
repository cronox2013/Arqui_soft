package cargarsintomas.archivos;


import monitor.Sintoma;
import monitor.Sintomas;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Redactor {
    private String path = getPath();
    private String pathCategorias = "sintomas";
    private String pathCategorias2 = "out/production/MonitorCovid/sintomas";

    public String getPath(){
        String path1 = "cargarsintomas/sintomas.txt";
        String path2 = "src/cargarsintomas/sintomas.txt";
        String res="";
        if(new File (path2).exists()){
            res += path2;
        }else{
            res+= path1;
        }
        System.out.println(res);
        return res;
    }

    public void aniadirSintoma(Sintoma s)
    {

        File archivo = new File(path);

        if(archivo.length()==0){
            escribirSintomaCabecera(s);
        }else{
            escribirSintoma(s);
        }


        /*File archivo = new File(path);
        if(archivo.exists()){
        if(archivo.length()==0){
            escribirSintoma(s);
        }else{
            escribirSintomaCabecera(s);
        }}
        else{
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/
    }

    public void escribirSintoma (Sintoma s)
    {

        try
        {
            MiObjectOutputStream oos = new MiObjectOutputStream(
                    new FileOutputStream(path,true));
            oos.writeObject(s);
            oos.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void escribirSintomaCabecera(Sintoma s){

        try (ObjectOutputStream objectOutput = new ObjectOutputStream(
                new FileOutputStream(path, true))) {
            objectOutput.writeObject(s);
            objectOutput.reset();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Sintomas leerSintoma(){
        FileInputStream ficheroIn = null;
        Sintoma s=null;
        Sintomas sintomas= new Sintomas();
        try {
            System.out.println(1);
            ficheroIn = new FileInputStream(path);
            System.out.println(2);
            
            ObjectInputStream tuberiaEntrada = new ObjectInputStream(ficheroIn);
            System.out.println(3);
            s = (Sintoma)tuberiaEntrada.readObject();
            while(true){
                sintomas.add(s);
                s = (Sintoma)tuberiaEntrada.readObject();
            }
        } catch (IOException  e) {
            System.out.println("Lectura terminada");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return sintomas;
    }

    public String[] getCategorias(){
        String[] cats ;
        File folder;
        if(new File (pathCategorias2).exists()){
             folder = new File(pathCategorias2);
        }else{
            folder = new File(pathCategorias);
            }
        cats = folder.list();
        for(int i=0;i< cats.length;i++){
            cats[i]=cats[i].substring(0,cats[i].length()-6);
        }
        for(int e=0;e<cats.length;e++){
            if(!doyClase(cats[e],"sintomas."+cats[e])){
                cats[e]="f";
            }
        }
        return cats;

}

public Sintoma crearObjeto(String nonSintoma,String nombre){
        Sintoma s = null;
        Class<?> c;
        try {
            c = Class.forName(nombre);
            Constructor<?> cons = c.getConstructor(String.class);
            Object object = cons.newInstance(new Object[] { nonSintoma });
            s = (Sintoma) object;
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        } catch (NoSuchMethodException classNotFoundException) {
            System.out.println("Clase no instanciada Detectada");
            //classNotFoundException.printStackTrace();
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        } catch (InstantiationException instantiationException) {
            instantiationException.printStackTrace();
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }
        return s;
    }
    public boolean doyClase(String n,String nombre) {
        return crearObjeto(n,nombre) instanceof Sintoma;
    }
}
