package varys;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ga.dryco.redditjerk.controllers.Comment;

public class CommentLayout extends JPanel
{
	private static final long serialVersionUID = 3509678682202409828L;
	private static JPanel panel;
	CommentLayout(Comment comment) 
	{
		int _score = comment.getScore();
		String _author = comment.getAuthor();
		String _text = comment.getBody();
		_text = _text.replaceAll("&gt;", " \" ");
		
		panel = new JPanel();
		JPanel buttonContainer = new JPanel();
		buttonContainer.setName("buttonContainer");
        
        ImageIcon upArrow = new ImageIcon(
                getClass().getClassLoader().getResource("resources/up-arrow.png"));
        ImageIcon downArrow = new ImageIcon(
                getClass().getClassLoader().getResource("resources/angle-arrow-down.png"));
        
        JLabel score = new JLabel();
        score.setName("score");
        JLabel author = new JLabel();
        author.setName("author");
        JTextArea text = new JTextArea();

        text.setName("text");
        JButton upVote = new JButton();
        JButton downVote = new JButton();

        panel.setLayout(new GridBagLayout());
        score.setFont(new Font("Tahoma", 0, 9));
        score.setText(String.valueOf(_score));
        panel.add(score, GridbagConstraintsSetup.getConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));
        
        author.setFont(new Font("Tahoma", 0, 9));
        author.setText(_author);
        panel.add(author, GridbagConstraintsSetup.getConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));

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
            	for (String item : UrlParser.extractUrls(textToExtract)) 
            	{
            		System.out.println(item);
            	}
            }
        });
            
        panel.add(text, GridbagConstraintsSetup.getConstraints(1, 1, 2, 1, 1, 2, GridBagConstraints.BOTH, new Insets(0,0,0,0)));
        
        buttonContainer.setLayout(new GridLayout(2, 0));
        upVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(upArrow.getImage(), 10, 10)));
        
        if (comment.getLikes() != null) 
        {
        	upVote.setEnabled(!comment.getLikes());
        	downVote.setEnabled(comment.getLikes());  
        }
        
        upVote.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae) 
            {
            	upVote(comment, upVote, downVote);
            	score.setText(String.valueOf(Integer.parseInt(score.getText()) + 1));
            }
        });
        buttonContainer.add(upVote);
        downVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(downArrow.getImage(), 10, 10)));
        downVote.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae) 
            {
            	System.out.println(comment.getLikes());
            	if (downVote.isEnabled()) 
            	{
	            	downVote(comment, downVote, upVote);
	            	score.setText(String.valueOf(Integer.parseInt(score.getText()) - 1));
            	}
            }
        });
        buttonContainer.add(downVote);
        panel.add(buttonContainer, GridbagConstraintsSetup.getConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));
	}

	JPanel getPanel() 
	{
		return panel;
	}
	
    private static void upVote(Comment comment, JButton upVote, JButton downVote) 
    {
    	comment.upvote(); 
    	upVote.setEnabled(false); 
    	downVote.setEnabled(true);
	}
    private static void downVote(Comment comment, JButton downVote, JButton upVote) 
    {
    	comment.downvote();
    	upVote.setEnabled(true);
    	downVote.setEnabled(false);
	}
}