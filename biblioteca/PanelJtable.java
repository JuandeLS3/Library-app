package biblioteca;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


import teoria.centros.ModeloDatos;

public class PanelJtable extends JScrollPane implements MouseListener{

	private JTable tabla;
	private DefaultTableModel modeloDatos=new ModeloDatos();
	private Connection conn;
	private String txt;
	private JFrameJTable ventanaPadre;
	private String nombreColumna;

	/**
	 * Create the panel.
	 */
	public PanelJtable() {
		tabla = new JTable();
		this.setViewportView(tabla);
	}
	public PanelJtable(Connection conn,String sql) {
		this.conn=conn;
		modeloDatos=modeloDatos(sql);
		tabla = new JTable(modeloDatos);
		this.setViewportView(tabla);
		tabla.addMouseListener(this);
	}

	public PanelJtable(Connection conn,String sql,JFrameJTable ventanaPadre) {
		this.conn=conn;
		this.ventanaPadre=ventanaPadre;
		modeloDatos=modeloDatos(sql);
		tabla = new JTable(modeloDatos);
		this.setViewportView(tabla);
		tabla.addMouseListener(this);
	}
	private ModeloDatos modeloDatos(String sql){
		ModeloDatos modelo=new ModeloDatos();
		try{
			//Creamos las filas
			Statement stmt=conn.createStatement();
			ResultSet rset=stmt.executeQuery(sql);
			
			//Nos traemos los metadatos
			ResultSetMetaData metaDatos = rset.getMetaData();
			// Se obtiene el número de columnas.
			int numeroColumnas = metaDatos.getColumnCount();

			// Se crea un array de etiquetas para rellenar
			Object[] etiquetas = new Object[numeroColumnas];

			// Se obtiene cada una de las etiquetas para cada columna
			for (int i = 0; i < numeroColumnas; i++)
			{
			   // Nuevamente, para ResultSetMetaData la primera columna es la 1. 
			   etiquetas[i] = metaDatos.getColumnLabel(i + 1); 
			}
			
			// Creamos las columnas.
			modelo.setColumnIdentifiers(etiquetas);
			
			// Bucle para cada resultado en la consulta
			while (rset.next())
			{
			   // Se crea un array que será una de las filas de la tabla. 
			   Object [] fila = new Object[numeroColumnas]; // Hay tres columnas en la tabla
	
			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<numeroColumnas;i++){
			      fila[i] = rset.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			   }
			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila); 
			}
	
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return modelo;
	}
	
	public void refreshTabla(String sql){
		tabla.setModel(modeloDatos(sql));
		//modeloDatos.fireTableDataChanged(); No funciona...
	}
	
	private void selectValorTabla(){
		int row=tabla.getSelectedRow();
		txt="";
        for (int i = 1; i < tabla.getColumnCount(); i++) {	// Bucle que recorre todas las columnas y va juntando las String
            txt+=tabla.getValueAt(row, i)+ " ";
        }
        nombreColumna=tabla.getColumnName(0);		// Si devuelve CODUSUARIO, es de la tabla usuario.
        this.ventanaPadre.setBusqueda(""+tabla.getValueAt(row, 0), txt, nombreColumna);		// Búsqueda que devuelve pk y string
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount()==2) {
			selectValorTabla();	
        }      
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

}
