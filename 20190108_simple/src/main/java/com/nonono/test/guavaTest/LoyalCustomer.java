package com.nonono.test.guavaTest;


import com.google.common.base.Predicate;
import org.checkerframework.checker.nullness.qual.Nullable;

public class LoyalCustomer implements Predicate<Customer> {
    @Override
    public boolean apply(@Nullable Customer input) {
        if (input == null) {
            return false;
        }
        return input.getId() > 10;
    }
}
