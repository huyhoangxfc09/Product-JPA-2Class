package com.example.product.formatter;

import com.example.product.model.Category;
import com.example.product.service.impl.CategoryServiceImpl;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class CategoryFormatter implements Formatter<Category> {
    private final CategoryServiceImpl categoryService;
    public CategoryFormatter(CategoryServiceImpl categoryService){
        this.categoryService = categoryService;
    }

    @Override
    public Category parse(String text, Locale locale) throws ParseException {
        return categoryService.findById(Long.parseLong(text));
    }

    @Override
    public String print(Category object, Locale locale) {
        return null;
    }
}
