package cargarsintomas.ventanaSinto;

import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PanelTablaSintomas extends JPanel {
    private final JScrollPane tableScollPanel;
    private final DefaultTableModel dataTableModel;

    public PanelTablaSintomas(Sintomas sintomas){
        dataTableModel = new DefaultTableModel();
        JTable table = new JTable(dataTableModel);
        tableScollPanel = new JScrollPane(table);
        tableScollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dataTableModel.addColumn("Nombre sintoma");
        dataTableModel.addColumn("Categoria");
        for(Sintoma sintoma: sintomas){
            String nombre = sintoma.toString();
            String categoria = sintoma.getClass().getName().split("\\.")[1];
            dataTableModel.addRow(new String[]{nombre, categoria });
        }
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(dataTableModel);
        table.setRowSorter(sorTable);
        add(tableScollPanel);
    }

    public void addRow(String[] row){
        dataTableModel.addRow(row);
    }

}
