package cargarregistros.ventanaReg;

import cargarregistros.archivos.RedactorRegistros;
import monitor.Registro;
import monitor.Registros;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TableRUI extends JPanel {
    private JTable registros;
    private JScrollPane sp;
    private DefaultTableModel modelo;
    private Sintomas sintomas;
    private JList <Registro>listaRegistro;
    private DefaultListModel <Registro>lRegistro;
    private JScrollPane scrollLista;

    public TableRUI(Sintomas sintomas){
        this.sintomas=sintomas;
        inicializarComponentes();
        cualidadComponentes();
        setLayout(new GridLayout(2,1));
        listaRegistro.addMouseListener(new SeleccionarRegistroListeners(this));
        listaRegistro.setCellRenderer(new ListCellRenderer<Registro>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Registro> list, Registro value, int index, boolean isSelected, boolean cellHasFocus) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fecha = dateFormat.format(value.getFecha());
                JLabel row = new JLabel(fecha);
                if (isSelected) {
                    row.setOpaque(true);
                    row.setBackground(Color.LIGHT_GRAY);
                }
                return row;
            }
        });
    }
    private void inicializarComponentes(){
        modelo=new DefaultTableModel();
        registros=new JTable(modelo);
        initTable();
        sp = new JScrollPane(registros);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listaRegistro = new JList<>();
        lRegistro=new DefaultListModel<>();
        listaRegistro.setModel(lRegistro);
        llenarListaRegistro();
        scrollLista = new JScrollPane(listaRegistro);
        scrollLista.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(modelo);
        registros.setRowSorter(sorTable);

    }
    private void cualidadComponentes(){
        add(sp);
        add(scrollLista);
    }
    private void initTable(){
        modelo.addColumn("Fecha");
        modelo.addColumn("Sintomas");
        Registros registros;
        registros = (new RedactorRegistros()).leerRegistro();
        if(!registros.isEmpty()){
            Registro reg = registros.peek();
            actualizarTable(reg);
        }

    }
    public void actualizarTable(Registro registro){
        modelo.setRowCount(0);
        String fecha =registro.getFecha().toString();
        Sintomas sintomas = registro.getSintomas();
        for(Sintoma sintoma : sintomas){
            if(modelo.getRowCount()==0){
                modelo.addRow(new String[]{fecha,sintoma.toString()});
            }else {
                modelo.addRow(new String[]{"", sintoma.toString()});
            }
        }
    }

    public void llenarListaRegistro(){
        lRegistro.setSize(0);
        List<Registro> lista = new ArrayList<Registro>();
        Registros registros=(new RedactorRegistros()).leerRegistro();
        for(Registro registro : registros){
            lista.add(registro);
        }
        Collections.reverse(lista);
        for(Registro registro : lista){
            lRegistro.addElement(registro);
        }
    }
    public void seleccionarRegistro(){
        Registro reg;
        if(listaRegistro.getSelectedIndex()!=-1){
            reg=listaRegistro.getSelectedValue();
            actualizarTable(reg);
        }
    }

}
