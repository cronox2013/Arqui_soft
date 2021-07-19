package cargarregistros.ventanaReg;

import monitor.Registro;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.*;

public class ListaRUI extends JPanel {
    private Sintomas sintomas;
    private JTable tablaObservables;
    private DefaultTableModel tableModelObservables;
    private JLabel label1;

    private JScrollPane scrollTablaObservables;

    ListaRUI(Sintomas sintomas){
        this.sintomas=sintomas;

        tableModelObservables=new DefaultTableModel();
        setLayout(new GridBagLayout());
        label1 = new JLabel("SINTOMAS OBSERVABLES");
        cualidadComponentes();
        aniadirComponentes();
    }
    private void cualidadComponentes(){
        tablaObservables = new JTable(tableModelObservables) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Sintoma.class;
                    case 1:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        tablaObservables.setModel(tableModelObservables);
        llenarTablaObservables();
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(tableModelObservables);
        tablaObservables.setRowSorter(sorTable);
        scrollTablaObservables = new JScrollPane(tablaObservables);
        scrollTablaObservables.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void aniadirComponentes(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weighty = 0.0;
        constraints.weightx = 0.0;
        add(label1,constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(scrollTablaObservables,constraints);
    }

    private void llenarTablaObservables() {
        tableModelObservables.addColumn("Sintoma");
        tableModelObservables.addColumn("Tipo");
        tableModelObservables.addColumn("Observado");
        for (Sintoma sintoma : sintomas) {
            tableModelObservables.addRow(new Object[]{sintoma, sintoma.getClass().getSimpleName(),false});
        }
    }

    public Registro cargarRegistro(){
        int tam = tableModelObservables.getRowCount();
        Sintomas sintomas = new Sintomas();
        for(int i=0;i<tam;i++){
            if((boolean)tableModelObservables.getValueAt(i,2)){
                sintomas.add((Sintoma) tableModelObservables.getValueAt(i,0));
            }
        }
        Registro ultimo = new Registro(new Date(),sintomas);
        return ultimo;
    }


}
