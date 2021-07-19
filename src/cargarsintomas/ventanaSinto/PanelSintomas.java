package cargarsintomas.ventanaSinto;

import cargarsintomas.archivos.ObjetoSintoma;
import cargarsintomas.archivos.RedactorSintomas;
import cargarsintomas.archivos.Validador;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelSintomas extends JPanel implements ActionListener{

    private final PanelTablaSintomas panelTablaSintomas;
    private final JPanel panelAgregar;
    private final JButton cargarButton;
    private final JButton cerrarButton;
    private final JLabel labelNombre;
    private final JLabel labelCategoria;
    private final JLabel labelEstado;
    private JTextField nombreSintoma;
    private JComboBox<String> importancia;
    private Sintomas sintomas;
    private CargarSIU frameSintomas;

    public PanelSintomas(Sintomas sintomas, CargarSIU frameSintomas){
        this.sintomas = sintomas;
        this.frameSintomas = frameSintomas;
        cargarButton = new JButton("Registrar");
        cerrarButton = new JButton("Terminar");
        labelNombre = new JLabel("Nombre de Sintoma");
        labelCategoria = new JLabel("Categoria sintoma");
        labelEstado = new JLabel(".");
        importancia = new JComboBox<>();
        nombreSintoma = new JTextField();
        panelTablaSintomas = new PanelTablaSintomas(sintomas);
        panelAgregar = new JPanel();
        setPropiedades();
        setTamanios();
        addItems();
    }

    private void setPropiedades() {
        cargarButton.addActionListener(this);
        cerrarButton.addActionListener(this);
        setCategoriasCombo();
        setLayout(new GridLayout(1,2));
    }
    private void setTamanios() {
        labelNombre.setBounds(5,5,200,20);
        nombreSintoma.setBounds(5,35,200,30);
        labelCategoria.setBounds(5,75,200,20);
        importancia.setBounds(5,105,200,30);
        cargarButton.setBounds(5,145,90,30);
        cerrarButton.setBounds(115,145,90,30);
        labelEstado.setBounds(5,250,250,20);
        panelTablaSintomas.setBounds(35, 5, 450,350);
        panelAgregar.setBounds(5,250,300,350);
    }

    private void addItems() {
        panelAgregar.setLayout(null);
        panelAgregar.add(labelNombre);
        panelAgregar.add(nombreSintoma);
        panelAgregar.add(labelCategoria);
        panelAgregar.add(importancia);
        panelAgregar.add(cargarButton);
        panelAgregar.add(cerrarButton);
        panelAgregar.add(labelEstado);
        add(panelAgregar);
        add(panelTablaSintomas);
    }

    private void setCategoriasCombo(){
        String[] categorias =  (new RedactorSintomas()).getCategorias();
        for(String categoria : categorias)
                importancia.addItem(categoria);
    }

    private void estadoExito(Boolean estado){
        if(estado){
            labelEstado.setText("Sintoma Agregado Correctamente");
            labelEstado.setForeground(Color.green);
        }else{
            labelEstado.setText("Error al agregar sintoma");
            labelEstado.setForeground(Color.red);
        }
    }
    private void cerrarVentana(){
        synchronized(frameSintomas){
            frameSintomas.notify();
        }
        frameSintomas.setVisible(false);
        frameSintomas.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botonPulsado = (JButton) e.getSource();
        if (botonPulsado == cargarButton) {
            String categoria = (String) importancia.getSelectedItem();
            String name = nombreSintoma.getText();
            Validador validador = new Validador();
            if(!name.equals("")){
                ObjetoSintoma crearSintoma = new ObjetoSintoma();
                name = validador.tratarNombre(name);
                Sintoma sintoma = crearSintoma.crearObjeto(name, categoria);
                if(validador.Validar(sintoma)){
                    sintomas.add(sintoma);
                    panelTablaSintomas.addRow(new String[]{sintoma.toString(), categoria });
                    nombreSintoma.setText("");
                    (new RedactorSintomas()).aniadirSintoma(sintoma);
                    estadoExito(true);
                }else{
                    estadoExito(false);
                }

            }
        } else if (botonPulsado == cerrarButton){
            cerrarVentana();
        }
    }
}
