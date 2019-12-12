package com.nonono.test._springboot.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonDataTime {

    private Integer id;

    private LocalDateTime time;
}
