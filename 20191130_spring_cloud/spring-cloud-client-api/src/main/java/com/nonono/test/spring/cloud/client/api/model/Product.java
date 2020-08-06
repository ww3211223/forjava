package com.nonono.test.spring.cloud.client.api.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Product implements Serializable {

    private static final long serialVersionUID = 2079340056993910781L;

    private Integer id;
    private String productName;
    private String address;
}
