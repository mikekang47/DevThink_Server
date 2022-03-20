package com.devthink.devthink_server.dto;


import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ApiModel("답글이 포함 된 댓글 조회 정보")
public class CommentDetailResponseData {

    private CommentResponseData comment;

    @Builder.Default
    private List<ReplyResponseData> replies = new ArrayList<>();

}
