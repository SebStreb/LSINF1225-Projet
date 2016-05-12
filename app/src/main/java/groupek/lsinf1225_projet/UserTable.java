package groupek.lsinf1225_projet;

/**
 * Created by Seb on 5/05/16.
 */
public class UserTable {

    private int id;
    private String login;
    private String pass;
    private String prenom;
    private String nom;
    private String genre;
    private String age;
    private String cheveux;
    private String yeux;
    private String rue;
    private int codePost;
    private String localite;
    private String pays;
    private String telephone;
    private String inclinaison;
    private String facebook;
    private String langue;
    private boolean cacherNom;
    private boolean cacherAdresse;
    private boolean cacherTelephone;
    private boolean cacherFacebook;

    public UserTable(int id, String login, String pass, String nom, String prenom, String genre, String age, String cheveux, String yeux, String rue, int codePost, String localite, String pays, String telephone, String inclinaison, String facebook, String langue, boolean cacherNom, boolean cacherAdresse, boolean cacherTelephone, boolean cacherFacebook) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.prenom = prenom;
        this.nom = nom;
        this.genre = genre;
        this.age = age;
        this.cheveux = cheveux;
        this.yeux = yeux;
        this.rue = rue;
        this.codePost = codePost;
        this.localite = localite;
        this.pays = pays;
        this.telephone = telephone;
        this.inclinaison = inclinaison;
        this.facebook = facebook;
        this.langue = langue;
        this.cacherNom = cacherNom;
        this.cacherAdresse = cacherAdresse;
        this.cacherTelephone = cacherTelephone;
        this.cacherFacebook = cacherFacebook;
    }

    public UserTable(String login, String pass){
        this.id = 0;
        this.login = login;
        this.pass = pass;
        this.nom = "vide";
        this.genre = "vide";
        this.age = "1970-01-01";
        this.cheveux = "vide";
        this.yeux = "vide";
        this.rue = "vide";
        this.codePost = 0;
        this.localite = "vide";
        this.pays = "vide";
        this.telephone = "vide";
        this.inclinaison = "vide";
        this.facebook = "vide";
        this.langue = "vide";
        this.cacherNom = false;
        this.cacherAdresse = false;
        this.cacherTelephone = false;
        this.cacherFacebook = false;
    }

    public UserTable() {

    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCheveux() {
        return cheveux;
    }

    public void setCheveux(String cheveux) {
        this.cheveux = cheveux;
    }

    public String getYeux() {
        return yeux;
    }

    public void setYeux(String yeux) {
        this.yeux = yeux;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getCodePost() {
        return codePost;
    }

    public void setCodePost(int codePost) {
        this.codePost = codePost;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getInclinaison() {
        return inclinaison;
    }

    public void setInclinaison(String inclinaison) {
        this.inclinaison = inclinaison;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public boolean getCacherNom() {
        return cacherNom;
    }

    public void setCacherNom(boolean cacherNom) {
        this.cacherNom = cacherNom;
    }

    public boolean getCacherAdresse() {
        return cacherAdresse;
    }

    public void setCacherAdresse(boolean cacherAdresse) {
        this.cacherAdresse = cacherAdresse;
    }

    public boolean getCacherTelephone() {
        return cacherTelephone;
    }

    public void setCacherTelephone(boolean cacherTelephone) {
        this.cacherTelephone = cacherTelephone;
    }

    public boolean getCacherFacebook() {
        return cacherFacebook;
    }

    public void setCacherFacebook(boolean cacherFacebook) {
        this.cacherFacebook = cacherFacebook;
    }

}
