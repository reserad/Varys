/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varys;
import java.awt.Color;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JSeparator;
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
    static JPanel printComments(List<T1> comments, int deep, boolean outter, JPanel browser, int jsp3Width)
    {
    	for (int a = 0; a < comments.size(); a++) 
    	{
    		Comment comment = comments.get(a).getData();
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