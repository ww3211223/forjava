package com.nonono.test.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Product {

    @Id
    private Integer id;

    private String name;
}
