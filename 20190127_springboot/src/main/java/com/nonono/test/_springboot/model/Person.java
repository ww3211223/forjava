package com.nonono.test._springboot.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;
    private Integer age;
}
