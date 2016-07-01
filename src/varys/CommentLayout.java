package varys;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import ga.dryco.redditjerk.controllers.Comment;

public class CommentLayout extends JPanel
{
	private static final long serialVersionUID = 3509678682202409828L;
	private static JPanel panel;
	private JButton upVote;
	private JButton downVote;
	private JLabel score;
	CommentLayout(Comment comment) 
	{
        upVote = new JButton();
        downVote = new JButton();
        score = new JLabel();
        panel = new JPanel();
        JPanel buttonContainer = new JPanel();
        JLabel author = new JLabel();
        JTextArea text = new JTextArea();
        
        ImageIcon upArrow = new ImageIcon(
                getClass().getClassLoader().getResource("resources/up-arrow.png"));
        ImageIcon downArrow = new ImageIcon(
                getClass().getClassLoader().getResource("resources/angle-arrow-down.png"));
        
		int _score = comment.getScore();
		String _author = comment.getAuthor();
		String _text = comment.getBody();
		_text = _text.replaceAll("&gt;", " \" ");		
       
        score.setName("score");
        score.setFont(new Font("Tahoma", 0, 9));
        score.setText(String.valueOf(_score));
        
        author.setName("author");
        author.setFont(new Font("Tahoma", 0, 9));
        author.setText(_author);
        
        panel.setLayout(new GridBagLayout());
        panel.add(score, GridbagConstraintsSetup.getConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));
        panel.add(author, GridbagConstraintsSetup.getConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));

        text.setName("text");
        text.setText(_text);
        text.setOpaque(false);
        text.setEditable(false);
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        final String textToExtract = _text;
        text.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
            	List<String> urls = UrlParser.extractUrls(textToExtract);
            	if (!urls.isEmpty()) 
            	{
	            	UrlDialogLayout panel = new UrlDialogLayout(urls);
	            	JOptionPane.showMessageDialog(
	            			new JFrame("URL found!"), 
	            			panel.getPanel(),
	            			"Comment contained URL(s) found.", 
	            			JOptionPane.QUESTION_MESSAGE);
            	}
            }
        });
            
        panel.add(text, GridbagConstraintsSetup.getConstraints(1, 1, 2, 1, 1, 2, GridBagConstraints.BOTH, new Insets(0,0,0,0)));
        
        buttonContainer.setName("buttonContainer");  
        buttonContainer.setLayout(new GridLayout(2, 0));
        
        upVote.setName("up");
        upVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(upArrow.getImage(), 10, 10)));
        
        downVote.setName("down");
        downVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(downArrow.getImage(), 10, 10)));
        
        performButtonOperation(upVote, comment);
        performButtonOperation(downVote, comment);
        
        colorScore(comment);
        
        buttonContainer.add(upVote);
        buttonContainer.add(downVote);
        panel.add(buttonContainer, GridbagConstraintsSetup.getConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));
	}

	JPanel getPanel() 
	{
		return panel;
	}
	
	private void performButtonOperation(JButton button, Comment comment) 
	{
		button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae) 
            {
            	int operation = 0;
            	boolean nullify = false;
            	if(button.getName().equals("down"))
            	{
            		if (comment.getLikes() == null) 
            		{
            			comment.setLikes(false);
            			operation = -1;
            		}
            		else if(comment.getLikes()) 
            		{
            			comment.setLikes(false);
            			operation = -2;
            		}
            		else 
            		{
            			comment.setLikes(null);
            			nullify = true;
            			operation = 1;
            		}
            		comment.fixedVote(operation, nullify);
            	}
            	else if(button.getName().equals("up")) 
            	{
            		if (comment.getLikes() == null) 
            		{
            			comment.setLikes(true);
            			operation = 1;
            		}
            		else if (!comment.getLikes())
            		{
             			comment.setLikes(true);
            			operation = 2;
            		}
            		else 
            		{
            			comment.setLikes(null);
            			nullify = true;
            			operation = -1;
            		}
            		comment.fixedVote(operation, nullify);
            	}
        		colorScore(comment);
        		tallyScore(comment, operation);
            }
        });
	}
	
	private void colorScore(Comment comment) 
	{
        if (comment.getLikes() != null) 
        {
        	if (comment.getLikes())
        		score.setForeground(Color.orange);
        	else
        		score.setForeground(Color.blue);
        }
        else 
        {
        	score.setForeground(Color.black);
        }
	}
	
	private void tallyScore(Comment comment, int operation) 
	{
		score.setText(String.valueOf(Integer.parseInt(score.getText()) + operation));
	}
}