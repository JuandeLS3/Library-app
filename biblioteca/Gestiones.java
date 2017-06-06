package biblioteca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Gestiones extends JFrame implements WindowListener,ActionListener{

	protected JPanel contentPane;
	protected JButton btnPrimero;
	protected JButton btnAnterior;
	protected JButton btnSiguiente;
	protected JButton btnUltimo;
	protected JTextField txtEstado;
	protected JButton btnModificar;
	protected JButton btnNuevo;
	protected JButton btnEliminar;
	protected Connection conn;
	protected JFrame principal;
	/**
	 * Create the frame.
	 **/
	public Gestiones() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 359);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnPrimero = new JButton("<<");
		btnPrimero.setBounds(14, 215, 54, 23);
		contentPane.add(btnPrimero);
		btnPrimero.addActionListener(this);
		
		btnAnterior = new JButton("<");
		btnAnterior.setBounds(82, 215, 54, 23);
		contentPane.add(btnAnterior);
		btnAnterior.addActionListener(this);
		
		btnSiguiente = new JButton(">");
		btnSiguiente.setBounds(250, 215, 54, 23);
		contentPane.add(btnSiguiente);
		btnSiguiente.addActionListener(this);
		
		btnUltimo = new JButton(">>");
		btnUltimo.setBounds(318, 215, 54, 23);
		contentPane.add(btnUltimo);
		btnUltimo.addActionListener(this);
		
		txtEstado = new JTextField();
		txtEstado.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstado.setEditable(false);
		txtEstado.setBounds(150, 216, 86, 20);
		contentPane.add(txtEstado);
		txtEstado.setColumns(10);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(30, 273, 89, 23);
		contentPane.add(btnModificar);
		btnModificar.addActionListener(this);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(149, 273, 89, 23);
		contentPane.add(btnNuevo);
		btnNuevo.addActionListener(this);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(268, 273, 89, 23);
		contentPane.add(btnEliminar);
		btnEliminar.addActionListener(this);
		
		setVisible(true);
		addWindowListener(this);
	}
	
	protected void generarListado() {
		try {
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
	}

	@Override
	public void windowOpened(WindowEvent e) {	
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		this.principal.setEnabled(true);
		this.principal.setVisible(true);
		
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
		// TODO Apéndice de método generado automáticamente
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

}
