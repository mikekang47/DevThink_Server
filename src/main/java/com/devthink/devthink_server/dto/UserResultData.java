package com.devthink.devthink_server.dto;

import com.devthink.devthink_server.domain.BaseTimeEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class UserResultData extends BaseTimeEntity {
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    @Size(max=8)
    private String nickname;

    @NotBlank
    private String role;

    private List<String> stack;

    private String blogAddr;

    private String gitNickname;

    private Integer point;

    private String status;
}
