package org.example;

public class LoginToProfile {
    private String login;
    private String password;
    public LoginToProfile(String login, String password){
        this.login = login;
        this.password = password;
    }
    public LoginToProfile(){
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
