package varys;

public class Account 
{
	private String username = "";
	private String password = "";
	Account(String username, String password) 
	{
		this.username = username;
		this.password = password;
	}
	String getUsername() 
	{
		return this.username;
	}
	String getPassword() 
	{
		return this.password;
	}
}
