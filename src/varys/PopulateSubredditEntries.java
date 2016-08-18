package varys;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import ga.dryco.redditjerk.Reddit;
import ga.dryco.redditjerk.Sorting;
import ga.dryco.redditjerk.controllers.Link;
import ga.dryco.redditjerk.controllers.RedditThread;
import ga.dryco.redditjerk.datamodels.T1;

public class PopulateSubredditEntries extends JPanel
{
	private static final long serialVersionUID = 3878310295757572973L;
	private static JPanel browserHeader;
	private JPanel browser;
	private JPanel postsInSubreddit;
	private JScrollPane jsp2;
	private Reddit red;
	private JScrollPane jsp3;
	//private JScrollPane jsp4;
	private Color highlightColor;
	private Color threadBackgroundColor;
	private JFrame frame;
	private LoadingGlassPane lgp;
	
	PopulateSubredditEntries(JFrame frame, JPanel browserHeader, JPanel browser, JPanel postsInSubreddit, JScrollPane jsp2, JScrollPane jsp3, Reddit red, Color highlightColor, Color threadBackgroundColor, LoadingGlassPane lgp) 
	{
		//this.jsp4 = jsp4;
		this.lgp = lgp;
		PopulateSubredditEntries.browserHeader = browserHeader;
		this.browser = browser;
		this.postsInSubreddit = postsInSubreddit;
		this.jsp2 = jsp2;
		this.red = red;
		this.jsp3 = jsp3;
		this.highlightColor = highlightColor;
		this.threadBackgroundColor = threadBackgroundColor;
		this.frame = frame;
	}
	
    static String[] getSubreddits() throws IOException
    {
        return "https://www.reddit.com/r/announcements+Art+AskReddit+askscience+aww+blog+books+creepy+dataisbeautiful+DIY+Documentaries+EarthPorn+explainlikeimfive+food+funny+Futurology+gadgets+gaming+GetMotivated+gifs+history+IAmA+InternetIsBeautiful+Jokes+LifeProTips+listentothis+mildlyinteresting+movies+Music+news+nosleep+nottheonion+OldSchoolCool+personalfinance+philosophy+photoshopbattles+pics+science+Showerthoughts+space+sports+television+tifu+todayilearned+TwoXChromosomes+UpliftingNews+videos+worldnews+WritingPrompts".split("/r/")[1].split("\\+");
    }
	
    void RefreshSubmissions(List<Link> submissions, int pageNumber) throws MalformedURLException
    {
		lgp.setVisiblity(true);
		lgp.revalidateGlass();
		lgp.revalidate();
		lgp.repaint();
		frame.revalidate();
		frame.repaint();
		
    	Thread t = new Thread(new Runnable() 
    	{
			@Override
			public void run() 
			{                
				long systemTime = System.currentTimeMillis();
                
                ImageIcon genericImageTemplate = new ImageIcon(
                        getClass().getClassLoader().getResource("resources/sprite-reddit.ZDiVRxCXXWg.png"));
                postsInSubreddit.setLayout(new BoxLayout(postsInSubreddit, BoxLayout.Y_AXIS));
                postsInSubreddit.setAlignmentX(Component.LEFT_ALIGNMENT);
                jsp2.setSize(jsp2.getWidth(), jsp2.getHeight());
                jsp2.getVerticalScrollBar().setUnitIncrement(16);
                for(final Link submission: submissions) 
                {
                    ImageIcon img = null;
                    if (submission.getThumbnail().length() > 10) 
                    {
                        try 
                        {
							img = new ImageIcon(new URL(submission.getThumbnail()));
						} 
                        catch (MalformedURLException e) 
                        {
                        	JOptionPane.showConfirmDialog(null, "Check your internet connection.", "Error!",
                        	        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						}
                        img = new ImageIcon(ImageManipulation.getScaledImage(img.getImage(), 42, 30));
                    }
                    else 
                    {
                    	img = new ImageIcon(createImage(new FilteredImageSource(genericImageTemplate.getImage().getSource(),
                                new CropImageFilter(0, 435, 70, 50))));
                    	img = new ImageIcon(ImageManipulation.getScaledImage(img.getImage(), 42, 30));
                    }
                    
                    Date d = new Date(Long.parseLong(String.valueOf(submission.getCreated()).replace(".", "").replace("E9", "")));

                    String _time = TimeFormat.getDisplayTime(d.getTime(), systemTime);
                    ThreadLayout threadLayout = new ThreadLayout(submission, img, _time);
                    JPanel thread = threadLayout.getPanel();
                    thread.setBackground(threadBackgroundColor);
                    final ImageIcon _img = img;
                    for (Component component: thread.getComponents()) 
                    {
                    	component.addMouseListener(new MouseAdapter () 
        	            {
        	                @Override
        	                public void mouseClicked(MouseEvent me) 
        	                {
        	                    browser.removeAll();
        	                    //browserHeader.removeAll();
        	                    //jsp4.removeAll();
        	                	clearBrowser(submission, _img, _time);
        	                }
        	                @Override
        	                public void mouseEntered(MouseEvent e) 
        	                {
        	                	thread.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	                	thread.setBackground(highlightColor);
        	                }
        	                @Override
        	                public void mouseExited(MouseEvent e) 
        	                {
        	                	thread.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        	                	thread.setBackground(threadBackgroundColor);
        	                }
        	            });
                    }
                    postsInSubreddit.add(thread);
                    postsInSubreddit.add(new JSeparator());
                }
                JPanel pageNavigation = new JPanel(new GridLayout(1, 2));
                
                JButton next = new JButton("Next");
                setNavButtonFunctionality(next, pageNumber, submissions, red, true);
                JButton previous = new JButton("Previous");
                setNavButtonFunctionality(previous, pageNumber, submissions, red, false);
                
                pageNavigation.add(previous);
                pageNavigation.add(next);

                postsInSubreddit.add(pageNavigation);
                InitializeLayout.updateScrollPanelWidths();
        		javax.swing.SwingUtilities.invokeLater(new Runnable() 
        		{
        		   public void run() 
        		   { 
        		       jsp2.getVerticalScrollBar().setValue(0);
        		   }
        		});
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run() 
					{
						lgp.revalidateGlass();
						lgp.revalidate();
						lgp.repaint();
						lgp.setVisiblity(false);
						frame.revalidate();
						frame.repaint();
					}
				});
			}
    	}, "Refresh");   
    	t.start();
    }
    
    private void clearBrowser(Link submission, ImageIcon genericImageTemplate, String _time) 
    {
        jsp3.setSize(jsp3.getWidth(), jsp3.getHeight());
        jsp3.getVerticalScrollBar().setUnitIncrement(16);
        RedditThread post;
		try
		{
			post = red.getRedditThread("http://www.reddit.com" + submission.getPermalink(), Sorting.HOT);
			try 
			{
				List<T1> comments = post.getComments().getData().getChildren();
				
				browserHeader.setLayout(new GridBagLayout());
				browserHeader = new CommentSummaryLayout(submission, genericImageTemplate, _time).getPanel();
				//browserHeader.setSize(jsp3.getWidth(), (jsp3.getHeight() * 2) / 3);
				//browserHeader.setBounds(0, 0, jsp3.getWidth(), (jsp3.getHeight() * 2) / 3);
				//frame.add(browserHeader, GridbagConstraintsSetup.getConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));
				
				//jsp4.add(browserHeader);
				browser.setLayout(new BoxLayout(browser, BoxLayout.Y_AXIS));
				browser.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
				browser = PopulateCommentEntries.printComments(comments, 0, true, browser, jsp3.getWidth());
			} 
			catch (Exception e) 
			{
				if (browser.getComponentCount() == 0)
            	JOptionPane.showConfirmDialog(null, "This thread is empty.", "Alert!",
            	        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
			
			InitializeLayout.updateScrollPanelWidths();
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
    }
    
    static JPanel getPanel() 
    {
    	if (browserHeader == null)
    		return new JPanel();
		return browserHeader;
    }
    
    private void setNavButtonFunctionality(JButton button, int pageNumber, List<Link> submissions, Reddit red, boolean isNext) 
    {
    	if (pageNumber == 0 && !isNext)
    		button.setEnabled(false);
    	else if (pageNumber > 0 && !isNext)
    		button.setEnabled(true);
    	button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae) 
            {
            	try 
            	{
            		int page = isNext ? pageNumber + 1 : pageNumber - 1;
            		
                    browser.removeAll();
                    browserHeader.removeAll();
                    jsp3.setSize(jsp3.getWidth(), jsp3.getHeight());
                    jsp3.getVerticalScrollBar().setUnitIncrement(16);
                    
					RefreshSubmissions(GetSubmissions(submissions.get(0).getSubreddit(), red, page, submissions.get(submissions.size() - 1).getName(), isNext), page);
					if (pageNumber == 0 && !isNext)
						button.setEnabled(false);
					else if (pageNumber == 1 && !isNext)
						button.setEnabled(true);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
            }
        });
    }
    
    List<Link> GetSubmissions(String subreddit, Reddit red, int pageNumber, String after, boolean next) 
    {
    	if (next)
    		return red.getSubredditByAfter(subreddit, 25, Sorting.HOT, after);
    	else
    		return red.getSubredditByBefore(subreddit, 25, Sorting.HOT, after);
    }
}