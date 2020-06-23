package com.example.concetto.models;

import com.example.concetto.utility.IntervalUtility;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class InterInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int repitionCount;

    @NotNull
    private int length;

    @NotNull
    private double eFactor;

    @NotNull
    private int responseRating;

    public InterInterval(){
        this.repitionCount = 0;
        IntervalUtility.calculate(this);
        this.eFactor = IntervalUtility.initialEFactor;
    }
}
