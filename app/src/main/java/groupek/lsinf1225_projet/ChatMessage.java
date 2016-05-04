package groupek.lsinf1225_projet;

/**
 * Created by Technovibe on 17-04-2015.
 */
public class ChatMessage {
    private boolean isMe;
    private String message;
    private String dateTime;

    public boolean getIsme() {
        return isMe;
    }

    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }
}
