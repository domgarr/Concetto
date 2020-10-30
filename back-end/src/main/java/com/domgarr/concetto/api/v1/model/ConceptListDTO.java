package com.domgarr.concetto.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor //Generates A constructor for all fields.
public class ConceptListDTO {
    List<ConceptDTO> concepts;
}
