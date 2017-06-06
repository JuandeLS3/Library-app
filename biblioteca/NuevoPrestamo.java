package biblioteca;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.Choice;
import com.toedter.calendar.JDateChooser;
import java.awt.Toolkit;
import java.awt.Font;

public class NuevoPrestamo extends JFrameJTable implements ActionListener,WindowListener,ItemListener{

	private JPanel contentPane;
	private JFrameJTable principal;
	private Connection conn;
	private JTextField txtUsuario;
	private Choice tipo;
	private JLabel lblTipo;
	private JTextField txtCodigo;
	private JLabel lblCodigo;
	private JLabel lblfecha_prestDePrestamo;
	private JButton btnAceptar;
	private JButton btnBuscarCodigo;
	private JButton btnBuscarUsuario;
	private JButton btnActualizar;
	private JDateChooser fecha_prest,fecha_devol;
	private String PKuser,PKproducto;
	private JTextField txtFecha_prest;


	/**
	 * @wbp.parser.constructor
	 */
	public NuevoPrestamo(Connection conn,JFrameJTable principal,String nombreuser,int codusuario,int codigoproducto,char tipochooser,String fecha) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(NuevoPrestamo.class.getResource("/biblioteca/images/book.png")));
		setTitle("Modificar préstamo");
		this.principal=principal;
		this.conn=conn;
		initComponents();
		
		btnBuscarUsuario.setEnabled(false);
		btnBuscarCodigo.setEnabled(false);
		txtUsuario.setText(nombreuser);
		txtCodigo.setText(""+codigoproducto);
		PKuser=""+codusuario;
		PKproducto=""+codigoproducto;

		switch (tipochooser) {
		case 'L':
			tipo.select("Libro");
			break;
		case 'R':
			tipo.select("Revista");
			break;
		case 'C':
			tipo.select("CDROM");
			break;
		case 'A':
			tipo.select("Articulo");
			break;
		default:
			break;
		}


		fecha_devol = new JDateChooser();
		fecha_devol.setEnabled(false);
		fecha_devol.setBounds(265, 208, 126, 23);
		contentPane.add(fecha_devol);
		fecha_devol.setEnabled(true);
		
		JLabel lblfecha_prestDevol = new JLabel("Fecha de devoluci\u00F3n");
		lblfecha_prestDevol.setBounds(265, 193, 148, 14);
		contentPane.add(lblfecha_prestDevol);
		
		txtFecha_prest = new JTextField();
		txtFecha_prest.setEditable(false);
		txtFecha_prest.setBounds(16, 208, 126, 23);
		contentPane.add(txtFecha_prest);
		txtFecha_prest.setColumns(10);
		txtFecha_prest.setText(fecha);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(157, 264, 98, 23);
		contentPane.add(btnActualizar);
		btnActualizar.addActionListener(this);
		
		
		setVisible(true);
	}
	
	public NuevoPrestamo(Connection conn, JFrameJTable principal) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(NuevoPrestamo.class.getResource("/biblioteca/images/book.png")));
		setTitle("Realizar préstamo");
		this.conn=conn;
		this.principal=principal;
		this.principal.setVisible(false);
		initComponents();		
		
		fecha_prest = new JDateChooser();
		fecha_prest.setBounds(16, 208, 126, 23);
		contentPane.add(fecha_prest);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(162, 264, 89, 23);
		contentPane.add(btnAceptar);
		btnAceptar.addActionListener(this);
		btnAceptar.setEnabled(false);
	
	}
	
	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 439, 337);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtUsuario.setEditable(false);
		txtUsuario.setBounds(16, 36, 314, 23);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(16, 21, 61, 14);
		contentPane.add(lblUsuario);
		
		txtCodigo = new JTextField();
		txtCodigo.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(16, 146, 314, 23);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		lblCodigo = new JLabel("Codigo ");
		lblCodigo.setBounds(16, 128, 126, 14);
		contentPane.add(lblCodigo);	
		
		lblfecha_prestDePrestamo = new JLabel("Fecha de pr\u00E9stamo");
		lblfecha_prestDePrestamo.setBounds(16, 193, 148, 14);
		contentPane.add(lblfecha_prestDePrestamo);
		
		btnBuscarCodigo = new JButton("...");
		btnBuscarCodigo.setBounds(346, 146, 61, 23);
		contentPane.add(btnBuscarCodigo);
		btnBuscarCodigo.setEnabled(false);
		btnBuscarCodigo.addActionListener(this);
		
		btnBuscarUsuario = new JButton("...");
		btnBuscarUsuario.setBounds(346, 35, 61, 23);
		contentPane.add(btnBuscarUsuario);
		btnBuscarUsuario.addActionListener(this);
		
		tipo = new Choice();
		tipo.setBounds(16, 91, 115, 20);
		tipo.add("Seleccione");
		tipo.add("Libro");
		tipo.add("Revista");
		tipo.add("CDROM");
		tipo.add("Articulo");
		tipo.setEnabled(false);
		tipo.addItemListener(this);
		contentPane.add(tipo);
		
		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(16, 71, 46, 14);
		contentPane.add(lblTipo);
		
		addWindowListener(this);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/**
	 *  Métodos que rellenan los texfield de usuario y codigo en Realizar préstamo
	 */
	
	@Override
	public void setUsuario(String pk, String usuario) {
		PKuser=pk;
		txtUsuario.setText(usuario);
	}
	
	@Override
	public void setCodigo(String pk, String tituloproducto) {
		PKproducto=pk;
		txtCodigo.setText(pk+" - "+tituloproducto);
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItemSelectable().equals(tipo)) {
			if (!tipo.getSelectedItem().equals("Seleccione")) {
				lblCodigo.setText("Codigo "+tipo.getSelectedItem());
				btnBuscarCodigo.setEnabled(true);
			} else {
				btnBuscarCodigo.setEnabled(false);
				lblCodigo.setText("Codigo ");
			}
			return;
		}
	}
	private void restart() {
		txtCodigo.setText("");
		txtUsuario.setText("");
		tipo.select("Seleccione");
		tipo.setEnabled(false);
		btnBuscarCodigo.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnBuscarUsuario)) {
			new Busqueda(conn, this, "select * from usuario", 'U');
			tipo.setEnabled(true);
			return;
		}
		if (e.getSource().equals(btnBuscarCodigo)) {
			String sql="select * from "+tipo.getSelectedItem();
			new Busqueda(conn, this, sql, tipo.getSelectedItem().charAt(0));
			btnAceptar.setEnabled(true); 		// Activamos el botón aceptar, ya que se han cumplimentado (supuestamente) todos los campos excepto fecha_prest
			return;
		}
		if (e.getSource().equals(btnAceptar)) {	// Se hace insert en la bd
			if (txtUsuario.getText().equals("")||tipo.getSelectedItem().equals("Seleccione")||txtCodigo.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Debe cumplimentar todos los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Formateo de fecha_prest //
			Calendar fecha_prest=Calendar.getInstance();
			try {
				fecha_prest.setTime(this.fecha_prest.getDate());
			} catch (NullPointerException e2) {
				JOptionPane.showMessageDialog(null, "Debe seleccionar la fecha del préstamo");
				return;
			}
			DateFormat formatofecha_prest=new SimpleDateFormat("dd/MM/yyyy");
			String fecha_prestFormateada=formatofecha_prest.format(fecha_prest.getTime());		// fecha_prest final
			String comprobacionsql="select count(*) from prestamo where codusuario="+PKuser+" and codmaterial="+PKproducto+" and tipomaterial='"
					+tipo.getSelectedItem().charAt(0)+"' and fecha_prest_prestamo='"+fecha_prestFormateada+"'";	
			String sql="insert into prestamo (codusuario,codmaterial,tipomaterial,fecha_prest_prestamo,fecha_prest_devolucion) values ("
					+ PKuser + "," + PKproducto + ",'" + tipo.getSelectedItem().charAt(0) + "','" + fecha_prestFormateada + "'," + "NULL" + ")";
			try {
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery(comprobacionsql);
				rset.next();
				//System.out.println(rset.getInt(1));
				//System.out.println(comprobacionsql);
				if (rset.getInt(1)>0) {
					JOptionPane.showMessageDialog(null, "El usuario "+txtUsuario.getText()+" ya tiene este producto prestado y no lo ha devuelto", "YA EXISTE UN PRÉSTAMO", JOptionPane.INFORMATION_MESSAGE);
					restart();
					return;
				}
				stmt.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "Se ha prestado con éxito el producto '"+tipo.getSelectedItem()+"' a "+txtUsuario.getText(), "PRÉSTAMO REALIZADO", JOptionPane.INFORMATION_MESSAGE);
				stmt.close();
				rset.close();
				restart();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			return;
		}
		if (e.getSource().equals(btnActualizar)) {
			// Formateo de fecha_devol //
			Calendar fecha_devol=Calendar.getInstance();
			try {
				fecha_devol.setTime(this.fecha_devol.getDate());
			} catch (NullPointerException e2) {
				JOptionPane.showMessageDialog(null, "Debe seleccionar la fecha de devolución del préstamo");
				return;
			}
			DateFormat formatofecha_devol=new SimpleDateFormat("dd/MM/yyyy");
			String fecha_devolFormateada=formatofecha_devol.format(fecha_devol.getTime());		// fecha_devol final
			
			String sql="update prestamo set fecha_devolucion='"+fecha_devolFormateada+"' where codusuario="+PKuser+" and codmaterial="+PKproducto
					+" and tipomaterial='"+tipo.getSelectedItem().charAt(0)+"' and fecha_prestamo='"+txtFecha_prest.getText()+"'";
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "Se ha devuelto el préstamo con éxito el día "+fecha_devolFormateada);
				stmt.close();
				restart();
				dispose();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {	
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.principal.setVisible(true);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
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
