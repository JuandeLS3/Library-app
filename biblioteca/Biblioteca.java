package biblioteca;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;

public class Biblioteca extends JFrameJTable implements ActionListener, WindowListener{

	private PanelFondoBib contentPane;
	private JMenuItem mntmArtculos;
	private JMenuItem mntmUsuarios;
	private JMenuItem mntmLibros;
	private JMenuItem mntmCdroms;
	private JMenuItem mntmRevistas;
	private JMenuItem mntmRealizarPrstamo;
	private JMenuItem mntmConsultarPrstamo;
	private JMenuItem mntmListarPrestamosPendientes;
	private JMenuItem mntmListarPrestUsuario;
	private JMenuItem mntmListadoUsuarios;
	private JMenuItem mntmListadoLibros;
	private JMenuItem mntmListadoCdroms;
	private JMenuItem mntmListadoArtculos;
	private JMenuItem mntmListadoRevistas;
	private JMenuItem mntmAcercaDe;
	private Connection conn;
	private JButton btnSalir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Biblioteca frame = new Biblioteca();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Biblioteca() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Biblioteca.class.getResource("/biblioteca/images/book.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Biblioteca 2017");
//		setExtendedState(MAXIMIZED_BOTH);
		setBounds(100, 100, 497, 348);
		setLocationRelativeTo(null);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGestion = new JMenu("Gestion");
		menuBar.add(mnGestion);
		
		mntmUsuarios = new JMenuItem("Usuarios");
		mnGestion.add(mntmUsuarios);
		mntmUsuarios.addActionListener(this);
		
		mntmLibros = new JMenuItem("Libros");
		mnGestion.add(mntmLibros);
		mntmLibros.addActionListener(this);
		
		mntmCdroms = new JMenuItem("CDROMS");
		mnGestion.add(mntmCdroms);
		mntmCdroms.addActionListener(this);
		
		mntmRevistas = new JMenuItem("Revistas");
		mnGestion.add(mntmRevistas);
		mntmRevistas.addActionListener(this);
		
		mntmArtculos = new JMenuItem("Art\u00EDculos");
		mnGestion.add(mntmArtculos);
		mntmArtculos.addActionListener(this);
		
		JMenu mnPrestamos = new JMenu("Prestamos");
		menuBar.add(mnPrestamos);
		
		mntmRealizarPrstamo = new JMenuItem("Realizar pr\u00E9stamo");
		mnPrestamos.add(mntmRealizarPrstamo);
		mntmRealizarPrstamo.addActionListener(this);
		
		mntmConsultarPrstamo = new JMenuItem("Consultar pr\u00E9stamo");
		mnPrestamos.add(mntmConsultarPrstamo);
		mntmConsultarPrstamo.addActionListener(this);
		
		JMenu mnListados = new JMenu("Listados");
		menuBar.add(mnListados);
		
		mntmListarPrestamosPendientes = new JMenuItem("Listar pr\u00E9stamos pendientes de devoluci\u00F3n");
		mnListados.add(mntmListarPrestamosPendientes);
		mntmListarPrestamosPendientes.addActionListener(this);
		
		mntmListarPrestUsuario = new JMenuItem("Listar pr\u00E9stamos por usuario");
		mnListados.add(mntmListarPrestUsuario);
		mntmListarPrestUsuario.addActionListener(this);
		
		mntmListadoUsuarios = new JMenuItem("Listado usuarios");
		mnListados.add(mntmListadoUsuarios);
		mntmListadoUsuarios.addActionListener(this);
		
		mntmListadoLibros = new JMenuItem("Listado libros");
		mnListados.add(mntmListadoLibros);
		mntmListadoLibros.addActionListener(this);
		
		mntmListadoCdroms = new JMenuItem("Listado CDROMS");
		mnListados.add(mntmListadoCdroms);
		mntmListadoCdroms.addActionListener(this);
		
		mntmListadoArtculos = new JMenuItem("Listado art\u00EDculos");
		mnListados.add(mntmListadoArtculos);
		mntmListadoArtculos.addActionListener(this);
		
		mntmListadoRevistas = new JMenuItem("Listado revistas");
		mnListados.add(mntmListadoRevistas);
		mntmListadoRevistas.addActionListener(this);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		mntmAcercaDe = new JMenuItem("Acerca de");
		mnAyuda.add(mntmAcercaDe);
		mntmAcercaDe.addActionListener(this);
		
		contentPane = new PanelFondoBib();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEscoja = new JLabel("> Escoja una opci\u00F3n del men\u00FA <");
		lblEscoja.setBackground(Color.WHITE);
		lblEscoja.setForeground(Color.WHITE);
		lblEscoja.setHorizontalAlignment(SwingConstants.CENTER);
		lblEscoja.setFont(new Font("Impact", Font.PLAIN, 21));
		lblEscoja.setBounds(68, 11, 354, 41);
		contentPane.add(lblEscoja);
		
		btnSalir = new JButton("");
		btnSalir.setIcon(new ImageIcon(Biblioteca.class.getResource("/biblioteca/images/exit.png")));
		btnSalir.setBounds(207, 246, 77, 41);
		contentPane.add(btnSalir);
		btnSalir.addActionListener(this);
		
		addWindowListener(this);
		setResizable(false);
	}
	
	private void conectar(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "java", "java");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al acceder a los drivers de la BD");
			System.exit(0);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Contraseña o usuario incorrectos. Saliendo de la biblioteca...");
			System.exit(0);
		}	
	}
	
	private void desconectar(){
		try {
			this.conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnSalir)) {
			desconectar();
			System.exit(0);
		}
		if (e.getSource().equals(mntmUsuarios)) {
			new GestionUsuarios(conn, this);
			return;
		}
		if (e.getSource().equals(mntmLibros)) {
			new GestionLibros(conn, this);
			return;
		}
		if (e.getSource().equals(mntmCdroms)) {
			new GestionCDROM(conn, this);
			return;
		}
		if (e.getSource().equals(mntmRevistas)) {
			new GestionRevistas(conn, this);
			return;
		}
		if (e.getSource().equals(mntmArtculos)) {
			new GestionArticulos(conn, this);
			return;
		}
		if (e.getSource().equals(mntmAcercaDe)) {
			new AcercaDe(this);
			return;
		}
		if (e.getSource().equals(mntmListadoArtculos)) {
			new GestionArticulos(conn, this).generarListado();
			return;
		}
		if (e.getSource().equals(mntmListadoCdroms)) {
			new GestionCDROM(conn, this).generarListado();
			return;
		}
		if (e.getSource().equals(mntmListadoLibros)) {
			new GestionLibros(conn, this).generarListado();
			return;
		}
		if (e.getSource().equals(mntmListadoRevistas)) {
			new GestionRevistas(conn, this).generarListado();
			return;
		}
		if (e.getSource().equals(mntmListadoUsuarios)) {
			new GestionUsuarios(conn, this).generarListado();
			return;
		}
		if (e.getSource().equals(mntmRealizarPrstamo)) {
			new NuevoPrestamo(conn,this);
			return;
		}
		if (e.getSource().equals(mntmConsultarPrstamo)) {
			String sql="select nombre,u.codusuario,codmaterial,tipomaterial,TO_CHAR(FECHA_PRESTAMO,'DD/MM/YY') as PRESTAMO,TO_CHAR(FECHA_DEVOLUCION,'DD/MM/YY') as DEVOLUCION from usuario u,prestamo p where u.codusuario = p.codusuario and fecha_devolucion is NULL";
			new ConsultarPrestamo(conn, this, sql);
			return;
		}
		if (e.getSource().equals(mntmListarPrestamosPendientes)) {
			String sql="select nombre,u.codusuario,codmaterial,tipomaterial,TO_CHAR(FECHA_PRESTAMO,'DD/MM/YY') as PRESTAMO,TO_CHAR(FECHA_DEVOLUCION,'DD/MM/YY') as DEVOLUCION from usuario u,prestamo p where u.codusuario = p.codusuario and fecha_devolucion is NULL";
			new ConsultarPrestamo(conn, this, sql).generarListado(sql);
			return;
		}
		if (e.getSource().equals(mntmListarPrestUsuario)) {
			String sql="select nombre,u.codusuario,codmaterial,tipomaterial,TO_CHAR(FECHA_PRESTAMO,'DD/MM/YY') as PRESTAMO,TO_CHAR(FECHA_DEVOLUCION,'DD/MM/YY') as DEVOLUCION from usuario u,prestamo p where u.codusuario = p.codusuario";
			new ConsultarPrestamo(conn, this, sql).generarListado(sql);
			return;
		}
	}
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		conectar();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		desconectar();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
	
}
