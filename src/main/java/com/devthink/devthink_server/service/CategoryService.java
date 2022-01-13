package com.devthink.devthink_server.service;

import com.devthink.devthink_server.domain.Category;
import com.devthink.devthink_server.dto.CategoryDto;
import com.devthink.devthink_server.infra.CategoryRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    public CategoryService(CategoryRepository categoryRepository, Mapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public Category savePost(CategoryDto categoryDto){
        Category category = mapper.map(categoryDto, Category.class);
        return categoryRepository.save(category);
    }
}
