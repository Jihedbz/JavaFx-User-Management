package controller;

public final class UserSession {

    private static UserSession instance;

    private int id;
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String birthday;
    private String gender;
    private String picture;
    private String phonenumber;
    private int level;
    private String role;

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getBirthday() { return birthday; }
    public String getGender() { return gender; }
    public String getPicture() { return picture; }
    public String getPhonenumber() { return phonenumber; }
    public int getLevel() { return level; }
    public String getRole() { return role; }
    public UserSession(int id,String username, String email, String password, String firstname, String lastname, String birthday, String gender
            , String picture, String phonenumber, int level, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.gender = gender;
        this.picture = picture;
        this.phonenumber = phonenumber;
        this.level = level;
        this.role = role;
    }

    public static UserSession getInstance(int id,String username, String email, String password, String firstname, String lastname, String birthday, String gender
            , String picture, String phonenumber, int level, String role) {
        if(instance == null) {
            instance = new UserSession(id, username, email, password, firstname, lastname, birthday, gender, picture, phonenumber, level, role);
        }
        return instance;
    }
    public static UserSession getInstance() {
        if (instance == null) {
            throw new IllegalStateException("UserSession has not been initialized.");
        }
        return instance;
    }

    public void cleanUserSession() {
        id = 0;
        username = null;
        email = null;
        password = null;
        firstname = null;
        lastname = null;
        birthday = null;
        gender = null;
        picture = null;
        phonenumber = null;
        level = 0;
        role = null;

    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", picture='" + picture + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", level=" + level +
                ", role='" + role + '\'' +
                '}';
    }
}
