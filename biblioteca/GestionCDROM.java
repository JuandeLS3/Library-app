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

public class GestionCDROM extends Gestiones implements WindowListener{
	
	private ResultSet rset;
	private Statement stmt;
	private int totalregistros;
	private JTextField txtCodcdrom;
	private JTextField txtSignatura;
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtMateria;
	private JTextField txtEditorial;
	private JLabel lblTitulo;
	private JLabel lblAutor;
	private JLabel lblSignatura;
	private JLabel lblMateria;
	private JLabel lblCodigo;
	private JLabel lblEditorial;
	private JLabel lblGestinDeCdroms;
	
	public GestionCDROM(Connection conn,JFrame principal) {
		super();
		this.conn=conn;		// Recibimos la conexión de Biblioteca
		this.principal=principal;
		this.principal.setEnabled(false);
		this.principal.setVisible(false);
		setTitle("Gestión de CDROMS");
		
		txtCodcdrom = new JTextField();
		txtCodcdrom.setEditable(false);
		txtCodcdrom.setHorizontalAlignment(SwingConstants.CENTER);
		txtCodcdrom.setBounds(143, 142, 99, 20);
		getContentPane().add(txtCodcdrom);
		txtCodcdrom.setColumns(10);
		
		txtSignatura = new JTextField();
		txtSignatura.setEditable(false);
		txtSignatura.setHorizontalAlignment(SwingConstants.CENTER);
		txtSignatura.setBounds(264, 71, 99, 20);
		getContentPane().add(txtSignatura);
		txtSignatura.setColumns(10);
		
		txtTitulo = new JTextField();
		txtTitulo.setEditable(false);
		txtTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitulo.setBounds(22, 71, 99, 20);
		getContentPane().add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtAutor = new JTextField();
		txtAutor.setEditable(false);
		txtAutor.setHorizontalAlignment(SwingConstants.CENTER);
		txtAutor.setBounds(143, 71, 99, 20);
		getContentPane().add(txtAutor);
		txtAutor.setColumns(10);
		
		txtMateria = new JTextField();
		txtMateria.setEditable(false);
		txtMateria.setHorizontalAlignment(SwingConstants.CENTER);
		txtMateria.setBounds(22, 142, 99, 20);
		getContentPane().add(txtMateria);
		txtMateria.setColumns(10);
		
		txtEditorial = new JTextField();
		txtEditorial.setEditable(false);
		txtEditorial.setHorizontalAlignment(SwingConstants.CENTER);
		txtEditorial.setBounds(264, 142, 99, 20);
		getContentPane().add(txtEditorial);
		txtEditorial.setColumns(10);
		
		lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(48, 46, 46, 14);
		getContentPane().add(lblTitulo);
		
		lblAutor = new JLabel("Autor");
		lblAutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutor.setBounds(169, 46, 46, 14);
		getContentPane().add(lblAutor);
		
		lblSignatura = new JLabel("Signatura");
		lblSignatura.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignatura.setBounds(284, 46, 59, 14);
		getContentPane().add(lblSignatura);
		
		lblMateria = new JLabel("Materia");
		lblMateria.setHorizontalAlignment(SwingConstants.CENTER);
		lblMateria.setBounds(48, 117, 46, 14);
		getContentPane().add(lblMateria);
		
		lblCodigo = new JLabel("Codigo");
		lblCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCodigo.setBounds(169, 117, 46, 14);
		getContentPane().add(lblCodigo);
		
		lblEditorial = new JLabel("Editorial");
		lblEditorial.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditorial.setBounds(290, 117, 46, 14);
		getContentPane().add(lblEditorial);
		
		lblGestinDeCdroms = new JLabel("Gesti\u00F3n de CDROMS");
		lblGestinDeCdroms.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestinDeCdroms.setForeground(Color.BLUE);
		lblGestinDeCdroms.setBounds(127, 11, 130, 14);
		getContentPane().add(lblGestinDeCdroms);
	}
	
	private void cargarDatos() {
		try {
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql="select * from cdrom";
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
		txtCodcdrom.setText(""+rset.getInt("codcdrom"));
		txtSignatura.setText(rset.getString("SIGNATURA"));
		txtTitulo.setText(rset.getString("titulo"));
		txtAutor.setText(rset.getString("autor"));
		txtMateria.setText(rset.getString("materia"));
		txtEditorial.setText(rset.getString("editorial"));
		controlarBotonesNavegacion();
		txtEstado.setText(rset.getRow()+"/"+totalregistros);
	}
	
	private void totalRegistros() {
		try {
			String sql="select count(*) from cdrom";
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
		txtCodcdrom.setEditable(activo);
		txtSignatura.setEditable(activo);
		txtTitulo.setEditable(activo);
		txtAutor.setEditable(activo);
		txtMateria.setEditable(activo);
		txtEditorial.setEditable(activo);
	}
	private void limpiarTxt() {
		txtCodcdrom.setText("");
		txtSignatura.setText("");
		txtTitulo.setText("");
		txtAutor.setText("");
		txtMateria.setText("");
		txtEditorial.setText("");
	}
	
	@Override
	protected void generarPDF(File file) {
		Document documento = new Document();
		FileOutputStream ficheroPdf;
		try {
			ficheroPdf = new FileOutputStream(file);		
			PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(25);
			documento.open();
			documento.add(new Paragraph("Listado de CDROMS",
					FontFactory.getFont("verdana",  
							20,              
							BaseColor.BLACK)));
			documento.add(new Paragraph(" "));
			try{
				Statement stmt=conn.createStatement();
				String sql="select * from cdrom where codcdrom="+txtCodcdrom.getText();
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
				String sql="insert into cdrom (codcdrom,signatura,titulo,autor,materia,editorial) values ("
						+ txtCodcdrom.getText()+",'"+txtSignatura.getText()+"','"+txtTitulo.getText()+"','"+txtAutor.getText()+"','"+txtMateria.getText()+"','"+txtEditorial.getText()+"')";
//				System.out.println(sql);
				try {
					rset.close();
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "Registro añadido");
				} catch (SQLException e1) {
					if(e1.getErrorCode()==1){
						JOptionPane.showMessageDialog(null, "El cdrom "+txtCodcdrom.getText()+" ya existe");
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
			String sql="delete from cdrom where codcdrom="+txtCodcdrom.getText();
			// Se cierra el rset, para que no se bloquee y te permita borrar
			if (pregunta==0) {
				try {
					rset.close();
					pregunta=stmt.executeUpdate(sql);
					if (pregunta>0) {
						JOptionPane.showMessageDialog(null, "Se ha eliminado el cdrom "+txtTitulo.getText()+" correctamente.");
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
				txtCodcdrom.setEditable(false);
				txtEstado.setText("Modificando");
			} else {
				setBotones(true);
				setText(false);
				btnModificar.setText("Modificar");
				String sql="update cdrom set signatura='"+txtSignatura.getText()+"',titulo='"+txtTitulo.getText()+"',autor='"+txtAutor.getText()+"',materia='"+txtMateria.getText()+"',editorial='"+txtEditorial.getText()+"' "
						+ "where codcdrom="+txtCodcdrom.getText();
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
