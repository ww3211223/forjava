package com.nonono.test.redis.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable {

    private static final long serialVersionUID = -9052454080496317025L;
    private Integer id;

    private String name;

    private LocalDateTime createTime;
}
