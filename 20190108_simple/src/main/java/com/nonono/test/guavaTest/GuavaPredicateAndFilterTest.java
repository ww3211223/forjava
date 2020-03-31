package com.nonono.test.guavaTest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * 谓语和过滤器测试
 */
public class GuavaPredicateAndFilterTest {

    public void test() {
        testPredicate();
    }

    private void testPredicate() {
        Customer customer1 = Customer.builder()
                .id(1)
                .name("新客户")
                .build();
        Customer customer2 = Customer.builder()
                .id(15)
                .name("老客户")
                .build();


        List<Customer> list = Lists.newArrayList(customer1, customer2);
        Collection<Customer> loyalCustomers = Collections2.filter(list, new LoyalCustomer());
        System.out.println("testPredicate(): " + JSON.toJSON(loyalCustomers));
    }
}
