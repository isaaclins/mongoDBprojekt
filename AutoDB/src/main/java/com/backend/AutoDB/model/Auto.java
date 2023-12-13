package com.backend.AutoDB.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "car" )
public class Auto {
    private String id;
    private String name;
    private String ps;
    private String baujahr;
    private String model;
}
