package com.nonono.test._springboot.service;


import com.google.common.collect.Maps;
import com.nonono.test._springboot.model.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class ProductService {

    private int autoIncrementId = 0;
    private Map<Integer, Product> productMap = Maps.newHashMap();

    @CachePut(value = "product", key = "#product.id")
    public Product addProduct(Product product) {
        if (Objects.isNull(product)) {
            return null;
        }
        System.out.println(String.format("addProduct被调用，productName:%s", product.getName()));
        int id = ++this.autoIncrementId;
        product.setId(id);
        productMap.put(id, product);
        return product;
    }

    @CacheEvict(value = "product")
    public Boolean delete(Integer id) {
        if (Objects.isNull(id) || id.intValue() <= 0) {
            return false;
        }

        System.out.println(String.format("delete被调用，id为：%s", id));
        if (productMap.containsKey(id)) {
            return productMap.remove(id, productMap.get(id));
        }
        return false;
    }

    @Cacheable(value = "product",key = "#id")
    public Product findOne(Integer id) {
        System.out.println(String.format("findOne被调用，id为：%s", id));
        if (productMap.containsKey(id)) {
            return productMap.get(id);
        }

        return null;
    }

    public Map<Integer, Product> getProductMap() {
        return this.productMap;
    }

}
