package com.nonono.test.yaml;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductModel {

    private Integer id;

    private String name;

    private String address;

    private List<CountryModel> countrys;
}
