package com.example.concetto.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
public class Concept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @Size(max = 255)
    private String explanation;

    private boolean reviewed;
    private boolean simplified;

    public Concept(){

    }
}
