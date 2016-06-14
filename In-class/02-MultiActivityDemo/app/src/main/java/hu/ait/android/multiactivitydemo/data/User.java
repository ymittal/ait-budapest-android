package hu.ait.android.multiactivitydemo.data;

import java.io.Serializable;

public class User implements Serializable{
    private String username;
    private String address;

    public User(String username, String address) {
        this.username = username;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
