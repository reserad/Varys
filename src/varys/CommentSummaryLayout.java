package varys;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ga.dryco.redditjerk.controllers.Link;

public class CommentSummaryLayout extends JPanel
{
	private static final long serialVersionUID = 611616240802322873L;
	private static JPanel panel;
	private JButton upVote;
	private JButton downVote;
	private JLabel score;
	
	CommentSummaryLayout(Link submission, ImageIcon icon, String _time) 
	{
		panel = new JPanel();
		
        JPanel buttonContainer = new JPanel();
        upVote = new JButton();
        score = new JLabel();
        downVote = new JButton();
        JLabel postIcon = new JLabel();
        JTextArea title = new JTextArea();
        JTextArea titleInfo = new JTextArea();
        JTextArea titleBody = new JTextArea();
        
        ImageIcon upArrow = new ImageIcon(
                getClass().getClassLoader().getResource("resources/up-arrow.png"));
        ImageIcon downArrow = new ImageIcon(
                getClass().getClassLoader().getResource("resources/angle-arrow-down.png"));

        panel.setLayout(new GridBagLayout());

        buttonContainer.setName("buttonContainer");  
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));

        upVote.setName("upVote");
        upVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(upArrow.getImage(), 10, 10)));
        buttonContainer.add(upVote);

        score.setName("score");
        score.setText(submission.getScore().toString());
        buttonContainer.add(score);

        downVote.setName("downVote");
        downVote.setIcon(new ImageIcon(ImageManipulation.getScaledImage(downArrow.getImage(), 10, 10)));
        buttonContainer.add(downVote);

        panel.add(buttonContainer, GridbagConstraintsSetup.getConstraints(0, 0, 1, 1, 2, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));

        postIcon.setName("postIcon");
        postIcon.setIcon(icon);
        panel.add(postIcon, GridbagConstraintsSetup.getConstraints(1, 0, 1, 1, 2, 1, GridBagConstraints.BOTH, new Insets(0,0,0,0)));

        title.setName("title");
        title.setOpaque(false);
        title.setEditable(false);
        title.setWrapStyleWord(true);
        title.setLineWrap(true);
        title.setText(submission.getTitle());
        panel.add(title, GridbagConstraintsSetup.getConstraints(2, 0, 7, 1, 1, 7, GridBagConstraints.BOTH, new Insets(0,0,0,0)));

        titleInfo.setName("titleInfo");
        titleInfo.setOpaque(false);
        titleInfo.setEditable(false);
        titleInfo.setWrapStyleWord(true);
        titleInfo.setLineWrap(true);
        titleInfo.setText("Submitted " + _time + " by " + submission.getAuthor());
        panel.add(titleInfo, GridbagConstraintsSetup.getConstraints(2, 1, 7, 1, 1, 7, GridBagConstraints.BOTH, new Insets(0,0,0,0)));

        titleBody.setName("titleBody");
        titleBody.setOpaque(false);
        titleBody.setEditable(false);
        titleBody.setWrapStyleWord(true);
        titleBody.setLineWrap(true);
        titleBody.setText(submission.getSelftext());
        panel.add(titleBody, GridbagConstraintsSetup.getConstraints(2, 2, 7, 7, 7, 7, GridBagConstraints.BOTH, new Insets(0,0,0,0)));
	}
	
	JPanel getPanel() 
	{
		return panel;
	}
}