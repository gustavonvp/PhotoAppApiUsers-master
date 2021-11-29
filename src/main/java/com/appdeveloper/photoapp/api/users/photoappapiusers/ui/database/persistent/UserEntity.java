package com.appdeveloper.photoapp.api.users.photoappapiusers.ui.database.persistent;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -91823980520891892L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column( nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String ecryptedPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEcryptedPassword() {
        return ecryptedPassword;
    }

    public void setEcryptedPassword(String ecryptedPassword) {
        this.ecryptedPassword = ecryptedPassword;
    }
}
