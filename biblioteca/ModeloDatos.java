package biblioteca;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ModeloDatos extends DefaultTableModel {
	
	public ModeloDatos() {
		super();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	
	
}
