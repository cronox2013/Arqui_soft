package cargarsintomas;

import cargarsintomas.archivos.Redactor;
import cargarsintomas.archivos.Validador;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CargarSIU extends JFrame {
    private JButton cargarButton;
    private JTextField nombreSintoma;
    private JTextArea impresion;
    private JComboBox importancia;
    private JScrollPane scroll ;
    Redactor red = new Redactor();
    Validador val = new Validador();
    public CargarSIU(){
        super("Cargar Sintomas");
        importancia = new JComboBox();
        importancia.setBounds(5,300,150,30);
        impresion = new JTextArea();
        impresion.setForeground(Color.BLUE);
        impresion.setBounds(220,5,340,600);
        impresion.setEditable(false);
        nombreSintoma = new JTextField();
        nombreSintoma.setBounds(5,50,200,30);
        cargarButton=new JButton("Cargar");
        cargarButton.setBounds(455,620,100,30);

        scroll = new JScrollPane(impresion);
        scroll.setBounds(220,5,340,600);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scroll);
        //add(impresion);

        actualizarSintomas();
        dameCategos();
        add(cargarButton);
        //add(impresion);
        add(nombreSintoma);
        add(importancia);

        final CargarSIU frame = this;
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){

                    // llevar al paquete cargarSintomas
                    //(new DatosSintomas()).guardarDatosSintomas(sintomas);
                    synchronized(frame){
                        frame.notify();
                    }
                    frame.setVisible(false);
                    frame.dispose();

            }
        });

        importancia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        cargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargar();
            }
        });
        nombreSintoma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        setSize(600,700);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        synchronized(frame){
            try{
                frame.wait();
            }
            catch(InterruptedException ex){
            }
        }
    }
    public void dameCategos(){
        String [] categos = red.getCategorias();
        for(int i=0; i<categos.length;i++){
            if(categos[i]!="f"){
                importancia.addItem(categos[i]);}
        }
    }
    public void actualizarSintomas(){
        Sintomas sintomas = red.leerSintoma();
        String res="--- Sintomas ---\n";
        for(Sintoma sin : sintomas){
            String arr= "Nombre: "+sin.toString() +"\n\tTipo: "+ sin.getClass();
            res=res+"\n"+arr;
        }
        impresion.setText(res);
    }
    public void cargar() {
        String clasificacion = importancia.getSelectedItem().toString();
        String nombre = nombreSintoma.getText();
        // int aux =selecciono(clasificacion);
        Sintoma sintoma =null;
        Class<?> clazz = null;
        nombre = val.tratarNombre(nombre);
        try {
            clazz = Class.forName("sintomas."+clasificacion);
            Constructor<?> ctor = clazz.getConstructor(String.class);
            Object object = ctor.newInstance(new Object[] { nombre });
            sintoma = (Sintoma) object;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        if(val.Validar(sintoma)){
            red.aniadirSintoma(sintoma);
            //System.out.println(sintoma.toString()+" agregado");

            impresion.setBackground(new Color(168, 229, 133));
            actualizarSintomas();
            JOptionPane.showMessageDialog(null, "Sintoma Añadido", "InfoBox: " + "Exito", JOptionPane.INFORMATION_MESSAGE);
            //System.out.println("Agregado Correctamente");
        }else{
            impresion.setBackground(new Color(213, 111, 111, 247));
            actualizarSintomas();
            JOptionPane.showMessageDialog(null, "Sintoma No Valido", "InfoBox: " + "Error", JOptionPane.ERROR_MESSAGE);
            //System.out.println("No valido para agregar");
        }

    }


  /*public static void main(String[]args){

        CargarSIU c= new CargarSIU();
    }*/

}
