package com.spa.springCommuProject.domain.post.freepost;

import com.spa.springCommuProject.domain.post.FreePost;
import com.spa.springCommuProject.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FreePostRepository {

    private final EntityManager em;


    public void save(Post post) {
        em.persist(post);
    }


    public Post findOnePost(Long id) {
        return em.find(FreePost.class, id);
    }


}
