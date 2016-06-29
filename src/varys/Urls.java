package varys;

public enum Urls 
{
	youtube("https://www.youtube.com/watch?v");
	
    private final String type;
    private Urls(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}