package biblioteca;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import java.awt.Toolkit;
import javax.swing.JRadioButton;

public class ConsultarPrestamo extends JFrameJTable implements WindowListener,ActionListener,KeyListener,ItemListener{

	private JPanel contentPane;
	private JFrameJTable principal;
	private Connection conn;
	private JTextField txtUsuario;
	private JButton btnBuscar;
	private PanelJtable table;
	private String sql;
	private Checkbox tipo,usuario;
	private CheckboxGroup opciones;

	
	public ConsultarPrestamo(Connection conn, JFrameJTable principal, String sql) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConsultarPrestamo.class.getResource("/biblioteca/images/book.png")));
		this.conn=conn;
		this.principal=principal;
		this.principal.setEnabled(false);
		setTitle("Consultar préstamo");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 703, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		opciones=new CheckboxGroup();
		tipo=new Checkbox("Tipo", opciones, false);
		usuario=new Checkbox("Usuario",opciones,true);
		tipo.addItemListener(this);
		usuario.addItemListener(this);
		tipo.setBounds(500, 20, 62, 23);
		usuario.setBounds(500, 49, 62, 23);
		contentPane.add(usuario);
		contentPane.add(tipo);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(10, 33, 465, 23);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		txtUsuario.addKeyListener(this);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(568, 33, 109, 23);
		contentPane.add(btnBuscar);
		btnBuscar.addActionListener(this);
		
		table = new PanelJtable(conn,sql,this);
		table.setBounds(10, 82, 667, 340);
		contentPane.add(table);
		
		addWindowListener(this);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	/**
	 *  Método que comprueba si el nombreColumna es o no CODUSUARIO, si lo es, debe modificar el texfield usuario.
	 */
	@Override
	public void setBusqueda(String pk, String cadena, String nombreColumna){
		String nombreusuario=pk;
		int codusuario=Integer.parseInt(""+cadena.charAt(0));
		int codigoproducto=Integer.parseInt(""+cadena.charAt(2));
		char tipochooser=cadena.charAt(4);
		String fecha=cadena.substring(6, 14);
		System.out.println(cadena);
		new NuevoPrestamo(this.conn,this, nombreusuario,codusuario,codigoproducto,tipochooser,fecha);
		
		this.principal.setEnabled(true);
		this.dispose();

	}
	@Override
	public void itemStateChanged(ItemEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnBuscar)) {
			if (opciones.getSelectedCheckbox().getLabel().equals("Usuario")) {
				if (!txtUsuario.getText().equals("")) {
					sql="select nombre,u.codusuario,codmaterial,tipomaterial,TO_CHAR(FECHA_PRESTAMO,'DD/MM/YY') as PRESTAMO,TO_CHAR(FECHA_DEVOLUCION,'DD/MM/YY') as DEVOLUCION from usuario u,prestamo p where lower(nombre) like '%"+txtUsuario.getText().toLowerCase()+"%' and u.codusuario = p.codusuario and fecha_devolucion is NULL";
					table.refreshTabla(sql);
				}
			} else {
				sql="select nombre,u.codusuario,codmaterial,tipomaterial,TO_CHAR(FECHA_PRESTAMO,'DD/MM/YY') as PRESTAMO,TO_CHAR(FECHA_DEVOLUCION,'DD/MM/YY') as DEVOLUCION from usuario u,prestamo p where codmaterial like '%"+txtUsuario.getText()+"%' and u.codusuario = p.codusuario and fecha_devolucion is NULL";
				table.refreshTabla(sql);
				
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}


	@Override
	public void keyPressed(KeyEvent e) {
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if (opciones.getSelectedCheckbox().getLabel().equals("Usuario")) {
			if (!txtUsuario.getText().equals("")) {
				sql="select nombre,u.codusuario,codmaterial,tipomaterial,TO_CHAR(FECHA_PRESTAMO,'DD/MM/YY') as PRESTAMO,TO_CHAR(FECHA_DEVOLUCION,'DD/MM/YY') as DEVOLUCION from usuario u,prestamo p where lower(nombre) like '%"+txtUsuario.getText().toLowerCase()+"%' and u.codusuario = p.codusuario and fecha_devolucion is NULL";
				//System.out.println(sql);
				table.refreshTabla(sql);
			}	
		} else {
			if (!txtUsuario.getText().equals("")) {
				sql="select nombre,u.codusuario,codmaterial,tipomaterial,TO_CHAR(FECHA_PRESTAMO,'DD/MM/YY') as PRESTAMO,TO_CHAR(FECHA_DEVOLUCION,'DD/MM/YY') as DEVOLUCION from usuario u,prestamo p where codmaterial like '%"+txtUsuario.getText()+"%' and u.codusuario = p.codusuario and fecha_devolucion is NULL";
				//System.out.println(sql);
				table.refreshTabla(sql);
			}
		}
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
	}
	
	protected void generarListado(String sql) {
		try {
			this.sql=sql;
			JFileChooser chooser = new JFileChooser(); 
			chooser.showOpenDialog(this);
			File file = chooser.getSelectedFile();
			if(file!=null){
				generarPDF(file);
				Desktop.getDesktop().open(file);
			}
		}catch(IOException ex) {
			JOptionPane.showMessageDialog(null, "No se encuentra el fichero creado");
			
		}
	}
	
	protected void generarPDF(File file) {
		Document documento = new Document();
		FileOutputStream ficheroPdf;
		try {
			ficheroPdf = new FileOutputStream(file);		
			PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(25);
			documento.open();
			documento.add(new Paragraph("Préstamos pendientes de devolución",
					FontFactory.getFont("verdana",  
							20,              
							BaseColor.BLACK)));
			documento.add(new Paragraph(" "));
			try{
				Statement stmt=conn.createStatement(); 
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
	public void windowClosed(WindowEvent e) {	

	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.principal.setEnabled(true);
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		setResizable(false);
	}

}
