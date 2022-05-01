package com.spa.springCommuProject.domain.post;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findOnePost(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll(){
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }

//    public List<User> findAll(){
//        return em.createQuery("select u from User u", User.class).getResultList();
//    }
//
//    public List<User> finByNickName(String nickName){
//        return em.createQuery("select u from User u where u.nickName = :nickName",User.class)
//                .setParameter("nickName",nickName)
//                .getResultList();
//    }
//
//    public Optional<User> findByLoginId(String loginId){
//        return findAll().stream().filter(m->m.getLoginId().equals(loginId)).findFirst();
//    }
}
