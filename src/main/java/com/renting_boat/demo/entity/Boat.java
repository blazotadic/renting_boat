package com.renting_boat.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "boat")
public class Boat implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String category;

    @Column
    private Double price;

    @Column
    private String brand;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "renting_until")
    private Date rentingUntil;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY) // LAZY
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
