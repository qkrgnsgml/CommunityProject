package com.spa.springCommuProject.domain.post.freepost;

import com.spa.springCommuProject.domain.post.Post;
import com.spa.springCommuProject.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FreePostRepository implements PostRepository {

    private final EntityManager em;

    @Override
    public void save(Post post) {
        em.persist(post);
    }

    @Override
    public Post findOnePost(Long id) {
        return em.find(FreePost.class, id);
    }
}
