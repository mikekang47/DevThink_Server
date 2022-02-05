package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Reply;
import com.devthink.devthink_server.dto.ReplyResponseData;
import com.devthink.devthink_server.infra.ReplyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    /**
     * 모든 Reply를 조회합니다.
     * @return 조회된 모든 Reply
     */
    public List<ReplyResponseData> getReplies() {
        return getReplyResponseDataList(replyRepository.findAll());
    }

    /**
     * entity List를 받아 dto List 데이터로 변환하여 반환합니다.
     * @param replies entity List
     * @return 입력된 dto 데이터로 변환된 list
     */
    private List<ReplyResponseData> getReplyResponseDataList(List<Reply> replies) {
        List<ReplyResponseData> replyResponseData = new ArrayList<>();

        for (Reply reply : replies)
            replyResponseData.add(reply.toReplyResponseData());
        return replyResponseData;
    }

}
