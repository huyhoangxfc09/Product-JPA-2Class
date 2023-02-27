package com.example.product.service.core;

import com.example.product.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService extends ICoreService<Product>{
    List<Product> searchByName(String name);
    List<Product> searchByCategory(Long id);
    Page<Product> getAllProduct(int pageNumber, int pageSize);
    Page<Product> getAllProductByName(String name,int pageNumber, int pageSize);

}
