package varys;

import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingGlassPane extends JPanel
{
	private static final long serialVersionUID = -3728726164203554879L;
	JPanel glass;
	LoadingGlassPane(JFrame container) 
	{
		glass = (JPanel) container.getGlassPane();
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
	    ImageIcon image = new ImageIcon(
                getClass().getClassLoader().getResource("resources/ajax-loader.gif"));
	    glass.add(new JLabel(image));
	}
	void setVisiblity(boolean visible) 
	{
		glass.setVisible(visible);
	}
	boolean getVisibility() 
	{
		return glass.isVisible();
	}
	void revalidateGlass() 
	{
		glass.revalidate();
		glass.repaint();
	}
}