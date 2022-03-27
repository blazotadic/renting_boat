package com.renting_boat.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.ALL) // LAZY
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    @ToString.Exclude // LAZY
    private List<Boat> boats = new ArrayList<>();

    public void addRole(Role role) {
        this.getRoles().add(role);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
    }

    public void removeByRoleId(int roleId)
    {
        Set<Role> roles = this.getRoles(); // dodatan select jer je lazy
        for (Role role : roles)
        {
            if (role.getId().equals(roleId))
            {
                roles.remove(role);
                break;
            }
        }
    }


}
