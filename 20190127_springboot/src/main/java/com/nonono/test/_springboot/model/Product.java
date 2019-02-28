package com.nonono.test._springboot.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;

    private String name;

    private BigDecimal price;

    private LocalDateTime createTime;

    private String auth;
}
