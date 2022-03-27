package com.renting_boat.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude   //lombok - iskljucivanje iz stri ga jer jer @JsonIgnore
    private Set<User> users = new HashSet<>();
}
