package com.nonono.test.mongodb;

import com.alibaba.fastjson.JSON;
import com.nonono.test.mongodb.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestJob implements ApplicationRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.test();
        System.out.println("testJob 运行完毕。");
    }

    private void test() {
//        Product product = new Product();
//        product.setId(3);
//        product.setName("加达里");
//        this.mongoTemplate.save(product);

        //repository测试
//        List<Product> products = productRepository.findByName("加达里");
        //简单Query测试
//        Query query = new Query(Criteria.where("name").is("加达里"));

        //复杂Query测试
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("name").regex("加"),
                Criteria.where("id").gt(3)
        );
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "id"));
        Query query = new Query(criteria);
        query.with(sort);

        List<Product> products = mongoTemplate.find(query, Product.class);

        System.out.println("products size:" + products.size());
        System.out.println("products: " + JSON.toJSONString(products));
    }
}
