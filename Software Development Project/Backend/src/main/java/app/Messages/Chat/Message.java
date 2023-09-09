package app.Messages.Chat;

public class Message {
	
    private String username;
    private String content;
    private String movieId;

    public Message(String movieId, String username, String content) {
        this.movieId = movieId;
    	this.username = username;
        this.content = content;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
