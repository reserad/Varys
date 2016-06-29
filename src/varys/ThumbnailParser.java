package varys;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

public class ThumbnailParser 
{
	private Urls type;
	private String url;
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
		if (type.equals(Urls.youtube)) 
		{
			url = url.split("https://www.youtube.com/watch?v=")[1];
			return new ImageIcon(new URL("http://img.youtube.com/vi/" + url + "/0.jpg"));
		}
		return null;
	}
}