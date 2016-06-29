package varys;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Profile 
{
	private Account account;
	Profile(String username, String password) 
	{
		account = new Account(username, password);
	}
	public Profile() {}
	@SuppressWarnings("unchecked")
	void write(String path)
	{
		try 
		{
			DataSecurity security = new DataSecurity();
			JSONObject jObj = new JSONObject();
			jObj.put("Username", new String(security.encrypt(account.getUsername())));
			jObj.put("Password", new String(security.encrypt(account.getPassword())));
			
			FileWriter file = new FileWriter(path);
			file.write(jObj.toString());
			file.close();
		} 
		catch (NoSuchAlgorithmException | IOException e) 
		{
        	JOptionPane.showConfirmDialog(null, "There was an error with your entered credentials!", "Error!",
        	        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}
	Account read(String path) throws NoSuchAlgorithmException
	{
		if (exists(System.getProperty("user.dir") + "\\profile.txt")) 
		{
			DataSecurity security = new DataSecurity();
			try 
			{
				JSONParser parser = new JSONParser();
	            Object obj = parser.parse(new FileReader(path));
	            JSONObject jsonObject = (JSONObject) obj;
	            String username = (String) jsonObject.get("Username");
	            String password = (String) jsonObject.get("Password");
				return new Account(
						new String(security.decrypt(username.getBytes())), 
						new String(security.decrypt(password.getBytes())));
			} 
			catch (Exception e) 
			{
            	JOptionPane.showConfirmDialog(null, "You credentials are corrupt! Please re-enter them in the settings tab.", "Error!",
            	        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            	new File(System.getProperty("user.dir") + "\\profile.txt").delete();
			}
		}
		return null;
	}
	
	boolean exists(String path)
	{
		File file = new File(path);
		if (file.exists())
			return true;
		return false;
	}
}