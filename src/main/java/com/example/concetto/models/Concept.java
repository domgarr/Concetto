package com.example.concetto.models;

import lombok.Data;

import javax.persistence.*;
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

    @ManyToOne()
    @JoinColumn(name="user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne()
    @JoinColumn(name="subject_id", nullable = false)
    @NotNull
    private Subject subject;

    @OneToOne()
    @JoinColumn(name="inter_interval_id", nullable = false)
    @NotNull
    private InterInterval interInterval;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String explanation;

    private boolean reviewed;
    private boolean simplified;
    private boolean done;
    private Date dateCreated;
    private Date nextReviewDate;

    public Concept() {
        this.dateCreated = new Date();
        this.nextReviewDate = new Date();
    }
}
