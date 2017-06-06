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

public class GestionRevistas extends Gestiones implements WindowListener{
	
	private ResultSet rset;
	private Statement stmt;
	private int totalregistros;
	private JTextField txtCodigo;
	private JTextField txtSignatura;
	private JTextField txtNombre;
	private JTextField txtMateria;
	private JLabel lblCodigo;
	private JLabel lblNombre;
	private JLabel lblSignatura;
	private JLabel lblMateria;
	private JLabel lblGestinDeRevistas;

	
	public GestionRevistas(Connection conn,JFrame principal) {
		super();
		this.conn=conn;		// Recibimos la conexión de Biblioteca
		this.principal=principal;
		this.principal.setEnabled(false);
		this.principal.setVisible(false);
		setTitle("Gestión de revistas");
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		txtCodigo.setBounds(58, 67, 106, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtSignatura = new JTextField();
		txtSignatura.setEditable(false);
		txtSignatura.setHorizontalAlignment(SwingConstants.CENTER);
		txtSignatura.setBounds(222, 67, 106, 20);
		getContentPane().add(txtSignatura);
		txtSignatura.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.setBounds(58, 136, 106, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		txtMateria = new JTextField();
		txtMateria.setEditable(false);
		txtMateria.setHorizontalAlignment(SwingConstants.CENTER);
		txtMateria.setBounds(222, 136, 106, 20);
		getContentPane().add(txtMateria);
		txtMateria.setColumns(10);
		
		lblCodigo = new JLabel("Codigo");
		lblCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCodigo.setBounds(81, 42, 60, 14);
		getContentPane().add(lblCodigo);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(245, 42, 60, 14);
		getContentPane().add(lblNombre);
		
		lblSignatura = new JLabel("Signatura");
		lblSignatura.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignatura.setBounds(81, 111, 60, 14);
		getContentPane().add(lblSignatura);
		
		lblMateria = new JLabel("Materia");
		lblMateria.setHorizontalAlignment(SwingConstants.CENTER);
		lblMateria.setBounds(245, 111, 60, 14);
		getContentPane().add(lblMateria);
		
		lblGestinDeRevistas = new JLabel("Gesti\u00F3n de revistas");
		lblGestinDeRevistas.setForeground(Color.BLUE);
		lblGestinDeRevistas.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestinDeRevistas.setBounds(142, 11, 114, 14);
		getContentPane().add(lblGestinDeRevistas);
		

	}
	
	private void cargarDatos() {
		try {
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql="select * from revista";
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
		txtCodigo.setText(""+rset.getInt("codrevista"));
		txtSignatura.setText(rset.getString("SIGNATURA"));
		txtNombre.setText(rset.getString("nombre"));
		txtMateria.setText(rset.getString("materia"));
		controlarBotonesNavegacion();
		txtEstado.setText(rset.getRow()+"/"+totalregistros);
	}
	
	private void totalRegistros() {
		try {
			String sql="select count(*) from revista";
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
		txtSignatura.setEditable(activo);
		txtNombre.setEditable(activo);
		txtMateria.setEditable(activo);
	}
	private void limpiarTxt() {
		txtCodigo.setText("");
		txtSignatura.setText("");
		txtNombre.setText("");
		txtMateria.setText("");
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
			documento.add(new Paragraph("Listado de revistas",
					FontFactory.getFont("verdana",  
							20,              
							BaseColor.BLACK)));
			documento.add(new Paragraph(" "));
			try{
				Statement stmt=conn.createStatement();
				String sql="select * from revista where codrevista="+txtCodigo.getText();
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
				String sql="insert into revista (codrevista,signatura,nombre,materia) values ("
						+ txtCodigo.getText()+",'"+txtSignatura.getText()+"','"+txtNombre.getText()+"','"+txtMateria.getText()+"')";
//				System.out.println(sql);
				try {
					rset.close();
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "Registro añadido");
				} catch (SQLException e1) {
					if(e1.getErrorCode()==1){
						JOptionPane.showMessageDialog(null, "La revista "+txtNombre.getText()+" ya existe");
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
			String sql="delete from revista where codrevista="+txtCodigo.getText();
			// Se cierra el rset, para que no se bloquee y te permita borrar
			if (pregunta==0) {
				try {
					rset.close();
					pregunta=stmt.executeUpdate(sql);
					if (pregunta>0) {
						JOptionPane.showMessageDialog(null, "Se ha eliminado la revista "+txtNombre.getText()+" correctamente.");
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
				String sql="update revista set signatura='"+txtSignatura.getText()+"',nombre='"+txtNombre.getText()+"',materia='"+txtMateria.getText()+"' "
						+ "where codrevista="+txtCodigo.getText();
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
