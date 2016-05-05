package groupek.lsinf1225_projet;

/**
 * Created by Seb on 5/05/16.
 */
public class RencontreTable {

    private int ID_user1;
    private int ID_user2;
    private String jour;

    public RencontreTable(int ID_user1, int ID_user2, String jour) {
        this.ID_user1 = ID_user1;
        this.ID_user2 = ID_user2;
        this.jour = jour;
    }

    public int getID_user1() {
        return ID_user1;
    }

    public void setID_user1(int ID_user1) {
        this.ID_user1 = ID_user1;
    }

    public int getID_user2() {
        return ID_user2;
    }

    public void setID_user2(int ID_user2) {
        this.ID_user2 = ID_user2;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

}
