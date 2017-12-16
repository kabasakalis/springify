package com.kabasakalis.springifyapi.models;

// import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.util.Calendar;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

  private String name;
  private String email;
  private String password;
  private String token;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(unique = true)
    public String getEmail() {
      return email;
    }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
