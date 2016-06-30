package varys;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;

public class JHyperlinkLabel extends JLabel 
{
	private static final long serialVersionUID = 2063137953346999455L;
	private Color underlineColor = null;

	public JHyperlinkLabel(String label) 
	{
		super(label);
		setForeground(Color.BLUE.darker());
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new HyperlinkLabelMouseAdapter());
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		g.setColor(underlineColor == null ? getForeground() : underlineColor);

		Insets insets = getInsets();

	    int left = insets.left;
	    if (getIcon() != null)
	      left += getIcon().getIconWidth() + getIconTextGap();
	
	    g.drawLine(left, getHeight() - 1 - insets.bottom, (int) getPreferredSize().getWidth()
	        - insets.right, getHeight() - 1 - insets.bottom);
	}

	public class HyperlinkLabelMouseAdapter extends MouseAdapter 
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			try 
			{
				openWebpage(new URI(getText()));
			}
			catch (URISyntaxException e1) 
			{
				e1.printStackTrace();
			}
		}
	}
  
	public static void openWebpage(URI uri) 
	{
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) 
	    {
	        try 
	        {
	            desktop.browse(uri);
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
	}

	public Color getUnderlineColor() 
	{
		return underlineColor;
	}

	public void setUnderlineColor(Color underlineColor)
	{
		this.underlineColor = underlineColor;
	}
}