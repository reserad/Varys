package varys;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DataSecurity
{
	byte[] key = {115, 35, -69, -112, 54, 114, 122, 74, 113, -2, 64, -37, -100, -112, 120, 107};
	private Cipher desCipher;
	private SecretKey myDesKey;
	public DataSecurity() throws NoSuchAlgorithmException 
	{
		myDesKey = new SecretKeySpec(key, 0, key.length, "AES");
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());   
	    try 
	    {
			try 
			{
				desCipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
			} 
			catch (NoSuchProviderException e) 
			{
				e.printStackTrace();
			}
		} 
	    catch (NoSuchAlgorithmException | NoSuchPaddingException e) 
	    {
			e.printStackTrace();
		}
	}
	public byte[] encrypt(String input) 
	{
		byte[] textEncrypted = null;
		try
		{
		    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
		    textEncrypted = desCipher.doFinal(input.getBytes());
		}
		catch(InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
		{
			e.printStackTrace();
		}
		return textEncrypted;
	}
	public byte[] decrypt(byte[] textEncrypted) throws IllegalBlockSizeException, BadPaddingException 
	{
	    try 
	    {
			desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
		} 
	    catch (InvalidKeyException e) 
	    {
			e.printStackTrace();
		}
	    return desCipher.doFinal(textEncrypted);
	}
}