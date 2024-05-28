package org.example;

public class Profile {
    private String login;
    private String password;

    private String name;

    public Profile(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String imya) {
        this.name = imya;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
