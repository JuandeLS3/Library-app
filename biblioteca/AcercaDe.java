package biblioteca;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;

public class AcercaDe extends JFrame implements WindowListener {

	private JPanel contentPane;
	private JFrame principal;

	/**
	 * Create the frame.
	 */
	public AcercaDe(JFrame principal) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AcercaDe.class.getResource("/biblioteca/images/book.png")));
		this.principal=principal;
		principal.setEnabled(false);
		
		setTitle("Acerca de Biblioteca 2017");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 332, 330);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnHola = new JTextPane();
		txtpnHola.setForeground(Color.WHITE);
		txtpnHola.setEditable(false);
		txtpnHola.setBackground(Color.BLACK);
		txtpnHola.setText("Biblioteca 2017 \u00A9 Juan Delgado Salmer\u00F3n");
		txtpnHola.setBounds(10, 11, 311, 20);
		contentPane.add(txtpnHola);
		
		JTextPane txtpnVersinBuild = new JTextPane();
		txtpnVersinBuild.setForeground(Color.WHITE);
		txtpnVersinBuild.setEditable(false);
		txtpnVersinBuild.setText("Versi\u00F3n 1.1.0\r\nBuild 20170516-5000");
		txtpnVersinBuild.setBackground(Color.BLACK);
		txtpnVersinBuild.setBounds(10, 42, 311, 34);
		contentPane.add(txtpnVersinBuild);
		
		JTextPane txtpnCopyrightBiblioteca = new JTextPane();
		txtpnCopyrightBiblioteca.setForeground(Color.WHITE);
		txtpnCopyrightBiblioteca.setEditable(false);
		txtpnCopyrightBiblioteca.setText("Copyright 2017 Biblioteca 2017 creado por Juan Delgado Salmer\u00F3n. Todos los derechos reservados por JunDev.\r\nEsta aplicaci\u00F3n tiene open-source los primeros 24 d\u00EDas de su distribuci\u00F3n, despu\u00E9s de dicha fecha se cobrar\u00E1n 200\u20AC por ella.");
		txtpnCopyrightBiblioteca.setBackground(Color.BLACK);
		txtpnCopyrightBiblioteca.setBounds(10, 98, 311, 109);
		contentPane.add(txtpnCopyrightBiblioteca);
		
		JLabel lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(AcercaDe.class.getResource("/biblioteca/images/jundev.png")));
		lblImage.setBounds(10, 214, 311, 79);
		contentPane.add(lblImage);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 331, 304);
		contentPane.add(panel);
		
		setVisible(true);
		addWindowListener(this);
		setResizable(false);
	}


	@Override
	public void windowOpened(WindowEvent e) {
		
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		this.principal.setEnabled(true);
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
}
