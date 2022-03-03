package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel("사용자 프로필 정보")
public class UserProfileData {

    @ApiModelProperty(value = "사용자 식별자", example = "1")
    private Long id;

    @ApiModelProperty(value = "사용자 닉네임", example = "싱크싱크")
    private String nickname;

    @ApiModelProperty(value = "사용자 프로필 이미지", example = "www.img.com")
    private String imageUrl;

    @ApiModelProperty(value = "사용자 직무", example = "프론트엔드")
    private String role;

    @ApiModelProperty(value = "사용자 삭제 여부", example = "false")
    private Boolean deleted;

}
