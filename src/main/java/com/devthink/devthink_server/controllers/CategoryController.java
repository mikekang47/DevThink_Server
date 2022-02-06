package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CategoryService;
import com.devthink.devthink_server.domain.Category;
import com.devthink.devthink_server.dto.CategoryRequestData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 작성 API
     * @param categoryDto
     * @return
     */
    @PostMapping
    @ApiOperation(value = "카테고리 저장",
            notes = "카테고리 정보를 받아 카테고리 작성하기")
    public Category save(@RequestBody CategoryRequestData categoryDto){
        return categoryService.savePost(categoryDto);
    }

    /**
     * 카테고리의 id를 검색하여 게시글을 가져옵니다.
     * @param id 카테고리의 고유 id값
     * @return id의 카테고리
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "카테고리 검색",
            notes = "카테고리의 id를 검색하여 게시글을 가져옵니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Long", value = "카테고리 고유 아이디")})
    public CategoryRequestData findById(@PathVariable Long id){
        Category data = categoryService.getCategory(id);
        return getCategoryData(data);
    }

    /**
     * 입력한 카테고리의 식별자 값과 valid한 카테고리의 정보를 받아, 기존의 게시글을 입력한 정보로 변경합니다.
     * @param id 카테고리의 식별자
     * @param categoryDto id, 이름
     * @return 기존 게시글의 정보 수정
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "카테고리 변경",
            notes = "입력한 카테고리의 고유 식별자 값과 카테고리의 정보를 받아, 기존의 카테고리를 변경합니다.")
    public CategoryRequestData update(@PathVariable Long id, @RequestBody @Valid CategoryRequestData categoryDto){
        Category update = categoryService.update(id, categoryDto);
        return getCategoryData(update);
    }

    /**
     * 입력한 카테고리의 식별자 값을 받아 게시글을 삭제합니다.
     * @param id 카테고리의 식별자
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "카테고리 삭제",
            notes = "입력한 카테고리의 고유 id 값을 받아 카테고리를 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Long", value = "카테고리 고유 아이디")})
    public void deletePost(@PathVariable Long id){
        categoryService.deletePost(id);
    }

    /**
     * 카테고리의 정보를 받아 게시글을 dto 데이터로 변환하여 반환합니다.
     * @param category 카테고리 정보
     * @return 입력된 dto 데이터로 변환된 값
     */
    private CategoryRequestData getCategoryData(Category category){
        if(category == null)
            return null;

        return CategoryRequestData.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
