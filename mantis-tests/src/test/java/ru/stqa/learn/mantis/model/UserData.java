package ru.stqa.learn.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table( name = "mantis_user_table")
public class UserData {

  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  public UserData withId(int id){
    this.id = id;
    return this;
  }

  public UserData withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public int getId() { return id; }

  public String getUsername() { return username; }

  public String getEmail() { return email; }
}