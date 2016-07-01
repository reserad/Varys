package varys;

public enum Urls 
{
	youtube("http://www.youtube.com/watch?v"),
	youtube2("https://www.youtube.com/watch?v"),
	youtube3("http://www.youtu.be/"),
	youtube4("http://youtu.be/");
	
    private final String type;
    private Urls(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}