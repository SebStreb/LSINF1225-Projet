package groupek.lsinf1225_projet;

/**
 * Created by Seb on 5/05/16.
 */
public class MessageTable {

    private int ID_from;
    private int ID_to;
    private String time;
    private String content;

    public MessageTable(int ID_from, int ID_to, String time) {
        this.ID_from = ID_from;
        this.ID_to = ID_to;
        this.time = time;
    }

    public MessageTable(int ID_from, int ID_to, String time, String content) {
        this.ID_from = ID_from;
        this.ID_to = ID_to;
        this.time = time;
        this.content = content;
    }

    public int getID_from() {
        return ID_from;
    }

    public void setID_from(int ID_from) {
        this.ID_from = ID_from;
    }

    public int getID_to() {
        return ID_to;
    }

    public void setID_to(int ID_to) {
        this.ID_to = ID_to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
