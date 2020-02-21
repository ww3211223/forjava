package com.nonono.test.mongodb;

import com.nonono.test.mongodb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Integer> {

    List<Product> findByName(String name);
}
