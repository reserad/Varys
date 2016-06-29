package varys;

public class TimeFormat 
{
	static String getDisplayTime(long providedTime, long currentTime) 
	{
		if (String.valueOf(providedTime).length() == 9)
			providedTime *= 10;
		
		currentTime /= 1000;
		long timeDiscrepancy = 8 * 3600;
		long difference = currentTime - (providedTime - timeDiscrepancy);
		int diffMinutes = (int) Math.floor(difference / (60));
		int diffHours = diffMinutes / 60;
		int diffDays = diffHours / 24;
		int diffMonths = diffDays / 30;
		
		if (diffMonths > 1)
			return String.valueOf(diffMonths) + " months ago";
		if (diffMonths == 1)
			return String.valueOf(diffMonths) + " month ago";
		if (diffDays > 1)
			return String.valueOf(diffDays) + " days ago";
		if (diffDays == 1)
			return String.valueOf(diffDays) + " day ago";
		if (diffHours > 1)
			return String.valueOf(diffHours) + " hours ago";
		if (diffHours == 1)
			return String.valueOf(diffHours) + " hour ago";
		if (diffMinutes > 1)
			return String.valueOf(diffMinutes) + " minutes ago";
		return "Just now";
	}
}