package com.example.concetto.utility;

import com.example.concetto.models.InterInterval;

//https://www.supermemo.com/en/archives1990-2015/english/ol/sm2
public class IntervalUtility {
    public static double initialEFactor = 2.5D;


    public static InterInterval calculateNextInterval(int responseRating, InterInterval interInterval){
        int length;

        if(interInterval.getRepitionCount() == 0){
            length = 0;
        } if(interInterval.getRepitionCount() == 1){
            length = 1;
        }else if(interInterval.getRepitionCount() == 2){
            length = 6;
        }else{
            interInterval.setEFactor(calculateEFactor(interInterval.getEFactor(), responseRating));
            length = (int)Math.round(interInterval.getLength() * interInterval.getEFactor());
        }

        interInterval.setLength(length);
        interInterval.setRepitionCount(interInterval.getRepitionCount() + 1);
        interInterval.setResponseRating(responseRating);

        return interInterval;
    }

    public static double calculateEFactor(double prevEFactor, int responseRated){
        double newEFactor = prevEFactor - 0.8D + 0.28D * responseRated - 0.02D * Math.pow(responseRated, 2D);
        if(newEFactor < 1.3D){
            return 1.3D;
        }else{
            return newEFactor;
        }
    }
}
