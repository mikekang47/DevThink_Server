package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Category;
import com.devthink.devthink_server.dto.CategoryDto;
import com.devthink.devthink_server.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 카테고리 작성
     * @param categoryDto
     * @return
     */
    @PostMapping
    public Category save(@RequestBody CategoryDto categoryDto)
    {
        return categoryService.savePost(categoryDto);
    }
}
