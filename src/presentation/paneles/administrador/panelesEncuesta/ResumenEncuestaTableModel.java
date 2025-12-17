package presentation.paneles.administrador.panelesEncuesta;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import data.Model.ResumenRespuestaDTO;

public class ResumenEncuestaTableModel extends AbstractTableModel {

    private final List<ResumenRespuestaDTO> resumen;

    private final String[] columnas = {
            "ID Encuesta",
            "Título",
            "Pregunta",
            "Valor",
            "Respuesta",
            "Cantidad"
    };

    public ResumenEncuestaTableModel(List<ResumenRespuestaDTO> resumen) {
        this.resumen = resumen;
    }

    @Override
    public int getRowCount() {
        return resumen.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ResumenRespuestaDTO dto = resumen.get(rowIndex);
        switch (columnIndex) {
            case 0: return dto.getEncuestaId();
            case 1: return dto.getTitulo();
            case 2: return dto.getPregunta();
            case 3: return dto.getValorAsignado();
            case 4: return dto.getRespuesta();
            case 5: return dto.getCantidad();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
