package groupek.lsinf1225_projet;

/**
 * Created by Seb on 5/05/16.
 */
public class RelationTable {

    private int ID_from;
    private int ID_to;
    private int etatReq;

    public RelationTable(int ID_from, int ID_to) {
        this.ID_from = ID_from;
        this.ID_to = ID_to;
    }

    public RelationTable(int ID_from, int ID_to, int etatReq) {
        this.ID_from = ID_from;
        this.ID_to = ID_to;
        this.etatReq = etatReq;
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

    public int getEtatReq() {
        return etatReq;
    }

    public void setEtatReq(int etatReq) {
        this.etatReq = etatReq;
    }

}
