package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @Mapping("id")
    private Long id;

    @Mapping("name")
    private String name;

}
