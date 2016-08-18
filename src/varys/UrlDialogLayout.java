package varys;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class UrlDialogLayout extends JPanel
{
	private static final long serialVersionUID = -4884235683837662122L;
	public UrlDialogLayout(List<String> urls) 
	{
		for (String url: urls) 
		{
	        JPanel panel = new JPanel();
	        JHyperlinkLabel hyperlink = new JHyperlinkLabel(url);
	        JLabel previewIcon = new JLabel();
	        
	        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
	        panel.setLayout(new java.awt.GridBagLayout());
	        panel.add(hyperlink, GridbagConstraintsSetup.getConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(0,5,0,5)));
	
	        ThumbnailParser parser = new ThumbnailParser(url);
	        try 
	        {
				previewIcon.setIcon(parser.getThumbnail());
			} 
	        catch (MalformedURLException e) 
	        {
				e.printStackTrace();
			}
	        panel.add(previewIcon, GridbagConstraintsSetup.getConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(0,5,0,5)));
	
	        add(panel);
		}
	}
	public JPanel getPanel()
	{
		return this;
	}
}