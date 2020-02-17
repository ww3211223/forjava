package com.nonono.test.mybaits;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IProductDao {
    ProductModel getProducts(@Param("id") Integer id);
}
