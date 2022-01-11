package com.devthink.devthink_server.service;

import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.infra.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void savePost(PostDto postDto){
        postRepository.save(postDto.toEntity());
    }
}
