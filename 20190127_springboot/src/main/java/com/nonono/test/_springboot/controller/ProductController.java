package com.nonono.test._springboot.controller;

import com.nonono.test._springboot.model.Product;
import com.nonono.test._springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable("id") Integer id) {
        return productService.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Integer addProduct(@RequestBody Product product) {
        Product productResult = productService.addProduct(product);
        if (Objects.isNull(productResult)) {
            return null;
        }
        return productResult.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Boolean deleteProduct(@PathVariable("id") Integer id) {
        return productService.delete(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Map<Integer, Product> getAll() {
        return productService.getProductMap();
    }
}
