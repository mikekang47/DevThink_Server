package com.devthink.devthink_server.service;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.infra.PostRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final Mapper mapper;


    public PostService(PostRepository postRepository, Mapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    public Post savePost(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
        return postRepository.save(post);
    }

    public List<Post> getPostList(){
        return postRepository.findAll();
    }

    public List<Post> list(int page){
        Page<Post> pageList = postRepository.findAll(PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.DESC, "id")));
        return pageList.getContent();
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

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    public List<Post> search(String keyword)
    {
        List<Post> postList = postRepository.findByTitleContaining(keyword);
        return postList;
    }
}
