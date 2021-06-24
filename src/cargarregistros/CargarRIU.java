package cargarregistros;

import cargarregistros.archivos.Redactor;
import cargarsintomas.CargarSIU;
import monitor.Registro;
import monitor.Registros;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class CargarRIU extends JFrame {
    private JButton cargarButton;
    private JTextArea impresion;
    private JList lista1;
    private JList lista2;
    private Registro ultimo;
    DefaultListModel l1;
    DefaultListModel l2;
    Redactor red ;
    Sintomas sintomas;

    public CargarRIU(Sintomas sintomas){
        super("Cargar Sintomas");
        this.sintomas = sintomas;
        red = new Redactor();
        lista1 = new JList();
        lista1.setBounds(5,5,350,300);
        lista2 = new JList();
        lista2.setBounds(360,5,350,300);
        impresion = new JTextArea();
        impresion.setForeground(Color.BLUE);
        impresion.setBounds(5,310,705,200);
        cargarButton=new JButton("Cargar");
        cargarButton.setBounds(605,530,100,35);
        actualizarRegistros();
        l1=new DefaultListModel<>();
        llenarList1();
        l2=new DefaultListModel<>();
        lista2.setModel(l2);
        add(cargarButton);
        add(impresion);
        add(lista1);
        add(lista2);


        final CargarRIU frameRegistro = this;

        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent we){
                try {
                    synchronized(frameRegistro){
                        frameRegistro.notify();
                    }
                    frameRegistro.setVisible(false);
                    frameRegistro.dispose();
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Error al guardar");
                }
            }
        });

        cargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarRegistro();
                actualizarRegistros();
            }
        });
        lista1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                seleccionarSintomas();
            }
        });

        lista2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                desseleccionarSintomas();
            }
        });

        setSize(730,610);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        synchronized(frameRegistro){
            try{
                frameRegistro.wait();
            }
            catch(InterruptedException ex){
            }
        }
    }

    public void actualizarRegistros(){
        Registros registros = red.leerRegistro();
        String res="--- Registros ---\n";
        for(Registro reg : registros){
            String arr= "Nombre: "+reg.getFecha() +"\n\tSintomas: ";
            for(Sintoma sin : reg.getSintomas()){
                arr+=sin+" || ";
            }
            res+=arr+"\n";
        }
        impresion.setText(res);
    }


    public void cargarRegistro(){
        int tam = l2.size();
        Sintomas sintomas = new Sintomas();
        for(int i=0;i<tam;i++){
            sintomas.add((Sintoma) l2.getElementAt(i));
        }
        ultimo = new Registro(new Date(),sintomas);
        red.escribirArchivo(ultimo);
    }

    public void llenarList1(){
        lista1.setModel(l1);
        for(Sintoma s : sintomas){
            l1.addElement(s);
        }
    }

    public void seleccionarSintomas(){
        Sintoma sin = null;
        if(lista1.getSelectedIndex()!=-1){
            sin= (Sintoma)lista1.getSelectedValue();
            l2.addElement(sin);
            l1.removeElement(sin);
        }
    }
    public void desseleccionarSintomas(){
        Sintoma sin = null;
        if(lista2.getSelectedIndex()!=-1){
            sin= (Sintoma)lista2.getSelectedValue();
            l1.addElement(sin);
            l2.removeElement(sin);
        }
    }
    public Registro getUltimo() {return ultimo;}

    /*public static void main(String[]args){
        cargarsintomas.archivos.Redactor red = new cargarsintomas.archivos.Redactor();
        Sintomas s = red.leerSintoma();
        CargarRIU c= new CargarRIU(s);
    }*/

}
