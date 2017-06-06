package biblioteca;


import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.*;
import java.sql.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;

import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.Color;

public class GestionArticulos extends Gestiones implements WindowListener{
	
	private ResultSet rset;
	private Statement stmt;
	private int totalregistros;
	private JTextField txtCodigo;
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtNumpaginas;
	
	public GestionArticulos(Connection conn,JFrame principal) {
		super();
		this.conn=conn;		// Recibimos la conexión de Biblioteca
		this.principal=principal;
		this.principal.setEnabled(false);
		this.principal.setVisible(false);
		setTitle("Gestión de artículos");
		
		txtCodigo = new JTextField();
		txtCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(59, 68, 105, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtTitulo = new JTextField();
		txtTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitulo.setEditable(false);
		txtTitulo.setBounds(223, 68, 105, 20);
		getContentPane().add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtAutor = new JTextField();
		txtAutor.setHorizontalAlignment(SwingConstants.CENTER);
		txtAutor.setEditable(false);
		txtAutor.setBounds(59, 131, 105, 20);
		getContentPane().add(txtAutor);
		txtAutor.setColumns(10);
		
		txtNumpaginas = new JTextField();
		txtNumpaginas.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumpaginas.setEditable(false);
		txtNumpaginas.setBounds(223, 131, 105, 20);
		getContentPane().add(txtNumpaginas);
		txtNumpaginas.setColumns(10);
		
		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCdigo.setBounds(88, 43, 46, 14);
		getContentPane().add(lblCdigo);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(252, 43, 46, 14);
		getContentPane().add(lblTitulo);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutor.setBounds(88, 106, 46, 14);
		getContentPane().add(lblAutor);
		
		JLabel lblNumPginas = new JLabel("Num. p\u00E1ginas");
		lblNumPginas.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumPginas.setBounds(228, 106, 95, 14);
		getContentPane().add(lblNumPginas);
		
		JLabel lblGestinDeArtculos = new JLabel("Gesti\u00F3n de art\u00EDculos");
		lblGestinDeArtculos.setForeground(Color.BLUE);
		lblGestinDeArtculos.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestinDeArtculos.setBounds(141, 11, 117, 14);
		getContentPane().add(lblGestinDeArtculos);
		

	}
	
	private void cargarDatos() {
		try {
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql="select * from articulo";
			rset=stmt.executeQuery(sql);	// Ejecutamos consulta
			rset.next();				// Pasamos de registro
			totalRegistros();
			mostrarDatos();	
		} catch (SQLException e) {
			if(e.getErrorCode()==17011){
				JOptionPane.showMessageDialog(null, "No hay ningún registro en la base de datos, cree uno!");
				setBotones(false);
				setText(false);
				btnNuevo.setEnabled(true);
			} else {
			JOptionPane.showMessageDialog(null, e.getMessage() + " " + e.getErrorCode());
			}
		}
	}
	
	private void mostrarDatos() throws SQLException {
		txtCodigo.setText(""+rset.getInt("codarticulo"));
		txtTitulo.setText(rset.getString("titulo"));
		txtAutor.setText(rset.getString("autor"));
		txtNumpaginas.setText(""+rset.getInt("NUMPAGINAS"));
		controlarBotonesNavegacion();
		txtEstado.setText(rset.getRow()+"/"+totalregistros);
	}
	
	private void totalRegistros() {
		try {
			String sql="select count(*) from articulo";
			Statement stmt=conn.createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			rset.next();
			totalregistros=rset.getInt(1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}	
	}
	private void controlarBotonesNavegacion() throws SQLException {
		btnPrimero.setEnabled(true);
		btnAnterior.setEnabled(true);
		btnSiguiente.setEnabled(true);
		btnUltimo.setEnabled(true);
		if (rset.isFirst()) {
			btnPrimero.setEnabled(false);
			btnAnterior.setEnabled(false);
		}
		if (rset.isLast()) {
			btnSiguiente.setEnabled(false);
			btnUltimo.setEnabled(false);
		}
	}
	private void setBotones(boolean activo) {
		btnPrimero.setEnabled(activo);
		btnAnterior.setEnabled(activo);
		btnSiguiente.setEnabled(activo);
		btnUltimo.setEnabled(activo);
		btnEliminar.setEnabled(activo);
		btnModificar.setEnabled(activo);
		btnNuevo.setEnabled(activo);
	}
	private void setText(boolean activo) {
		txtCodigo.setEditable(activo);
		txtTitulo.setEditable(activo);
		txtAutor.setEditable(activo);
		txtNumpaginas.setEditable(activo);
	}
	private void limpiarTxt() {
		txtCodigo.setText("");
		txtTitulo.setText("");
		txtAutor.setText("");
		txtNumpaginas.setText("");
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		setResizable(false);
		cargarDatos();
	}

	@Override
	protected void generarPDF(File file) {
		Document documento = new Document();
		FileOutputStream ficheroPdf;
		try {
			ficheroPdf = new FileOutputStream(file);		
			PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(25);
			documento.open();
			documento.add(new Paragraph("Listado de artículos",
					FontFactory.getFont("verdana",  
							20,              
							BaseColor.BLACK)));
			documento.add(new Paragraph(" "));
			try{
				Statement stmt=conn.createStatement();
				String sql="select * from articulo where codarticulo="+txtCodigo.getText();
				ResultSet rset=stmt.executeQuery(sql);
				ResultSetMetaData metaDatos = rset.getMetaData();
				int numeroColumnas = metaDatos.getColumnCount();
				PdfPTable tabla = new PdfPTable(numeroColumnas);
				for(int i=1;i<=numeroColumnas;i++){
					tabla.addCell(metaDatos.getColumnLabel(i));
				}
				
				while (rset.next()) {
					for (int i=1;i<=numeroColumnas;i++){
						tabla.addCell(""+rset.getObject(i));
					}
				}
				documento.add(tabla);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			documento.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "La ruta introducida no es valida");
		} catch (DocumentException e) {
			JOptionPane.showMessageDialog(null, "Error al Crear el listado");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// --------------------------------------------------------------------------//
		
		if (e.getSource().equals(btnSiguiente)) {
			try {
				rset.next();
				mostrarDatos();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			return;
		}
		if (e.getSource().equals(btnAnterior)) {
			try {
				rset.previous();
				mostrarDatos();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			return;
		}
		if (e.getSource().equals(btnPrimero)) {
			try {
				rset.first();
				mostrarDatos();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			return;
		}
		if (e.getSource().equals(btnUltimo)) {
			try {
				rset.last();
				mostrarDatos();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			return;
		}
		
		// --------------------------------------------------------------------------//
		
		if (e.getSource().equals(btnNuevo)) {
			if (btnNuevo.getText().equals("Nuevo")) {
				setText(true);
				setBotones(false);
				btnNuevo.setEnabled(true);
				btnNuevo.setText("Guardar");
				limpiarTxt();
				txtEstado.setText("Nuevo");
			} else {		// Si no está en "Nuevo"...
				String sql="insert into articulo (codarticulo,titulo,autor,numpaginas) values ("
						+ txtCodigo.getText()+",'"+txtTitulo.getText()+"','"+txtAutor.getText()+"',"+txtNumpaginas.getText()+")";
//				System.out.println(sql);
				try {
					rset.close();
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "Registro añadido");
				} catch (SQLException e1) {
					if(e1.getErrorCode()==1){
						JOptionPane.showMessageDialog(null, "El artículo "+txtCodigo.getText()+" ya existe");
					}else{
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				setText(false);
				setBotones(true);
				btnNuevo.setText("Nuevo");
				cargarDatos();
			}
			return;
		}
		if (e.getSource().equals(btnEliminar)) {
			int pregunta=JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar el registro?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			String sql="delete from articulo where codarticulo="+txtCodigo.getText();
			// Se cierra el rset, para que no se bloquee y te permita borrar
			if (pregunta==0) {
				try {
					rset.close();
					pregunta=stmt.executeUpdate(sql);
					if (pregunta>0) {
						JOptionPane.showMessageDialog(null, "Se ha eliminado el artículo "+txtTitulo.getText()+" correctamente.");
					}
					cargarDatos();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}	
			return;
		}
		if (e.getSource().equals(btnModificar)) {
			if (btnModificar.getText().equals("Modificar")) {
				setBotones(false);
				btnModificar.setEnabled(true);
				btnModificar.setText("Guardar");
				setText(true);
				txtCodigo.setEditable(false);
				txtEstado.setText("Modificando");
			} else {
				setBotones(true);
				setText(false);
				btnModificar.setText("Modificar");
				String sql="update articulo set titulo='"+txtTitulo.getText()+"',autor='"+txtAutor.getText()+"',numpaginas="+txtNumpaginas.getText()+" "
						+ "where codarticulo="+txtCodigo.getText();
			//	System.out.println(sql);
				try {
					rset.close();
					int ret=stmt.executeUpdate(sql);
					if (ret>0) {
						JOptionPane.showMessageDialog(null, "Se ha modificado con éxito el registro");
					}
					cargarDatos();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				
			}
		}
	}
}
