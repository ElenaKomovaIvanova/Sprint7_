public class Courier {
    private String login;
    private String password;
    private String firstName;
    private int id;

    public Courier(String login, String password, String firstName, int id) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.id = id;
    }
    public Courier (String password, String firstName, int id) {
        this.password = password;
        this.firstName = firstName;
        this.id = id;
    }

    public static Courier noPassword(String login, String firstName,int id) {
        return new Courier(login,firstName,id);
    }
    public Courier(String firstName, int id) {
        this.firstName = firstName;
        this.id = id;
    }




    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }
}
