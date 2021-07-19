package cargarsintomas.archivos;

import monitor.Sintoma;
import monitor.Sintomas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RedactorSintomas {
    private String path;
    private String pathCategorias = "sintomas";
    private String pathCategorias2 = "out/production/MonitorCovid/sintomas";
    private boolean esUnJar;
    public RedactorSintomas(){
        esUnJar=false;
        path = getPath();

    }
    public String getPath(){
        String path1 = "cargarsintomas/sintomas.txt";
        String path2 = "src/cargarsintomas/AndyGarciaSintomas.dat";
        String path3 = "AndyGarciaSintomas.dat";
        String res="";
        if(new File (path2).exists()){
            res =path2;

        }else{
            try {
                File myObj = new File(path1);
                myObj.createNewFile();
                res =path1;
            } catch (IOException e) {
                res =path3;
                File myObj = new File(path3);
                esUnJar=true;
                try {
                    myObj.createNewFile();
                } catch (IOException ioException) {
                }
            }

        }
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Sintomas leerSintoma(){
        FileInputStream ficheroIn = null;
        Sintoma s=null;
        Sintomas sintomas= new Sintomas();
        try {
            ficheroIn = new FileInputStream(path);
            ObjectInputStream tuberiaEntrada = new ObjectInputStream(ficheroIn);

            s = (Sintoma)tuberiaEntrada.readObject();
            while(true){
                sintomas.add(s);
                s = (Sintoma)tuberiaEntrada.readObject();
            }
        } catch (IOException  e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return sintomas;
    }

    public String[] getCategorias(){
        String [] res;
        List<String> listaClasesPaquete = new ArrayList<>();
        if (esUnJar) {
            try {
                ZipInputStream zip = new ZipInputStream(new FileInputStream("home.jar"));
                for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace('/', '.'); // including ".class"

                        if (className.split("\\.")[0].equals("sintomas")) {
                            listaClasesPaquete.add(className.split("\\.")[1]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            res = listaClasesPaquete.toArray(new String [0]);
        }else{
             res = getCategoria();
        }
        return res;
    }

    private String[] getCategoria(){
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
    public boolean doyClase(String n,String categoria) {
        Sintoma sinto =(new ObjetoSintoma()).crearObjeto(categoria, n);
        return sinto instanceof Sintoma;
    }
}
