package com.kavsoftware.kaveer.gandh.Model;

/**
 * Created by kaveer on 1/6/2018.
 */

public class AccountViewModel extends StatusCodeViewModel {
    private int UserId;
    private String Username;
    private String Email;
    private String Password;
    private boolean IsActive;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

}
