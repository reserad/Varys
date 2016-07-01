package varys;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

public class ThumbnailParser 
{
	private Urls type;
	private String url = "";
	ThumbnailParser(String url) 
	{
		this.url = url;
		for (Urls _url : Urls.values()) 
		{
			if (url.contains(_url.getType())) 
			{
				type = _url;
			}
		}
	}
	
	ImageIcon getThumbnail() throws MalformedURLException 
	{
		if (type!= null 
				&& (type.equals(Urls.youtube) 
				|| type.equals(Urls.youtube2))) 
		{
			String[] urlPart = url.split("=");
			ImageIcon img = new ImageIcon(new URL("http://img.youtube.com/vi/" + urlPart[1] + "/0.jpg"));
			return new ImageIcon(ImageManipulation.getScaledImage(img.getImage(), 100,100));
		}
		if (type!= null && (type.equals(Urls.youtube3)
				|| type.equals(Urls.youtube4)))
		{
			String[] urlPart = url.split("\\.")[1].split("b")[1].split("e")[1].split("/");
			ImageIcon img = new ImageIcon(new URL("http://img.youtube.com/vi/" + urlPart[1] + "/0.jpg"));
			return new ImageIcon(ImageManipulation.getScaledImage(img.getImage(), 100,100));
		}
		return null;
	}
}