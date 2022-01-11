package com.devthink.devthink_server.service;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.infra.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Long savePost(PostDto postDto){
        return postRepository.save(postDto.toEntity()).getId();
    }

    @Transactional
    public List<PostDto> getPostList(){
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : posts) {
            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .user_id(post.getUser_id())
                    .category_id(post.getCategory_id())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .status(post.getStatus())
                    .build();

            postDtoList.add(postDto);
        }
        return postDtoList;


    }
}
