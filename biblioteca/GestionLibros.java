package biblioteca;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class GestionLibros extends Gestiones implements WindowListener{
	
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtMateria;
	private JTextField txtEditorial;
	private JTextField txtISBN;
	private JTextField txtSignatura;
	private ResultSet rset;
	private Statement stmt;
	private int totalregistros;
	private JLabel lblGestinDeLibros;
	
	
	

	public GestionLibros(Connection conn,JFrame principal) {
		super();
		this.conn=conn;		// Recibimos la conexión de Biblioteca
		this.principal=principal;
		this.principal.setEnabled(false);
		this.principal.setVisible(false);
		setTitle("Gestión de libros");
		
		txtTitulo = new JTextField();
		txtTitulo.setEditable(false);
		txtTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitulo.setBounds(23, 73, 98, 20);
		getContentPane().add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtAutor = new JTextField();
		txtAutor.setEditable(false);
		txtAutor.setHorizontalAlignment(SwingConstants.CENTER);
		txtAutor.setBounds(144, 73, 98, 20);
		getContentPane().add(txtAutor);
		txtAutor.setColumns(10);
		
		txtMateria = new JTextField();
		txtMateria.setEditable(false);
		txtMateria.setHorizontalAlignment(SwingConstants.CENTER);
		txtMateria.setBounds(260, 73, 98, 20);
		getContentPane().add(txtMateria);
		txtMateria.setColumns(10);
		
		txtEditorial = new JTextField();
		txtEditorial.setEditable(false);
		txtEditorial.setHorizontalAlignment(SwingConstants.CENTER);
		txtEditorial.setBounds(23, 144, 98, 20);
		getContentPane().add(txtEditorial);
		txtEditorial.setColumns(10);
		
		txtISBN = new JTextField();
		txtISBN.setEditable(false);
		txtISBN.setHorizontalAlignment(SwingConstants.CENTER);
		txtISBN.setBounds(144, 144, 98, 20);
		getContentPane().add(txtISBN);
		txtISBN.setColumns(10);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(41, 48, 63, 14);
		getContentPane().add(lblTitulo);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutor.setBounds(162, 48, 63, 14);
		getContentPane().add(lblAutor);
		
		txtSignatura = new JTextField();
		txtSignatura.setEditable(false);
		txtSignatura.setHorizontalAlignment(SwingConstants.CENTER);
		txtSignatura.setColumns(10);
		txtSignatura.setBounds(260, 144, 98, 20);
		getContentPane().add(txtSignatura);
		
		JLabel lblMateria = new JLabel("Materia");
		lblMateria.setHorizontalAlignment(SwingConstants.CENTER);
		lblMateria.setBounds(278, 48, 63, 14);
		getContentPane().add(lblMateria);
		
		JLabel lblEditorial = new JLabel("Editorial");
		lblEditorial.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditorial.setBounds(41, 119, 63, 14);
		getContentPane().add(lblEditorial);
		
		JLabel lblISBN = new JLabel("ISBN");
		lblISBN.setHorizontalAlignment(SwingConstants.CENTER);
		lblISBN.setBounds(162, 119, 63, 14);
		getContentPane().add(lblISBN);
		
		JLabel lblSignatura = new JLabel("Signatura");
		lblSignatura.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignatura.setBounds(278, 119, 63, 14);
		getContentPane().add(lblSignatura);
		
		lblGestinDeLibros = new JLabel("Gesti\u00F3n de libros");
		lblGestinDeLibros.setForeground(Color.BLUE);
		lblGestinDeLibros.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestinDeLibros.setBounds(144, 11, 98, 14);
		getContentPane().add(lblGestinDeLibros);
		
		
	}
	
	private void cargarDatos() {
		try {
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql="select * from libro";
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
		txtTitulo.setText(rset.getString("TITULO"));
		txtSignatura.setText(rset.getString("SIGNATURA"));
		txtAutor.setText(rset.getString("AUTOR"));
		txtMateria.setText(rset.getString("MATERIA"));
		txtEditorial.setText(rset.getString("EDITORIAL"));
		txtISBN.setText(""+rset.getInt("ISBN"));
		controlarBotonesNavegacion();
		txtEstado.setText(rset.getRow()+"/"+totalregistros);
	}
	
	private void totalRegistros() {
		try {
			String sql="select count(*) from libro";
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
		txtTitulo.setEditable(activo);
		txtSignatura.setEditable(activo);
		txtAutor.setEditable(activo);
		txtMateria.setEditable(activo);
		txtEditorial.setEditable(activo);
		txtISBN.setEditable(activo);
	}
	private void limpiarTxt() {
		txtTitulo.setText("");
		txtSignatura.setText("");
		txtAutor.setText("");
		txtMateria.setText("");
		txtEditorial.setText("");
		txtISBN.setText("");
	}
	
	@Override
	protected void generarPDF(File file) {
		Document documento = new Document();
		FileOutputStream ficheroPdf;
		try {
			ficheroPdf = new FileOutputStream(file);		
			PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(25);
			documento.open();
			documento.add(new Paragraph("Listado de libros",
					FontFactory.getFont("verdana",  
							20,              
							BaseColor.BLACK)));
			documento.add(new Paragraph(" "));
			try{
				Statement stmt=conn.createStatement();
				String sql="select * from libro where isbn="+txtISBN.getText();
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
	public void windowOpened(WindowEvent e) {
		setResizable(false);
		cargarDatos();
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
				String sql="insert into libro (isbn,signatura,titulo,autor,materia,editorial) values ("
						+ txtISBN.getText()+",'"+txtSignatura.getText()+"','"+txtTitulo.getText()+"','"+txtAutor.getText()+"','"+txtMateria.getText()+"','"+txtEditorial.getText()+"')";
//				System.out.println(sql);
				try {
					rset.close();
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "Registro añadido");
				} catch (SQLException e1) {
					if(e1.getErrorCode()==1){
						JOptionPane.showMessageDialog(null, "El libro "+txtTitulo.getText()+" ya existe");
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
			String sql="delete from libro where isbn="+txtISBN.getText();
			// Se cierra el rset, para que no se bloquee y te permita borrar
			if (pregunta==0) {
				try {
					rset.close();
					pregunta=stmt.executeUpdate(sql);
					if (pregunta>0) {
						JOptionPane.showMessageDialog(null, "Se ha eliminado el libro "+txtTitulo.getText()+" correctamente.");
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
				txtISBN.setEditable(false);
				txtEstado.setText("Modificando");
			} else {
				setBotones(true);
				setText(false);
				btnModificar.setText("Modificar");
				String sql="update libro set signatura='"+txtSignatura.getText()+"',titulo='"+txtTitulo.getText()+"',autor='"+txtAutor.getText()+"',materia='"+txtMateria.getText()+"',editorial='"+txtEditorial.getText()+"' "
						+ "where isbn="+txtISBN.getText();
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
