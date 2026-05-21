package org.partapp.kicktask4.entity;

import java.util.Objects;

public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private Boolean isAdmin;

    public UserEntity() {

    }

    public UserEntity(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = false;
    }

    public UserEntity(Long id, String username, String password, Boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return isAdmin == that.isAdmin &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, isAdmin);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                ", username='" + username + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

}
