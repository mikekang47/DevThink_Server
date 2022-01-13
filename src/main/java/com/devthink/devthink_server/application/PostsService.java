package com.devthink.devthink_server.application;


import com.devthink.devthink_server.domain.posts.Posts;
import com.devthink.devthink_server.domain.posts.PostsRepository;
import com.devthink.devthink_server.dto.posts.PostsResponseDto;
import com.devthink.devthink_server.dto.posts.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public List<Posts> list(){
        List<Posts> postList = postsRepository.findAll();
        return postList;
    }
}
