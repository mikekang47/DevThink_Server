package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@ApiModel("사용자 가입 완료 정보")
public class UserResultData {
    private Long id;

    @ApiModelProperty(value = "사용자 이메일", example = "test@email.com")
    private String email;

    @ApiModelProperty(value = "사용자 이름", example = "김싱크")
    private String name;

    @ApiModelProperty(value = "사용자 닉네임", example = "싱크싱크")
    private String nickname;

    @ApiModelProperty(value = "사용자 전화번호", example = "010-1234-1234")
    private String phoneNum;

    @ApiModelProperty(value = "사용자 수준", example = "주니어")
    private String role;

    @ApiModelProperty(value = "사용자가 사용하는 기술", example = "C++, Java, Spring")
    private List<String> stack;

    @ApiModelProperty(value = "사용자의 기술 블로그 주소", example = "mirrorofcode.tistory.com")
    private String blogAddr;

    @ApiModelProperty(value = "사용자의 github 닉네임", example = "mikekang47")
    private String gitNickname;

    @ApiModelProperty(value = "사용자의 점수", example = "128")
    private Integer point;

    @ApiModelProperty(value = "사용자 삭제 여부", example = "true")
    private boolean deleted;

    @ApiModelProperty(value = "사용자 생성 시각", example = "2022-01-24T16:19:01.359146")
    private LocalDateTime create_at;

    @ApiModelProperty(value = "사용자 수정 시각", example = "2022-01-24T16:19:01.359146")
    private LocalDateTime update_at;
}
