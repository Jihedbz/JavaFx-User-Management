package entities;

public class user {
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

//Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() {return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public user() {}
    //Add Constructor
    /*public user(String username, String email, String password, String firstname, String lastname, String birthday, String gender, String picture, String phonenumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.gender = gender;
        this.picture = picture;
        this.phonenumber = phonenumber;
    }*/

    //Add Constructor
    public user(String username, String email, String password, String firstname, String lastname, String birthday, String gender
                , String picture, String phonenumber) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.gender = gender;
        this.picture = picture;
        this.phonenumber = phonenumber;
        this.level = 0;
        this.role = "user";
    }
    //Constructor for the update
    public user(int id,String username, String email, String password, String firstname, String lastname, String birthday, String gender
            , String picture, String phonenumber) {
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
    }
    //Login Constructor
    public user(int id,String username, String email, String password, String firstname, String lastname, String birthday, String gender
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

    @Override
    public String toString() {
        return "user{" +
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
