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

    /**
     * 无论如何都会将方法的返回值放到缓存中
     *
     * @param product
     * @return
     */
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

    /**
     * 将一条或多条缓存删除
     *
     * @param id
     * @return
     */
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

    /**
     * 在方法执行前Spring先查看缓存中是否有数据
     * 有数据，直接返回
     * 没有数据，调用方法并将方法返回值放进缓存
     *
     * @param id
     * @return
     */
    @Cacheable(value = "product", key = "#id")
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
