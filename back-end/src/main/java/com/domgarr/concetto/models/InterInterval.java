package com.domgarr.concetto.models;

import com.domgarr.concetto.utility.IntervalUtility;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

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
        this.repitionCount = 0;
        //Response rating doesn't matter for the first calculation.
        IntervalUtility.calculateNextInterval(0,this);
        this.eFactor = IntervalUtility.initialEFactor;
    }
}
