package com.wpkk.stepicJavaServer03_02.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UsersDataSet implements Serializable{
    private static final long serialVersionUID = -822323432423798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true, updatable = false)
    private String name;

    @Column(name = "password", updatable = false)
    private String password;

    public UsersDataSet() {
    }

    public UsersDataSet(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public  String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
