package groupek.lsinf1225_projet;

/**
 * Created by Seb on 5/05/16.
 */
public class DispoTable {

    private int ID_from;
    private int ID_to;
    private String jour;

    public DispoTable(int ID_from, int ID_to, String jour) {
        this.ID_from = ID_from;
        this.ID_to = ID_to;
        this.jour = jour;
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

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

}
