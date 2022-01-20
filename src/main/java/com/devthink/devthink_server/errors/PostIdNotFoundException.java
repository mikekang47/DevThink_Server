package com.devthink.devthink_server.errors;

public class PostIdNotFoundException extends RuntimeException{
    public PostIdNotFoundException(Long id)
    {
        super("게시글을 찾을 수 없습니다. "+id);
    }
}
