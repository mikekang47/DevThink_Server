package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel(value="리뷰 수정 요청 정보")
public class ReviewModificationData {

    @NotNull
    @ApiModelProperty(value = "사용자 식별자", required = true, example = "1")
    private Long userId;

    @ApiModelProperty(value = "제목", required = true, example = "스프링 개념의 바이블!")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "내용", required = true, example = "★ 스프링의 3대 핵심 기술인 IoC/DI, PSA, AOP를 빠르고 효과적으로 배울 수 있는 실전 예제 중심의 설명\n" +
            "\n" +
            "개발 현장에서 매일 만나는 평범한 자바코드를 스프링의 핵심 기술을 적용해서 깔끔하고 스프링다운 코드로 개선해나가는 과정을 상세하게 보여줌으로써 스프링의 핵심 원리와 적용 방법을 이해할 수 있게 해준다.\n" +
            "\n" +
            "★ 자바언어와 JDBC만 알면 누구라도 따라할 수 있는 52단계의 상세한 스프링 애플리케이션 핵심 코드 개발과정\n" +
            "\n" +
            "자바 초보 개발자도 부담없이 따라할 수 있도록 52단계로 세분화된 애플리케이션 핵심코드 개발과정과 52개의 예제 프로젝트를 제공해 복잡한 스프링의 기술을 차근차근 학습해 나갈 수 있게 해준다.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @ApiModelProperty(value = "별점", required = true, example = "4.5")
    @DecimalMin("0") @DecimalMax("5")
    @NotNull(message = "별점을 입력해주세요.")
    private BigDecimal score;

}
