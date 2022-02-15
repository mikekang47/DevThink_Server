package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Reply;
import com.devthink.devthink_server.domain.ReplyHeart;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.errors.HeartAlreadyExistsException;
import com.devthink.devthink_server.errors.ReplyNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.ReplyHeartRepository;
import com.devthink.devthink_server.infra.ReplyRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReplyHeartService {

    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final ReplyHeartRepository replyHeartRepository;

    public ReplyHeartService(UserRepository userRepository, ReplyRepository replyRepository, ReplyHeartRepository replyHeartRepository) {
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
        this.replyHeartRepository = replyHeartRepository;
    }

    public ReplyHeart create(Long replyId, Long userId) {
        User user = findUser(userId);
        Reply reply = findReply(replyId);

        if(replyHeartRepository.existsByReplyIdAndUserId(replyId, userId)) {
            throw new HeartAlreadyExistsException();
        }
        return null;
    }

    private Reply findReply(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException(replyId));
    }

    private User findUser(Long userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
