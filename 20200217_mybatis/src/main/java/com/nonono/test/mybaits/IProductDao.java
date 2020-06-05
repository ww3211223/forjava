package com.nonono.test.mybaits;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IProductDao {

    /**
     * 获取产品
     *
     * @param id
     * @return
     */
    ProductModel getProducts(@Param("id") Integer id);
}
