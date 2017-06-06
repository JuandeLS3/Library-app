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

public class GestionUsuarios extends Gestiones implements WindowListener{
	
	private JTextField txtApellido1;
	private JTextField txtApellido2;
	private JTextField txtNombre;
	private JTextField txtCodigoUser;
	private JLabel lblCodigo;
	private JLabel lblGestinDeUsuario;
	private ResultSet rset;
	private Statement stmt;
	private int totalregistros;
	

	public GestionUsuarios(Connection conn,JFrame principal) {
		super();
		this.conn=conn;		// Recibimos la conexión de Biblioteca
		this.principal=principal;
		this.principal.setEnabled(false);
		this.principal.setVisible(false);
		setTitle("Gestión de usuarios");
		
		txtApellido1 = new JTextField();
		txtApellido1.setEditable(false);
		txtApellido1.setHorizontalAlignment(SwingConstants.CENTER);
		txtApellido1.setBounds(14, 78, 110, 20);
		getContentPane().add(txtApellido1);
		txtApellido1.setColumns(10);
		
		txtApellido2 = new JTextField();
		txtApellido2.setEditable(false);
		txtApellido2.setHorizontalAlignment(SwingConstants.CENTER);
		txtApellido2.setBounds(138, 78, 110, 20);
		getContentPane().add(txtApellido2);
		txtApellido2.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.setBounds(262, 78, 110, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido 1");
		lblApellido.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellido.setBounds(41, 53, 56, 14);
		getContentPane().add(lblApellido);
		
		JLabel lblApellido_1 = new JLabel("Apellido 2");
		lblApellido_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellido_1.setBounds(165, 53, 56, 14);
		getContentPane().add(lblApellido_1);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(289, 53, 56, 14);
		getContentPane().add(lblNombre);
		
		txtCodigoUser = new JTextField();
		txtCodigoUser.setEditable(false);
		txtCodigoUser.setHorizontalAlignment(SwingConstants.CENTER);
		txtCodigoUser.setBounds(153, 136, 86, 20);
		getContentPane().add(txtCodigoUser);
		txtCodigoUser.setColumns(10);
		
		lblCodigo = new JLabel("Codigo");
		lblCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCodigo.setBounds(173, 111, 46, 14);
		getContentPane().add(lblCodigo);
		
		lblGestinDeUsuario = new JLabel("Gesti\u00F3n de usuario");
		lblGestinDeUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestinDeUsuario.setForeground(Color.BLUE);
		lblGestinDeUsuario.setBounds(137, 11, 111, 14);
		getContentPane().add(lblGestinDeUsuario);
	}
	
	private void cargarDatos() {
		try {
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql="select * from usuario";
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
		txtApellido1.setText(rset.getString("APELLIDO1"));
		txtApellido2.setText(rset.getString("APELLIDO2"));
		txtNombre.setText(rset.getString("NOMBRE"));
		txtCodigoUser.setText(""+rset.getInt("CODUSUARIO"));
		controlarBotonesNavegacion();
		txtEstado.setText(rset.getRow()+"/"+totalregistros);
	}
	
	private void totalRegistros() {
		try {
			String sql="select count(*) from usuario";
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
		txtApellido1.setEditable(activo);
		txtApellido2.setEditable(activo);
		txtNombre.setEditable(activo);
		txtCodigoUser.setEditable(activo);
	}
	
	@Override
	protected void generarPDF(File file) {
		Document documento = new Document();
		FileOutputStream ficheroPdf;
		try {
			ficheroPdf = new FileOutputStream(file);		
			PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(25);
			documento.open();
			documento.add(new Paragraph("Listado de usuarios",
					FontFactory.getFont("verdana",  
							20,              
							BaseColor.BLACK)));
			documento.add(new Paragraph(" "));
			try{
				Statement stmt=conn.createStatement();
				String sql="select * from usuario where codusuario="+txtCodigoUser.getText();
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
				txtApellido1.setText("");
				txtApellido2.setText("");
				txtNombre.setText("");
				txtCodigoUser.setText("");
				txtEstado.setText("Nuevo");
			} else {		// Si no está en "Nuevo"...
				String sql="insert into usuario (codusuario,nombre,apellido1,apellido2) values ("
						+ txtCodigoUser.getText()+",'"+txtNombre.getText()+"','"+txtApellido1.getText()+"','"+txtApellido2.getText()+"')";
				try {
					rset.close();
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "Registro añadido");
				} catch (SQLException e1) {
					if(e1.getErrorCode()==1){
						JOptionPane.showMessageDialog(null, "El usuario "+txtNombre.getText()+" ya existe");
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
			String sql="delete from usuario where codusuario="+txtCodigoUser.getText();
			// Se cierra el rset, para que no se bloquee y te permita borrar
			if (pregunta==0) {
				try {
					rset.close();
					pregunta=stmt.executeUpdate(sql);
					if (pregunta>0) {
						JOptionPane.showMessageDialog(null, "Se ha eliminado a "+txtNombre.getText()+" correctamente.");
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
				txtCodigoUser.setEditable(false);
				txtEstado.setText("Modificando");
			} else {
				setBotones(true);
				setText(false);
				btnModificar.setText("Modificar");
				String sql="update usuario set nombre='"+txtNombre.getText()+"',apellido1='"+txtApellido1.getText()+"',apellido2='"+txtApellido2.getText()+"'"
						+ "where codusuario="+txtCodigoUser.getText();
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
