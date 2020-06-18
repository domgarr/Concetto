package com.example.concetto.models;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class Subject {

    public Subject(){
        this.reviewCount = 0;
        this.lastUpdate = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max=100)
    @NotNull
    private String name;

    private int count;
    private int reviewCount;
    private Date lastUpdate;
}