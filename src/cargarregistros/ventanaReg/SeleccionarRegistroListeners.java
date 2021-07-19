package cargarregistros.ventanaReg;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SeleccionarRegistroListeners extends MouseAdapter{
    private TableRUI tableRUI;
    public SeleccionarRegistroListeners(TableRUI tableRUI){
        this.tableRUI=tableRUI;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        tableRUI.seleccionarRegistro();
    }

}


