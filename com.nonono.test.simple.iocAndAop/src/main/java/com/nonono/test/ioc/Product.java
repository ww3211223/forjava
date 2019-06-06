package com.nonono.test.ioc;

public class Product {
    private String name;
    private String title;
    private String memo;
    private ProductSource productSource;

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMemo() {
        return this.memo;
    }

    public ProductSource getProductSource() {
        return this.productSource;
    }

}
