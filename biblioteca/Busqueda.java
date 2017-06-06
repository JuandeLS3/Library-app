package biblioteca;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Toolkit;

public class Busqueda extends JFrameJTable implements WindowListener,ActionListener,KeyListener{

	private JPanel contentPane;
	private JFrameJTable principal;
	private Connection conn;
	private JTextField txtUsuario;
	private JButton btnBuscar;
	private PanelJtable table;
	private String sql;
	private char QuienEs;

	
	public Busqueda(Connection conn, JFrameJTable principal, String sql, char QuienEs) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/biblioteca/images/book.png")));
		this.QuienEs=QuienEs;
		this.conn=conn;
		this.principal=principal;
		this.principal.setEnabled(false);
		setTitle("Búsqueda");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 492, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(10, 33, 328, 23);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		txtUsuario.addKeyListener(this);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(357, 32, 109, 23);
		contentPane.add(btnBuscar);
		btnBuscar.addActionListener(this);
		
		table = new PanelJtable(conn,sql,this);
		table.setBounds(10, 78, 456, 242);
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
		if (nombreColumna.equals("CODUSUARIO")) {
	        this.principal.setUsuario(pk,cadena);
			this.principal.setEnabled(true);
	        this.dispose();
		} else {
			this.principal.setCodigo(pk, cadena);
			this.principal.setEnabled(true);
			this.dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnBuscar)) {
			switch (QuienEs) {
			case 'U':
				sql="select * from usuario where lower(nombre) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(apellido1) like '%"+txtUsuario.getText().toLowerCase()
				+"%' or lower(apellido2) like '%"+txtUsuario.getText().toLowerCase()+"%'";
				table.refreshTabla(sql);
				break;
			case 'L':
				sql="select * from libro where lower(signatura) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(titulo) like '%"+txtUsuario.getText().toLowerCase()
				+"%' or lower(autor) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(materia) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(editorial) like '%"+txtUsuario.getText().toLowerCase()+"%'";
				table.refreshTabla(sql);
				break;
			case 'R':
				sql="select * from revista where lower(signatura) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(nombre) like '%"+txtUsuario.getText().toLowerCase()
				+"%' or lower(materia) like '%"+txtUsuario.getText().toLowerCase()+"%'";
				table.refreshTabla(sql);
				break;
			case 'C':
				sql="select * from cdrom where lower(signatura) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(titulo) like '%"+txtUsuario.getText().toLowerCase()
				+"%' or lower(autor) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(materia) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(editorial) like '%"+txtUsuario.getText().toLowerCase()+"%'";
				table.refreshTabla(sql);
				break;
			case 'A':
				sql="select * from articulo where lower(titulo) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(autor) like '%"+txtUsuario.getText().toLowerCase()
				+"%'";
				table.refreshTabla(sql);
				break;
			default:
				break;
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
		switch (QuienEs) {
		case 'U':
			sql="select * from usuario where lower(nombre) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(apellido1) like '%"+txtUsuario.getText().toLowerCase()
			+"%' or lower(apellido2) like '%"+txtUsuario.getText().toLowerCase()+"%'";
			table.refreshTabla(sql);
			break;
		case 'L':
			sql="select * from libro where lower(signatura) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(titulo) like '%"+txtUsuario.getText().toLowerCase()
			+"%' or lower(autor) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(materia) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(editorial) like '%"+txtUsuario.getText().toLowerCase()+"%'";
			table.refreshTabla(sql);
			break;
		case 'R':
			sql="select * from revista where lower(signatura) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(nombre) like '%"+txtUsuario.getText().toLowerCase()
			+"%' or lower(materia) like '%"+txtUsuario.getText().toLowerCase()+"%'";
			table.refreshTabla(sql);
			break;
		case 'C':
			sql="select * from cdrom where lower(signatura) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(titulo) like '%"+txtUsuario.getText().toLowerCase()
			+"%' or lower(autor) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(materia) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(editorial) like '%"+txtUsuario.getText().toLowerCase()+"%'";
			table.refreshTabla(sql);
			break;
		case 'A':
			sql="select * from articulo where lower(titulo) like '%"+txtUsuario.getText().toLowerCase()+"%' or lower(autor) like '%"+txtUsuario.getText().toLowerCase()
			+"%'";
			table.refreshTabla(sql);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
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
