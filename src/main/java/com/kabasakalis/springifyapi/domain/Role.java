
package com.kabasakalis.springifyapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "roles")

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

    public Set<SpringifyUser> getSpringifyusers() {
        return springifyUsers;
    }

    public void setSpringifyusers(Set<SpringifyUser> springifyUsers) {
        this.springifyUsers = springifyUsers;
    }
}
