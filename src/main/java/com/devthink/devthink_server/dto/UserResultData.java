package com.devthink.devthink_server.dto;

import com.devthink.devthink_server.domain.BaseTimeEntity;
import com.github.dozermapper.core.Mapping;
import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class UserResultData extends BaseTimeEntity {
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max=8)
    private String nickname;

    @NotBlank
    private String role;

    @Builder.Default
    private List<String> stack = new ArrayList<>();

    @Mapping("bloagAddr")
    private String blogAddr;

    @Mapping("gitNickname")
    private String gitNickname;

    private Integer point;

    private boolean deleted;

    @Mapping("createAt")
    private LocalDateTime createAt;

    @Mapping("updateAt")
    private LocalDateTime updateAt;
}
