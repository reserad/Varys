package varys;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Settings extends JPanel
{
	private static final long serialVersionUID = 6945632831887621140L;
	private JPasswordField password;
	private JTextField username;
	Settings()
	{
		setLayout(new GridLayout(5, 2));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		JButton save = new JButton("Save");
		
		username = new JTextField();
		username.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		username.setToolTipText("Username");
		password = new JPasswordField();
		password.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		password.setToolTipText("Password");
		
		panel.add(username, getConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH));
		panel.add(password, getConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.BOTH));		
		panel.add(save, getConstraints(0, 1, 2, 1, 1, 2, GridBagConstraints.BOTH));
		add(panel);
		
		String path = System.getProperty("user.dir") + "\\profile.txt";
		save.addMouseListener(new MouseAdapter () 
        {
            @Override
            public void mouseClicked(MouseEvent me) 
            {
            	Profile profile = new Profile(username.getText(), new String(password.getPassword()));
            	profile.write(path);
            	JOptionPane.showConfirmDialog(null, "Local credentials updated.", "Success!",
            	        JOptionPane.DEFAULT_OPTION);
            }
        });
	}
	private GridBagConstraints getConstraints(int gridx, int gridy, int weightx, int weighty, int gridheight, int gridwidth, int fill) 
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		constraints.gridheight = gridheight;
		constraints.gridwidth = gridwidth;
		constraints.fill = fill;
		return constraints;
	}
	void setData(Profile profile, String path) 
	{
		if (profile.exists(path)) 
		{
			Account account;
			try 
			{
				account = profile.read(path);
				password.setText(account.getPassword());
				username.setText(account.getUsername());
			} 
			catch (NoSuchAlgorithmException e) 
			{
            	JOptionPane.showConfirmDialog(null, "You credentials are corrupt! Please re-enter them in the settings tab.", "Error!",
            	        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            	new File(System.getProperty("user.dir") + "\\profile.txt").delete();
			}
		}
	}
}