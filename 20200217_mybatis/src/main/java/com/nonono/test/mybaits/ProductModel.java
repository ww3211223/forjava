package com.nonono.test.mybaits;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductModel {

    private Integer id;

    private String name;

    private String address;

    private BigDecimal price;
}
