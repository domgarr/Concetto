package com.example.concetto.utility;

import com.example.concetto.models.InterInterval;

//https://www.supermemo.com/en/archives1990-2015/english/ol/sm2
public class IntervalUtility {
    public static double initialEFactor = 2.5D;


    public static int calculate(InterInterval interInterval){
        if(interInterval.getRepitionCount() == 0){
            return 1;
        }else if(interInterval.getRepitionCount() == 1){
            return 6;
        }else{
            return (int)Math.round(interInterval.getLength() * calculateEFactor(interInterval.getEFactor(), interInterval.getResponseRating()));
        }
    }

    public static double calculateEFactor(double prevEFactor, int responseRated){
        return prevEFactor - 0.8D + 0.28D * responseRated - 0.02D * Math.pow(responseRated, 2D);
    }
}
