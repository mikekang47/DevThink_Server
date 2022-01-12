package com.devthink.devthink_server.service;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.infra.PostRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(PostDto postDto){
        return postRepository.save(postDto.toEntity());

    }

    public List<Post> getPostList(){
       return postRepository.findAll();

    }

    public Optional<Post> getPost(Long id){
        return postRepository.findById(id);
    }

    public Post update(Long id, PostDto postDto){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물이 없습니다. " + id));

        post.update(postDto.getTitle(), postDto.getContent());
        return postRepository.save(post);

    }
}
