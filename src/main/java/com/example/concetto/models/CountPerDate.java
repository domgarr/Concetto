package com.example.concetto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.boot.model.relational.SimpleAuxiliaryDatabaseObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountPerDate {
    private Date reviewDate;
    private Long count;
}
