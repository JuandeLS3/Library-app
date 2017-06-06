package biblioteca;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JFrameJTable extends JFrame {

	private JPanel contentPane;


	public JFrameJTable() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	public void setBusqueda(String pk,String cadena, String nombreColumna){
	}

	public void setUsuario(String pk,String usuario){
	}
	
	public void setCodigo(String pk,String nombretipo){
	}
}
