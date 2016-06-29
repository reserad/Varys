/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varys;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import ga.dryco.redditjerk.controllers.Comment;
import ga.dryco.redditjerk.datamodels.T1;
import ga.dryco.redditjerk.datamodels.T1ListingData; 
/**
 *
 * @author areser
 */

public class PopulateCommentEntries 
{
	static class runThread implements Runnable 
	{
		private volatile LoadingGlassPane lgp;
		private volatile List<T1> comments;
	    private volatile JPanel _browser; 
	    private volatile int jsp3Width;
		runThread (LoadingGlassPane lgp, List<T1> comments, JPanel _browser, int jsp3Width) 
		{
			this.lgp = lgp;
			this.comments = comments;
			this._browser = _browser;
			this.jsp3Width = jsp3Width;
		}

		@Override
		public void run() 
		{
			browser = printComments(comments, 0, true, _browser, jsp3Width);
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run() 
				{
					lgp.setVisiblity(false);
					lgp.revalidateGlass();
					lgp.revalidate();
					lgp.repaint();
					browser.revalidate();
					browser.repaint();
				}
			});
		}
	    public JPanel getValue() 
	    {
	        return browser;
	    }
	}
	
	static JPanel browser;
	private static Comment comment;
	static JPanel begin(LoadingGlassPane lgp, List<T1> comments, int deep, boolean outter, JPanel _browser, int jsp3Width) 
	{
		lgp.setVisiblity(true);
		lgp.revalidateGlass();
		lgp.revalidate();
		lgp.repaint();
		_browser.revalidate();
		_browser.repaint();
		runThread rt = new runThread(lgp, comments, _browser, jsp3Width);
    	Thread t = new Thread(rt);
    	t.start();
    	try 
    	{
			t.join();
		} 
    	catch (InterruptedException e) 
    	{
        	JOptionPane.showConfirmDialog(null, "Comments failed to load.", "Error!",
        	        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
    	return rt.getValue();
	}
    static JPanel printComments(List<T1> comments, int deep, boolean outter, JPanel browser, int jsp3Width)
    {
    	for (int a = 0; a < comments.size(); a++) 
    	{
        	comment = comments.get(a).getData();
        	CommentLayout commentLayout = null;
    		try {commentLayout = new CommentLayout(comment);} catch(Exception e) 
    		{
    			continue;
    		}        	

        	JPanel test = commentLayout.getPanel();
    		test.setBorder(new EmptyBorder(5, 20 * deep, 5, 5));
    		if (deep % 2 == 1) 
    			test.setBackground(new Color(230,230,230));
    		
    		browser.add(test);
        	browser.add(new JSeparator());
        	if (comment.getReplies() != null) 
        	{
        		T1ListingData replies = comment.getReplies().getData();
        		printComments(replies.getChildren(), deep+1, false, browser, jsp3Width);        		
        	}
    	}
    	return browser;
    }
}