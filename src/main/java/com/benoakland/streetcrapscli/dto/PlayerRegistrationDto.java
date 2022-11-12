package com.benoakland.streetcrapscli.dto;

public class PlayerRegistrationDto {
    // Properties
    private String displayName;
    private String hashedPassword;
    private String salt;

    // Constructors


    public PlayerRegistrationDto() {
    }

    public PlayerRegistrationDto(String displayName, String hashedPassword, String salt) {
        this.displayName = displayName;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
