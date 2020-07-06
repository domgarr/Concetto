package com.example.concetto.utility;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtility {

    public Date addDaysToDate(Date date, int daysToAdd){
        Date dateToUpdate = date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToUpdate);
        cal.add(Calendar.DATE, daysToAdd);
        return cal.getTime();
    }

}
