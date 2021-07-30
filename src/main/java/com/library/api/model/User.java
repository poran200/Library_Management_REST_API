package com.library.api.model;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "library_user")
public class User {
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String fistName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String mobileNO;
    @Column(nullable = false)
    private String role;
    private String department;
    private String batch;
    private boolean isActive;
    private boolean isAccountNotLocked;
    @OneToMany(mappedBy = "student",orphanRemoval = true)
    private Set<BorrowBook>  borrowBooks = new HashSet<>();
    public User(Long id, String username, String fistName, String lastName, String email, String password, String mobileNO, String role, String department, String batch, boolean isActive, boolean isAccountNotLocked) {
        this.id = id;
        this.username = username;
        this.fistName = fistName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobileNO = mobileNO;
        this.role = role;
        this.department = department;
        this.batch = batch;
        this.isActive = isActive;
        this.isAccountNotLocked = isAccountNotLocked;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (isActive != user.isActive) return false;
        if (isAccountNotLocked != user.isAccountNotLocked) return false;
        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(fistName, user.fistName)) return false;
        if (!Objects.equals(lastName, user.lastName)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(mobileNO, user.mobileNO)) return false;
        if (!Objects.equals(role, user.role)) return false;
        if (!Objects.equals(department, user.department)) return false;
        return Objects.equals(batch, user.batch);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (fistName != null ? fistName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (mobileNO != null ? mobileNO.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (batch != null ? batch.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (isAccountNotLocked ? 1 : 0);
        return result;
    }
}
