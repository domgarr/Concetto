package com.example.concetto.api.v1.model;

import lombok.Data;

import java.util.Date;

@Data
public class SubjectDTO {
    private Long id;
    private String name;
    private int count;
    private int reviewCount;
    private Date nextReviewDate;
}
