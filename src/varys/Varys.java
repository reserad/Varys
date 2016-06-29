package varys;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.alee.laf.WebLookAndFeel;
import ga.dryco.redditjerk.Reddit;
import ga.dryco.redditjerk.RedditApi;
import ga.dryco.redditjerk.controllers.User;
/**
 *
 * @author areser
 */
public class Varys
{
	private static String clientId = "Ko0GIsrmvqHFPQ";
	private static String secret = "kntdzuy1Oknd_jJ7JxXel0K2ueg";
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater ( new Runnable ()
        {
            public void run ()
            {
                // Install WebLaF as application L&F
                WebLookAndFeel.install ();
                try 
                {
                	JFrame f = new JFrame();
                	f.setSize(900, 640);
                	f.setMinimumSize(new Dimension(900, 640));
                	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                	Profile profile = new Profile();
                	String path = System.getProperty("user.dir") + "\\profile.txt";
                	Account account = profile.read(path);
                	User myUser = new User();
                	Reddit red = RedditApi.getRedditInstance("Reddit client Test v1313");
                	if (account != null) 
                		myUser = red.login(account.getUsername(), account.getPassword(), clientId, secret);
        
                	LoadingGlassPane lgp = new LoadingGlassPane(f);
                	f.setGlassPane(lgp.glass);
                    InitializeLayout d = new InitializeLayout(f, red, myUser, lgp);
                    JTabbedPane pane = new JTabbedPane();
                    Settings settings = new Settings();
                    pane.add("<html><body style='color:white;'>HOME</body></html>", d);
                    pane.setBackgroundAt(0, new Color(0, 114, 198));
                    pane.add("SETTINGS", settings);
                    pane.addChangeListener(new ChangeListener() 
                    {
                        public void stateChanged(ChangeEvent e) 
                        {
                            if (pane.getSelectedIndex() == 1) 
                            {
                            	settings.setData(profile, path);
                            }
                        }
                    });
                    
        			f.pack();
        			f.setVisible(true);
        			f.add(pane);
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            }
        });
    }
}