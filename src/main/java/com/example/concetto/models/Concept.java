package com.example.concetto.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class Concept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 255)
    private String explanation;

    private boolean reviewed;
    private boolean simplified;
    private boolean done;
    private Date dateCreated;
    private Date dateLastReviewed;

    public Concept(){
        dateCreated = new Date();
    }
}
