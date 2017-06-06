package biblioteca;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondoBib extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelFondoBib() {
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Dimension tamanio = getSize();
		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/biblioteca/images/biblioteca.jpg"));
		g.drawImage(imagenFondo.getImage(),0,0,tamanio.width, tamanio.height, null);
		setOpaque(false);
		super.paintComponent(g);	
	}
}
