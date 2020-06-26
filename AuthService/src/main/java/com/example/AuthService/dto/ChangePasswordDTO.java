package com.example.AuthService.dto;

public class ChangePasswordDTO {
    private String username;
    private String newPassword;
    private String oldPassword;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String username, String newPassword, String oldPassword) {
        this.username = username;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
