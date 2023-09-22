package rmi.server.models;

import java.io.Serializable;

public class Credentials implements Serializable {
    private static final long serialVersionUID = 15723952385729L;

    private final String username;
    private final String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
