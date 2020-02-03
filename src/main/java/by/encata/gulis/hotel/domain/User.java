package by.encata.gulis.hotel.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.ZoneId;

@Document(collection = "user")
public class User {

    @Id
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String role;

    private ZoneId userZoneId;

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ZoneId getUserZoneId() {
        return userZoneId;
    }

    public void setUserZoneId(ZoneId userZoneId) {
        this.userZoneId = userZoneId;
    }
}
