package com.domgarr.concetto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountPerDate {
    private Date reviewDate;
    private Long count;
}
