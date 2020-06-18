package com.example.concetto.api.v1.model;

import lombok.Data;

@Data
public class SubjectDTO {
    private Long id;
    private String name;
    private int count;
    private int reviewCount;
}
