package varys;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        JLabel score = new JLabel();
        
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
        panel = new JPanel();
        
        panel.setLayout(new GridBagLayout());

        picture.setIcon(icon);
        panel.add(picture, GridbagConstraintsSetup.getConstraints(1, 0, 0, 0, 3, 1, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));

        author.setText(_author);
        author.setOpaque(true);
        panel.add(author, GridbagConstraintsSetup.getConstraints(2, 0, 0, 0, 1, 5, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));

        text.setText(_text);
        text.setOpaque(false);
        text.setFont(new Font("Tahoma", Font.BOLD, 9));
        title.setForeground(Color.DARK_GRAY);
        panel.add(text, GridbagConstraintsSetup.getConstraints(2, 2, 0, 0, 1, 5, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0)));

        time.setText(_time);
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        time.setEditable(false);
        time.setOpaque(false);
        time.setFont(new Font("Tahoma", Font.BOLD, 8));
        time.setForeground(textColor);
        
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        panel.add(time, GridbagConstraintsSetup.getConstraints(1, 0, 0, 0, 1, 1, GridBagConstraints.BOTH, new Insets(0, 3, 0, 3)));

        title.setEditable(false);
        title.setOpaque(false);
        title.setText(_title);
        title.setFont(new Font("Tahoma", 1, 13));
        title.setForeground(textColor);
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        title.setOpaque(false);
        
        panel.add(title, GridbagConstraintsSetup.getConstraints(2, 1, 0, 0, 1, 5, GridBagConstraints.BOTH, new Insets(0, 3, 0, 3)));
        
        voteContainer.setOpaque(false);
        voteContainer.setLayout(new BoxLayout(voteContainer, BoxLayout.Y_AXIS));
        voteContainer.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        upVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(upArrow.getImage(), 10, 10)));
        upVote.addMouseListener(new MouseAdapter () 
        {
            @Override
            public void mouseClicked(MouseEvent me) 
            {
            	upVote(submission, upVote, downVote);
            }
        });
        
        voteContainer.add(upVote);
        
        score.setText(String.valueOf(_score));
        score.setHorizontalAlignment(JLabel.LEFT);
        voteContainer.add(score);        
        
        if (submission.getLikes() != null) 
        {
        	upVote.setEnabled(!submission.getLikes());
        	downVote.setEnabled(submission.getLikes());  
        }
        
        downVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(downArrow.getImage(), 10, 10)));
        downVote.addMouseListener(new MouseAdapter () 
        {
            @Override
            public void mouseClicked(MouseEvent me) 
            {
            	downVote(submission, upVote, downVote);
            }
        });
        
        voteContainer.add(downVote);

        panel.add(voteContainer, GridbagConstraintsSetup.getConstraints(0, 1, 0, 0, 1, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));
	}

	public JPanel getPanel() 
	{
		return panel;
	}
	
    private static void upVote(Link thread, JButton upVote, JButton downVote) 
    {
    	thread.upvote(); 
    	upVote.setEnabled(false); 
    	downVote.setEnabled(true);
	}
    private static void downVote(Link thread, JButton downVote, JButton upVote) 
    {
    	thread.downvote();
    	upVote.setEnabled(true);
    	downVote.setEnabled(false);
	}
}