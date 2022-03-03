package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CategoryService;
import com.devthink.devthink_server.domain.Category;
import com.devthink.devthink_server.dto.CategoryData;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 작성 API
     * [Post] /categories
     * @param categorydata (카테고리 id, 이름)
     * @return categoryData (카테고리 id, 이름)
     */
    @PostMapping
    @ApiOperation(value = "카테고리 저장", notes = "카테고리 정보를 받아 카테고리 작성하기")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryData save(@RequestBody CategoryData categorydata){
        Category category = categoryService.save(categorydata);
        return category.toCategoryData();
    }

    /**
     * 카테고리의 id를 검색하여 카테고리를 가져옵니다.
     * [GET] categories/:id
     * @param id 카테고리의 고유 id값
     * @return id의 카테고리
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "카테고리 검색", notes = "카테고리의 id를 검색하여 게시글을 가져옵니다.")
    @ResponseStatus(HttpStatus.OK)
    public CategoryData findById(@PathVariable Long id){
        Category category = categoryService.getCategory(id);
        return category.toCategoryData();
    }

    /**
     * 카테고리의 id를 검색하여 모든 카테고리를 가져옵니다.
     * [GET] /categories
     * @return List<CategoryData> 카테고리
     */
    @GetMapping
    @ApiOperation(value = "카테고리 검색", notes = "카테고리를 전부 가져옵니다.")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryData> findAll(){
        return categoryService.getAllCategory();
    }

    /**
     * 입력한 카테고리의 식별자 값과 valid한 카테고리의 정보를 받아, 기존의 게시글을 입력한 정보로 변경합니다.
     * [PUT] /categories/:id
     * @param id 카테고리의 식별자
     * @return 기존 게시글의 정보 수정
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "카테고리 변경", notes = "입력한 카테고리의 고유 식별자 값과 카테고리의 정보를 받아, 기존의 카테고리를 변경합니다.")
    @ResponseStatus(HttpStatus.OK)
    public CategoryData update(@PathVariable Long id, @RequestBody @Valid CategoryData categoryData){
        Category category = categoryService.getCategory(id);
        categoryService.update(category, categoryData);
        return category.toCategoryData();
    }

    /**
     * 입력한 카테고리의 식별자 값을 받아 게시글을 삭제합니다.
     * [DELETE] /categories/:id
     * @param id 카테고리의 식별자
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "카테고리 삭제", notes = "입력한 카테고리의 고유 id 값을 받아 카테고리를 삭제합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id){
        Category category = categoryService.getCategory(id);
        categoryService.delete(category);
    }

}
