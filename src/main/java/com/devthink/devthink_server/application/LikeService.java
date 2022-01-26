package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Like;
import com.devthink.devthink_server.errors.LikeNotFoundException;
import com.devthink.devthink_server.infra.LikeRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final Mapper mapper;

    public LikeService(LikeRepository likeRepository, Mapper mapper) {
        this.likeRepository = likeRepository;
        this.mapper = mapper;
    }

    public Like getLike(Long id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new LikeNotFoundException(id));
    }

    public Like createLike(Long userId, Long postId) {
        return null;
    }
}
