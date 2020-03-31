package com.nonono.test.guavaTest;

import com.google.common.collect.ComparisonChain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private Integer id;

    private String name;

    private String address;

    public int compareTo(Customer other) {
        return ComparisonChain.start()
                .compare(this.id, other.id)
                .compare(this.name, other.name)
                .result();
    }
}
