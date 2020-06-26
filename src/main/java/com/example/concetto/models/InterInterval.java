package com.example.concetto.models;

import com.example.concetto.utility.IntervalUtility;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class InterInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int repitionCount;
    private int length;
    private double eFactor;

    @Range(min=0, max=5)
    private int responseRating;

    public InterInterval(){
        this.repitionCount = 1;
        //Response rating doesn't matter for the first calculation.
        IntervalUtility.calculateNextInterval(0,this);
        this.eFactor = IntervalUtility.initialEFactor;
    }
}
