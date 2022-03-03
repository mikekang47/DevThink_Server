package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.CategoryData;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "카테고리 고유 번호", example = "1")
    private Long id;

    @ApiModelProperty(notes = "카테고리 이름", example = "example")
    private String name;

    @Builder.Default
    private boolean deleted = false;

    public void update(String name){
        this.name = name;
    }

    public CategoryData toCategoryData() {
        return CategoryData.builder()
                .id(id)
                .name(name)
                .build();
    }

    public void destroy() {
        deleted = true;
    }
}
