package varys;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import ga.dryco.redditjerk.controllers.Link;

public class ThreadLayout extends JPanel
{
	private static final long serialVersionUID = -3468644075209289220L;
	private static JPanel panel;
	private Color textColor = new Color(0, 103, 203);
	private JButton downVote;
	private JButton upVote;
	private JLabel score;
	ThreadLayout(Link submission, ImageIcon icon, String _time) 
	{
		this.setBackground(Color.white);
        upVote = new JButton();
        downVote = new JButton();
        
        JLabel picture = new JLabel();
        JLabel author = new JLabel();
        JLabel text = new JLabel();
        JTextArea time = new JTextArea();
        JTextArea title = new JTextArea();
        JPanel voteContainer = new JPanel();
        score = new JLabel();
        
        ImageIcon upArrow = new ImageIcon(
                getClass().getClassLoader().getResource("resources/up-arrow.png"));
        ImageIcon downArrow = new ImageIcon(
                getClass().getClassLoader().getResource("resources/angle-arrow-down.png"));
        
		String _title = submission.getTitle().trim();
		String _author = submission.getAuthor().trim();
		String _text = submission.getSelftext().trim();
		long _score = submission.getScore();
		
		if (_text.length() > 20)
			_text = _text.substring(0, 20) + "...";

        picture.setIcon(icon);

        author.setText(_author);
        author.setOpaque(true);

        text.setText(_text);
        text.setOpaque(false);
        text.setFont(new Font("Tahoma", Font.BOLD, 9));     

        time.setText(_time);
        time.setEditable(false);
        time.setOpaque(false);
        time.setFont(new Font("Tahoma", Font.BOLD, 8));
        time.setForeground(textColor);
        
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        title.setEditable(false);
        title.setOpaque(false);
        title.setText(_title);
        title.setFont(new Font("Tahoma", 1, 13));
        title.setForeground(textColor);
        
        panel = new JPanel(new GridBagLayout());
        panel.add(picture, GridbagConstraintsSetup.getConstraints(1, 0, 0, 0, 3, 1, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));
        panel.add(author, GridbagConstraintsSetup.getConstraints(2, 0, 0, 0, 1, 5, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));
        panel.add(text, GridbagConstraintsSetup.getConstraints(2, 2, 0, 0, 1, 5, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));
        panel.add(time, GridbagConstraintsSetup.getConstraints(1, 0, 0, 0, 1, 1, GridBagConstraints.BOTH, new Insets(0, 3, 0, 3)));
        panel.add(title, GridbagConstraintsSetup.getConstraints(2, 1, 0, 0, 1, 5, GridBagConstraints.BOTH, new Insets(0, 3, 0, 3)));   
        
        score.setText(String.valueOf(_score));
        score.setHorizontalAlignment(JLabel.LEFT);
        
        upVote.setName("up");
        upVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(upArrow.getImage(), 10, 10)));
        
        downVote.setName("down");
        downVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(downArrow.getImage(), 10, 10)));
        
        performButtonOperation(upVote, submission);   
        performButtonOperation(downVote, submission);
        
        colorScore(submission);
        
        voteContainer.setOpaque(false);
        voteContainer.setLayout(new BoxLayout(voteContainer, BoxLayout.Y_AXIS));
        voteContainer.setAlignmentX(JPanel.LEFT_ALIGNMENT);    
        voteContainer.add(score);
        voteContainer.add(upVote);
        voteContainer.add(downVote);
        panel.add(voteContainer, GridbagConstraintsSetup.getConstraints(0, 1, 0, 0, 1, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));
	}

	public JPanel getPanel() 
	{
		return panel;
	}
	
	private void performButtonOperation(JButton button, Link submission) 
	{
		button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae) 
            {
            	int operation = 0;
            	boolean nullify = false;
            	if(button.getName().equals("down"))
            	{
            		if (submission.getLikes() == null) 
            		{
            			submission.setLikes(false);
            			operation = -1;
            		}
            		else if(submission.getLikes()) 
            		{
            			submission.setLikes(false);
            			operation = -2;
            		}
            		else 
            		{
            			submission.setLikes(null);
            			nullify = true;
            			operation = 1;
            		}
            		submission.fixedVote(operation, nullify);
            	}
            	else if(button.getName().equals("up")) 
            	{
            		if (submission.getLikes() == null) 
            		{
            			submission.setLikes(true);
            			operation = 1;
            		}
            		else if (!submission.getLikes())
            		{
            			submission.setLikes(true);
            			operation = 2;
            		}
            		else 
            		{
            			submission.setLikes(null);
            			nullify = true;
            			operation = -1;
            		}
            		submission.fixedVote(operation, nullify);
            	}
        		colorScore(submission);
        		tallyScore(submission, operation);
            }
        });
	}
	
	private void colorScore(Link submission) 
	{
        if (submission.getLikes() != null) 
        {
        	if (submission.getLikes())
        		score.setForeground(Color.orange);
        	else
        		score.setForeground(Color.blue);
        }
        else 
        {
        	score.setForeground(Color.black);
        }
	}
	
	private void tallyScore(Link submission, int operation) 
	{
		score.setText(String.valueOf(Integer.parseInt(score.getText()) + operation));
	}
}