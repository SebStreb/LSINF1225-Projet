package groupek.lsinf1225_projet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Seb on 5/05/16.
 */
public class PhotoTable {

    private int ID_user;
    private String nom;
    private Bitmap image;

    public PhotoTable(int ID_user, String nom, byte[] image) {
        this.ID_user = ID_user;
        this.nom = nom;
        this.image = BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public int getID_user() {
        return ID_user;
    }

    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = BitmapFactory.decodeByteArray(image, 0, image.length);;
    }
}
