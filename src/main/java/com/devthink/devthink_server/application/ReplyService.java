package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.Reply;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReplyResponseData;
import com.devthink.devthink_server.errors.CommentNotFoundException;
import com.devthink.devthink_server.errors.ReplyNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.CommentRepository;
import com.devthink.devthink_server.infra.ReplyRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public ReplyService(ReplyRepository replyRepository,
                        CommentRepository commentRepository,
                        UserRepository userRepository) {
        this.replyRepository = replyRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    /**
     * 모든 Reply를 조회합니다.
     * @return 조회된 모든 Reply
     */
    public List<ReplyResponseData> getReplies() {
        return getReplyResponseDataList(replyRepository.findAll());
    }

    /**
     * 특정 Reply를 조회합니다.
     * @param replyId 조회할 대댓글의 식별자
     * @return 조회된 Reply
     */
    public Reply getReply(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException(replyId));
    }

    /**
     * 특정 사용자가 등록한 Reply를 모두 조회합니다.
     * @param userIdx 대댓글을 조회할 사용자의 식별자
     * @return 특정 사용자가 작성한 Reply 리스트
     */
    public List<ReplyResponseData> getUserReplies(Long userIdx) {
        if (!userRepository.existsById(userIdx))
            throw new UserNotFoundException(userIdx);
        List<Reply> userReplies = replyRepository.findByUserId(userIdx);
        if (userReplies.isEmpty())
            throw new ReplyNotFoundException();
        return getReplyResponseDataList(userReplies);
    }

    /**
     * 특정 Comment의 Reply를 조회합니다.
     * @param commentIdx 조회할 대상 댓글의 식별자
     * @return 특정 댓글에 작성된 Reply 리스트
     */
    public List<ReplyResponseData> getCommentReplies(Long commentIdx) {
        if (!commentRepository.existsById(commentIdx))
            throw new CommentNotFoundException(commentIdx);
        List<Reply> commentReplies = replyRepository.findByCommentId(commentIdx);
        if (commentReplies.isEmpty())
            throw new ReplyNotFoundException();
        return getReplyResponseDataList(commentReplies);
    }

    /**
     * 입력된 reply 정보로 Comment에 등록할 새로운 Reply를 생성합니다.
     * @param user Reply를 등록하려고 하는 User
     * @param comment Reply와 연결되는 Comment
     * @param content Reply의 내용
     * @return 생성된 Reply의 결과 정보
     */
    public ReplyResponseData createReply(User user, Comment comment, String content) {
        // replyRepository에 새로운 대댓글을 생성합니다.
        return replyRepository.save(
                Reply.builder()
                        .user(user)
                        .comment(comment)
                        .content(content)
                        .build()
        ).toReplyResponseData();
    }

    /**
     * replyId를 통하여 기존의 Reply를 수정합니다.
     * @param replyId 수정할 Reply의 식별자
     * @param content 수정할 content 내용
     */
    public ReplyResponseData updateReply(Long replyId, String content) {
        Reply reply = getReply(replyId);
        reply.setContent(content);
        return replyRepository.save(reply).toReplyResponseData();
    }

    /**
     * replyId를 통하여 기존의 Reply를 삭제합니다.
     * @param replyId 삭제할 Reply의 식별자
     */
    public void deleteReply(Long replyId) {
        getReply(replyId);
        replyRepository.deleteById(replyId);
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
