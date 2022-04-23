package com.spa.springCommuProject.domain.post;


import com.spa.springCommuProject.domain.post.freepost.FreePost;

public interface PostService {

    Long join(Post post);

    Post findOnePost(Long postId);

}
