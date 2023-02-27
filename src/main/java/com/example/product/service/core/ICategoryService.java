package com.example.product.service.core;

import com.example.product.model.Category;
import org.springframework.data.domain.Page;

public interface ICategoryService extends ICoreService<Category> {
    Page<Category> getAllCategory(int pageNumber, int pageSize);
}
