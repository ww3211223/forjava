package com.nonono.test._springboot.model;

import lombok.*;

/**
 * 泛型类型
 *
 * @param <T>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataResult<T> {

    private Integer status;
    private String memo;
    private T data;

}
