package varys;

import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import ga.dryco.redditjerk.Reddit;
import ga.dryco.redditjerk.controllers.User;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.simple.parser.ParseException;
/**
 *
 * @author areser
 */
public class InitializeLayout extends JPanel
{   
	private static final long serialVersionUID = 2901269502232195914L;
	private JPanel subreddits;
	private static JPanel postsInSubreddit;
	private static JPanel browser;
    private JScrollPane jsp;
	private static JScrollPane jsp2;
	private static JScrollPane jsp3;
	private JFrame frame;
    private Color bgColor = new Color(247,247,247);
    private Color highlightColor = new Color(230,242,250);
    private Color threadBackgroundColor = Color.white;
    private LoadingGlassPane lgp;
    InitializeLayout(JFrame frame, Reddit red, User myUser, LoadingGlassPane lgp) throws MalformedURLException, IOException, ParseException
    {
    	setLayout(new GridBagLayout());
    	setBackground(bgColor);
    	
    	this.lgp = lgp;
    	this.frame = frame;

    	JPanel toolBar = new JPanel();
    	toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
    	toolBar.setBackground(bgColor);
    	toolBar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(120, 120, 120)));
    	toolBar.add(new JLabel("blarrgh"));
    	add(toolBar, GridbagConstraintsSetup.getConstraints(0, 0, 0, 0, 1, 4, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));   
        
        subreddits = new JPanel();
        subreddits.setLayout(new GridLayout());
        subreddits.setBackground(bgColor);
        
        jsp = new JScrollPane(subreddits); 
        jsp.setBorder(null);
        subreddits = new JPanel(new GridLayout(populateSubreddits(PopulateSubredditEntries.getSubreddits(), red, 0), 1));
        add(jsp, GridbagConstraintsSetup.getConstraints(0, 1, 1, 1, 3, 1, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));   
        
        postsInSubreddit = new JPanel();
        postsInSubreddit.setBackground(Color.white);
		jsp2 = new JScrollPane(postsInSubreddit);
		jsp2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(120, 120, 120)));
        add(jsp2, GridbagConstraintsSetup.getConstraints(1, 1, 1, 1, 3, 1, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));   
        
        browser = new JPanel();
        browser.setBackground(Color.white);
        browser.setLayout(new BoxLayout(browser, BoxLayout.Y_AXIS));
        browser.setAlignmentX(RIGHT_ALIGNMENT);
        
		jsp3 = new JScrollPane(browser);
		jsp3.setBorder(null);
		
		add(new JPanel(), GridbagConstraintsSetup.getConstraints(2, 1, 2, 1, 1, 2, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));
		
        add(jsp3, GridbagConstraintsSetup.getConstraints(2, 2, 2, 1, 2, 2, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));   
        
        this.addComponentListener(new ComponentAdapter () 
        {
            @Override
            public void componentResized(ComponentEvent e) 
            {
            	updateScrollPanelWidths();
            }
        });
    }
    
    static void updateScrollPanelWidths() 
    {
        browser.revalidate();
        postsInSubreddit.repaint();
        postsInSubreddit.revalidate();
        
        for (Component c: postsInSubreddit.getComponents()) 
        {
            if (c instanceof JPanel)
            {
            		c.setSize(new Dimension((jsp2.getWidth()), c.getHeight()));
            }
        }
        for (Component c: browser.getComponents()) 
        {
            if (c instanceof JPanel)
            {
            	c.setSize(new Dimension((jsp3.getWidth()), c.getHeight()));
            	for (Component cc: ((JPanel) c).getComponents()) 
            	{
                    if (cc instanceof JTextArea && cc.getName().equals("text"))
                    { 
                    	cc.setSize(new Dimension((jsp3.getWidth() * 5) / 8, c.getHeight()));
                    }
            	}
            }
        }
    }    

       
    private int populateSubreddits(String[] threads, Reddit red, int pageNumber)
    {
    	DefaultMutableTreeNode node = new DefaultMutableTreeNode("Favorites");
    	DefaultMutableTreeNode inbox = new DefaultMutableTreeNode("Inbox");
    	JTree _subreddits = new JTree(node);
    	node.add(inbox);
    	node.add(new DefaultMutableTreeNode("Sent Items"));
    	node.add(new DefaultMutableTreeNode("Deleted Items"));
    	
        jsp.getVerticalScrollBar().setUnitIncrement(16);
        ArrayList<String> subs = new ArrayList<String>(Arrays.asList(threads));
        
        for(final String thread: subs) 
            inbox.add(new DefaultMutableTreeNode(thread));
        
        _subreddits.addMouseListener(new MouseAdapter () 
        {
            @Override
            public void mouseClicked(MouseEvent me) 
            {
            	boolean contains = false;
                try 
                {
                	TreePath tp = _subreddits.getPathForLocation(me.getX(), me.getY());
                	String title = tp.getPathComponent(tp.getPathCount()-1).toString();
                	contains = subs.contains(title);
                	if (contains)
                	{
                    	PopulateSubredditEntries populate = new PopulateSubredditEntries(frame, browser, postsInSubreddit, jsp2, jsp3, red, highlightColor, threadBackgroundColor, lgp);
                    	populate.RefreshSubmissions(populate.GetSubmissions(title, red, pageNumber, "", true), pageNumber);
                	}
                } 
                catch (Exception ex) 
                {
                	if (contains) 
                	{
                    	JOptionPane.showConfirmDialog(null, "Check your internet connection.", "Error!",
                    	        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                	}
                }
            }
        });
        subreddits.add(_subreddits);
        return threads.length;
    }
}