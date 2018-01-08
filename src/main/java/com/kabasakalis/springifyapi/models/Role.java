
package com.kabasakalis.springifyapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "role")

public class Role extends BaseEntity {
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<SpringifyUser> springifyUsers;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<SpringifyUser> getSpringifyUsers() {
        return springifyUsers;
    }

    public void setSpringifyUsers(Set<SpringifyUser> springifyUsers) {
        this.springifyUsers = springifyUsers;
    }
}
