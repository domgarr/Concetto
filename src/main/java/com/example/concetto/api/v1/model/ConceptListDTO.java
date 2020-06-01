package com.example.concetto.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@AllArgsConstructor //Generates A constructor for all fields.
public class ConceptListDTO {
    List<ConceptDTO> concepts;
}
